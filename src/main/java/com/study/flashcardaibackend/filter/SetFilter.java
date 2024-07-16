package com.study.flashcardaibackend.filter;

import com.study.flashcardaibackend.constant.ErrorConstants;
import com.study.flashcardaibackend.constant.FilterAttrConstants;
import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.set.Set;
import com.study.flashcardaibackend.service.set.SetService;
import com.study.flashcardaibackend.util.StringUtil;
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
import java.util.UUID;

public class SetFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(SetFilter.class);
    private final HandlerExceptionResolver resolver;
    private final SetService setService;


    public SetFilter(HandlerExceptionResolver resolver, SetService setService) {
        this.resolver = resolver;
        this.setService = setService;
    }

    private boolean isSetDetailRoute(HttpServletRequest request) {

        boolean isEndWithSetRoute = request.getRequestURI().endsWith(PathConstants.SET);
        return !isEndWithSetRoute;
    }

    // Allow filter running in all /api/sets/* routes except: /api/sets
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !this.isSetDetailRoute(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("SetFilter.doFilterInternal");
            UUID userId = getUserId(request);
            UUID setId = getSetId(request);
            validateSet(userId, setId);
            request.setAttribute(FilterAttrConstants.SET_ID, setId);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.info("Exception caught in SetFilter: {}", e.getMessage());
            resolver.resolveException(request, response, null, e);
        }
    }

    private UUID getUserId(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute(FilterAttrConstants.USER_ID);
        if (userId == null) {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.USER_NOT_FOUND);
        }
        return userId;
    }

    private UUID getSetId(HttpServletRequest request) {
        String setIdStr = StringUtil.extractString(request.getRequestURI(), PathConstants.SET + "/", PathConstants.QUESTION);
        if (setIdStr == null) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.SET_NOT_FOUND);
        }
        UUID setId = StringUtil.tryParseUUID(setIdStr);
        if (setId == null) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.API_NOT_FOUND);
        }
        return setId;
    }

    private void validateSet(UUID userId, UUID setId) {
        Set set = setService.getSet(setId);
        if (set == null) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.SET_NOT_FOUND);
        }
        if (!set.getOwnerId().equals(userId)) {
            throw new HttpRuntimeException(HttpStatus.FORBIDDEN, ErrorConstants.SET_NOT_BELONG_TO_USER);
        }
        if (set.getIsDeleted()) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.SET_IS_DELETED);
        }
    }

}


