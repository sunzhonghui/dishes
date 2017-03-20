package com.zhonghui.tool.processor;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpTool {
	public static Document urlToDoc(String url,String host,int port) {
        Document doc = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        RequestConfig requestconfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000)
                .setCookieSpec(CookieSpecs.BEST_MATCH).build();
        if(host!=null&&host.length()>0){
        	HttpHost httphost=new HttpHost(host,port);
        	requestconfig = RequestConfig.custom()
                    .setSocketTimeout(50000).setConnectTimeout(50000).setProxy(httphost)
                    .setCookieSpec(CookieSpecs.BEST_MATCH).build();
        }
        httpget.setConfig(requestconfig);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0"); 
        httpget.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        try {
            response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err
                        .println("Method failed: " + response.getStatusLine());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                doc = Jsoup.parse(in, "utf-8", url);

            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                Thread.sleep(50);
                response.close();
                httpclient.close();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
        return doc;
    }
	public static Document urlToDoc(String url) {
        Document doc = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        RequestConfig requestconfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000)
                .setCookieSpec(CookieSpecs.BEST_MATCH).build();
        httpget.setConfig(requestconfig);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0"); 
        httpget.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        try {
            response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err
                        .println("Method failed: " + response.getStatusLine());
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream in = entity.getContent();
                doc = Jsoup.parse(in, "utf-8", url);

            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                Thread.sleep(50);
                response.close();
                httpclient.close();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
        return doc;
    }
}
