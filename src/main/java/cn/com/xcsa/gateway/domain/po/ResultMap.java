package cn.com.xcsa.gateway.domain.po;

import com.alibaba.fastjson2.JSONObject;

/**
 * <p> 返回map对象</p>.
 *
 * @author huyu
 * @since 2023-09-14
 */
public class ResultMap extends JSONObject {

    /**
     * 构造方法.
     */
    public ResultMap() {
        super();
    }

    /**
     * 根据传入的值返回resultMap.
     * @param value
     * @param resultMap
     * @return 返回新的resultMap对象.
     */
    public static ResultMap getResultMap(String value, ResultMap resultMap) {
        String fixedKey = "msgInfo";

        // 创建新的 ResultMap 对象并拷贝原有对象的键值对
        ResultMap newResultMap = new ResultMap();
        newResultMap.putAll(resultMap);

        newResultMap.put(fixedKey, value); // 在新对象中添加指定的键值对

        return newResultMap;
    }

}
