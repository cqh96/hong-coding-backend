package cn.sheeranpj.blog.gateway.util;

import cn.sheeranpj.blog.gateway.config.NacosConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

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

    public static String parseTokenMono(NacosConfig config) {
        return Jwts.builder()
                .setSubject("user1")
                .signWith(SignatureAlgorithm.HS256, config.getSecretBytes())
                .compact();
    }

}
