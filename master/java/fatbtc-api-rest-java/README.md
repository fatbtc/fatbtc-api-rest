# FatBTC Rest Api Java版示例


## 注意事项

- 1.Api使用示例 ApiDemo.java  
``` Java
	//从FatBTC申请的 apiKey和apiSecret
	private static String apiKey="";
	private static String apiSecret="";
```
 
 
- 2.返回数据:

		所有接口返回数据格式都为  {"status":1, "msg":"success", data:""}
		
		1. status: 状态码, 1表示成功 其他表示失败 
		
		2. msg/message: 提示信息   
			success: 成功  
			ILLEGAL_TIMESTAMP_FORMAT: 时间戳格式错误  
			ILLEGAL_SIGN_TYPE: 签名方法错误  
			ILLEGAL_API_KEY: APIKey错误  
			ILLEGAL_SIGN: 签名错误  
			ILLEGAL_TIMESTAMP: 时间戳错误  
			STOP_EX: 暂停服务   
			ILLEGAL_IP: IP地址不正确    
			API_NO_PRIVILEGE: APIKey没有该权限   
			ILLEGAL_SYMBOL: 交易对不存在   
			ILLEGAL_TRADE_PAIR: 交易对不存在或未启用
			ILLEGAL_VOLUME: 订单数量不正确
			ILLEGAL_PRICE: 订单价格不正确
			ILLEGAL_PRICE_TYPE: 订单价格类型不正确（limit market）
			ILLEGAL_O_TYPE: 订单类型不正确（buy sell）
			ILLEGAL_SOURCE: 订单来源不正确（api web app）
			LITTLE_THAN_MIN_BUY_VOLUME: 数量低于最低买入数量
			LITTLE_THAN_MIN_SELL_VOLUME: 数量低于最低卖出数量
			STOP_BUY_EX: 暂停买入
			STOP_SELL_EX: 暂停卖出
			ILLEGAL_PRICE_PRECISION: 价格小数位不准确
			ILLEGAL_VOLUME_PRECISION: 数量小数位不准确
			NO_SUFFICIENT_FUNDS: 余额不足
			ILLEGAL_CURRENCY: 虚拟币不存在
			CANNOT_WITHDRAW: 当前币种暂停提现
			ILLEGAL_ADDRESS: 提现地址错误
			ILLEGAL_WITHDRAW_AMOUNT: 提现数量有误
			WITHDRAW_AMOUNT_MIN_THAN_MIN: 提现数量低于最小提现额度
			NOT_AUTH_IDENTITY: 未实名认证
			WITH_DRAW_THAN_MAX_V1: 超过24小时V1认证级别最大提现额度
			WITH_DRAW_THAN_MAX_V2: 超过24小时V2认证级别最大提现额度
			ORDER_DOES_NOT_EXIST: 订单不存在或订单号错误 
			MARKET_ORDER_CANNOT_BE_CANCELLED: 市价单不需要取消
			ORDER_IS_DONE_OR_CANCELED: 订单已取消或已完成
		
		3. data: 数据, 详见接口说明 
	
	 
 
## 接口说明
- 1.获得系统时间戳  
getSystemTimeStamp() 
  
		url:/m/timestamp/{timestamp}
		
		method:get
		
		request:timestamp 时间戳
		
		response：{
			"status":1,
			"msg":"success",
			"data":系统时间戳 
		}


- 2.创建订单  
createOrder() 

		url:/order/api/order
		
		method:post
		
		request: 
		{
			api_key: api_key可以在用户中心中获取 ,
			o_no: 订单编号，在当前交易对需唯一 ,
			o_price_type: 价格类型：limit、market，分别代表限价单、市价单 ,
			o_type: 订单类型：buy、sell，分别代表卖出、买入 ,
			price: 价格，对限价单，表示买入/卖出价格，对于市价单，请填0 ,
			sign: 使用api_secret对请求参数进行签名的结果 ,
			sign_type: 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
			symbol: 交易对名称，如BTCCNY、LTCCNY、ETHCNY ,
			timestamp: 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
			volume: 数量，对限价单，表示买入/卖出数量，对于市价买单，表示买入多少计价货币(如CNY)，市价卖单表示卖出多少基础货币(如BTC)
		}
		
		response：
		{
		  "status": 1,
		  "msg": "success",
		  "data": 订单id
		}


- 3.取消订单  
cancelOrder() 

		url:/order/api/order
		
		method:delete
		
		request:
		{
			api_key: api_key可以在用户中心中获取 ,
			id: 订单ID ,
			o_no: 订单号 ,
			sign: 使用api_secret对请求参数进行签名的结果 ,
			sign_type: 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
			symbol: 交易对名称，如BTCCNY、LTCCNY、ETHCNY ,
			timestamp: 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。
		}
		
		response：
		{
		  "status": 1,
		  "msg": "success"
		}


- 4.提现  
withdraw() 

		url:/order/api/withdraw
		
		method:post
		
		request: 
		{
			addr: 提币地址 ,
			amount: 提币数量 ,
			api_key: api_key可以在用户中心中获取 ,
			currency: 币类型，如BTC、LTC、ETH ,
			sign: 使用api_secret对请求参数进行签名的结果 ,
			sign_type: 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
			timestamp: 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。
		}
		
		response：
	    {
	      "status": 1,
	      "msg": "success",
	      "data": 提现订单id
	    }


- 5.获得单个币资产  
getSingleCurrency() 

		url:/m/api/a/account
		
		method:get
		
		request:
			currency: 币类型，如BTC、LTC、ETH ,
			api_key: api_key可以在用户中心中获取 ,
			timestamp: 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
			sign_type: 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
			sign: 使用api_secret对请求参数进行签名的结果 ,
			(apikey、timestamp、signType和sign详见签名方法)
		
		response： 
		{
			"status":1,
			"msg": "success",
			account: {
				available_balance: 可用余额，总余额-冻结余额 ,
				currency: 货币代码，如BTC ,
				frozen_balance: 冻结余额，如有挂单未成交，则有冻结余额 ,
				total_balance: 总余额。可交易余额 = 总余额 - 冻结余额
			}
		}


- 6.获得资产列表  
getCurrencyList() 

		url:/api/a/accounts/{apikey}/{timestamp}/{signType}/{sign}
		
		method:get
		
		request:
			api_key: api_key可以在用户中心中获取 ,
			timestamp: 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
			sign_type: 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
			sign: 使用api_secret对请求参数进行签名的结果 ,
			(apikey、timestamp、signType和sign详见签名方法)
			
		response：
		{
			"status":1,
			"msg": "success",
			"accounts": [{
				available_balance: 可用余额，总余额-冻结余额 ,
				currency: 货币代码，如BTC ,
				frozen_balance: 冻结余额，如有挂单未成交，则有冻结余额 ,
				total_balance: 总余额。可交易余额 = 总余额 - 冻结余额
			}],
			"currencys":[{
				c_cannot_recharge_desc_cn: 不能充值时中文描述 ,
				c_cannot_recharge_desc_en: 不能充值时英文描述 ,
				c_cannot_withdraw_desc_cn: 不能提现时中文描述 ,
				c_cannot_withdraw_desc_en: 不能提现时英文描述 ,
				c_intro_cn: 中文简要描述 ,
				c_intro_en: 英文简要描述 ,
				c_min_recharge: 最小充值额度 ,
				c_min_withdraw: 最小提现额 ,
				c_order: 排序 ,
				c_precision: 小数位数 ,
				can_recharge: 是否可充值 ,
				can_withdraw: 是否可提现 ,
				currency: 虚拟币代码 ,
				currency_name: 虚拟币名称 ,
				id: 虚拟币ID ,
				is_in_eth: 是否为以太坊架构的币 ,
				withdraw_fee: 提现手续费，分固定和百分比，详见下面的withdraw_fee_percent ,
				withdraw_fee_max: 最高提现手续费，在百分比为1时有用，为0时，表示按百分比上不封顶 ,
				withdraw_fee_min: 最低提现手续费，在百分比为1时有用 ,
				withdraw_fee_percent: 提现手续费是否按百分比，0表示固定手续费，即固定为withdraw_fee，1表示提现手续费按百分比，即提现额的百分之withdraw_fee
			}]
		}


- 7.获得单个订单的交易明细（仅返回当前apikey对应数据）  
getSingleOrderDetail() 

		url:/m/o/order/trades/{symbol}/{id}/{apikey}/{timestamp}/{signType}/{sign}
		
		method:get
		
		request:
			symbol:交易对名称，如BTCCNY、LTCCNY、ETHCNY
			id:交易id
			api_key: api_key可以在用户中心中获取 ,
			timestamp: 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
			sign_type: 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
			sign: 使用api_secret对请求参数进行签名的结果 
			(apikey、timestamp、signType和sign详见签名方法)
			
		response：
		{
			"status":1,
			"msg": "success",
			symbol: 交易对 ,
			timestamp: 时间戳 ,
			total: 总记录数 ,
			trades:[{
				base_currency: 基础货币 如ETH ,
				done_time: 成交时间 ,
				fee: 成交手续费 ,
				fee_currency: 手续费货币 ,
				price: 成交价格 ,
				quote_currency: 计价货币 如BTC ,
				t_type: 订单类型：buy 买入、sell 卖出 ,
				taker: 吃单方，取值为self(当前用户为吃单方)和opposite(对方用户为吃单方) ,
				volume: 成交数量
			}]
		}


- 8.获得订单列表（仅返回当前apikey对应数据）  
getOrderList() 

		url:/m/api/o/orders
		
		method:get
		
		request:
			symbol:交易对名称，如BTCCNY、LTCCNY、ETHCNY
			page：页数，从1开始，
			pageSize：每页记录数，最大20，默认按时间倒叙排列 
			status 0表示未完成（挂单中），1表示已完成（含已取消），2表示所有
			api_key: api_key可以在用户中心中获取 ,
			timestamp: 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
			sign_type: 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
			sign: 使用api_secret对请求参数进行签名的结果 
	 		(apikey、timestamp、signType和sign详见签名方法)
	 		
		response：
		{
			"status":1,
			"msg": "success",
			symbol: 交易对 ,
			timestamp: 时间戳 ,
			total: 总记录数,
			orders: [{
				base_currency: 基础货币 ,
				cancel_time: 撤销时间 ,
				create_time: 创建时间 ,
				done_amount: 已成交总额 ,
				done_avg_price: 已成交均价 ,
				done_fee: 已成交手续费，卖出方为计价货币，买入方为基础货币 ,
				done_volume: 已成交数量 ,
				fee_currency: 已成交手续费单位 ,
				id: id ,
				o_no: 订单号 ,
				o_price_type: 价格类型：limit、market，即限价单、市价单 ,
				o_status: watting 等待中,partial-done 部分成交,done 成交,partial-canceled 部分成交撤单,canceled 撤单 ,
				o_type: 订单类型：buy、sell，即卖单、买单 ,
				price: 价格，对限价单，表示下单时指定的价格，对于市价单，默认为0 ,
				quote_currency: 计价货币 ,
				source: 订单来源：api、Web、Wap、App ,
				volume: 数量，对限价单，表示下单时指定的数量，对于市价买单，表示买多少计价货币，市价卖单表示卖多少基础货币
			}]
		}


- 8.获得已成交记录（仅返回当前apikey对应数据）  
getSuccessedOrders() 

		url:/m/t/trades/{symbol}/{page}/{pageSize}/{apikey}/{timestamp}/{signType}/{sign}
		
		method:get
		
		request:
			symbol:交易对名称，如BTCCNY、LTCCNY、ETHCNY
			page：页数，从1开始，
			pageSize：每页记录数，最大20，默认按时间倒叙排列 
			status: 0表示未完成（挂单中），1表示已完成（含已取消），2表示所有
			api_key: api_key可以在用户中心中获取 ,
			timestamp: 时间戳，注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)，系统判定误差正负10秒内为合法时间戳。 ,
			sign_type: 使用api_secret对请求参数进行签名的方法，目前支持MD5、HmacSHA256，注意大小写，签名方法详见单独说明 ,
			sign: 使用api_secret对请求参数进行签名的结果 
			(apikey、timestamp、signType和sign详见签名方法)
		
		response:
		{
			"msg": "success",
		    "status": 1,
			symbol: 交易对 ,
			timestamp: 时间戳 ,
			total: 总记录数,
			trades:[{
				base_currency: 基础货币 如ETH ,
				done_time: 成交时间 ,
				fee: 成交手续费 ,
				fee_currency: 手续费货币 ,
				price: 成交价格 ,
				quote_currency: 计价货币 如BTC ,
				t_type: 订单类型：buy 买入、sell 卖出 ,
				taker: 吃单方，取值为self(当前用户为吃单方)和opposite(对方用户为吃单方) ,
				volume: 成交数量
			}]
		}















