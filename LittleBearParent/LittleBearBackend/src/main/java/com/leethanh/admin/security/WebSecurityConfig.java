package com.leethanh.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	UserDetailsService userDetailsService() {

		return new LittleBearUserDetailsService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	SecurityFilterChain configureHttp(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider());

		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/users/**").hasAuthority("Admin")
				.requestMatchers("/categories/**").hasAnyAuthority("Admin","Editor")
				.requestMatchers("/brands/**").hasAnyAuthority("Admin","Editor")
				.requestMatchers("/products/**").hasAnyAuthority("Admin","Editor","Salesperson","Shipper")
				.requestMatchers("/questions/**").hasAnyAuthority("Admin","Assitant")
				.requestMatchers("/reviews/**").hasAnyAuthority("Admin","Assitant")
				.requestMatchers("/customers/**").hasAnyAuthority("Admin","Salesperson")
				.requestMatchers("/shipping/**").hasAnyAuthority("Admin","Salesperson")
				.requestMatchers("/orders/**").hasAnyAuthority("Admin","Salesperson","Shipper")
				.requestMatchers("/report/**").hasAnyAuthority("Admin","Salesperson")
				.requestMatchers("/articles/**").hasAnyAuthority("Admin","Editor")
				.requestMatchers("/menus/**").hasAnyAuthority("Admin","Editor")
				.requestMatchers("/settings/**").hasAuthority("Admin")
				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").usernameParameter("email").permitAll()

				).logout(logout -> logout.permitAll()).csrf(csrf -> csrf.disable())
				.rememberMe(rem -> rem.key("AbcDefgHijKlmnOpqrs_1234567890").tokenValiditySeconds(7 * 24 * 60 * 60));
		;

		return http.build();
	}

	@Bean
	WebSecurityCustomizer configureWebSecurity() throws Exception {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}

}
