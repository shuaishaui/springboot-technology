package com.shuai.commons;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * JWT工具
 */
public class JWTUtils {
	
	// 服务器的key。用于做加解密的key数据。 如果可以使用客户端生成的key。当前定义的常亮可以不使用。
	private static final String JWT_SECERT = "test_jwt_secert" ;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	public static final int JWT_ERRCODE_EXPIRE = 1005;//Token过期
	public static final int JWT_ERRCODE_FAIL = 1006;//验证不通过

	public static SecretKey generalKey() {
		try {
			// byte[] encodedKey = Base64.decode(JWT_SECERT); 
			// 不管哪种方式最终得到一个byte[]类型的key就行
			byte[] encodedKey = JWT_SECERT.getBytes("UTF-8");
		    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		    return key;
		} catch (Exception e) {
			e.printStackTrace();
			 return null;
		}
	}
	/**
	 * 签发JWT，创建token的方法。
	 * @param id  jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
	 * @param iss jwt签发者
	 * @param subject jwt所面向的用户。payload中记录的public claims。当前环境中就是用户的登录名。
	 * @param ttlMillis 有效期,单位毫秒
	 * @return token， token是一次性的。是为一个用户的有效登录周期准备的一个token。用户退出或超时，token失效。
	 * @throws Exception
	 */
	public static String createJWT(String id,String iss, String subject, long ttlMillis) {
		// 加密算法
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		// 当前时间。
		long nowMillis = System.currentTimeMillis();
		// 当前时间的日期对象。
		Date now = new Date(nowMillis);
		SecretKey secretKey = generalKey();
		// 创建JWT的构建器。 就是使用指定的信息和加密算法，生成Token的工具。
		JwtBuilder builder = Jwts.builder()
				.setId(id)  // 设置身份标志。就是一个客户端的唯一标记。 如：可以使用用户的主键，客户端的IP，服务器生成的随机数据。
				.setIssuer(iss)
				.setSubject(subject)
				.setIssuedAt(now) // token生成的时间。
				.signWith(signatureAlgorithm, secretKey); // 设定密匙和算法
		if (ttlMillis >= 0) { 
			long expMillis = nowMillis + ttlMillis;
			Date expDate = new Date(expMillis); // token的失效时间。
			builder.setExpiration(expDate);
		}
		return builder.compact(); // 生成token
	}
	
	/**
	 * 验证JWT
	 * @param jwtStr
	 * @return
	 */
	public static JWTResult validateJWT(String jwtStr) {
		JWTResult checkResult = new JWTResult();
		Claims claims = null;
		try {
			claims = parseJWT(jwtStr);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) { // token超时
			checkResult.setErrCode(JWT_ERRCODE_EXPIRE);
			checkResult.setSuccess(false);
		} catch (SignatureException e) { // 校验失败
			checkResult.setErrCode(JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setErrCode(JWT_ERRCODE_FAIL);
			checkResult.setSuccess(false);
		}
		return checkResult;
	}
	
	/**
	 * 
	 * 解析JWT字符串
	 * @param jwt 就是服务器为客户端生成的签名数据，就是token。
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(jwt)
			.getBody(); // getBody获取的就是token中记录的payload数据。就是payload中保存的所有的claims。
	}
	
	/**
	 * 生成subject信息
	 * @param subObj - 要转换的对象。
	 * @return java对象->JSON字符串出错时返回null
	 */
	public static String generalSubject(Object subObj){
		try {
			return MAPPER.writeValueAsString(subObj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
