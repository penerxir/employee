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


   /* // 应用标识
    private final String APP_ID = "2016101900725971";
    // 应用私钥
    private final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLtZ7jH8hwsjHCMELHQPlG7BRksdG4EvdDpQb+mmJCwuCv8BPD3C8aXgDqVf5qJ8KyOlC9YJDRoBh2/9hl+kIkpNLA7bqcGJIH6uO9mY/J/GK0DqgdtceDoki1VmrTmDoIGJu1hQFFxZdWsvRX8SVvngLrbKoBqkBFkmNUIMtcGUfOPJ3Q7lKZ4K/2sXd6gIYYnCo/Ka4nBYVGzMCYYBU8cxWpADNvXghpMtbZ5wdTg8CwOgmte/LvlMqxHFsrMiLaAmBELVD4ysS/qhAYhU/IcFRnYMLSoFHY/Wg3apprg7ubHO50jas5KC5aveDzUnOFDdbG81pkjsWkGzASxSOTAgMBAAECggEAF9Beg/Jv9R26s5IMX/BoTTmChGN3DvxUn3gqyA1CwIR9TiALDnCiqsjpOQhtdGTQoBammpEyPjke0nHrzf4pdhuOPQqIq4DYq3bTWQcq5lAZ9v0klOPeXUa1RAu169lf0u924ysHu3/huwqH8dGOAweZEc4U8HG3wOQiAD4Sy+DWpK75xopz2XMIHxzqwP5xPTwBkl1lIQOJnEPWV+V/juaE2ng3mBfc9dAGC6q7Xq1GK4QYMKoIsX+1kbRe6rUEIFiAQMNUL7CF/eHJHrzRvPfCYoDkWhjayDVgNnVm2CarIJFZhdv5v3uy1DTmQC3hFagSP2RbKkW21iGvEH0veQKBgQDLE1G+/vfTNQCrUXC0m/4fV51pgmix0eUu+b6TtoGo67rT0ryuQgLlg/NK7QLHF/4isO1LQ4OWI+EYbAAd7kbiYH8diiR99fdy9CoRPmtkz28Ii/P8a1OS8U3wDWq2hG8AhAzjzoZRRUVQ5duT9JZGSmLwe6qWxL+18zY0Wcy1fwKBgQCwHq5/c+5sDgY60RdgiV7CyzuxNuBkkfoqt+eXyzVEIjKRbRkxKIKY5l8bF3m7+N22LdBtuwVHeJ7XP2fNs6C7NQBBnuNVhRzAT9MozM+ieEWUUCHXaGUhsk8wBtrCM+h+4hGe6dPAoYOd9QoHB3x2exrq48G0XtIJtpyV7G9j7QKBgEbNy5iuSTYIqt0Qnh06rlW7NJUO9PXa2u+QABR8c6Sz3AXy2fpf5aakZPVgwbEs+lyaF4JAXebBR8A+F6RX6bsQLgHconCQi+A2pVwxyTvy3yG5ui3uNctr+uKRqv1MG2+wmtXqsgYwAsT7/50MkECduvViCM5cdwM3c1S5Yph9AoGALn+7RI1AOqktPeQsjuFV8ooj9XLupb7g1Nx/OdV998IrpbevOgpfCK1yGL4G9vkK2GmaKQ6Co08WaPAloOq3GhYxPiIMFW0ynRe2QYRpoZStki3h8XRvlwjz3VNB+Jop3S4kxtcngClkjZS/lAGG++UewztkzhFm1QewqeiltDUCgYAuMpN9hnxGO0K8a/Szh2SXnEyCXdpz9jr7vVsuf2PBvk2YPzTPkWPeecZaE7nsg1y25KbV4YqM0LtDy+QG6b7V0G3KCSqtALIQu+2eYj5brl49YexH1x9An7AXpLE2oUU0EdkOL9LRbPKgXi9/Hc6lxqJ12jlMxI3JTHWKLaf9tg==";
    // 编码格式
    private final String CHARSET = "UTF-8";
    // 支付宝公钥
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhjkdyo6Gc9wtlLUMItgLyJhCVc1aUO+IaU9kHbKqbdIx/ZXhuYn1fJFpW5/IM8RRgQhP9eZVpUGyE01lxf3b2XU7SsTJ8ub649cNAO1+2c4fnCUbltihzzQwA39vFLgD+F2d7klLhEF0Ju7RYtG4n0vvi9tUoHi0PCJJoEEFIPpM4vgMQLVdV7jyZ/ztnvvOQAO/p4DFXKGW15mcI7wSHRpjp4kKxW58xc6R70PA1dYp92EDNe3ufZA/uYig1RbNwdj7WBHf0UGZlvbPaaN+k9/ftBrPX1ywIUVooMj7prWUKg9j8FZDCXnxDRuBCpS7TcSzvR77ABwOxeHMn2FOJwIDAQAB";
    // 沙箱路径接口，正是路径应为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    // 参数返回格式
    private final String FORMAT = "JSON";
    // 签名方式
    private final String SIGN_TYPE = "RSA2";
    // 支付宝异步通知路径，付款完毕后会异步调用本项目的方法，必须为公网地址
    private final String NOTIFY_URL = "http://127.0.0.1/notifyURL";
    // 支付宝同步通知路径，也就是当付款完毕后跳转本项目的页面，可以不是公网地址
    private final String RETURN_URL = "http://127.0.0.1:8888/pay2/sxt/PayPage2";*/

    @RequestMapping(value = "/alipay",method = RequestMethod.GET)
    public void aliPay(HttpServletResponse httpResponse, double money)  {



        /*//创建客户端
        AlipayClient alipayClient = new DefaultAlipayClient(
                GATEWAY_URL,APP_ID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE
        );
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //生成商户订单号
        String orderId = UUID.randomUUID().toString();

        String total_amount =Double.toString(money);

        String subject = "测试支付";
        String body = "尊敬的会员欢聘用 金牌支持  家沐 竭尽为您服";
        request.setBizContent("{\"orderId\":\""+ orderId +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form="";

        try {
            //
            form = alipayClient.pageExecute(request).getBody();

            response.setContentType("text/html;charset=" + CHARSET);
            response.getWriter().write(form);// 直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();

        }*/
        // 实例化客户端,填入所需参数
        // 网关地址 应用标识 应用私钥 传输格式 编码格式 支付宝公钥 签名类型
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
