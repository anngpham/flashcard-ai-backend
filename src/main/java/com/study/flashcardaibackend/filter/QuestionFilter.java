package com.study.flashcardaibackend.filter;

import com.study.flashcardaibackend.constant.ErrorConstants;
import com.study.flashcardaibackend.constant.FilterAttrConstants;
import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.exception.HttpRuntimeException;
import com.study.flashcardaibackend.model.question.Question;
import com.study.flashcardaibackend.service.question.QuestionService;
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

public class QuestionFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(QuestionFilter.class);
    private final HandlerExceptionResolver resolver;
    private final QuestionService questionService;


    public QuestionFilter(HandlerExceptionResolver resolver, QuestionService questionService) {
        this.resolver = resolver;
        this.questionService = questionService;
    }

    private boolean isQuestionDetailRoute(HttpServletRequest request) {
        boolean isContainQuestionPath = request.getRequestURI().contains(PathConstants.QUESTION);
        if (!isContainQuestionPath) return false;

        boolean isEndWithQuestionRoute = request.getRequestURI().endsWith(PathConstants.QUESTION);
        if (isEndWithQuestionRoute) return false;

        return true;
    }

    // Allow filter running in all /api/sets/*/questions/* routes except: /api/sets/*/questions/
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !this.isQuestionDetailRoute(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            System.out.println("QuestionFilter.doFilterInternal");
            UUID setId = getSetId(request);
            UUID questionId = getQuestionId(request);
            validateQuestion(setId, questionId);
            request.setAttribute(FilterAttrConstants.QUESTION_ID, questionId);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.info("Exception caught in QuestionFilter: {}", e.getMessage());
            resolver.resolveException(request, response, null, e);
        }
    }

    private UUID getSetId(HttpServletRequest request) {
        UUID setId = (UUID) request.getAttribute(FilterAttrConstants.SET_ID);
        if (setId == null) {
            throw new HttpRuntimeException(HttpStatus.UNAUTHORIZED, ErrorConstants.SET_NOT_FOUND);
        }
        return setId;
    }

    private UUID getQuestionId(HttpServletRequest request) {
        String questionIdStr = StringUtil.extractString(request.getRequestURI(), PathConstants.QUESTION + "/", null);
        if (questionIdStr == null) {
            throw new HttpRuntimeException(HttpStatus.BAD_REQUEST, ErrorConstants.QUESTION_NOT_FOUND);
        }
        UUID questionId = StringUtil.tryParseUUID(questionIdStr);
        if (questionId == null) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.API_NOT_FOUND);
        }
        return questionId;
    }


    private void validateQuestion(UUID setId, UUID questionId) {
        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.QUESTION_NOT_FOUND);
        }
        if (!question.getSetId().equals(setId)) {
            throw new HttpRuntimeException(HttpStatus.FORBIDDEN, ErrorConstants.QUESTION_NOT_BELONG_TO_SET);
        }
        if (question.getIsDeleted()) {
            throw new HttpRuntimeException(HttpStatus.NOT_FOUND, ErrorConstants.QUESTION_IS_DELETED);
        }
    }

}


