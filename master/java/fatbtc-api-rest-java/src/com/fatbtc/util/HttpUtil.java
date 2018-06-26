package com.fatbtc.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	/**
	 * get请求
	 * 
	 * @return
	 */
	public static String doGet(String url) {
		
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			// 发送get请求
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Accept", "application/json");

			HttpResponse response = client.execute(httpGet);

			HttpEntity responseEntity = response.getEntity();
			String jsonString = EntityUtils.toString(responseEntity);
			return jsonString;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String doPostJson(String url, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(params ,"utf-8"));
//		httpPost.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);

		CloseableHttpResponse response = null;
		
		try {
			response = httpclient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			String jsonString = EntityUtils.toString(responseEntity);
			return jsonString;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String doDeleteJson(String url, String params) throws Exception {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
		httpDelete.setHeader("Accept", "application/json");
		httpDelete.setHeader("Content-Type", "application/json");
		httpDelete.setEntity(new StringEntity(params ,"utf-8"));
		
		CloseableHttpResponse response = null;
		
		try {
			response = httpclient.execute(httpDelete);
			HttpEntity responseEntity = response.getEntity();
			String jsonString = EntityUtils.toString(responseEntity);
			return jsonString;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
