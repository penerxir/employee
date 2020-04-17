package com.wangpx.controller;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class PayController {
    private final String APP_ID = "2016101900721571";
    // 应用标识
    // 应用私钥
    private final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCBSN+0kqWm1/CKfe5he5p/vyXCLBrXL4Dr3yH4DZNpeFg+mV7WmLsgI//M9keb3CaGMVCVtHjSgctVi2DA3vNo3VB5x+eqUYKkOmfnC6X8dJuP4V3UrHJOPkWhSxJb3bC8qo7oBE0F20duXKRW63MjpPRW2IGlMk3+bCNHm+1+ZI+RN1yY8oTnwk1zFqmBf77NubmcCK5sgvBoM/29OT+zVzUuDREOJ8rZjHx+2GgeI/N2Sk1tgF7VV4QEe/fjf9iJQj3Gd/IaZ+su/XlLaS1Dgapg1uQ2DuifeOFLlCCMF4aDuIxn2TACkYKBI8tpd0PEIm0GD7a/LigfJ2bukYCjAgMBAAECggEADZP8wrdAJJS7U6BEQAK+SDpoGcuOChmYHa2yoJ20ZLhZ9EEhX8JsrMR7IiiffrQRa7iUMRTiN6GgHFzmDXuc7t+HizK+lvm2kc78iJyFk00f5W2S8DooSyVpJLaAuBtBa58yIRYKjNnp16JtNB/IWO8DJnfYrePpoxXyYpvyxnpcFcvyzqUlAX1qagbG095neKARqA4AllyDj+JitDciIPBMI+EqrCRNTKt2gxEeVLpsnJ2EJiCLmIYCJqTbI9NHPBrawR4Zdxgb12+XUXCb/1BQio8tP7S2QPHrHm21oIqwWqJDpVNdQGax5Ejip4zoVlW8v9L9CugnTSJz8KSXcQKBgQDj4I/qpSnT+uGwO31ZQafuyx6BFwwSh13v2rPQMkXGmSJ9ESaiz7+OHl7QIfCShKcIeU1Fuc+c/xFHk6B9xOLp62hZ8vgBEikEx+F1Ah/M6mzS4fv+g49sQCuzlgNf9EZ0S62wVz+Z92kWIUcAu3OMG+slTG1T+8PJffVPpJhvyQKBgQCRPW28dxPMA/VonBZmNNzIpRU0IEfTok4HayxlrpaNS6gp4USoSmvnJqNYb4QRVl0RaD+dTka64Ry9oBegVCfPEAfUIbjmUDrsOMm3tF5DOojHLCXsP2MI3QH4xURuFuTJkZ4YvfTfqEz5Jqr0iv6zVWveOYOOxEN+wPe7dKabCwKBgCZtvFdpuwAQkvbRUXY36EYQ4vgKFvMY+PJ2A1Tc5X90W6iTt9xFBJzG6l5XGubakACR1EIp76UeLp/PeYU8KrggBt0EaGZl1Z0EyDSfQxWfgvKdQY3OSeWLfNMwPSl1XFxIVuYiEIu6eXJN8ldhRT8fhiwoE1fSYVYdc70WmM5hAoGAYwUXcLtacuhsAwMPvmTh9aWfHCe58I6iVip6XWS3XeOr5cmgboA2qqRMDzoQN2XDE+AYJt48GKLrXljsYrGdZm1OeRD4p4UhdvWMiUNeVMVuhSQTQstAcMWE159edRQErWwq9kFj1qw+tkrnmMu+tHNTlHFvwQEL5zoWa+YuxFsCgYBsgAeZwdyal7/x2Tmt7wVWNjSwcETJ5AUYW0M1z3LLJHUShGZWRo1eyzDUC3Bpg8otn8kKwsSEP2AC3hfoNnkn6le3rveqdnIH+qOknNuP4KuoqTGEmyGkH1pV4DL4I7/g5EL46CB7jeDclAua9OLSCCYKMXEL6AfErDFrkz56iA==";
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

    @RequestMapping(value = "/alipay",method = RequestMethod.GET)
    public void aliPay(HttpServletResponse response, double money)  {



        //创建客户端
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
        request.setBizContent("{\"orderId\":\""+ orderId +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\"");
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

        }




    }

}
