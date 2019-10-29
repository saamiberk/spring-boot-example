package com.spring.security.example.security;

import com.spring.security.example.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {


    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //UserDetailsService change with our interface
    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*
    * SessionCreationPolicy: provide api stateless which prevent header from being cache, http session cant be created.
    * */
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                .permitAll().anyRequest().authenticated().and()
                .addFilter(getGetAuthenticationFilter()).addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{noop}password").roles("ADMIN").build());
        manager.createUser(User.withUsername("admin").password("{noop}admin").roles("USER").build());
        auth.userDetailsService(manager);
        */
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getGetAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager(), userDetailsService);
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }
}
