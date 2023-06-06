# fatbtc-api-rest
FatBTC Rest Api Example For Java：[https://github.com/fatbtc/fatbtc-api-rest/tree/master/master/java/fatbtc-api-rest-java](https://github.com/fatbtc/fatbtc-api-rest/tree/master/master/java/fatbtc-api-rest-java)


## Notices

- 1.Generate apikey and apiSecret on FatBTC 

 
 
- 2.Return data:		All interfaces return data in the format  {"status":1, "msg":"success", data:""}
		
		1. status: Status code: 1 is success and the others are failure 
		
		2. msg/message: error message   
			success: success  
			ILLEGAL_TIMESTAMP_FORMAT: timestamp format error  
			ILLEGAL_SIGN_TYPE: signature method error  
			ILLEGAL_API_KEY: APIKeyerror  
			ILLEGAL_SIGN: signature method error   
			ILLEGAL_TIMESTAMP: timestamp format error  
			STOP_EX: suspend service   
			ILLEGAL_IP: IP address error    
			API_NO_PRIVILEGE: APIKey has no privilege   
			ILLEGAL_SYMBOL: The trading pair does not exist			
			ILLEGAL_TRADE_PAIR:The trading pair does not exist or is not enabled			
			ILLEGAL_VOLUME: incorrect order amount			
			ILLEGAL_PRICE: incorrect order price			
			ILLEGAL_PRICE_TYPE:incorrect order price type (limit market)			
			ILLEGAL_O_TYPE: incorrect order type (buy sell)			
			ILLEGAL_SOURCE: incorrect order source (API web app)
			LITTLE_THAN_MIN_BUY_VOLUME: The amount is lower than the minimum purchase amount			
			LITTLE_THAN_MIN_SELL_VOLUME: The amount is lower than the minimum sold amount
			STOP_BUY_EX: suspend buying
			STOP_SELL_EX: suspend selling
			ILLEGAL_PRICE_PRECISION: price decimal is not accurate			
			ILLEGAL_VOLUME_PRECISION: amount decimal is not accurate			
			NO_SUFFICIENT_FUNDS: insufficient balances			
			ILLEGAL_CURRENCY: cryptocurrency does not exist
			CANNOT_WITHDRAW: withdrawals for the current cryptocurrency is suspended
			ILLEGAL_ADDRESS: wrong withdrawal address		
			ILLEGAL_WITHDRAW_AMOUNT: wrong withdrawal amount		
			WITHDRAW_AMOUNT_MIN_THAN_MIN: the withdrawal amount is less than the minimum withdrawal amount			
			NOT_AUTH_IDENTITY:unverified			
			WITH_DRAW_THAN_MAX_V1: exceeds 24H max.withdrawal for KYC verification 1
			WITH_DRAW_THAN_MAX_V2: exceeds 24H max.withdrawal for KYC verification 2

			ORDER_DOES_NOT_EXIST: order does not exist or order number is incorrect 
			MARKET_ORDER_CANNOT_BE_CANCELLED: the market order does not need to be cancelled
			ORDER_IS_DONE_OR_CANCELED:  the order has been canceled or completed
		
		3. data: data, see interface description 
	

- 3.The URL prefix in this example is: https://www.fatbtc.com (Recommended for users in mainland China access https://www.fatbtc.us)


- 4.The signature and signature method related to the interface description (apikey, timestamp, signtype, and the details of thesignature method):
[https://github.com/fatbtc/fatbtc-api-rest/blob/master/master/java/fatbtc-api-rest-java/src/com/fatbtc/util/MD5Util.java] MD5Util.createSign()
 
## Interface Description
- 1.Get the system timestamp  
getSystemTimeStamp() 
  
		url:/m/timestamp/{timestamp}
		
		method:get
		
		request:timestamp timestamp
		
		response：{
			"status":1,
			"msg":"success",
			"data": system timestamp 
		}
		
		Note: when the transaction request returns ILLEGAL_TIMESTAMP, the timestamp returned by this method is used to substitute the request parameter

- 2.Get the trading pair supported by the system   
getSymbols() 
  
		url:/m/symbols/{site_id}/{timestamp}
		
		method:get
                
		request:
			site_id: 1, 
			timestamp: timestamp
		
		response：
		{
			"status":1,
			"msg":"success",
			"symbols":
			[{
				"symbol":trading pair,
				"base_currency": base cryptocurrency,
				"quote_currency": quote_currency,
				"price_precision": the decimal place of the price,
				"volume_precision": the decimal place of the amount				
				"taker_fee": fee for taker,
				"maker_fee": fee for marker			
			}]
		}
			

- 3.Place an order  
createOrder() 

		url:/order/api/order
		
		method:post
		
		request: 
		{
			api_key: api_key can generate the API key in Account,			
			site_id: 1,
			o_no: order number, unique in current transaction pair,			
			o_price_type: price type: limit, market, respectively represent limit order, market order,
			o_type: order type: buy, sell, which means sell, buy,			
			price: price: for the limit order, it means the buy/sell price. For the market order, please fill in 0
			sign: the result of signing the request parameters using api_secret,
			sign_type: use api_secret to sign the request parameters.Currently, MD5 and HmacSHA256 are supported.Please note that they are case sensitive, and the signature method is described separately.			
			symbol: the name of the trading pair,such as BTCCNY, LTCCNY, ETHCNY,
			timestamp: timestamp: note: some of the values taken by the system are in the level of milliseconds, which need to be converted into seconds (10 digits). The system determines that the error of plus or minus 10 seconds is the legal timestamp.			
			volume: aoumt, for the limit order, indicating the number of buy/sell, for the market price, indicating how many cryptocurrency to buy (such as CNY), the market price to sell how many base cryptocurrency (such as BTC)
		}
		
		response：
		{
		  "status": 1,
		  "msg": "success",
		  "data": order id		
		}


- 4.Cancel the order  
cancelOrder() 

		url:/order/api/order
		
		method:delete
		
		request:
		{
			api_key: api_key can be generated in Account section on FatBTC.
			id: order ID
			o_no: order number,			
			site_id: 1,
			sign: the result of signing the request parameter with api_secret,			
			sign_type:Use api_secret to sign the request parameters,which is currently supporting MD5, HmacSHA256.Please note that they are case sensitive, and the signature method is described separately.			
			symbol: the name of the trading pair，such as BTCCNY、LTCCNY、ETHCNY ,
			timestamp:timestamp, note: Some systems take the value of milliseconds and need to convert to seconds (10 digits). The system determines that the error is within 10 seconds of the legal timestamp.
		}
		
		response：
		{
		  "status": 1,
		  "msg": "success"
		}


- 5.withdraw  
withdraw() 

		url:/order/api/withdraw
		
		method:post
		
		request: 
		{
			addr: withdraw address
			amount: withdraw amount			
			api_key: api_key can generate the API key in Account,			
			site_id: 1,
			currency: cryptocurrency type, such as BTC,LTC,ETH,
			sign: the result of signing the request parameter with api_secret,
			sign_type: use api_secret to sign the request parameters.Currently, MD5 and HmacSHA256 are supported, and the signature method is described separately.
			timestamp: timestamp, note:some of the values taken by the system are in the millisecond level and need to be converted into seconds (10 digits). The system determines that the error of plus or minus 10 seconds is the legal timestamp.
		}
		
		response：
	    {
	      "status": 1,
	      "msg": "success",
	      "data": withdraw order id
	    }


- 6.get a single cryptocurrency asset  
getSingleCurrency() 

		url:/m/api/a/account/{site_id}/{currency}/{api_key}/{timestamp}/{sign_type}/{sign}
		
		method:get
		
		request:
			site_id: 1,
			currency: cryptocurrency type, such as BTC,LTC,ETH,
			api_key: api_key can generate the API key in Account,
			timestamp: timestamp, note: some of the vaule system to take the value of milliseconds, need to be converted into seconds (10 digits), the system determines the error of plus or minus 10 seconds as the legal timestamp. ,
			sign_type: Use api_secret to sign the request parameters. Currently, MD5 and HmacSHA256 are supported, and the signature method is described separately.
			sign: the result of signing the request parameter with api_secret,
      (apikey, timestamp, signType, and sign are detailed in the signature method)
		
		response： 
		{
			"status":1,
			"msg": "success",
			account: {
				available_balance: available balance, total balance-in order,
				currency: cryptocurrency symbol, such as BTC,
				frozen_balance: in order balance. If there is a pending order, there will be a in-order balance.
				total_balance: total balance. available balance = total balance - in order balance
			}
		}


- 7.get a list of assets  
getCurrencyList() 

		url:/m/api/a/accounts/{site_id}/{apikey}/{timestamp}/{signType}/{sign}
		
		method:get
		
		request:
			site_id: 1,
			api_key: api_key can generate the API key in Account,
			timestamp: timestamp, note: some of the vaule system to take the value of milliseconds, need to be converted into seconds (10 digits), the system determines the error of plus or minus 10 seconds as the legal timestamp. 
			sign_type: Use api_secret to sign the request parameters. Currently, MD5 and HmacSHA256 are supported, and the signature method is described separately.
			sign: the result of signing the request parameter with api_secret,
			(apikey, timestamp, signType, and sign are detailed in the signature method)
			
		response：
		{
			"status":1,
			"msg": "success",
			"accounts": [{
				available_balance: available balance, total balance-in order,
				currency: cryptocurrency symbol, such as BTC,
				frozen_balance:in order balance. If there is a pending order, there will be a in-order balance.
				total_balance: total balance. available balance = total balance - in order balance
			}],
			"currencys":[{
				c_cannot_recharge_desc_cn: can not make a deposit when the Chinese description,
				c_cannot_recharge_desc_en: can not make a deposit when the Englsih description,
				c_cannot_withdraw_desc_cn: can not submit a withdrawal request when the Chinese description,
				c_cannot_withdraw_desc_en: can not submit a withdrawal request when the Englsih description,
				c_intro_cn: a brief description of Chinese,
				c_intro_en: a brief description of English,
				c_min_recharge: minimum deposit amount,
				c_min_withdraw: minimum withdrawal amount,
				c_order: sort,
				c_precision: the number of decimal places,
				can_recharge: Whether it can make a deposit,
				can_withdraw: Whether it can submit a withdrawal request,
				currency: cryptocurrency symbol ,
				currency_name: the name of the cryptocurrency,
				id: cryptocurrency ID ,
				is_in_eth: Whether it is an ERC 20 token,
				withdraw_fee: withdrawal fee, fixed and percentage, see withdraw_fee_percent below,
				withdraw_fee_max: the maximum withdrawal fee is applied when the percentage is 1. When it is 0, it means that the percentage is not capped.
				withdraw_fee_min: minimum withdrawal fee, be applied when the percentage is 1.
				withdraw_fee_percent: Whether the withdrawal fee is a percentage, 0 means a fixed fee, which is fixed as withdraw_fee, 1 means the withdrawal fee is percentage, that is, the withdrawal amount is withdraw_fee
			}]
		}


- 8.get the transaction details of a single order (only return the corresponding data of the current apikey)  
getSingleOrderDetail() 

		url:/m/api/o/order/trades/{symbol}/{id}/{apikey}/{timestamp}/{signType}/{sign}
		
		method:get
		
		request:
			symbol:the name of the trading pair, such as BTCCNY,LTCCNY,ETHCNY
			id:order id
			api_key: api_key can generate the API key in Account, 
			timestamp: timestamp, note: some of the vaule system to take the value of milliseconds, need to be converted into seconds (10 digits), the system determines the error of plus or minus 10 seconds as the legal timestamp. ,
			sign_type: Use api_secret to sign the request parameters. Currently, MD5 and HmacSHA256 are supported, and the signature method is described separately.
			sign: the result of signing the request parameter with api_secret ,
      (apikey, timestamp, signType, and sign are detailed in the signature method)
			
		response：
		{
			"status":1,
			"msg": "success",
			symbol: trading pair ,
			timestamp: timestamp ,
			total: total records,
			trades:[{
				base_currency: base cryptocurrency, such as ETH,
				done_time: executed time,
				fee: executed fee,
				fee_currency: cryptocurrencies charged as fees,
				price: executed price ,
				quote_currency: quote cryptocurrency, such as BTC,
				t_type: order type：buy buy, sell sell,
				taker: taker, which takes the values of self(current user is a taker) and opposite(opposite user is a marker),
				volume: excuted amount
			}]
		}


- 9.Get the order list (only return the corresponding data of the current apikey)  
getOrderList() 

		url:/m/api/o/orders
		
		method:get
		
		request:
			symbol:the name of the trading pair, such as BTCCNY, LTCCNY, ETHCNY
			page：page, starting as 1
			pageSize：number of records per page, up to 20, arranged in reverse chronological order by default
			status 0 is pending (open order), 1 is (including canceled), 2 is all
			api_key: api_key can generate the API key in Account,
			timestamp: timestamp, note: some of the vaule system to take the value of milliseconds, need to be converted into seconds (10 digits), the system determines the error of plus or minus 10 seconds as the legal timestamp. ,
			sign_type: Use api_secret to sign the request parameters. Currently, MD5 and HmacSHA256 are supported, and the signature method is described separately,
			sign: the result of signing the request parameter with api_secret
	 		(apikey, timestamp, signType, and sign are detailed in the signature method)
	 		
		response：
		{
			"status":1,
			"msg": "success",
			symbol: trading pair
			timestamp: timestamp,
			total: total records,
			orders: [{
				base_currency: base cryptocurrency
				cancel_time: cancel time ,
				create_time: create time ,
				done_amount: executed amount,
				done_avg_price: average price of executed transaction,
				done_fee: transaction fee, the seller is the currency of the calculation, and the buyer is the base cryptocurrency,
				done_volume: executed amount,
				fee_currency: the transaction fee unit,
				id: id ,
				o_no: order number
				o_price_type: price type：limit, market,is limit order, market order
				o_status: watting pending,partial-done partial-completed,done completed,partial-canceled partial-canceled,canceled cancel,
				o_type: order type：buy, sell is sell order,buy order,
				price: price, for the limit order, indicating the price specified when placing the order, for the market order, the default is 0,
				quote_currency: quote cryptocurrency,
				source: Order source：api, Web, Wap, App,
				volume: amount, for the limit order, indicating the amount specified when placing the order, for the market order, it means how many cryptocurrency to buy, and the market price indicates how many the base cryptocurrency is sold
			}]
		}


- 10.Get the transaction record (only return the corresponding data of the current apikey)  
getSuccessedOrders() 

		url:/m/api/t/trades/{symbol}/{page}/{pageSize}/{apikey}/{timestamp}/{signType}/{sign}
		
		method:get
		
		request:
			symbol:the name of the trading pair, such as BTCCNY, LTCCNY, ETHCNY
			page：page, starting from 1,
			pageSize：number of records per page, up to 20, arranged in reverse chronological order by default
			status: 0 is in pending (open order), 1 is completed (including cancelled), 2 is all
			api_key: api_key can generate the API key in Account,
			timestamp:  timestamp, note: some of the vaule system to take the value of milliseconds, need to be converted into seconds (10 digits), the system determines the error of plus or minus 10 seconds as the legal timestamp. ,
			sign_type: Use api_secret to sign the request parameters. Currently, MD5 and HmacSHA256 are supported, and the signature method is described separately.
			sign: the result of signing the request parameter with api_secret ,
			(apikey, timestamp, signType, and sign are detailed in the signature method)
		
		response:
		{
			"msg": "success",
		    	"status": 1,
			symbol: trading pair,
			timestamp: timestampe
			total: total records,
			trades:[{
				base_currency: base cryptocurrency such as ETH,
				done_time: executed time,
				fee: executed fee,
				fee_currency: cryptocurrencies charged as fees,
				price: executed price
				quote_currency: quote cryptocurrency, such as BTC,
				t_type: order type：buy buy order, sell sell order,
				taker: taker, which takes the values of self(current user is a taker) and opposite(opposite user is a marker),
				volume: executed amount,
			}]
		}

- 11.Get the details of a single order(only return the corresponding data of the current apikey)  
getOrderDetail() 

		url:/m/api/o/order/detail/{symbol}/{id}/{apikey}/{timestamp}/{signType}/{sign}
		
		method:get
		
		request:
			 symbol:the name of the trading pair, such as BTCCNY, LTCCNY, ETHCNY
			 id:id
			 api_key: api_key can generate the API key in Account,
			timestamp:  timestamp, note: some of the vaule system to take the value of milliseconds, need to be converted into seconds (10 digits), the system determines the error of plus or minus 10 seconds as the legal timestamp. ,
			sign_type: Use api_secret to sign the request parameters. Currently, MD5 and HmacSHA256 are supported, and the signature method is described separately.
			sign: the result of signing the request parameter with api_secret ,
			(apikey, timestamp, signType, and sign are detailed in the signature method)
		response:
		{
			"msg": "success",
			"status": 1,
			order:{
				id: id ,
				o_no: order number ,
				base_currency: base cryptocurrency ,
				cancel_time: cancel time ,
				create_time: create time ,
				create_time_unix: create timestamp ,
				done_amount: executed amount,
				done_avg_price: average price of executed transaction,
				done_fee: transaction fee, the seller is the currency of the calculation, and the buyer is the base cryptocurrency,
				done_volume: executed amount,
				fee_currency:  cryptocurrencies charged as fees,
				o_price_type: price type：limit, market,is limit order, market order
				o_status: watting pending,partial-done partial-completed,done completed,partial-canceled partial-canceled,canceled cancel,
				o_type: order type：buy buy order, sell sell order,
				price: price, for the limit order, indicating the price specified when placing the order, for the market order, the default is 0,,
				quote_currency: quote currency ,
				source: Order source：api, Web, Wap, App,
				volume: amount, for the limit order, indicating the amount specified when placing the order, for the market order, it means how many cryptocurrency to buy, and the market price indicates how many the base cryptocurrency is sold
			}
			timestamp: timestampe
			
		}













