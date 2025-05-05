package org.zerock.study.global.jwtToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.study.domain.entity.Members;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 인증 없이 통과할 경로
        if (requestURI.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String bearer = request.getHeader(AUTH_HEADER);

            if (bearer == null || !bearer.startsWith(BEARER_PREFIX)) {
                throw new IllegalArgumentException("Missing or malformed Authorization header");
            }

            String token = bearer.substring(BEARER_PREFIX.length()).trim();
            Members member = jwtTokenProvider.getAuthentication(token);
            MemberContext.set(member);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } finally {
            MemberContext.clear();
        }
    }
}
