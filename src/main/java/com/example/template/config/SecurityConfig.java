package com.example.template.config;

import com.example.template.Jwt.CustomAccessDeniedHandle;
import com.example.template.Jwt.CustomAuthenticationEntryPoint;
import com.example.template.Jwt.JwtLoginFilter;
import com.example.template.Jwt.JwtVerifyFilter;
import com.example.template.security.CustomAuthenticationFailureHandler;
import com.example.template.security.CustomAuthenticationFilter;
import com.example.template.security.CustomAuthenticationSuccessHandler;
import com.example.template.security.CustomLogoutSuccessHandler;
import com.example.template.security.CustomPermissionEvaluator;
import com.example.template.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Author: LGX-LUCIFER
 * @Date: 2022-03-23 17:12
 * @Description:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // swagger
            .antMatchers("/swagger**/**").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/v2/**").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .successHandler(customAuthenticationSuccessHandler)
            .failureHandler(customAuthenticationFailureHandler)
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler(logoutSuccessHandler)
            .permitAll()
            .and()
            .rememberMe()
            .tokenRepository(persistentTokenRepository())
            .tokenValiditySeconds(60)
            .userDetailsService(userDetailsService)
        ;
        // 禁用 Spring Security 自带的跨域处理
        http.csrf().disable()
            // .addFilter(new JwtLoginFilter(super.authenticationManager()))
            // .addFilter(new JwtVerifyFilter(super.authenticationManager()))
            // .exceptionHandling()
            // .accessDeniedHandler(new CustomAccessDeniedHandle())
            // .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            // .and()
            // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return handler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        String separator = System.lineSeparator();
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_USER " + separator + " ROLE_USER > ROLE_TOURISTS";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
