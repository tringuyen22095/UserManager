package com.tri.config;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Resource(name = "userService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtEntryPoint unauthorizedHandler;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
	}

	@Bean
	public JwtFilter authenticationTokenFilterBean() throws Exception {
		return new JwtFilter();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable().csrf().disable().authorizeRequests()
				.antMatchers("/", "/user/signIn", "/user/signInSocial", "/user/signUp").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/*.html", "/*.css", "/*.js");
	}
	/*
	 * @Bean
	 * 
	 * @ConfigurationProperties("security.oauth2.google.client") public
	 * AuthorizationCodeResourceDetails google() { return new
	 * AuthorizationCodeResourceDetails(); }
	 * 
	 * @Bean
	 * 
	 * @ConfigurationProperties("security.oauth2.google.resource") public
	 * ResourceServerProperties googleResource() { return new
	 * ResourceServerProperties(); }
	 * 
	 * private Filter ssoGoogleFilter() { OAuth2ClientAuthenticationProcessingFilter
	 * googleFilter = new OAuth2ClientAuthenticationProcessingFilter(
	 * "/google/login"); OAuth2RestTemplate googleTemplate = new
	 * OAuth2RestTemplate(google(), oauth2ClientContext);
	 * googleFilter.setRestTemplate(googleTemplate); googleFilter
	 * .setTokenServices(new
	 * UserInfoTokenServices(googleResource().getUserInfoUri(),
	 * google().getClientId())); return googleFilter; }
	 * 
	 * private Filter ssoFilter() { List<Filter> filters = new ArrayList<>();
	 * filters.add(ssoGoogleFilter()); // filters.add(ssoFacebookFilter());
	 * 
	 * CompositeFilter filter = new CompositeFilter(); filter.setFilters(filters);
	 * return filter; }
	 */
}