package com.zhonghui.tool.controller;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 网关接口
 * Created by pengmaokui on 16/3/14.
 */
@Controller
@RequestMapping("/gateway")
public class GatewayController {

  private final static Logger logger = LoggerFactory.getLogger(GatewayController.class);

  /**
   * 网关接口后缀
   */
  private final static String GATEWAY = "/gateway";

  /**
   * 个人绑卡注册接口
   * @param request 前台传输的参�?
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doPersonalRegister")
  public ModelAndView doPersonalRegister(HttpServletRequest request) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, Object> reqData = new HashMap<String, Object>();

    //前台获取参数
    String platformUserNo = request.getParameter("platformUserNo");
    String realName = request.getParameter("realName");
    String idCardType = request.getParameter("idCardType");
    String idCardNo = request.getParameter("idCardNo");
    String mobile = request.getParameter("mobile");
    String bankcardNo = request.getParameter("bankcardNo");
    String callbackUrl = request.getParameter("callbackUrl");

    //后台生成
    String requestNo = CgtUtil.getTampTime();
    String userRole = "NORMAL";

    reqData.put("platformUserNo", platformUserNo);
    reqData.put("requestNo", requestNo);
    reqData.put("legal", realName);
    reqData.put("enterpriseName","牛x公司");
    reqData.put("bankLicense","3654564546");
    reqData.put("orgNo","14213");
    reqData.put("businessLicense","3543434");
    reqData.put("taxNo","34343434");
    reqData.put("unifiedCode","34343434");
    reqData.put("creditCode","34343434");
    reqData.put("contact", "aaa");
    reqData.put("userRole", "NORMAL");
    reqData.put("legalIdCardNo", idCardNo);
    reqData.put("contactPhone", mobile);
    reqData.put("bankcardNo", bankcardNo);
    reqData.put("callbackUrl", callbackUrl);
    reqData.put("bankcode", "ICBK");
    reqData.put("userIdentity","BORROWERS");

    //必须添加的参�?
    reqData.put("timestamp", requestNo);

    Map<String, String> result = null;
    try {
      result = CgtUtil.createConnParam("ENTERPRISE_REGISTER", reqData);
      HttpClient httpc=new HttpClient("http://220.181.25.233:8602/bha-neo-app/lanmaotech/gateway");
      int codes=httpc.sendPost(result,"utf-8");
      if(codes==200){
    	  System.out.println(httpc.getResult());
      }
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    String url = "http://220.181.25.233:8602/bha-neo-app/lanmaotech/gateway";

    return new ModelAndView("reqeust/gatewayPost").addObject("result", result).addObject("url", url);
  }

  /**
   * 充�?�接�?
   * @param request 前台传输的参�?
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doRecharge")
  public ModelAndView doRecharge(HttpServletRequest request) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, Object> reqData = new HashMap<String, Object>();

    //前台获取参数
    String platformUserNo = request.getParameter("platformUserNo");
    String amount = request.getParameter("amount");
    String rechargeWay = request.getParameter("rechargeWay");
    String bankcode = request.getParameter("bankcode");
    String callbackUrl = request.getParameter("callbackUrl");


    //后台生成
    String requestNo = CgtUtil.getTampTime();

    reqData.put("platformUserNo", platformUserNo);
    reqData.put("requestNo", requestNo);
    reqData.put("amount", amount);
    reqData.put("rechargeWay", rechargeWay);
    reqData.put("bankcode", bankcode);
    reqData.put("callbackUrl", callbackUrl);

    //计算页面过期时间 当前时间增加30分钟
    DateTime dateTime = new DateTime();
    reqData.put("expired",
        dateTime.plusMinutes(30).toString("yyyyMMddHHmmss"));

    //必须添加的参�?
    reqData.put("timestamp", requestNo);

    Map<String, String> result = null;
    try {
      result = CgtUtil.createConnParam("RECHARGE", reqData);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }

    String url =  "http://220.181.25.233:8602/bha-neo-app/lanmaotech/gateway";

    return new ModelAndView("reqeust/gatewayPost").addObject("result", result).addObject("url", url);
  }

  /**
   * 提现接口
   * @param request 前台传输的参�?
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doWithdraw.do")
  public ModelAndView doWithdraw(HttpServletRequest request) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, Object> reqData = new HashMap<String, Object>();

    //前台获取参数
    String platformUserNo = request.getParameter("platformUserNo");
    String amount = request.getParameter("amount");
    String feeMode = request.getParameter("feeMode");
    String withdrawType = request.getParameter("withdrawType");
    String callbackUrl = request.getParameter("callbackUrl");


    //后台生成
    String requestNo = CgtUtil.getTampTime();

    reqData.put("platformUserNo", platformUserNo);
    reqData.put("requestNo", requestNo);
    reqData.put("amount", amount);
    reqData.put("feeMode", feeMode);
    reqData.put("withdrawType", withdrawType);
    reqData.put("callbackUrl", callbackUrl);

    //计算页面过期时间 当前时间增加30分钟
    DateTime dateTime = new DateTime();
    reqData.put("expired",
        dateTime.plusMinutes(30).toString("yyyyMMddHHmmss"));

    //必须添加的参�?
    reqData.put("timestamp", requestNo);

    Map<String, String> result = null;
    try {
      result = CgtUtil.createConnParam("WITHDRAW", reqData);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }

    String url = config.getUrl() + GatewayController.GATEWAY;

    return new ModelAndView("reqeust/gatewayPost").addObject("result", result).addObject("url", url);
  }
}
