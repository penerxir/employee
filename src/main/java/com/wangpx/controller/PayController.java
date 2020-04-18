package com.wangpx.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
public class PayController {
    private final String APP_ID = "2016101900721571";
    // 应用标识
    // 应用私钥
    private final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJFGavXnRE9snyYbnFifS26MHqQxJ5kau+ik0IlgUtQqA8g0KMsyugn6f9ceyavGfj+32Z8nPzGgrDmNtYYXv9hJuFGM9wsha5UMpGFUbAJKJRK+z3H/6xWYvhbAhgO9zUFWrFnUtlvIEViD/Dl/zEQ3/di0pYUR4IU6xwTSuL2+dR0nhaVlb8uuxrzmi9RGNpJ0DrVCwrQyobXeqkfhHbLlJZcf9jeQUA18M22mqTT3Aam4irT1aktxiGpzCSBff/tI6FqAdBTVoFcua6DrzLcTKrfCqzbZTRkfmYSatd8kVYuhHUK1acOLs6/K9ULgdac9Zh/9keKH976Fi2xIidAgMBAAECggEAV+62UBiPDQ4iTVFTEdyFFjAI9HVCDKndOcUN1Uwm1fm/QBITRp5IhijkYjgKRthE7IIO+AoHRxKVhRMw4WjnWKEQAjkAGJKk8QtaNcrAL9FQLjG3OG9Vuf5vNJNQbCr6ZTG35DwaeZCKXX2hKZ79jgiepUcM0cp3MXA7VW6rWHMudUVf8iuLsCcaD4CJU+QeR8SW+l2GdWoX1hK5Ly0Sco69zDUgN4XVZyhBaTN+RAoiGiaYNI3R3yvjYFHAsC8/v6EgsPk3adZbDH8XO6DK1URuzId1qpEu+YxCdvYuWSZfyJe1CU0Nm1SCdApkJAyT4Y9m4x+mxAcdodvt1tbVAQKBgQDNqqfTPjHmSQrULakL3GrkKa6ui9vvEYhVAb7Yd3bdm4YciTx8q5cVX+EmCpKcsiVd9u/3+W+BuO9PIFyfqstiyoVo4piO5uSVRIlzSd5Tmp5mWIEBIzb0OLr4dnQ8ZXYt6hJJ5XHgYoBrZnNoJvbsHhi1uV6kyJPOIp/2gsNV2QKBgQCqoKoBFmvexsRxvdy5Ed950RzllduED6Rwpc8b6E0Z2Nwv/4ruQPrZMHHcWR4bY4ZM9ipn8xFWFYsSWYuYWExEWMo030Lc1axOpllT5xp/kB2NO1DjZpGR3knZ5YUeNFPMUbizH/aOxpejIWE7l4IUecQnzfRmJdo/l5STlXS6ZQKBgQCXtxEwUowK3c0AMmKmnkW35Eu49PEcDWntk2kOVCo6VdT7zfzx1r6QBPeY0WnP79aC13u0CKxKHHtkenH5UsvQJ6d8dNOnbwomVrWyd0Zrk/7PbvBTP3/1brf2O6OJPiuMuUQegQSdSuyCzcPKjBvQmG8P9xurIU9vW7l25oVkGQKBgHCDoZc2AcvVppSaboJbltHtYvDB8Q8UgYsGy5vLg4wXxJqiGCskDkZJ+8shuQi2iEkXyr7hOf1aF2ryQWskTqaDJlPXJXAaNpVmfGEndAHKNrJ6+sv5RuS5gFl/UFiqiNeiy/jSivN7YWLhEA0Ly2tVIFRfK1pwe4qTmhX36BFBAoGAIwoavKxnfUGaqrLjRtPqgzJTCoOHI3UV8JsitlJRxLrlGVyz/7Nu6kxnJa3NTJ3BisjNvGqQ1B+rHzqKE+ONkmrJsnOSCAJvKYyl5xn4qFJ9Rvw6aseTYAcRvJFJZbXZq208FZ4tGnDoDwKrff+ch9gATLZuzfUp4rPhGlxJK7c=";
    // 编码格式
    private final String CHARSET = "UTF-8";
    // 支付宝公钥
    private final String ALIPAY_PUBLIC_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJFGavXnRE9snyYbnFifS26MHqQxJ5kau+ik0IlgUtQqA8g0KMsyugn6f9ceyavGfj+32Z8nPzGgrDmNtYYXv9hJuFGM9wsha5UMpGFUbAJKJRK+z3H/6xWYvhbAhgO9zUFWrFnUtlvIEViD/Dl/zEQ3/di0pYUR4IU6xwTSuL2+dR0nhaVlb8uuxrzmi9RGNpJ0DrVCwrQyobXeqkfhHbLlJZcf9jeQUA18M22mqTT3Aam4irT1aktxiGpzCSBff/tI6FqAdBTVoFcua6DrzLcTKrfCqzbZTRkfmYSatd8kVYuhHUK1acOLs6/K9ULgdac9Zh/9keKH976Fi2xIidAgMBAAECggEAV+62UBiPDQ4iTVFTEdyFFjAI9HVCDKndOcUN1Uwm1fm/QBITRp5IhijkYjgKRthE7IIO+AoHRxKVhRMw4WjnWKEQAjkAGJKk8QtaNcrAL9FQLjG3OG9Vuf5vNJNQbCr6ZTG35DwaeZCKXX2hKZ79jgiepUcM0cp3MXA7VW6rWHMudUVf8iuLsCcaD4CJU+QeR8SW+l2GdWoX1hK5Ly0Sco69zDUgN4XVZyhBaTN+RAoiGiaYNI3R3yvjYFHAsC8/v6EgsPk3adZbDH8XO6DK1URuzId1qpEu+YxCdvYuWSZfyJe1CU0Nm1SCdApkJAyT4Y9m4x+mxAcdodvt1tbVAQKBgQDNqqfTPjHmSQrULakL3GrkKa6ui9vvEYhVAb7Yd3bdm4YciTx8q5cVX+EmCpKcsiVd9u/3+W+BuO9PIFyfqstiyoVo4piO5uSVRIlzSd5Tmp5mWIEBIzb0OLr4dnQ8ZXYt6hJJ5XHgYoBrZnNoJvbsHhi1uV6kyJPOIp/2gsNV2QKBgQCqoKoBFmvexsRxvdy5Ed950RzllduED6Rwpc8b6E0Z2Nwv/4ruQPrZMHHcWR4bY4ZM9ipn8xFWFYsSWYuYWExEWMo030Lc1axOpllT5xp/kB2NO1DjZpGR3knZ5YUeNFPMUbizH/aOxpejIWE7l4IUecQnzfRmJdo/l5STlXS6ZQKBgQCXtxEwUowK3c0AMmKmnkW35Eu49PEcDWntk2kOVCo6VdT7zfzx1r6QBPeY0WnP79aC13u0CKxKHHtkenH5UsvQJ6d8dNOnbwomVrWyd0Zrk/7PbvBTP3/1brf2O6OJPiuMuUQegQSdSuyCzcPKjBvQmG8P9xurIU9vW7l25oVkGQKBgHCDoZc2AcvVppSaboJbltHtYvDB8Q8UgYsGy5vLg4wXxJqiGCskDkZJ+8shuQi2iEkXyr7hOf1aF2ryQWskTqaDJlPXJXAaNpVmfGEndAHKNrJ6+sv5RuS5gFl/UFiqiNeiy/jSivN7YWLhEA0Ly2tVIFRfK1pwe4qTmhX36BFBAoGAIwoavKxnfUGaqrLjRtPqgzJTCoOHI3UV8JsitlJRxLrlGVyz/7Nu6kxnJa3NTJ3BisjNvGqQ1B+rHzqKE+ONkmrJsnOSCAJvKYyl5xn4qFJ9Rvw6aseTYAcRvJFJZbXZq208FZ4tGnDoDwKrff+ch9gATLZuzfUp4rPhGlxJK7c=";
    // 沙箱路径接口，正是路径应为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    // 参数返回格式
    private final String FORMAT = "JSON";
    // 签名方式
    private final String SIGN_TYPE = "RSA2";
    // 支付宝异步通知路径，付款完毕后会异步调用本项目的方法，必须为公网地址
    private final String NOTIFY_URL = "http://127.0.0.1/notifyURL";
    // 支付宝同步通知路径，也就是当付款完毕后跳转本项目的页面，可以不是公网地址
    private final String RETURN_URL = "http://127.0.0.1/alipay";



    @RequestMapping(value = "/alipay",method = RequestMethod.GET)
    public void aliPay(HttpServletResponse httpResponse, double money)  {




        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount =Double.toString(money);
        //订单名称，必填
        String subject ="测试支付";
        //商品描述，可空
        String body = "尊敬的会员欢聘用 金牌支持  家沐 竭尽为您服";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
            httpResponse.setContentType("text/html;charset=" + CHARSET);
            httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
