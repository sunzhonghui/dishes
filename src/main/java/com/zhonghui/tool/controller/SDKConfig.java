/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28       MPI基本参数工具�?
 * =============================================================================
 */
package com.zhonghui.tool.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * 软件�?发工具包 配制
 * 
 * @author xuyaowen
 * 
 */
public final class SDKConfig {

  private static final Logger logger = LoggerFactory.getLogger(SDKConfig.class);

	private static final String FILE_NAME = "cgt_sdk.properties";

  private static SDKConfig sdkConfig = null;

	/**
	 * 接口地址
	 */
	private String url;

	/**
	 * 平台编好
	 */
	private String platformNo;

	/**
	 * 证书编号
	 */
	private String keySerial;

	/**
	 * 私钥,用于加密参数
	 */
	private String privateKey;

	/**
	 * 存管通公�?,用于解密
	 */
	private String cgt_publicKey;

  /**
   * 连接超时
   */
  private int connectionTimeout;

  /**
   * 读去超时
   */
  private int readTimeout;

  private SDKConfig () {
    this.loadPropertiesFromSrc();
  }

	/**
	 * 获取config对象.
	 * 
	 * @return
	 */
	public static SDKConfig getConfig() {
    synchronized (SDKConfig.class) {
      if (null == sdkConfig) {
        sdkConfig = new SDKConfig();
      }
      return sdkConfig;
    }
	}

	/**
	 * 从classpath路径下加载配置参�?
	 */
	private void loadPropertiesFromSrc() {
		InputStream in = null;
		try {
			Properties properties = null;
			logger.info("从classpath: " +SDKConfig.class.getClassLoader().getResource("").getPath()+" 获取属�?�文�?"+FILE_NAME);
			in = SDKConfig.class.getClassLoader()
					.getResourceAsStream(FILE_NAME);
			if (null != in) {
				BufferedReader bf = new BufferedReader(new InputStreamReader(
						in, "utf-8"));
				properties = new Properties();
				try {
					properties.load(bf);
				} catch (IOException e) {
					throw e;
				}
			} else {
				logger.error(FILE_NAME + "属�?�文件未能在classpath指定的目录下 "+SDKConfig.class.getClassLoader().getResource("").getPath()+" 找到!");
				return;
			}
			loadProperties(properties);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据传入的properties对象设置配置参数
	 * 
	 * @param pro
	 */
	private void loadProperties(Properties pro) {
		logger.info("�?始从属�?�文件中加载配置�?");
		String value = null;
		value = pro.getProperty("url");
		if (!StringUtils.isEmpty(value)) {
			this.url = value.trim();
		}
    value = pro.getProperty("platformNo");
		if (!StringUtils.isEmpty(value)) {
			this.platformNo = value.trim();
		}
    value = pro.getProperty("keySerial");
		if (!StringUtils.isEmpty(value)) {
			this.keySerial = value.trim();
		}
    value = pro.getProperty("privateKey");
		if (!StringUtils.isEmpty(value)) {
			this.privateKey = value.trim();
		}
    value = pro.getProperty("cgt_publicKey");
		if (!StringUtils.isEmpty(value)) {
			this.cgt_publicKey = value.trim();
		}
    value = pro.getProperty("connectionTimeout");
		if (!StringUtils.isEmpty(value)) {
			this.connectionTimeout = Integer.valueOf(value.trim());
		}
    value = pro.getProperty("readTimeout");
		if (!StringUtils.isEmpty(value)) {
			this.readTimeout = Integer.valueOf(value.trim());
		}

	}

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPlatformNo() {
    return platformNo;
  }

  public void setPlatformNo(String platformNo) {
    this.platformNo = platformNo;
  }

  public String getKeySerial() {
    return keySerial;
  }

  public void setKeySerial(String keySerial) {
    this.keySerial = keySerial;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  public String getCgt_publicKey() {
    return cgt_publicKey;
  }

  public void setCgt_publicKey(String cgt_publicKey) {
    this.cgt_publicKey = cgt_publicKey;
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }
}
