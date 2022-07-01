package com.api.codingchallenge.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.codingchallenge.enums.Role;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.cors().and().csrf().disable()
                .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll() 
                .antMatchers(HttpMethod.POST, "/login" , "/users").permitAll()
                .antMatchers(HttpMethod.DELETE, "/comments/**").hasAnyRole(Role.MODERADOR.name())
                .antMatchers(HttpMethod.PUT, "/comments/mark-as-repeated/**").hasAnyRole(Role.MODERADOR.name())
                .antMatchers(HttpMethod.PUT, "/comments/to-react/**").hasAnyRole(Role.AVANCADO.name(), Role.MODERADOR.name())
                .antMatchers(HttpMethod.DELETE, "/comments/remove-reaction/**").hasAnyRole(Role.AVANCADO.name(), Role.MODERADOR.name())
                .antMatchers(HttpMethod.PUT,"/comments/**").hasAnyRole(Role.BASICO.name(), Role.AVANCADO.name(), Role.MODERADOR.name())
                .antMatchers(HttpMethod.POST,"/comments/**").hasAnyRole(Role.BASICO.name(), Role.AVANCADO.name(), Role.MODERADOR.name())
                .antMatchers("/evaluations**", "/movie**").hasAnyRole(Role.LEITOR.name(), Role.BASICO.name(), Role.AVANCADO.name(), Role.MODERADOR.name())
                .antMatchers("/users/make-moderator/**").hasAnyRole(Role.MODERADOR.name())                
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}