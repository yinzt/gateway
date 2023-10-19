package cn.com.xcsa.gateway.util;

import cn.com.xcsa.api.exception.ApiRuntimeException;
import cn.com.xcsa.api.util.InfoCode;
import com.google.common.collect.Maps;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * 滑块验证工具类.
 *
 * @description: 滑块验证工具类
 * @author: wuhui
 * @create: 2019/11/15 18:44
 */
public class PictureUtils {

    private static final String SMALL_NAME = "slid_62";

    private static final Logger LOGGER = LoggerFactory.getLogger(PictureUtils.class);
    private static final Integer WIDTH = 3;
    private static final Integer X = 0;
    private static final Integer Y = 0;
    private static final Float VAR_1 = 0.2f;
    private static final Integer FIVE = 5;
    private static final Integer EIGHT = 8;
    private static final Integer SIXTEEN = 16;
    private static final Integer TWENTY_FOUR = 24;
    private static final Integer ONE_HUNDRED_AND_FIFTY = 150;
    private static final Integer TWO_HUNDRED = 200;
    private static final Integer  FOUR_HUNDRED_AND_SIX = 406;

    private static final Long RGB_NUMBER = 16777215L;

    private static final int MY_PARAMETER = 0xff;



    /**
     * 根据模板切图.
     *
     * @param pictureCount
     * @param targetType
     * @return .
     * @throws Exception
     */
    public static Map<String, Object>
    pictureTemplatesCut(int pictureCount, String targetType) throws Exception {
        Map<String, Object> pictureMap = Maps.newHashMap();
        // 文件类型
        if (StringUtils.isEmpty(targetType)) {
            throw new ApiRuntimeException(InfoCode.FILE_NOT_EXIST);
        }

        int targetNo = new Random().nextInt(pictureCount) + 1;

        // copy 滑动小图
        InputStream tempStream = new PictureUtils().getClass().
                getClassLoader().getResourceAsStream("static/templates/" + SMALL_NAME + ".png");

        // copy 模板大图 oss事例
        InputStream targetStream = new PictureUtils().getClass().
                getClassLoader().getResourceAsStream("static/targets/" + targetNo + ".jpg");
        File bigPicTemp = new File("./temp/" + targetNo + ".jpg");
        FileUtils.copyInputStreamToFile(targetStream, bigPicTemp);
        LOGGER.info("随机图片={}", targetNo);

        // 源文件流
        File bigPicTemplate = bigPicTemp;
        // 模板图
        BufferedImage smallPicTemplate = ImageIO.read(tempStream);
        // 随机坐标
        int width = smallPicTemplate.getWidth();
        int height = smallPicTemplate.getHeight();
        Map<String, Integer> axisMap = generateCutoutCoordinates(width, height);
        Integer axisX = axisMap.get("axisX");
        Integer axisY = axisMap.get("axisY");
        // 最终图像
        BufferedImage newImage = new BufferedImage(width, height, smallPicTemplate.getType());
        Graphics2D graphics = newImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        // 获取感兴趣的目标区域
        BufferedImage targetImageNoDeal =
                getTargetArea(axisX, axisY, width, height, bigPicTemplate, targetType);
        // 根据模板图片抠图
        newImage = dealCutPictureByTemplate(targetImageNoDeal, smallPicTemplate, newImage);

        // 设置"锯齿"的属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(newImage, X, Y, null);
        graphics.dispose();

        // 新建流
        ByteArrayOutputStream smallOS = new ByteArrayOutputStream();
        // 利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。
        Thumbnails.of(newImage).scale(1f).outputQuality(VAR_1)
                .outputFormat("png").toOutputStream(smallOS);
        // 源图生成遮罩
        BufferedImage oriImage = ImageIO.read(bigPicTemplate);
        byte[] bigBype = dealOriPictureByTemplate(oriImage, smallPicTemplate, axisX, axisY);
        pictureMap.put("smallPic", smallOS.toByteArray());
        pictureMap.put("bigPic", bigBype);
        pictureMap.put("axisX", axisX);
        pictureMap.put("axisY", axisY);
        return pictureMap;
    }

    /**
     * 抠图后原图生成.
     *
     * @param oriImage
     * @param templateImage
     * @param axisX
     * @param axisY
     * @return .
     * @throws Exception
     */
    private static byte[] dealOriPictureByTemplate(BufferedImage oriImage,
                                                   BufferedImage templateImage,
                                                   int axisX,
                                                   int axisY) throws Exception {
        BufferedImage oriCopyImage = null;
        ByteArrayOutputStream os = null;
        try {
            // 源文件备份图像矩阵 支持alpha通道的rgb图像
            oriCopyImage = new BufferedImage(oriImage.getWidth(), oriImage.getHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            // 源文件图像矩阵
            int[][] oriImageData = getData(oriImage);
            // 模板图像矩阵
            int[][] templateImageData = getData(templateImage);

            //copy 源图做不透明处理
            for (int i = 0; i < oriImageData.length; i++) {
                for (int j = 0; j < oriImageData[0].length; j++) {
                    int rgb = oriImage.getRGB(i, j);
                    int r = (MY_PARAMETER & rgb);
                    int g = (MY_PARAMETER & (rgb >> EIGHT));
                    int b = (MY_PARAMETER & (rgb >> SIXTEEN));
                    //无透明处理
                    oriCopyImage.setRGB(i, j, rgb);
                }
            }
            int width = oriCopyImage.getWidth();
            int height = oriCopyImage.getHeight();
            for (int i = 0; i < templateImageData.length; i++) {
                for (int j = 0; j < templateImageData[0].length - 1; j++) {
                    int rgb = templateImage.getRGB(i, j);
                    //对源文件备份图像(x+i,y+j)坐标点进行透明处理
                    if (rgb != RGB_NUMBER && rgb <= 0 && width > (axisX + i)
                            && height > (axisY + j)) {
                        int rgbOri = oriCopyImage.getRGB(axisX + i, axisY + j);
                        int r = (MY_PARAMETER & rgbOri);
                        int g = (MY_PARAMETER & (rgbOri >> EIGHT));
                        int b = (MY_PARAMETER & (rgbOri >> SIXTEEN));
                        rgbOri = r + (g << EIGHT) + (b << SIXTEEN)
                                + (ONE_HUNDRED_AND_FIFTY << TWENTY_FOUR);
                        oriCopyImage.setRGB(axisX + i, axisY + j, rgbOri);
                    } else {
                        //do nothing
                        System.out.println("代码合规");
                    }
                }
            }
            os = new ByteArrayOutputStream();
            Thumbnails.of(oriCopyImage).scale(1.00f).outputQuality(0.8f)
                    .outputFormat("jpg").toOutputStream(os);
            // 利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流
            //ImageIO.write(ori_copy_image, "png", os);
            // 从流中获取数据数组
            byte[] b = os.toByteArray();
            return b;
        } catch (Exception e) {
            LOGGER.error("抠图后原图生成异常.", e);
        } finally {
            if (null != oriCopyImage) {
                oriCopyImage.flush();
            }
            if (null != os) {
                os.flush();
                os.close();
            }
        }
        return null;
    }


    /**
     * 根据模板图片抠图.
     * @param oriImage
     * @param templateImage
     * @param targetImage
     * @return 抠图.
     * @throws Exception
     */
    private static BufferedImage dealCutPictureByTemplate(BufferedImage oriImage,
                                                          BufferedImage templateImage,
                                                          BufferedImage targetImage)
            throws Exception {
        // 源文件图像矩阵
        int[][] oriImageData = getData(oriImage);
        // 模板图像矩阵
        int[][] templateImageData = getData(templateImage);
        // 模板图像宽度
        for (int i = 0; i < templateImageData.length; i++) {
            // 模板图片高度
            for (int j = 0; j < templateImageData[0].length; j++) {
                // 如果模板图像当前像素点不是白色 copy源文件信息到目标图片中
                int rgb = templateImageData[i][j];
                if (rgb != RGB_NUMBER && rgb <= 0 && i < oriImageData.length
                        && j < oriImageData[i].length) {
                    targetImage.setRGB(i, j, oriImageData[i][j]);
                }
            }
        }
        return targetImage;
    }


    /**
     * 获取目标区域.
     *
     * @param axisX          随机切图坐标x轴位置
     * @param axisY          随机切图坐标y轴位置
     * @param targetWidth    切图后目标宽度
     * @param targetHeight   切图后目标高度
     * @param bigPicTemplate 源文件
     * @param fileType 文件类型
     * @return 目标区.
     * @throws Exception
     */
    private static BufferedImage getTargetArea(int axisX, int axisY,
                                               int targetWidth,
                                               int targetHeight,
                                               File bigPicTemplate,
                                               String fileType) throws Exception {
        ImageInputStream imageInputStream = null;
        InputStream orgInput = null;
        try {
            orgInput = new FileInputStream(bigPicTemplate);
            Iterator<ImageReader> imageReaderList = ImageIO.getImageReadersByFormatName(fileType);
            ImageReader imageReader = imageReaderList.next();
            // 获取图片流
            imageInputStream = ImageIO.createImageInputStream(orgInput);
            // 输入源中的图像将只按顺序读取
            imageReader.setInput(imageInputStream, true);

            ImageReadParam param = imageReader.getDefaultReadParam();
            Rectangle rec = new Rectangle(axisX, axisY, targetWidth, targetHeight);
            param.setSourceRegion(rec);
            LOGGER.info("image={},param={}", imageReader, param);
            BufferedImage targetImage = imageReader.read(0, param);
            return targetImage;
        } catch (IOException e) {
            LOGGER.error("获取目标区域异常.", e);
        } finally {
            if (null != orgInput) {
                orgInput.close();
            }
            if (null != imageInputStream) {
                imageInputStream.flush();
                imageInputStream.close();
            }
        }
        return null;
    }


    /**
     * 生成图像矩阵.
      * @param bimg
     * @return 图像矩阵.
     * @throws Exception
     */
    private static int[][] getData(BufferedImage bimg) throws Exception {
        int[][] data = new int[bimg.getWidth()][bimg.getHeight()];
        for (int i = 0; i < bimg.getWidth(); i++) {
            for (int j = 0; j < bimg.getHeight(); j++) {
                data[i][j] = bimg.getRGB(i, j);
            }
        }
        return data;
    }

    /**
     *  随机生成抠图坐标.
     * @param width
     * @param height
     * @return 坐标.
     */
    private static Map<String, Integer> generateCutoutCoordinates(int width, int height) {
        int axisX = FOUR_HUNDRED_AND_SIX;
        int axisY = FOUR_HUNDRED_AND_SIX;
        int oriwidth = FOUR_HUNDRED_AND_SIX;
        int oriheight = TWO_HUNDRED;
        Random random = new Random();
        int widthDifference = oriwidth - width;
        int heightDifference = oriheight - height;

        if (widthDifference <= 0) {
            axisX = FIVE;
        } else {
            // axisX = random.nextInt(ORI_WIDTH - width) + 5;
            axisX = random.nextInt((oriwidth - width) - width + 1) + width;
        }

        if (heightDifference <= 0) {
            axisY = FIVE;
        } else {
            axisY = random.nextInt(oriheight - height) + FIVE;
        }
        LOGGER.info("图片x:{}-y:{}", axisX, axisY);
        Map<String, Integer> result = Maps.newHashMap();
        result.put("axisX", axisX);
        result.put("axisY", axisY);
        return result;
    }
}
