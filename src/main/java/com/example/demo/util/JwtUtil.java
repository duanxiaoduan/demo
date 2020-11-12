package com.example.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author duanxiaoduan
 * @version 2019-05-10
 */
public class JwtUtil {
    static final String SECRET = "ThisIsASecret";

    public static String generateToken(String username) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put("username", username);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000_000L))// 1000 hour
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return "Bearer " + jwt; //jwt前面一般都会加Bearer
    }

    public static void validateToken(String token) {
        try {
            // parse the token.
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid Token. " + e.getMessage());
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        String str = null;
        final FileInputStream fileInputStream = new FileInputStream(str);


        System.out.println(1);
    }
}
