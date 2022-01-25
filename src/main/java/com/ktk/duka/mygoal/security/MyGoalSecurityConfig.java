package com.ktk.duka.mygoal.security;

import com.ktk.duka.mygoal.service.entity.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class MyGoalSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGIN_SUCCESS_URL = "/users";

	private final UserService userService;

	public MyGoalSecurityConfig(UserService userService) {
		this.userService = userService;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new MyGoalUserDetailsManager(userService);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return SecurityUtils.encryptSecret((String) rawPassword);
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return SecurityUtils.encryptSecret((String) rawPassword).equals(encodedPassword);
			}
		});
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.cors()
					.and()
				.csrf()
					.disable()
				.requestCache()
					.requestCache(new MyGoalRequestCache())
					.and()

				.authorizeRequests()
					.requestMatchers(SecurityUtils::isInternalRequest)
						.permitAll()
							.anyRequest()
							.authenticated()
					.and()

				.formLogin()
					.loginPage(LOGIN_URL)
						.permitAll()
						.loginProcessingUrl(LOGIN_URL)
						.failureUrl(LOGIN_FAILURE_URL)
						.successForwardUrl(LOGIN_SUCCESS_URL)
					.and()
				.logout()
					.logoutSuccessUrl(LOGIN_URL)
					.and();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/VAADIN/**",
                "/META-INF/resources/icons/favicon.ico",
				"/robots.txt",
				"/manifest.webmanifest",
				"/sw.js",
				"/offline.html",
				"/icons/**",
				"/images/**",
				"/styles/**",
				"/h2-console/**"
		);
	}
}
