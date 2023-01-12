package com.playlist.cassette.config.security.jwt;

import com.playlist.cassette.handler.exception.ExceptionCode;
import com.playlist.cassette.handler.exception.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            JwtValidationType jwtValidationType = jwtTokenProvider.validateToken(jwt);
            if (StringUtils.hasText(jwt) && jwtValidationType == JwtValidationType.VALID_JWT) {
                Long userId = jwtTokenProvider.getUserFromJwt(jwt);

                UserAuthentication authentication = new UserAuthentication(userId, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                request.setAttribute("UnAuthorization", jwtTokenProvider.validateToken(jwt));
//                throwErrorMessage(jwtTokenProvider.validateToken(jwt));
            }

        } catch (Exception exception) {
            log.error("error: ", exception);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    private void throwErrorMessage(JwtValidationType jwtValidationType) {
        if (jwtValidationType.equals(JwtValidationType.EMPTY_JWT)) {
            throw new UserException(ExceptionCode.EMPTY_TOKEN, ExceptionCode.EMPTY_TOKEN.getMessage());
        }
    }
}
