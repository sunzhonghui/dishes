package com.zhonghui.tool.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 直连接口Controller
 * Created by pengmaokui on 16/3/16.
 */
@Controller
@RequestMapping("/service")
public class ServiceController {

  private final static Logger logger = LoggerFactory.getLogger(ServiceController.class);

  /**
   * 直连接口后缀
   */
  private final static String SERVICE = "/service";

  /**
   * 下载接口后缀
   */
  private final static String DOWNLOAD = "/download";

  /**
   * 上传接口后缀
   */
  private final static String UPLOAD = "/upload";

  //对账文件确认接口的文件类型
  private final static String[] CHECK_FILE_TYPE =
      {"COMMISSION","RECHARGE","WITHDRAW","BALANCE"};

  public String serviceRequests(Map<String,String> reqData) {
	    SDKConfig config = SDKConfig.getConfig();
	    String result = "";
	    String url = config.getUrl() + ServiceController.SERVICE;
	    HttpClient httpClient = new HttpClient(url);
	    try {
	      int status = httpClient.sendPost(reqData);
	      if (200 == status) {
	        result = httpClient.getResult();
	      }else{
	        logger.info("返回http状态码["+status+"]，请检查请求报文或者请求地址是否正确");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return result;
	  }
  /**
   * 用户查询接口
   * @param request 前台传输的参数
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doQueryUser")
  @ResponseBody
  public String doQueryUser(HttpServletRequest request) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, Object> reqData = new HashMap<String, Object>();

    //前台获取参数
  //  String platformUserNo = request.getParameter("projectNo");
    reqData.put("requestNo", "BGB20161027000000001");
	reqData.put("projectNo", 578506);
	reqData.put("status", "REPAYING");// 还款中
    //必须添加的参数
    reqData.put("timestamp", CgtUtil.getTampTime());

    Map<String, String> result = null;
    try {
      result = CgtUtil.createConnParam("MODIFY_PROJECT", reqData);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }

    return serviceRequests(result);
  }

  /**
   * 对账单下载接口
   * @param request 前台传输的参数
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doCheckFileDownload.do")
  public ModelAndView doCheckFileDownload(HttpServletRequest request) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, Object> reqData = new HashMap<String, Object>();

    //前台获取参数
    String fileDate = request.getParameter("fileDate");

    reqData.put("fileDate", fileDate);

    //必须添加的参数
    reqData.put("timestamp", CgtUtil.getTampTime());

    Map<String, String> result = null;
    try {
      result = CgtUtil.createConnParam("DOWNLOAD_CHECKFILE", reqData);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }

    String url = config.getUrl() + ServiceController.DOWNLOAD;

    return new ModelAndView("reqeust/downloadPost").addObject("result", result).addObject("url", url);
  }

  /**
   * 对账单确认接口
   * @param request 前台传输的参数
   * @param file 前台传输的文件
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doCheckFileConfirm.do")
  public ModelAndView doCheckFileConfirm(HttpServletRequest request, MultipartFile file) throws IOException {
    Map<String, String> result = null;

    ZipInputStream zis = null;
    try {
      //获取properties参数
      SDKConfig config = SDKConfig.getConfig();
      //定义reqData参数集合
      Map<String, Object> reqData = new HashMap<String, Object>();

      //前台获取参数
      String fileDate = request.getParameter("fileDate");

      //判断上传文件是不是zip压缩包 不是的话就报错
      if (!file.getOriginalFilename().endsWith(".zip")) {
        return new ModelAndView("error").addObject("message", "请上传zip格式文件");
      }

      //获取私钥
      PrivateKey privateKey = SignatureUtils.getRsaPkcs8PrivateKey(
          Base64.decodeBase64(config.getPrivateKey()));

      //生成detail请求参数(加密文件)
      JSONArray detail = new JSONArray();
      byte[] signfile = null;
      zis = new ZipInputStream(file.getInputStream());
      ZipEntry entry = null;
      while ((entry = zis.getNextEntry()) != null) {
        //判断格式
        String fileName = entry.getName();
        String[] arr = fileName.split("_");
        if (arr.length != 3) {
          //格式不符 跳过
          continue;
        }
        String checkFileDate = arr[0];
        if (!checkFileDate.equals(fileDate)) {
          //和文件时间对应不上的文件跳过
          continue;
        }

        String fileType = arr[2].substring(0, arr[2].indexOf("."));
        if (CgtUtil.isIn(fileType, ServiceController.CHECK_FILE_TYPE)) {
          InputStream input = new DataInputStream(zis);
          signfile = SignatureUtils.sign(SignatureAlgorithm.SHA1WithRSA, privateKey, input);
          JSONObject jo = new JSONObject();
          jo.put("fileType", fileType);
          jo.put("fileSign", Base64.encodeBase64String(signfile));
          detail.add(jo);

          logger.debug("正在加密的文件名为:" + entry.getName());
        }
      }

      //拼接reqDate
      String nowDate = CgtUtil.getTampTime();
      reqData.put("requestNo", nowDate);
      reqData.put("fileDate", fileDate);
      reqData.put("detail", detail);

      //必须添加的参数
      reqData.put("timestamp", nowDate);

      result = CgtUtil.createConnParam("CONFIRM_CHECKFILE", reqData);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != zis) {
        zis.close();
      }
    }
    return new ModelAndView("reqeust/servicePost").addObject("result", result);

  }


  /**
   * 对账单上传接口
   * @param request 前台传输的参数
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doCheckFileUpload.do")
  public ModelAndView doCheckFileUpload(HttpServletRequest request) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, Object> reqData = new HashMap<String, Object>();

    //前台获取参数
    String fileDate = request.getParameter("fileDate");
    String fileType = request.getParameter("fileType");

    //拼接reqData
    String nowDate = CgtUtil.getTampTime();
    reqData.put("requestNo", nowDate);
    reqData.put("fileDate", fileDate);
    reqData.put("fileType", fileType);

    //必须添加的参数
    reqData.put("timestamp", nowDate);

    Map<String, String> result = null;
    try {
      result = CgtUtil.createConnParam("UPLOAD_CHECKFILE", reqData);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }

    return new ModelAndView("reqeust/uploadPost").addObject("result", result);
  }

  /**
   * 交易查询接口
   * @param request 前台传输的参数
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doTransactionQuery.do")
  public ModelAndView doTransactionQuery(HttpServletRequest request) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, Object> reqData = new HashMap<String, Object>();

    //前台获取参数
    String transactionType = request.getParameter("transactionType");
    String requestNo = request.getParameter("requestNo");

    //拼接reqData
    String nowDate = CgtUtil.getTampTime();
    reqData.put("transactionType", transactionType);
    reqData.put("requestNo", requestNo);

    //必须添加的参数
    reqData.put("timestamp", nowDate);

    Map<String, String> result = null;
    try {
      result = CgtUtil.createConnParam("QUERY_TRANSACTION", reqData);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }

    return new ModelAndView("reqeust/servicePost").addObject("result", result);
  }

  /**
   * 创建标的接口
   * @param request 前台传输的参数
   * @return 返回post页面进行提交
   */
  @RequestMapping("/doEstablishProject.do")
  public ModelAndView doEstablishProject(HttpServletRequest request) {
    //获取properties参数
    SDKConfig config = SDKConfig.getConfig();
    //定义reqData参数集合
    Map<String, Object> reqData = new HashMap<String, Object>();

    //前台获取参数
    String platformUserNo = request.getParameter("platformUserNo");
    String projectNo = request.getParameter("projectNo");
    String projectType = request.getParameter("projectType");
    String projectName = request.getParameter("projectName");
    String projectAmount = request.getParameter("projectAmount");
    String annnualInterestRate = request.getParameter("annnualInterestRate");
    String repaymentWay = request.getParameter("repaymentWay");

    //拼接reqData
    String nowDate = CgtUtil.getTampTime();
    reqData.put("platformUserNo", platformUserNo);
    reqData.put("requestNo", nowDate);
    reqData.put("projectNo", projectNo);
    reqData.put("projectType", projectType);
    reqData.put("projectName", projectName);
    reqData.put("projectAmount", projectAmount);
    reqData.put("annnualInterestRate", annnualInterestRate);
    reqData.put("repaymentWay", repaymentWay);

    //必须添加的参数
    reqData.put("timestamp", nowDate);

    Map<String, String> result = null;
    try {
      result = CgtUtil.createConnParam("ESTABLISH_PROJECT", reqData);
    } catch (GeneralSecurityException e) {
      e.printStackTrace();
    }

    return new ModelAndView("reqeust/servicePost").addObject("result", result);
  }


  /**
   * 公用的直连接口方法<br>
   * HTTP请求返回数据然后传回前台显示
   * @param request 请求参数
   * @return 接口返回值
   */
  @ResponseBody
  @RequestMapping(value = "/serviceRequest.do", produces = "application/json; charset=utf-8")
  public String serviceRequest(HttpServletRequest request) {
    Map<String, String> reqData = CgtUtil.getParameterMap(request);
    SDKConfig config = SDKConfig.getConfig();
    String result = "";

    String url = config.getUrl() + ServiceController.SERVICE;

    HttpClient httpClient = new HttpClient(url);
    try {
      int status = httpClient.sendPost(reqData);
      if (200 == status) {
        result = httpClient.getResult();
      }else{
        logger.info("返回http状态码["+status+"]，请检查请求报文或者请求地址是否正确");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 上传文件接口方法<br>
   * 通过Apache HttpClient上传文件<br>
   * HTTP请求返回数据然后传回前台显示
   * @param request 请求参数
   * @param file 上传的文件
   * @return 接口返回值
   */
//  @ResponseBody
//  @RequestMapping(value = "/uploadRequest.do", produces = "application/json; charset=utf-8")
//  public String uploadRequest(HttpServletRequest request, MultipartFile file) throws IOException {
//    Map<String, String> reqData = CgtUtil.getParameterMap(request);
//    SDKConfig config = SDKConfig.getConfig();
//    String result = "";
//
//    String url = config.getUrl() + ServiceController.UPLOAD;
//
//    CloseableHttpClient httpClient = HttpClients.createDefault();
//    CloseableHttpResponse response = null;
//
//    try{
//      HttpPost httpPost = new HttpPost(url);
//      //设置超时时间
//      RequestConfig requestConfig = RequestConfig.custom()
//          .setConnectTimeout(config.getConnectionTimeout())
//          .setSocketTimeout(config.getReadTimeout()).build();
//      httpPost.setConfig(requestConfig);
//      MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//
//      //拼接普通的参数
//      //设置文本编码
//      ContentType contentType = ContentType.create("text/plain", "UTF-8");
//      for (String key : reqData.keySet()) {
//        multipartEntityBuilder.addTextBody(key, reqData.get(key), contentType);
//      }
//      //拼接文件
//      multipartEntityBuilder.addBinaryBody("file", file.getInputStream(),
//          ContentType.create(file.getContentType()), file.getOriginalFilename());
//      HttpEntity httpEntity = multipartEntityBuilder.build();
//      httpPost.setEntity(httpEntity);
//
//      response = httpClient.execute(httpPost);
//
//      int status = response.getStatusLine().getStatusCode();
//      if (status == HttpStatus.SC_OK) {
//        HttpEntity resEntity = response.getEntity();
//
//        result = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
//
//        //销毁响应内容
//        EntityUtils.consume(resEntity);
//      } else {
//        logger.info("返回http状态码["+status+"]，请检查请求报文或者请求地址是否正确");
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      if (null != response) {
//        response.close();
//      }
//      if (null != httpClient) {
//        httpClient.close();
//      }
//    }
//
//    return result;
//  }
  public static void main(String[] args) {
	ServiceController se=new ServiceController();
	System.out.println(se.doQueryUser(null));
}
}
