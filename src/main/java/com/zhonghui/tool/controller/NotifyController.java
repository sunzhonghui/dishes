package com.zhonghui.tool.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import sun.misc.BASE64Decoder;

/**
 * Created by pengmaokui on 16/3/17.
 */
@Controller
@RequestMapping("/notify")
public class NotifyController {

  @RequestMapping(value = "/notifywithdraw")
  public String notifyWithdraw(HttpServletRequest request, HttpServletResponse response, Model model) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, String> reqDate = CgtUtil.getParameterMap(request);;

    String respData = reqDate.get("respData");

    try {
      //验签
      PublicKey publicKey = SignatureUtils.getRsaX509PublicKey(Base64.decodeBase64(config.getCgt_publicKey()));

      boolean verify = SignatureUtils.verify(SignatureAlgorithm.SHA1WithRSA,
          publicKey, respData, Base64.decodeBase64(reqDate.get("sign")));
      if (verify) {
        Map<String, Object> respMap = JSON.parseObject(respData, Map.class);
        String code = (String) respMap.get("code");
        if (("0").equals(code)) {
          //交易成功
          //判断金额是否相同 amount与请求是否相等
          synchronized (NotifyController.class) {
            //判断是否已经执行成功
            //执行业务
          }
          if ("NOTIFY".equals(reqDate.get("responseType"))) {
            //异步通知返回SUCCESS
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("SUCCESS");
            response.getWriter().flush();
          } else if ("CALLBACK".equals(reqDate.get("responseType"))) {
            //同步通知 返回页面
            //设置返回的参数
            model.addAttribute("message","支付成功");
            return "success";
          }
        }
      } else {
        //验签失败
        return null;
      }
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
  public static void main(String[] args) throws IOException {
	  BASE64Decoder decoder = new BASE64Decoder();
	  byte[]  aa =decoder.decodeBuffer("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlCaYDnWkZ5mhVJF3veBKqIUbQWe95gvVfwJVc+oDoqvKWl/F+oBmqXXEKO+mirWM/znMEvM5QgG0MSmhypw5K9XAYch50U8lbHEPlmc00ro4uEMpLkv0q4y2IGZYb9EFQzHUwOk58jhNqYPf4bQ7Iu6z5IAFaIqDUsY6gYSnXOKgUBsY5Ds503wrIiThOQxe3QqXRC66vYdhFaxD/6OZkVFAuEJi+XZFJyoLlw+5nyvQaoiIgvfugCkXLPjAxK7PdNI/Vaf9hc6xytdPFOlmZRKr6hFlFqWiMYUBuUJMZbFAIrVcQGBQ1GOC4GEQR/dsjnCSx0aY0cqmMz6jrUFx7QIDAQAB") ; 
	  System.out.println(new String(aa,"utf-8"));
	  System.out.println(new String(Base64.decodeBase64("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlCaYDnWkZ5mhVJF3veBKqIUbQWe95gvVfwJVc+oDoqvKWl/F+oBmqXXEKO+mirWM/znMEvM5QgG0MSmhypw5K9XAYch50U8lbHEPlmc00ro4uEMpLkv0q4y2IGZYb9EFQzHUwOk58jhNqYPf4bQ7Iu6z5IAFaIqDUsY6gYSnXOKgUBsY5Ds503wrIiThOQxe3QqXRC66vYdhFaxD/6OZkVFAuEJi+XZFJyoLlw+5nyvQaoiIgvfugCkXLPjAxK7PdNI/Vaf9hc6xytdPFOlmZRKr6hFlFqWiMYUBuUJMZbFAIrVcQGBQ1GOC4GEQR/dsjnCSx0aY0cqmMz6jrUFx7QIDAQAB"),"utf-8"));
	  
	  
}
}
