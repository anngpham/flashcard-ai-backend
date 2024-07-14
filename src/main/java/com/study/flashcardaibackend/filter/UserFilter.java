package com.study.flashcardaibackend.filter;

import com.study.flashcardaibackend.constant.ErrorConstants;
import com.study.flashcardaibackend.constant.FilterAttrConstants;
import com.study.flashcardaibackend.constant.JwtConstants;
import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.user.User;
import com.study.flashcardaibackend.service.user.JwtService;
import com.study.flashcardaibackend.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class UserFilter extends OncePerRequestFilter {

    private static final String[] NOT_INCLUDED_PATHS = {PathConstants.AUTH};
    private static final String HEADER_AUTHORIZATION = "Authorization";

    private final HandlerExceptionResolver resolver;
    private final JwtService jwtService;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(SetFilter.class);


    public UserFilter(HandlerExceptionResolver resolver, JwtService jwtService, UserService userService) {
        this.resolver = resolver;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    // Allow filter running in all routes except /api/auth/login, /api/auth/register
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        for (String notIncludedPath : NOT_INCLUDED_PATHS) {
            if (path.startsWith(PathConstants.API_PREFIX + notIncludedPath)) return true;
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = this.getTokenFromRequest(request);
            String email = jwtService.extractEmail(jwtToken);
            User user = userService.getUserByEmail(email);
            if (user == null) throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.USER_NOT_FOUND);
            request.setAttribute(FilterAttrConstants.USER_ID, user.getId());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.info("Exception caught in UserFilter: {}", e.getMessage());
            resolver.resolveException(request, response, null, e);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authenHeader = request.getHeader(HEADER_AUTHORIZATION);
        if (authenHeader == null) {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.HEADER_TOKEN_NOT_FOUND);
        }
        if (!authenHeader.startsWith(JwtConstants.TOKEN_PREFIX + " ")) {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.HEADER_TOKEN_INVALID);
        }
        return authenHeader.substring(JwtConstants.TOKEN_PREFIX.length() + 1);
    }

}


