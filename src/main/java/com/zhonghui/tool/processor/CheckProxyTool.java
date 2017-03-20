package com.zhonghui.tool.processor;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class CheckProxyTool {
	public static boolean CheckProxy(String ip,int prot) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = null;
        HttpHost httphost=new HttpHost(ip,prot);
        RequestConfig requestconfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000).setProxy(httphost)
                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
        httpget.setConfig(requestconfig);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0"); 
        httpget.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        try {
            response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err
                        .println("Method failed: " + response.getStatusLine());
                return false;
            }else{
            	return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                Thread.sleep(50);
                if(response!=null)
                response.close();
                httpclient.close();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
    }
	public static void main(String[] args) {
		System.out.println(CheckProxy("128.199.166.84",8080));
	}
}
