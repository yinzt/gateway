package cn.com.xcsa.gateway.service;


public interface CaptchaService {
    /**
     * 发送邮件验证码.
     * @param receiverEmail
     * @param type
     * @return 返回发送邮件结果.
     */
    Object sendEmail(String receiverEmail, String type);

    /**
     * 发送短信验证码.
     * @param clientIP
     * @param phone
     * @param type
     * @return 发送短信验证码.
     */
    Object sms(String clientIP, String phone, String type);


    /**
     * 根据手机号或ip地址获取redis键.
     * @param value
     * @param type
     * @return 拼接后的redis键.
     */
    public String getKey(String value, String type);
}
