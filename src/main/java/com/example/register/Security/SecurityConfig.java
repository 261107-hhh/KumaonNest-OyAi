package com.example.register.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.register.Config.AuthEntryPointJwt;
import com.example.register.Service.Impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Autowired
	AuthTokenFilter authTokenFilter;

//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter() {
//		return new AuthTokenFilter();
//	}

	// @Override
	// public void configure(AuthenticationManagerBuilder
	// authenticationManagerBuilder) throws Exception {
//	    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	// }

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	// @Bean
	// @Override
	// public AuthenticationManager authenticationManagerBean() throws Exception {
//	    return super.authenticationManagerBean();
	// }

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
//	    http.cors().and().csrf().disable()
//	      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//	      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//	      .antMatchers("/api/test/**").permitAll()
//	      .anyRequest().authenticated();
	//
//	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	// }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		System.out.println("HAHAHAHHAHAH111");
		http.csrf(csrf -> csrf.disable())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/auth/**").permitAll()
						.anyRequest().authenticated());

		http.authenticationProvider(authenticationProvider());

		http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

//	http.csrf((csrf) -> csrf.disable()).cors((cors) -> cors.disable())
//	.authorizeHttpRequests((auth) -> auth.requestMatchers(PUBLIC_URL).permitAll()
//			.requestMatchers(PRIVATE_URL).permitAll())
//	.logout((logout) -> logout.logoutUrl("/logout").permitAll().clearAuthentication(true)
//			.invalidateHttpSession(true))
//	.exceptionHandling((ex) -> ex.authenticationEntryPoint(entryPoint))
//	.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//
//return http.build();

}

//package com.example.register.Config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.example.register.Security.JwtAuthenticationEntryPoint;
//import com.example.register.Security.JwtAuthenticationFilter;
//
//@Component
//class WebSecurityConfig {
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public DaoAuthenticationProvider DaoauthenticationProvider() {
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//		return daoAuthenticationProvider;
//	}
//
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//
//				registry.addMapping("*")
//				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
//			}
//
//		};
//	}
//////		  registry.addMapping("/register/createUser")
//////        .allowedOrigins("http://localhost:3000")
//////        .allowedHeaders("*")
//////        .allowCredentials(true);
//
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
//
//	}
//
//	@Bean
//	protected AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
//		// TODO Auto-generated method stub
//		return builder.getAuthenticationManager();
//	}
//
//}
