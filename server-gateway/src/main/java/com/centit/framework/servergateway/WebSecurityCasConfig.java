package com.centit.framework.servergateway;

import com.centit.support.algorithm.BooleanBaseOpt;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@ConditionalOnClass(name="org.jasig.cas.client.session.SingleSignOutFilter")
public class WebSecurityCasConfig extends WebSecurityBaseConfig {

//    @Override
//    protected String[] getAuthenticatedUrl() {
//        if(BooleanBaseOpt.castObjectToBoolean(env.getProperty("access.resource.notallowed.anonymous"),false)) {
//            return new String[]{"/**"};
//        }
//        return new String[]{"/system/mainframe/logincas"};
//    }
//
//    @Override
//    protected String[] getPermitAllUrl() {
//        return new String[]{"/system/exception"};
//    }
//
//    @Override
//    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
//        ServiceProperties serviceProperties = createCasServiceProperties();
//        CasAuthenticationEntryPoint casEntryPoint = new CasAuthenticationEntryPoint();
//        casEntryPoint.setLoginUrl(env.getProperty("login.cas.casHome")+"/login");
//        casEntryPoint.setServiceProperties(serviceProperties);
//        return casEntryPoint;
//    }
//
//    @Override
//    protected AbstractAuthenticationProcessingFilter getAuthenticationFilter() {
//        CasAuthenticationFilter casFilter = new CasAuthenticationFilter();
//        casFilter.setAuthenticationManager(authenticationManager);
//        casFilter.setAuthenticationFailureHandler(createFailureHandler());
//        casFilter.setAuthenticationSuccessHandler(createSuccessHandler());
//
///*
//        if(sessionRegistry != null) {
//            casFilter.setSessionAuthenticationStrategy(
//                new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
//        }
//*/
//
//        return casFilter;
//    }
//
//    @Override
//    protected Filter logoutFilter() {
//        return new LogoutFilter(env.getProperty("login.cas.casHome")+"/logout", new SecurityContextLogoutHandler());
//    }
//
//    @Override
//    protected AuthenticationProvider getAuthenticationProvider() {
//        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
//        casAuthenticationProvider.setUserDetailsService(centitUserDetailsService);
//        casAuthenticationProvider.setServiceProperties(createCasServiceProperties());
//        casAuthenticationProvider.setTicketValidator(new Cas20ServiceTicketValidator(env.getProperty("login.cas.casHome")));
//        casAuthenticationProvider.setKey(env.getProperty("app.key"));
//        return casAuthenticationProvider;
//    }

    private ServiceProperties createCasServiceProperties() {
        ServiceProperties casServiceProperties = new ServiceProperties();
        casServiceProperties.setService(securityProperties.getLogin().getCas().getLocalHome()+"/login/cas");
        casServiceProperties.setSendRenew(false);
        return casServiceProperties;
    }


    private SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleLogoutFilter = new SingleSignOutFilter();
        singleLogoutFilter.setCasServerUrlPrefix(securityProperties.getLogin().getCas().getCasHome());
        return singleLogoutFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        // 添加单点登出过滤器
        http.addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
    }

}
