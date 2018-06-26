package com.fatbtc;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.fatbtc.util.HttpUtil;
import com.fatbtc.util.MD5Util;
import com.fatbtc.util.StringUtil;

public class ApiDemoTest{
	
	private static String url="https://www.fatbtc.us";//交易/提现
	
	//从FatBTC申请的 api_secretapiKey和apiSecret
	private static String apiKey="73bb2722-fbf2-4e8d-bb7f-16273230a7ca";
	private static String apiSecret="a0178004ec331b1b9d04f86cd5617f3e";
	
	private static String signType="MD5";//MD5,HmacSHA256
	
	public static void main(String[] args) {
//		createOrder();
//		cancelOrder();
//		withdraw();
//		getSingleCurrency();
//		getCurrencyList();
//		getSingleOrderDetail();
//		getOrderList();
//		getSuccessedOrders();
//		getSystemTimeStamp();
		
	}
	
	/**
	 * 创建订单
	 * 
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * o_no (string): 订单编号，在当前交易对需唯一 ,
	 * o_price_type (string): 价格类型：limit、market，分别代表限价单、市价单 ,
	 * o_type (string): 订单类型：buy、sell，分别代表买入、卖出 ,
	 * price (number): 价格，对限价单，表示买入/卖出价格，对于市价单，请填0 ,
	 * volume (number): 数量，对限价单，表示买入/卖出数量，对于市价买单，表示买入多少计价货币(如CNY)，市价卖单表示卖出多少基础货币(如BTC)
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * symbol (string): 交易对名称，如BTCCNY、LTCCNY、ETHCNY ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 */
	public static void createOrder() {
		
//		FATBTC
//		WKCFAT
		String reqUrl = url+"/order/api/order";
		ObjectMapper mapper = new ObjectMapper();
		
		String orderNo = String.valueOf(System.currentTimeMillis());
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("api_key", apiKey);
			map.put("o_no", orderNo);
			map.put("o_price_type", "limit");
			map.put("o_type", "buy");
			map.put("price", 95);
			map.put("volume", 1);
			map.put("sign_type", signType);
			map.put("symbol", "BTCFCNY");
			map.put("timestamp", StringUtil.getTimeStamp());
			map.put("sign", MD5Util.createSign(map, apiSecret));
			
			String params = mapper.writeValueAsString(map);
			String response = HttpUtil.doPostJson(reqUrl, params);
			System.out.println(response);
//			成功：{"status":1,"message":"success","data":1}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消订单
	 * 
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * id (integer): 订单ID ,
	 * o_no (string): 订单号 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * symbol (string): 交易对名称，如BTCCNY、LTCCNY、ETHCNY ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 */
	
	public static void cancelOrder() {
		String reqUrl = url+"/order/api/order";
		ObjectMapper mapper = new ObjectMapper();
		
		String orderNo = String.valueOf(System.currentTimeMillis());
		
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("api_key", apiKey);
			map.put("id", 2);
			map.put("o_no", orderNo);
			map.put("symbol", "BTCFCNY");
			map.put("timestamp", StringUtil.getTimeStamp());
			map.put("sign_type", signType);
			map.put("sign", MD5Util.createSign(map, apiSecret));
			
			String params = mapper.writeValueAsString(map);
			String response = HttpUtil.doDeleteJson(reqUrl, params);
			System.out.println(response);
//			成功：{"status":1,"message":"success","data":100000}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 提现
	 * 
	 * addr (string): 提币地址 ,
	 * amount (number): 提币数量 ,
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * currency (string): 币类型，如BTC、LTC、ETH ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 */
	public static void withdraw() {
		String reqUrl = url+"/order/api/withdraw";
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("addr", 0x0000000000000000000000000000000000000000);
			map.put("amount", 10);
			map.put("api_key", apiKey);
			map.put("currency", "ETH");
			map.put("sign_type", signType);
			map.put("timestamp", StringUtil.getTimeStamp());
			map.put("sign", MD5Util.createSign(map, apiSecret));
			
			String params = mapper.writeValueAsString(map);
			String response = HttpUtil.doPostJson(reqUrl, params);
			System.out.println(response);
//			成功：{"status":1,"message":"success","data":1}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 获得单个币资产
	 * 
	 * currency (string): 币类型，如BTC、LTC、ETH ,
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 */
	public static void getSingleCurrency() {
		String reqUrl = url+"/m/api/a/account";
//		/api/a/account/{currency}/{apikey}/{timestamp}/{signType}/{sign}
		

		try {
			String currency = "BTC";
			Long timestamp = StringUtil.getTimeStamp();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			Map<String, Object> map = new HashMap<>();
			map.put("api_key", apiKey);
			map.put("sign_type", signType);
			map.put("timestamp", timestamp);
			String sign = MD5Util.createSign(map, apiSecret);
//			拼接url成 /api/a/account/{currency}/{apikey}/{timestamp}/{signType}/{sign}
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(currency)
					.append("/").append(apiKey)
					.append("/").append(timestamp)
					.append("/").append(signType)
					.append("/").append(sign);
			String response = HttpUtil.doGet(sBuffer.toString());
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	

	
	/**
	 * 获得资产列表
	 * 
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 */
	public static void getCurrencyList() {
		String reqUrl = url+"/m/api/a/accounts";
//		/api/a/accounts/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			Long timestamp = StringUtil.getTimeStamp();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			Map<String, Object> map = new HashMap<>();
			map.put("api_key", apiKey);
			map.put("sign_type", signType);
			map.put("timestamp", timestamp);
			String sign = MD5Util.createSign(map, apiSecret);
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(apiKey)
					.append("/").append(timestamp)
					.append("/").append(signType)
					.append("/").append(sign);
			String response = HttpUtil.doGet(sBuffer.toString());
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * 获得单个订单的交易明细（仅返回当前apikey对应数据）
	 * 
	 * symbol:交易对名称，如BTCCNY、LTCCNY、ETHCNY
	 * id:交易id
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 */
	public static void getSingleOrderDetail() {
		String reqUrl = url+"/m/api/o/order/trades";
//		/m/o/order/trades/{symbol}/{id}/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			String symbol="BTCFCNY";
			String id="8402";
			Long timestamp = StringUtil.getTimeStamp();
			
			Map<String, Object> map = new HashMap<>();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			map.put("api_key", apiKey);
			map.put("sign_type", signType);
			map.put("timestamp", timestamp);
			String sign = MD5Util.createSign(map, apiSecret);
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(symbol)
					.append("/").append(id)
					.append("/").append(apiKey)
					.append("/").append(timestamp)
					.append("/").append(signType)
					.append("/").append(sign);
			String response = HttpUtil.doGet(sBuffer.toString());
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 获得订单列表（仅返回当前apikey对应数据）
	 * symbol:交易对名称，如BTCCNY、LTCCNY、ETHCNY
	 * page：页数，从1开始，
	 * pageSize：每页记录数，最大20，默认按时间倒叙排列 
	 * status 0表示未完成（挂单中），1表示已完成（含已取消），2表示所有
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 */
	public static void getOrderList() {
		String reqUrl = url+"/m/api/o/orders";
//		/m/o/orders/{symbol}/{page}/{pageSize}/{status}/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			String symbol="BTCFCNY";
			int page=1;//从1开始
			int pageSize=20;//最大20
			int status=2;// 0表示未完成（挂单中），1表示已完成（含已取消），2表示所有
			Long timestamp = StringUtil.getTimeStamp();
			
			Map<String, Object> map = new HashMap<>();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			map.put("api_key", apiKey);
			map.put("sign_type", signType);
			map.put("timestamp", timestamp);
			String sign = MD5Util.createSign(map, apiSecret);
			
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(symbol)
			.append("/").append(page)
			.append("/").append(pageSize)
			.append("/").append(status)
			.append("/").append(apiKey)
			.append("/").append(timestamp)
			.append("/").append(signType)
			.append("/").append(sign);
			String response = HttpUtil.doGet(sBuffer.toString());
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 获得已成交记录（仅返回当前apikey对应数据）
	 * 
	 * symbol:交易对名称，如BTCCNY、LTCCNY、ETHCNY
	 * page：页数，从1开始，
	 * pageSize：每页记录数，最大20，默认按时间倒叙排列 
	 * status 0表示未完成（挂单中），1表示已完成（含已取消），2表示所有
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 */
	public static void getSuccessedOrders() {
		String reqUrl = url+"/m/api/t/trades";
//		/m/t/trades/{symbol}/{page}/{pageSize}/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			String symbol="BTCFCNY";
			int page=1;//从1开始
			int pageSize=20;//最大20
			Long timestamp = StringUtil.getTimeStamp();
			
			Map<String, Object> map = new HashMap<>();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			map.put("api_key", apiKey);
			map.put("sign_type", signType);
			map.put("timestamp", timestamp);
			String sign = MD5Util.createSign(map, apiSecret);
			
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(symbol)
			.append("/").append(page)
			.append("/").append(pageSize)
			.append("/").append(apiKey)
			.append("/").append(timestamp)
			.append("/").append(signType)
			.append("/").append(sign);
			String response = HttpUtil.doGet(sBuffer.toString());
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获得系统时间戳
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 */
	public static void getSystemTimeStamp() {
//		/m/timestamp/{timestamp}
		String reqUrl = url+"/m/timestamp/";
		
		try {
			Long timestamp = StringUtil.getTimeStamp();
			
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(timestamp);
			
			String response = HttpUtil.doGet(sBuffer.toString());
			System.out.println(response);
//			{"status":1,"msg":"success","data":"1529995831"}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
