package com.fatbtc.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class MD5Util {

	/**
	 * 生成32位MD5值
     * generate a 32-bit MD5 value
	 */
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getMD5String(String str) {
		try {
			if (str == null || str.trim().length() == 0) {
				return "";
			}
			byte[] bytes = str.getBytes();
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(bytes);
			bytes = messageDigest.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >> 4] + ""
						+ HEX_DIGITS[bytes[i] & 0xf]);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getMD5String(String str, int len) {
		String string = getMD5String(str);
		if(!"".equals(string)) {
			return string.substring(0, len);
		}
		return "";
	}
	
	/**
	 * 签名验证
	 * signature verification
	 * 
	 * @param params
	 * @param api_secret
	 * @return
	 */
	public static String createSign(Map<String, Object> params, String apiSecret) {
		//签名key按字母升序排序
		//signature key sorted in ascending alphabetical order
		SortedMap<String, Object> sortedMap = new TreeMap<String, Object>(params);

		StringBuffer sb = new StringBuffer();
		Set es = sortedMap.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		if ("MD5".equals(params.get("sign_type"))) {
			// MD5签名时，apiSecret放在最后
			// when signing with MD5, apiSecret comes last
			sb.append("apiSecret=" + apiSecret); 
		} else if ("HmacSHA256".equals(params.get("sign_type"))) {
			// 删除最后的&
			// delete the last &
			sb.deleteCharAt(sb.length() - 1); 
		} else {
			return null;
		}

		String valueToDigest = sb.toString();
		String actualSign = "";
		if ("MD5".equals(params.get("sign_type"))) {
			//加密字符串取28位
			//crypto string takes the first 28 bits
			actualSign = MD5Util.getMD5String(valueToDigest, 28);
		} else if ("HmacSHA256".equals(params.get("sign_type"))) {
			byte[] hash = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, apiSecret).hmac(valueToDigest);
			actualSign = Base64.encodeBase64String(hash);
			//因为HmacSHA256加密后的密文带有/等字符串导致get请求失效,这里再进行一次MD5加密
			//加密字符串取28位
			//MD5 crypto needs to perform again as the HMAC SHA256 crypto ciphertext has a /, and other strings that invalidate the get request.
			//crypto string takes the first 28 bits
			actualSign = MD5Util.getMD5String(actualSign, 28);
		}

		return actualSign;
	}

}
