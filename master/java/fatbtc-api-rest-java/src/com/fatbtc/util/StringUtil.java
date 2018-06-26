package com.fatbtc.util;

public class StringUtil {
	  /**
	   * 取得当前时间戳（精确到秒）
	   * @return nowTimeStamp
	   */
	  public static Long getTimeStamp() {
	    long time = System.currentTimeMillis();
	    Long timestamp = Long.parseLong(String.valueOf(time / 1000));
	    return timestamp;
	  }
}
