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
 *   xshu       2014-05-28       HTTP通信工具类
 * =============================================================================
 */
package com.zhonghui.tool.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {

  private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

	/**
	 * 目标地址
	 */
	private URL url;

	/**
	 * 通信连接超时时间
	 */
	private int connectionTimeout;

	/**
	 * 通信读超时时间
	 */
	private int readTimeOut;

	/**
	 * 通信结果
	 */
	private String result;

	/**
	 * 获取通信结果
	 * @return
	 */
	public String getResult() {
		return result;
	}

	/**
	 * 设置通信结果
	 * @param result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
   * 构造函数<br>
   * http连接超时时间和读写超时时间设置为cgt_sdk.properties里面的值
   * @param url 目标地址
   */
	public HttpClient(String url) {
    try {
      this.url = new URL(url);
      SDKConfig config = SDKConfig.getConfig();
      this.connectionTimeout = config.getConnectionTimeout();
      this.readTimeOut = config.getReadTimeout();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

	/**
	 * 构造函数
	 * @param url 目标地址
	 * @param connectionTimeout HTTP连接超时时间
	 * @param readTimeOut HTTP读写超时时间
	 */
	public HttpClient(String url, int connectionTimeout, int readTimeOut) {
    try {
      this.url = new URL(url);
      this.connectionTimeout = connectionTimeout;
      this.readTimeOut = readTimeOut;
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
	}

  /**
   * post发送信息到服务端,编码默认为UTF-8
   * @param data 发送的数据
   * @return
   * @throws Exception
   */
  public int sendPost(Map<String, String> data) throws Exception {
    return this.sendPost(data, null);
  }

	/**
	 * post发送信息到服务端
	 * @param data
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public int sendPost(Map<String, String> data, String encoding) throws Exception {
		try {
      if (StringUtils.isEmpty(encoding)) {
        encoding = "UTF-8";
      }
			HttpURLConnection httpURLConnection = createConnection(encoding);
			if(null == httpURLConnection){
				throw new Exception("创建联接失败");
			}
			String sendData = this.getRequestParamString(data, encoding);
      logger.info("请求报文:[" + sendData + "]");
			this.requestServer(httpURLConnection, sendData,
					encoding);
			this.result = this.response(httpURLConnection, encoding);
			logger.info("同步返回报文:[" + result + "]");
			return httpURLConnection.getResponseCode();
		} catch (Exception e) {
			throw e;
		}
	}

  /**
   * 发送信息到服务端 GET方式 编码为utf-8
   * @return
   * @throws Exception
   */
  public int sendGet() throws  Exception {
    return this.sendGet(null);
  }

	/**
	 * 发送信息到服务端 GET方式
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public int sendGet(String encoding) throws Exception {
		try {
      if (StringUtils.isEmpty(encoding)) {
        encoding = "UTF-8";
      }
			HttpURLConnection httpURLConnection = createConnectionGet(encoding);
			if(null == httpURLConnection){
				throw new Exception("创建联接失败");
			}
			this.result = this.response(httpURLConnection, encoding);
			logger.info("同步返回报文:[" + result + "]");
			return httpURLConnection.getResponseCode();
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * HTTP Post发送消息
	 *
	 * @param connection 连接
	 * @param message 发送的数据
	 * @throws IOException
	 */
	private void requestServer(final URLConnection connection, String message, String encoder)
			throws Exception {
		PrintStream out = null;
		try {
			connection.connect();
			out = new PrintStream(connection.getOutputStream(), false, encoder);
			out.print(message);
			out.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != out) {
				out.close();
			}
		}
	}

	/**
	 * 显示Response消息
	 *
	 * @param connection 连接
	 * @param encoding 编码
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private String response(final HttpURLConnection connection, String encoding)
			throws URISyntaxException, IOException, Exception {
		InputStream in = null;
		StringBuilder sb = new StringBuilder(1024);
		BufferedReader br = null;
		try {
			if (200 == connection.getResponseCode()) {
				in = connection.getInputStream();
				sb.append(new String(read(in), encoding));
			} else {
				in = connection.getErrorStream();
				sb.append(new String(read(in), encoding));
			}
      logger.info("HTTP Return Status-Code:["
					+ connection.getResponseCode() + "]");
			return sb.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != br) {
				br.close();
			}
			if (null != in) {
				in.close();
			}
			if (null != connection) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 显示Response消息
	 *
	 * @param connection 连接
	 * @param encoding 编码
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private InputStream responseInput(final HttpURLConnection connection, String encoding)
			throws URISyntaxException, IOException, Exception {
		InputStream in = null;
		try {
			if (200 == connection.getResponseCode()) {
				in = connection.getInputStream();
			} else {
				in = connection.getErrorStream();
			}
			logger.info("HTTP Return Status-Code:["
					+ connection.getResponseCode() + "]");
			return in;
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
		}
	}
	
	public static byte[] read(InputStream in) throws IOException {
		byte[] buf = new byte[1024];
		int length = 0;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		while ((length = in.read(buf, 0, buf.length)) > 0) {
			bout.write(buf, 0, length);
		}
		bout.flush();
		return bout.toByteArray();
	}
	
	/**
	 * 创建Post连接
	 *
	 * @return
	 * @throws ProtocolException
	 */
	private HttpURLConnection createConnection(String encoding) throws ProtocolException {
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		httpURLConnection.setConnectTimeout(this.connectionTimeout);// 连接超时时间
		httpURLConnection.setReadTimeout(this.readTimeOut);// 读取结果超时时间
		httpURLConnection.setDoInput(true); // 可读 post设置的参数
		httpURLConnection.setDoOutput(true); // 可写 post设置的参数
		httpURLConnection.setUseCaches(false);// 取消缓存
		httpURLConnection.setRequestProperty("Content-type",
				"application/x-www-form-urlencoded;charset=" + encoding);
		httpURLConnection.setRequestMethod("POST");
		return httpURLConnection;
	}

	/**
	 * 创建get连接
	 *
	 * @return
	 * @throws ProtocolException
	 */
	private HttpURLConnection createConnectionGet(String encoding) throws ProtocolException {
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		httpURLConnection.setConnectTimeout(this.connectionTimeout);// 连接超时时间
		httpURLConnection.setReadTimeout(this.readTimeOut);// 读取结果超时时间
		httpURLConnection.setUseCaches(false);// 取消缓存
		httpURLConnection.setRequestProperty("Content-type",
				"application/x-www-form-urlencoded;charset=" + encoding);
		httpURLConnection.setRequestMethod("GET");
		return httpURLConnection;
	}
	
	/**
	 * 将Map存储的对象，转换为key=value&key=value的字符
	 *
	 * @param requestParam
	 * @param coder
	 * @return
	 */
	private String getRequestParamString(Map<String, String> requestParam, String coder) {
		StringBuffer sf = new StringBuffer("");
		String reqstr = "";
		if (null != requestParam && 0 != requestParam.size()) {
			for (Entry<String, String> en : requestParam.entrySet()) {
				try {
					sf.append(en.getKey()
							+ "="
							+ (null == en.getValue() || "".equals(en.getValue()) ? "" : URLEncoder
									.encode(en.getValue(), coder)) + "&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return "";
				}
			}
			reqstr = sf.substring(0, sf.length() - 1);
		}
		logger.info("请求报文(已做过URLEncode编码):[" + reqstr + "]");
		return reqstr;
	}

}
