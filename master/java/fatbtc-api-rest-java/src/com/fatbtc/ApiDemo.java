package com.fatbtc;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.fatbtc.util.HttpUtil;
import com.fatbtc.util.MD5Util;
import com.fatbtc.util.StringUtil;

public class ApiDemo{
	
	private static String url="https://www.fatbtc.com";
	
	//从FatBTC申请的 api_secretapiKey和apiSecret
	//apiKey and apiSecret can be applied from your FatBTC account
	private static String apiKey="";
	private static String apiSecret="";
	private static String signType="MD5";//MD5,HmacSHA256
	
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		getSystemTimeStamp();
		getSymbols();
		
//		createOrder();
//		cancelOrder();
//		withdraw();
//		getSingleCurrency();
//		getCurrencyList();
//		getSingleOrderDetail();
//		getOrderList();
//		getSuccessedOrders();
//		getOrderDetail();
//		System.out.println(System.currentTimeMillis()-t1);
		
	}
	
	/**
	 * 创建订单
	 * Create an order
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
	 * api_key (string): api_key can applied and generate from Account - API management,
	 * o_no (string): The order number, which needs to be unique in the current trading pair ,
	 * o_price_type (string): Price type: limit, market represents a limit order, a market order,
	 * o_type (string): Order type: buy,sell represents buying and selling,
	 * price (number): Price, for limit orders, indicates the bid/ask price, for market orders, please fill in 0 ,
	 * volume (number): Quantity, for limit orders, indicates the number of buys/sells, for market buy orders, indicates how much of the quoted currency (such as CNY) to buy, and for a market sell order, it indicates how much base currency (such as BTC) to sell
	 * sign (string): Results of signing request parameters using api_secret,
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate note,
	 * symbol (string): Name of the trading pair Such as: BTCCNY,LTCCNY,ETHCNY.
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign, please refer to the signature method
	 */
	public static void createOrder() {
		
//		FATBTC
//		WKCFAT
		String reqUrl = url+"/order/api/order";
		ObjectMapper mapper = new ObjectMapper();
		
		String orderNo = String.valueOf(System.currentTimeMillis());
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("site_id", 1);
			map.put("api_key", apiKey);
			map.put("o_no", orderNo);
			map.put("o_price_type", "limit");
			map.put("o_type", "buy");
			map.put("price", 100);
			map.put("volume", 1);
			map.put("sign_type", signType);
			map.put("symbol", "BTCFCNY");
			map.put("timestamp", getSystemTimeStamp());
			map.put("sign", MD5Util.createSign(map, apiSecret));
			
			String params = mapper.writeValueAsString(map);
			String response = HttpUtil.doPostJson(reqUrl, params);
			System.out.println(response);
//			Success：{"status":1,"msg":"success","data":1}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消订单
	 * Cancel an order
	 * 
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * id (integer): 订单ID ,
	 * o_no (string): 订单号 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * symbol (string): 交易对名称，如BTCCNY、LTCCNY、ETHCNY ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。
	 * 
	 * api_key (string): api_key can be applied from the Account - API management,
	 * id (integer):Order ID ,
	 * o_no (string): Order number ,
	 * sign (string): Results of using api_secret to sign request parameters,
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * symbol (string): Name of the trading pair, such as: BTCCNY,LTCCNY,ETHCNY.
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
     * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	
	public static void cancelOrder() {
		String reqUrl = url+"/order/api/order";
		ObjectMapper mapper = new ObjectMapper();
		
		String orderNo = String.valueOf(System.currentTimeMillis());
		
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("site_id", 1);
			map.put("api_key", apiKey);
			map.put("id", 8115);
			map.put("o_no", orderNo);
			map.put("symbol", "BTCFCNY");
			map.put("timestamp", getSystemTimeStamp());
			map.put("sign_type", signType);
			map.put("sign", MD5Util.createSign(map, apiSecret));
			
			String params = mapper.writeValueAsString(map);
			String response = HttpUtil.doDeleteJson(reqUrl, params);
			System.out.println(response);
//			Success：{"status":1,"msg":"success","data":100000}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 提现
	 * Withdraw
	 * 
	 * addr (string): 提币地址 ,
	 * amount (number): 提币数量 ,
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * currency (string): 币类型，如BTC、LTC、ETH ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。
	 * 
	 * addr (string): Withdrawal address,
	 * amount (number): Withdrawal amount
	 * api_key (string): api_key can applied from the Account - API management,
	 * currency (string): Name of the token/coin, such: BTC,LTC,ETH.
	 * sign (string): Results of using api_secret to sign request parameters,
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	public static void withdraw() {
		String reqUrl = url+"/order/api/withdraw";
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("addr", 0x000000000000000000000000000000);
			map.put("amount", 1);
			map.put("site_id", 1);
			map.put("api_key", apiKey);
			map.put("currency", "ETH");
			map.put("sign_type", signType);
			map.put("timestamp", getSystemTimeStamp());
			map.put("sign", MD5Util.createSign(map, apiSecret));
			
			String params = mapper.writeValueAsString(map);
			String response = HttpUtil.doPostJson(reqUrl, params);
			System.out.println(response);
//			Success：{"status":1,"msg":"success","data":1}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 获得单个币资产
	 * Get balances in a single token/coin
	 * 
	 * currency (string): 币类型，如BTC、LTC、ETH ,
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * 
	 * currency (string): Name of the token/coin, such: BTC,LTC,ETH.
	 * api_key (string): api_key can applied from the Account - API management,
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * sign (string): Results of using api_secret to sign request parameters,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	public static void getSingleCurrency() {
		String reqUrl = url+"/m/api/a/account";
//		/m/api/a/account/{currency}/{apikey}/{timestamp}/{signType}/{sign}
		

		try {
			String currency = "BTC";
			Long timestamp = getSystemTimeStamp();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			//Only 3 parameters need to be passed for participating signatures: api_key, sign_type, timestamp
			Map<String, Object> map = new HashMap<>();
			map.put("api_key", apiKey);
			map.put("sign_type", signType);
			map.put("timestamp", timestamp);
			String sign = MD5Util.createSign(map, apiSecret);
//			拼接url成 /api/a/account/1/{currency}/{apikey}/{timestamp}/{signType}/{sign}
//			Splicing URL into /api/a/account/1/{currency}/{apikey}/{timestamp}/{signType}/{sign}
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(1)
					.append("/").append(currency)
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
	 * Get the list of balances
	 * 
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 
	 * sign (string): 使用api_secret对请求参数进行签名的结果 
	 * 
	 * api_key (string): api_key can applied from the Account - API management,
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * sign (string): Results of using api_secret to sign request parameters,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	public static void getCurrencyList() {
		String reqUrl = url+"/m/api/a/accounts";
//		/m/api/a/accounts/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			Long timestamp = getSystemTimeStamp();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			//Only 3 parameters need to be passed for participating signatures: api_key, sign_type, timestamp
			Map<String, Object> map = new HashMap<>();
			map.put("api_key", apiKey);
			map.put("sign_type", signType);
			map.put("timestamp", timestamp);
			String sign = MD5Util.createSign(map, apiSecret);
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(1)
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
	 * 获得单个订单的交易明细（仅返回当前apikey对应数据）
	 * Get the transaction details of a single order (only the data corresponding to the current apikey is returned)
	 * 
	 * symbol:交易对名称，如BTCCNY、LTCCNY、ETHCNY
	 * id:订单id
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * 
	 * symbol:Name of the trading pair. Such:BTCCNY,LTCCNY,ETHCNY
	 * id:Order id
	 * api_key (string): api_key can applied from the Account - API management,
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * sign (string): Results of using api_secret to sign request parameters,
	 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	public static void getSingleOrderDetail() {
		String reqUrl = url+"/m/api/o/order/trades";
//		/m/api/o/order/trades/{symbol}/{id}/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			String symbol="BTCFCNY";
			String id="8402";
			Long timestamp = getSystemTimeStamp();
			
			Map<String, Object> map = new HashMap<>();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			//Only 3 parameters need to be passed for participating signatures: api_key, sign_type, timestamp
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
	 * Get the list of orders (return only the data corresponding to the current apikey)
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
	 * symbol: Name of the trading pair. Such as:BTCCNY,LTCCNY,ETHCNY
	 * page: Page starting from 1.
	 * pageSize: Number of records per page, max. 20, default chronological order 
	 * status 0 means unfilled (pending order), 1 means filled (including cancelled), 2 means all
	 * api_key (string): api_key can applied from the Account - API management,
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * sign (string): Results of using api_secret to sign request parameters,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	public static void getOrderList() {
		String reqUrl = url+"/m/api/o/orders";
//		/m/api/o/orders/{symbol}/{page}/{pageSize}/{status}/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			String symbol="BTCFCNY";
			int page=1;//从1开始 Starting from 1
			int pageSize=20;//最大20 Max.20
			int status=2;// 0表示未完成（挂单中），1表示已完成（含已取消），2表示所有   0 means unfilled (pending order), 1 means filled (including cancelled), 2 means all
			Long timestamp = getSystemTimeStamp();
			
			Map<String, Object> map = new HashMap<>();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			//Only 3 parameters need to be passed for participating signatures: api_key, sign_type, timestamp
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
	 * Get the records of filled orders (return only the data corresponding to the current apikey)
	 * 
	 * symbol:交易对名称，如BTCFCNY、LTCFCNY、ETHFCNY
	 * page：页数，从1开始，
	 * pageSize：每页记录数，最大20，默认按时间倒叙排列 
	 * status 0表示未完成（挂单中），1表示已完成（含已取消），2表示所有
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 
	 * 
	 * symbol: Name of the trading pair. Such:BTCFCNY,LTCFCNY,ETHFCNY
	 * page: Page starting from 1
	 * pageSize: Number of records per page, max. 20, default chronological order
	 * 0 means unfilled (pending order), 1 means filled (including cancelled), 2 means all
	 * api_key (string): api_key can applied from the Account - API management,
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * sign (string): Results of using api_secret to sign request parameters,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	public static void getSuccessedOrders() {
		String reqUrl = url+"/m/api/t/trades";
//		/m/api/t/trades/{symbol}/{page}/{pageSize}/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			String symbol="BTCFCNY";
			int page=1;//从1开始 starting from 1
			int pageSize=20;//最大20 Max.20
			Long timestamp = getSystemTimeStamp();
			
			Map<String, Object> map = new HashMap<>();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			//Only 3 parameters need to be passed for participating signatures: api_key, sign_type, timestamp
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
	 * Get system timestamp
	 * 
	 * 交易请求返回 ILLEGAL_TIMESTAMP时，使用该方法返回的时间戳
	 * url:/m/timestamp/{timestamp}
	 * request:timestamp 本地时间戳
	 * response：{"status":1,"msg":"success","data":"1529995831"}, 成功时：data值为系统时间戳
	 * 
	 * When the transaction request returns ILLEGAL_TIMESTAMP, use the timestamp returned by this method
	 * url:/m/timestamp/{timestamp}
	 * request:timestamp local timestamp
	 * response: {"status":1,"msg":"success","data":"1529995831"}, On success: data value is the system timestamp
	 */
	public static Long getSystemTimeStamp() {
		String reqUrl = url+"/m/timestamp";
		
		try {
			Long timestamp = StringUtil.getTimeStamp();
			
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(timestamp);
			
			String response = HttpUtil.doGet(sBuffer.toString());
//			System.out.println(response);
//			{"status":1,"msg":"success","data":"1529995831"}
			ObjectMapper mapper = new ObjectMapper();
			Map map = mapper.readValue(response, HashMap.class);
			return Long.valueOf(map.get("data")+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 获得系统支持的交易对
	 * Get trading pairs supported by system
	 * 交易请求返回 ILLEGAL_TIMESTAMP时，使用该方法返回的时间戳
	 * url:/m/symbols/{site_id}/{timestamp}
	 * request:timestamp 本地时间戳
	 * response：{"status":1,"msg":"success","symbols":[{...}]}, 成功时：data值为系统时间戳
	 * 
	 * When the transaction request returns ILLEGAL_TIMESTAMP, use the timestamp returned by this method
	 * url:/m/symbols/{site_id}/{timestamp}
	 * request:timestamp local timestamp
	 * response: {"status":1,"msg":"success","symbols":[{...}]}, On success: data value is system timestamp
	 */
	public static void getSymbols() {
		String reqUrl = url+"/m/symbols";
		
		try {
			Long timestamp = StringUtil.getTimeStamp();
			
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(1).append("/").append(timestamp);
			
			String response = HttpUtil.doGet(sBuffer.toString());
			System.out.println(response);
//			{"status":1,"msg":"success","symbols":[{"symbol":"FATBTC","base_currency":"FAT","quote_currency":"BTC","price_precision":8,"volume_precision":0,"taker_fee":0.00200000,"maker_fee":0.00200000}]}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获得单个订单的详情（仅返回当前apikey对应数据）
	 * Get the details of a single order (only the data corresponding to the current apikey is returned)
	 * 
	 * symbol:交易对名称，如BTCFCNY、LTCFCNY、ETHFCNY
	 * id:订单id
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * 
	 * symbol:Name of the trading pair. Such as:BTCFCNY,LTCFCNY,ETHFCNY
	 * id: Order id
	 * api_key (string): api_key can applied from the Account - API management,
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * sign (string): Results of using api_secret to sign request parameters,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	public static void getOrderDetail() {
		String reqUrl = url+"/m/api/o/order/detail";
//		/m/api/o/order/detail/{symbol}/{id}/{apikey}/{timestamp}/{signType}/{sign}
		
		try {
			String symbol="BTCFCNY";
			String id="1";
			Long timestamp = getSystemTimeStamp();
			
			Map<String, Object> map = new HashMap<>();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			//Only 3 parameters need to be passed for participating signatures: api_key, sign_type, timestamp
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
	 * 获取币充值地址
	 * Get the deposit address of the token/coin
	 * 
	 * currency (string): 币类型，如BTC、LTC、ETH ,
	 * api_key (string): api_key可以在用户中心中获取 ,
	 * timestamp (integer): 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
	 * sign_type (string): 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
	 * sign (string): 使用api_secret对请求参数进行签名的结果 ,
	 * 
	 * currency (string): Token/coin type. Such as BTC,LTC,ETH,
	 * api_key (string): api_key can applied from the Account - API management,
	 * timestamp (integer): Timestamp Note: the value taken by the partial system is milliseconds, which needs to be converted into seconds (10 digits), and the system determines that the error is within plus or minus 10 seconds as the legal timestamp.
	 * sign_type (string): The method of using api_secret to sign the request parameters, currently supports MD5, HmacSHA256, note the capitalization, the signature method is detailed in a separate link,
	 * sign (string): Results of using api_secret to sign request parameters,
	 * 
	 * apikey、timestamp、signType和sign详见签名方法
	 * For details of apikey, timestamp, signType and sign,please refer to the signature method
	 */
	public static String getDepositAddr() {
//		/m/api/a/deposit/addr/{site_id}/{currency}/{apikey}/{timestamp}/{signType}/{sign}
		String reqUrl = url+"/m/api/a/deposit/addr";
		
		String currency = "BTC";
		try {
			Long timestamp=getSystemTimeStamp();
			
			//参与签名的只需要传3个参数 api_key, sign_type, timestamp
			//Only 3 parameters need to be passed for participating signatures: api_key, sign_type, timestamp
			Map<String, Object> map = new HashMap<>();
			map.put("api_key", apiKey);
			map.put("sign_type", signType);
			map.put("timestamp", timestamp);
			String sign = MD5Util.createSign(map, apiSecret);
//			拼接url成 /api/a/account/1/{currency}/{apikey}/{timestamp}/{signType}/{sign}
//			Splicing URL into/api/a/account/1/{currency}/{apikey}/{timestamp}/{signType}/{sign}
			
			StringBuffer sBuffer = new StringBuffer(reqUrl);
			sBuffer.append("/").append(1)
			.append("/").append(currency)
			.append("/").append(apiKey)
			.append("/").append(timestamp)
			.append("/").append(signType)
			.append("/").append(sign);
			String response = HttpUtil.doGet(sBuffer.toString());
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
