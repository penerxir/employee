package com.wangpx.controller;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class PayController {
    private final String APP_ID = "2016101900721571";
    // 应用标识
    // 应用私钥
    private final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC9cluy/42Sh5UF2FYQQlC4oEk5RBtdTmPg0YHfzcLZ42jj7fP7h5r59DCKJEz6WGuxCY0dSqLqvtGvEyLrDt01qrPsesAjcWm8w9Q8JIr2dyXADJuVVyu11KpfbOLMI569dbm7TzJTKI9/n1qNQyLpeSkJuZ5oQMk81r/FetUIzzxlC8/hpgbzgdoGUfVhrdNHWtkn5cTv161vTYtPdCI7TnUQUI2C/b/VOQpKF/Ip9gYiHjbG0rc6VByFUJjzgvi6LR8TDWTVH0OuohOQDD0eXjW7MEKIlrmSFSPOvaMHPXC0JE3JCkZjdMo67mE8l52lIZNllPCm1j+tt4FrXGbHAgMBAAECggEAMCxZRw4W2PDjIyZc1CuYzSntFMPN1Y8udKexx9BRwQhDztneVdKoxPCkOyx56pr7X3xXRuCIxvqY5VUPZwhTleb/c53PJ/QZ+Ccv289LCttHXSuXkIUnF0ipvjQ5A8H3BqReA1RjLqQ8wS4rX4p34TivB8sMjeQTiAXvo+7VJzTjNwa1O+aZzPgB1jO46FTrSnl7UDjpRoP1ICa0hYqPLC6V+gRX/ygHxSUwUCReozLQYlHi1wi8RVlIFgIjStAYcDoZ2fTYeu+j69oewEXzD1bvLdX4vYB2eHoxwNFqubHkj3hrYhhFJWj3F4AFFi9O/SXsqvixp/uCGcIFV5TWyQKBgQDn8sKlQ+yVp4IC5vcuAdn03KVeh8tBl5O7mE28rtFAerX9PzjZrRcIb07b0tM402YG8M/Z/PYMIBiW2KdkbPZQAIzWaEmNwB8/YdbisVatRvDCaBAfja4FP5nrHnDXMC9pDE4JN8kyUxP9N6cVG3nMRRszqzapSISEWGDMhBVFiwKBgQDRF1yv3jriKikuY13tHeYILcXvOVjH9U2FO2uSxnCTxApPWGBwa+QPWwYpxonySjxQSLpxmskP9wvNdFeVsCR9F+gstUrUpJUeOQHmQMBLYtVk3383OCb+7yDI11S+xAMqVh0p4GMGDyXCPLqbf9B0Wp50upPpYExiemd627kjNQKBgQCr8sNpUNlqeCthi2diq+ywIYJWecSsc3oJb/bxEYf+jPmtK9kxRaf1kM6dF8WOMulH6+2qZGTiyEhyS1qMkFppJH9D0zvz4aFIk3eBxoXDHvwMoqiRO67uJSAttPtiC06aSYzwOHLm47/F0CQE+cFpNTHdM+fpxuC2W+4mryQ10QKBgC2Wi2Xo7Su8h4gOsD7vwuTLnhlfhoTqbgF7iCPTUl5VYu4T1csNKwdSEFDxccg6+VWD1erJFcd+VLtx/1qqsfn4SW3eBTfBvqV9RnW+bm7BWMeKl0Eh5Ba3ao1/rYF+WTZU2jz0KIL8sH3eIc2fm4KS83N97pv2nBDooUE/QOl1AoGBAN2go6GPnVkeRY/JPk7xrk69EznkqneTRT7TadsvZrz5lok+d5uShtomRTQKwQ63GgUYSmGdUCMIQZsWfAILeIc1uu3BiYIB/BfdDKExfVIS01gwFptK/WDsI6ycrGdT8Q3igXCSg5H7C9oG1Tnv5b5MePvxLk6WqnlLwVXkXs5B";
    // 编码格式
    private final String CHARSET = "UTF-8";
    // 支付宝公钥
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAloWxDI65eeziHu2rocM2+y0mAaYNpG7dnejsiT0Ki5KtBTFlCFcZsqkb0+63Nhw4xfynOpZS5rdXbJ7iFZnDhtAjawIF2yM2iSoXgiu0uIn6I6MdRCC9MobRdfj5lOJWFzImAcrmnzb3VFD7GfR2jQU+yXOCMDJ06QlvvfRaD5HNAMYCly7JuvIboS3bSYu2wq47P89y2HbJThKU5Di5nQ2YPtR2NT+/SCoCtHN1irLMDoqSCTWDrLThQLAw9CybM/Iesjl0rKRjI7hZDCSeTvIvXAi7y/gXVoa5FAX57qfSHbikfeBn2Lqx90NoI1GpMOQATE8ky8zd8XmQvhfT1wIDAQAB";
    // 沙箱路径接口，正是路径应为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    // 参数返回格式
    private final String FORMAT = "JSON";
    // 签名方式
    private final String SIGN_TYPE = "RSA2";
    // 支付宝异步通知路径，付款完毕后会异步调用本项目的方法，必须为公网地址
    private final String NOTIFY_URL = "http://127.0.0.1/notifyURL";
    // 支付宝同步通知路径，也就是当付款完毕后跳转本项目的页面，可以不是公网地址
    private final String RETURN_URL = "http://127.0.0.1/returnURL";

    @RequestMapping("/alipay")
    public void aliPay(HttpServletResponse response,double money) throws Exception {



        //创建客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAY_URL,APP_ID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE
        );
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //生成商户订单号
        String orderId = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount =Double.toString(money);
        //订单名称，必填
        String subject = "测试支付";
        request.setBizContent("{\"orderId\":\""+ orderId +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form="";

        try {
            //
            form = alipayClient.pageExecute(request).getBody();
        } catch (Exception e) {
            e.printStackTrace();

        }

        response.setContentType("text/html;charset=" + CHARSET);
        response.getWriter().write(form);// 直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();


    }

}
