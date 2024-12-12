package org.example.labreservationsystem.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.example.labreservationsystem.exception.Code;
import org.example.labreservationsystem.exception.XException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
public class JWTComponent {
//    private String token;
    @Value("${my.secretkey}")
    private String secretkey;
//   属性初始化时完成不了注入 需要组件创建完成之后塞进去
//   private final Algorithm algorithm = Algorithm.HMAC256(secretkey);
    private Algorithm algorithm;
    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secretkey);
    }
    public String encode(Map<String,Object> map){
        LocalDateTime time = LocalDateTime.now().plusDays(1);
        return JWT.create()
                .withPayload(map)
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
    }
    public DecodedJWT decode(String token) {
        try {
            return JWT.require(algorithm).build().verify(token);
        } catch (TokenExpiredException | SignatureVerificationException e) {
            if (e instanceof SignatureVerificationException) {
                throw XException.builder().code(Code.FORBIDDEN).build();
            }
            throw XException.builder().code(Code.TOKEN_EXPIRED).build();
        }
    }
}
