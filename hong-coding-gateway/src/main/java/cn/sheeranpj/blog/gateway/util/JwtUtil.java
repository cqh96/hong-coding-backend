package cn.sheeranpj.blog.gateway.util;

import cn.sheeranpj.blog.gateway.config.NacosConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sheeran
 * @version 1.0
 */
public class JwtUtil {

    public static Claims parseToken(String token, NacosConfig config) throws SignatureException {
        return Jwts.parser()
                .setSigningKey(config.getSecretBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public static String parseTokenMono(String userName, NacosConfig config) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, config.getSecretBytes())
                .compact();
    }

}
