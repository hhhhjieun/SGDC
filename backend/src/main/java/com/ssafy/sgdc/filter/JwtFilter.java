package com.ssafy.sgdc.filter;

import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import com.ssafy.sgdc.util.JwtUtil;
import com.ssafy.sgdc.util.response.Code;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        if(jwt == null){
            logger.debug("토큰이 없습니다.");
            request.setAttribute("errorCode",Code.NULL_JWT_TOKEN);
        }
        else { //토큰 유효

            try {
                String loginId = jwtUtil.getUsernameFromToken(jwt);
                User user = userRepo.findByLoginId(loginId)
                        .orElseThrow(() -> new RuntimeException("doFilterInternal -> 해당 유저를 찾을 수 없습니다."));

                System.out.println(user.toString());
                // TODO: user != null 수정
                if (user != null && jwtUtil.validateToken(jwt, user)) {
                    System.out.println(user.getAuthorities());
                    // authentication하기

                    UsernamePasswordAuthenticationToken securityToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    securityToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(securityToken);
                    logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다. uri:{}}", requestURI);
                }
                //토큰 X
                else {
                    logger.debug("유효한 JWT토큰이 없습니다. uri: {}", requestURI);
                    request.setAttribute("errorCode", Code.INVALID_TOKEN);
                }
            }
            catch (ExpiredJwtException e){
                request.setAttribute("errorCode", Code.EXPIRED_TOKEN);
            }
            catch (Exception e){
                request.setAttribute("errorCode", Code.INVALID_TOKEN);
            }
        }

        filterChain.doFilter(request,response);
    }



    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;

    }
}
