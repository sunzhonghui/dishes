package com.zhonghui.tool.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * Created by pengmaokui on 16/3/15.
 */
public class CgtUtil {

  private final static Logger logger = LoggerFactory.getLogger(CgtUtil.class);

  /**
   * 生成网关接口,直连接口,对账下载参数<br>
   * userDevice默认为pc<br>
   * keySerial默认为1<br>
   * sign默认为properties配置的值<br>
   * @param serviceName 接口名称
   * @param reqDataMap 请求参数
   * @return
   */
  public static Map<String, String> createConnParam(String serviceName, Map<String, Object> reqDataMap) throws GeneralSecurityException {
    Map<String, String> result = new HashMap<String, String>();

    SDKConfig config = SDKConfig.getConfig();
    String privateStr = config.getPrivateKey();

    String reqData = JSON.toJSONString(reqDataMap);
    logger.debug("请求参数reqData:" + reqData);
    PrivateKey privateKey = SignatureUtils.getRsaPkcs8PrivateKey(
        Base64.decodeBase64(privateStr));
    byte[] sign = SignatureUtils.sign(SignatureAlgorithm.SHA1WithRSA,
        privateKey, reqData);

    //拼装网关参数
    result.put("serviceName", serviceName);
    result.put("platformNo", config.getPlatformNo());
    result.put("reqData", reqData);
    result.put("keySerial", config.getKeySerial());
    result.put("sign", Base64.encodeBase64String(sign));

    return result;
  }

  /**
   * 从request中获得参数Map，并返回可读的Map
   *
   * @param request
   * @return
   */
  public static Map<String, String> getParameterMap(HttpServletRequest request) {
    Map<String, String> map = new HashMap<String, String>();
    Enumeration<String> names = request.getParameterNames();
    while(names.hasMoreElements()){
      String key = names.nextElement();
      String value = request.getParameter(key);
      if(value == null || value.trim().equals("")){
        continue;
      }
      map.put(key, value);
    }
    return map;
  }

  /**
   * 获取当前时间<br>
   * 格式为yyyyMMddHHmmss
   * @return 返回相应的字符串
   */
  public static String getTampTime() {
    SimpleDateFormat pattern = new SimpleDateFormat("yyyyMMddHHmmss");
    return pattern.format(new Date());
  }

  /**
   * JAVA判断字符串数组中是否包含某字符串元素
   *
   * @param substring 某字符串
   * @param source 源字符串数组
   * @return 包含则返回true，否则返回false
   */
  public static boolean isIn(String substring, String[] source) {
    if (source == null || source.length == 0) {
      return false;
    }
    for (int i = 0, l = source.length; i < l; i++) {
      String aSource = source[i];
      if (aSource.equals(substring)) {
        return true;
      }
    }
    return false;
  }

  /**
   * inputStream转化为file
   * @param ins 输入流
   * @param file 转化的file
   */
  public void inputstreamtofile(InputStream ins, File file) throws IOException {
    OutputStream os = null;
    try {
      os = new FileOutputStream(file);
      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
        os.write(buffer, 0, bytesRead);
      }
      os.flush();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    os.close();
    ins.close();
  }

}
