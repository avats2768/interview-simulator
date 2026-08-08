    package com.simulator.backend.security;

    import com.simulator.backend.user.UserEntity;
    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    import javax.crypto.SecretKey;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.function.Function;

    @Service
    public class JwtService {

        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private long jwtExpiration;

        /**
         * Generate JWT Token
         */
        public String generateToken(UserEntity user) {

            Map<String, Object> claims = new HashMap<>();

            claims.put("uuid", user.getUuid());
            claims.put("role", user.getRole());

            return createToken(claims, user.getEmail());
        }

        /**
         * Create Token
         */
        private String createToken(
                Map<String, Object> claims,
                String subject
        ) {

            return Jwts.builder()
                    .claims(claims)
                    .subject(subject)
                    .issuedAt(new Date())
                    .expiration(
                            new Date(
                                    System.currentTimeMillis()
                                            + jwtExpiration
                            )
                    )
                    .signWith(getSigningKey())
                    .compact();
        }

        /**
         * Secret Key
         */
        private SecretKey getSigningKey() {

            return Keys.hmacShaKeyFor(
                    Decoders.BASE64.decode(secret)
            );
        }

        /**
         * Extract Email
         */
        public String extractEmail(String token) {

            return extractClaim(
                    token,
                    Claims::getSubject
            );
        }

        /**
         * Extract UUID
         */
        public String extractUuid(String token) {

            return extractAllClaims(token)
                    .get("uuid", String.class);
        }

        /**
         * Extract Role
         */
        public String extractRole(String token) {

            return extractAllClaims(token)
                    .get("role", String.class);
        }

        /**
         * Validate Token
         */
        public boolean isTokenValid(
                String token,
                UserEntity user
        ) {

            String email = extractEmail(token);

            return email.equals(user.getEmail())
                    && !isTokenExpired(token);
        }

        /**
         * Check Expiration
         */
        private boolean isTokenExpired(
                String token
        ) {

            return extractExpiration(token)
                    .before(new Date());
        }

        /**
         * Extract Expiration
         */
        private Date extractExpiration(
                String token
        ) {

            return extractClaim(
                    token,
                    Claims::getExpiration
            );
        }

        /**
         * Generic Claim
         */
        public <T> T extractClaim(
                String token,
                Function<Claims, T> resolver
        ) {

            Claims claims =
                    extractAllClaims(token);

            return resolver.apply(claims);
        }

        /**
         * Read Claims
         */
        private Claims extractAllClaims(
                String token
        ) {

            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }

    }