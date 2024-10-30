package com.entradas.sociosFutbol.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        logger.info("=== Starting Request Filter ===");
        logger.info("Request URI: " + request.getRequestURI());

        final String requestTokenHeader = request.getHeader("Authorization");
        logger.info("Token Header: " + requestTokenHeader);

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                // Decodificar el token para debug
                String[] chunks = jwtToken.split("\\.");
                Base64.Decoder decoder = Base64.getUrlDecoder();

                String header = new String(decoder.decode(chunks[0]));
                String payload = new String(decoder.decode(chunks[1]));

                logger.info("Token Header: " + header);
                logger.info("Token Payload: " + payload);

                username = jwtUtil.extractUsername(jwtToken);
                logger.info("Extracted Username: " + username);
            } catch (Exception e) {
                logger.error("Failed to decode token", e);
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                logger.info("Attempting to validate token...");
                if (jwtUtil.validateToken(jwtToken)) {
                    logger.info("Token is valid, setting up authentication");

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext()
                            .setAuthentication(usernamePasswordAuthenticationToken);

                    logger.info("Authentication set successfully");
                } else {
                    logger.error("Token validation failed");
                }
            } catch (Exception e) {
                logger.error("Token validation error", e);
            }
        }

        logger.info("=== Continuing Filter Chain ===");
        chain.doFilter(request, response);
    }
}

