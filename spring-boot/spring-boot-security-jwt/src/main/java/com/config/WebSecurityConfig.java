package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.exception.CustomAccessDeniedHandler;
import com.exception.CustomHttp403ForbiddenEntryPoint;
import com.exception.JWTAuthenticationEntryPoint;
import com.filter.JWTAuthenticationFilter;
import com.filter.JWTLoginFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests() //
			.antMatchers("/favicon.ico", "/auth/login-handle").permitAll() //
			.anyRequest().authenticated()
			.and()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
			.and()
				.addFilterBefore(new JWTLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class) //
				.addFilterBefore(new JWTAuthenticationFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class) //
				.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()); // .and() // 403
				// .exceptionHandling().authenticationEntryPoint(new CustomHttp403ForbiddenEntryPoint());
				// .headers().cacheControl();
	}

}