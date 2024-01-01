/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.security.configuration;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.mbk.app.commons.Name;
import com.mbk.app.commons.utils.Strings;
import com.mbk.app.commons.web.model.experience.AuthenticationResponse;
import com.mbk.app.security.configuration.filter.JwtAuthenticationFilter;
import com.mbk.app.security.properties.ApplicationSecurityProperties;
import com.mbk.app.security.token.JwtTokenProvider;
import com.mbk.app.security.userdetails.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.mbk.app.commons.error.CommonErrors;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract implementation for Web Security and sets up the basic security configuration (like authentication entry
 * point, success / failure handlers, etc.).
 * <p>
 * It is the responsibility of the subclasses to annotate their implementations using @{@link Configuration}, @{@link
 * EnableWebSecurity}, @{@link org.springframework.core.annotation.Order}
 *
 * @author Editor
 */
public abstract class AbstractWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /** Login endpoint. */
    private static final String LOGIN_ENDPOINT = "/auth/login";

    /** Logout endpoint. */
    private static final String LOGOUT_ENDPOINT = "/auth/logout";

    /** Configuration properties instance of type {@link ApplicationSecurityProperties}. */
    protected final ApplicationSecurityProperties applicationSecurityProperties;

    /** Singleton instance of ObjectMapper for JSON serialization. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /** Password encoder of type {@link PasswordEncoder}. */
    protected final PasswordEncoder passwordEncoder;

    /** Service implementation of type {@link UserDetailsService}. */
    protected final UserDetailsService userDetailsService;

    /** JWT token provider instance of type {@link JwtTokenProvider}. */
    protected final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructor.
     *
     * @param passwordEncoder
     *         Password encoder of type {@link PasswordEncoder}.
     * @param userDetailsService
     *         Service implementation of type {@link UserDetailsService}.
     * @param jwtTokenProvider
     *         JWT token provider instance of type {@link JwtTokenProvider}.
     */
    public AbstractWebSecurityConfiguration(final JwtTokenProvider jwtTokenProvider,final UserDetailsService userDetailsService,
                                            final ApplicationSecurityProperties applicationSecurityProperties,
                                            final PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.applicationSecurityProperties = applicationSecurityProperties;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * This method creates a bean of type {@link JwtAuthenticationFilter} and configured to be called before {@link
     * UsernamePasswordAuthenticationFilter}.
     *
     * @return Bean instance of type {@link JwtAuthenticationFilter}.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {

        // @formatter:off
        httpSecurity.csrf().disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(getAuthenticationEntryPoint())
                .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers(getUnsecuredEndpoints().toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginProcessingUrl(AbstractWebSecurityConfiguration.LOGIN_ENDPOINT)
                        .failureHandler(getAuthenticationFailureHandler())
                        .successHandler(getAuthenticationSuccessHandler())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .logout()
                    .logoutUrl(AbstractWebSecurityConfiguration.LOGOUT_ENDPOINT)
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
                    .invalidateHttpSession(true)
                    .deleteCookies(Name.JSESSIONID.key());
		// @formatter:on

        // Set the JWTAuthenticationFilter to be called before the UsernamePasswordAuthenticationFilter
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * This method returns the authentication entry point instance of type {@link AuthenticationEntryPoint} that is
     * responsible to initiate an authentication scheme.
     *
     * @return Authentication entry point of type {@link AuthenticationEntryPoint} that is responsible to initiate an
     * authentication scheme.
     */
    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return new BasicAuthenticationEntryPoint();
    }

    /**
     * This method returns the handler that is responsible to handle the case when a successful authentication happens.
     * We will return a JSON response (instead of the standard url redirection).
     *
     * @return Handler of type {@link AuthenticationSuccessHandler}, which will be called / triggered whenever a
     * successful authentication happens.
     */
    protected AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            final String accessToken = jwtTokenProvider.generateToken(authentication);
            final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            // @formatter:off
            final AuthenticationResponse authResponse = AuthenticationResponse.builder()
                    .username(userPrincipal.getUsername())
                    .roles(userPrincipal.getAssignedRoles())
                    .accessToken(accessToken)
                    .build();
            // @formatter:on

            final PrintWriter writer = response.getWriter();
            AbstractWebSecurityConfiguration.OBJECT_MAPPER.writeValue(writer, authResponse);
            writer.flush();
        };
    }

    /**
     * This method returns the handler that is responsible to handle the case when an authentication failure happens. We
     * will return a JSON response (instead of the standard url redirection).
     *
     * @return Handler of type {@link AuthenticationFailureHandler}, which will be called / triggered whenever an
     * authentication failure occurs.
     */
    protected AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            final Map<String, Object> responseData = new HashMap<>();
            String message = exception.getMessage();
            if (message.equalsIgnoreCase(CommonErrors.USER_ACCOUNT_EXPIRY.message())) {
                responseData.put(Name.ERROR_CODE.key(), CommonErrors.USER_ACCOUNT_EXPIRY);
                responseData.put(Name.ERROR_MESSAGE.key(), CommonErrors.USER_ACCOUNT_EXPIRY.message());
            }  else if(message.equalsIgnoreCase(String.valueOf(CommonErrors.PLEASE_WAIT_UNTIL_YOUR_ACCOUNT_IS_APPROVED))){
                responseData.put(Name.ERROR_CODE.key(), CommonErrors.PLEASE_WAIT_UNTIL_YOUR_ACCOUNT_IS_APPROVED);
                responseData.put(Name.ERROR_MESSAGE.key(), CommonErrors.PLEASE_WAIT_UNTIL_YOUR_ACCOUNT_IS_APPROVED.message());
                //responseData.put("message","Your login request is not yet approved");
            }
            else if(message.equalsIgnoreCase(String.valueOf(CommonErrors.ADMIN_REJECTED))){
                responseData.put(Name.ERROR_CODE.key(), CommonErrors.ADMIN_REJECTED);
                responseData.put(Name.ERROR_MESSAGE.key(), CommonErrors.ADMIN_REJECTED.message());
               // responseData.put("message","Your login request is rejected");
            }else {
                responseData.put(Name.ERROR_CODE.key(), CommonErrors.INVALID_CREDENTIALS);
                responseData.put(Name.ERROR_MESSAGE.key(), CommonErrors.INVALID_CREDENTIALS.message());
            }

           // responseData.put("message", CommonErrors.INVALID_CREDENTIALS.message());

            final PrintWriter writer = response.getWriter();
            AbstractWebSecurityConfiguration.OBJECT_MAPPER.writeValue(writer, responseData);
            writer.flush();
        };
    }

    /**
     * This method returns a collection of unsecured endpoints. Subclasses can override to return their own unsecured
     * endpoints.
     *
     * @return Collection of endpoints that are unsecured i.e. these endpoints can be accessed without any
     * authentication.
     */
    protected Collection<String> getUnsecuredEndpoints() {
        return applicationSecurityProperties.getEndpoints().getUnsecured();
    }
}