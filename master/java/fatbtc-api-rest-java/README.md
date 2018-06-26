# FatBTC Rest Api Java版示例


## 注意事项

-1.Api使用示例 ApiDemo.java
``` java
	//从FatBTC申请的 apiKey和apiSecret
	private static String apiKey="";
	private static String apiSecret="";
```

+2.返回数据:
  所有接口返回数据格式都为
  {"status":1,"msg":"success",data:""}

  status: 状态码, 1成功 -1失败 500失败

  msg/message: 提示信息  
  success: 成功  
  ILLEGAL_TIMESTAMP_FORMAT: 时间戳格式错误  
  ILLEGAL_SIGN_TYPE: 签名方法错误  
  ILLEGAL_API_KEY: APIKey错误  
  ILLEGAL_SIGN: 签名错误  
  ILLEGAL_TIMESTAMP: 时间戳错误  

  data: 数据, 详见接口说明



## 接口说明
  **1.getSystemTimeStamp**















