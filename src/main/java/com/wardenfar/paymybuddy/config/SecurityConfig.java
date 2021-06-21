package com.wardenfar.paymybuddy.config;

import com.wardenfar.paymybuddy.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Configuration of security
 * - login / logout
 * - password hash
 * - User details
 * - Authentification
 * -
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Custom User details
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Password encoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Jpa Authentification
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Configure Spring security
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // anonymous access to these pages
                .antMatchers(
                        "/login",
                        "/register",
                        "/security/login_check",
                        "/security/logout",
                        "/favicon.ico",
                        "/reset"
                ).permitAll()
                // other pages require auth
                .anyRequest().authenticated()
                .and()
                // Configure login / logout / remember me
                .formLogin()
                .usernameParameter("j_username") // name of the inputs in HTML
                .passwordParameter("j_password")
                .loginPage("/login")
                .permitAll() // anonymous access to login page
                .loginProcessingUrl("/security/login_check").permitAll()
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=Wrong Password")
                .and()
                .logout()
                .logoutUrl("/security/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .key("fdjfhslds3g4654gs84g65sdggds")
                .tokenValiditySeconds(3600); // 10 minutes
    }
}
