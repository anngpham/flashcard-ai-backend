package com.study.flashcardaibackend.configuration;


import com.study.flashcardaibackend.constant.PathConstants;
import com.study.flashcardaibackend.filter.QuestionFilter;
import com.study.flashcardaibackend.filter.SetFilter;
import com.study.flashcardaibackend.filter.UserFilter;
import com.study.flashcardaibackend.service.question.QuestionService;
import com.study.flashcardaibackend.service.set.SetService;
import com.study.flashcardaibackend.service.user.JwtService;
import com.study.flashcardaibackend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class FilterConfig {

    private final HandlerExceptionResolver resolver;
    private final JwtService jwtService;
    private final UserService userService;
    private final SetService setService;
    private final QuestionService questionService;


    @Autowired
    public FilterConfig(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
            JwtService jwtService, UserService userService, SetService setService, QuestionService questionService) {
        this.resolver = resolver;
        this.jwtService = jwtService;
        this.userService = userService;
        this.setService = setService;
        this.questionService = questionService;
    }

    @Bean
    public FilterRegistrationBean<UserFilter> userFilter() {
        FilterRegistrationBean<UserFilter> userFilterRegistrationBean
                = new FilterRegistrationBean<>();

        userFilterRegistrationBean.setFilter(new UserFilter(resolver, jwtService, userService));
        userFilterRegistrationBean.addUrlPatterns("/*"); // all endpoints
        userFilterRegistrationBean.setOrder(1); // run first

        return userFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<SetFilter> setFilter() {
        FilterRegistrationBean<SetFilter> setFilterRegistrationBean
                = new FilterRegistrationBean<>();

        setFilterRegistrationBean.setFilter(new SetFilter(resolver, setService));
        setFilterRegistrationBean.addUrlPatterns(PathConstants.SET + "/*"); // all set endpoints and sub-endpoints
        setFilterRegistrationBean.setOrder(2); // run after user filter

        return setFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<QuestionFilter> questionFilter() {
        FilterRegistrationBean<QuestionFilter> questionFilterRegistrationBean
                = new FilterRegistrationBean<>();

        questionFilterRegistrationBean.setFilter(new QuestionFilter(resolver, questionService));
        questionFilterRegistrationBean.addUrlPatterns("/*"); // all question endpoints and sub-endpoints
        questionFilterRegistrationBean.setOrder(3); // run after set filter

        return questionFilterRegistrationBean;
    }

}