package cn.com.xcsa.gateway;

import cn.com.xcsa.framework.core.service.BaseService;
import cn.com.xcsa.framework.core.service.impl.BaseServiceImpl;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 根据数据库表生成代码工具类.
 */
public final class FastAutoGeneratorTest {

    private static final String URL = "jdbc:mysql://172.16.0.61:3306/xcsa?"
            + "zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8"
            + "&allowMultiQueries=true";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "P@ssw0rd";

    private static final String PACKAGE = "cn.com.xcsa.gateway";

    private static final String AUTHOR = "huyu";

    private static final String PROJECT_NAME = "gateway";

    /**
     * ss.
     */
    private FastAutoGeneratorTest() {

    }


    /**
     * 生成代码.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        File directory = new File(""); // 参数为空
        String outputDir = directory.getCanonicalPath() + "/"
                + PROJECT_NAME + "/src/main/java/";
        String xmlFile = directory.getCanonicalPath() + "/"
                + PROJECT_NAME + "/src/main/resources/";

        String table = scanner("表名");
        String tablePrefix = scanner("表名前缀");
        generator(outputDir, xmlFile, AUTHOR, table, tablePrefix);
    }

    /**
     * 生成代码.
     * @param outputDir
     * @param xmlFile
     * @param author
     * @param table
     * @param tablePrefix
     */
    public static void generator(String outputDir, String xmlFile,
                                 String author, String table, String tablePrefix) {
        FastAutoGenerator fag = FastAutoGenerator.create(URL, USERNAME, PASSWORD);
        FreemarkerTemplateEngine freemarkerTemplateEngine = new FreemarkerTemplateEngine();
        freemarkerTemplateEngine.templateFilePath(xmlFile + "/templates/");
        Map<OutputFile, String> outMap = new HashMap<>();
        outMap.put(OutputFile.xml, xmlFile + "mapper/");
        outMap.put(OutputFile.entity, outputDir + "cn/com/xcsa/gateway/domain/po/");
        fag.globalConfig((scanner, builder) -> builder.author(author))
                .globalConfig((scanner, builder) -> builder.outputDir(outputDir))
                .globalConfig((scanner, builder) -> builder.disableOpenDir())
                .packageConfig((scanner, builder) -> {
                    builder.parent(PACKAGE);
                builder.entity("domain.po");
                }).packageConfig((scanner, builder) -> builder.pathInfo(outMap))
                .strategyConfig((scanner, builder) ->
                        builder.addTablePrefix(tablePrefix).addInclude(table)
                        .controllerBuilder().enableRestStyle()
                        .mapperBuilder().enableBaseColumnList().enableBaseResultMap()
                                .enableFileOverride()//mapper以及xml文件覆盖
                        .entityBuilder().enableActiveRecord()
                        .enableLombok().enableRemoveIsPrefix()
                                .enableFileOverride()//实体覆盖
                        .serviceBuilder().formatServiceFileName("%sService")
                        .superServiceImplClass(BaseServiceImpl.class)
                        .superServiceClass(BaseService.class).build())
                .templateEngine(freemarkerTemplateEngine)
                .execute();
    }


    /**
     * <p> 读取控制台内容</p>.
     * @param tip
     * @return 输入的内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help);
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
