package com.example.springmvc.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Securityconfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource datasource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		PasswordEncoder pass=passwordencoder();
		
		System.out.println("***********************");
		System.out.println("encoded password :"+pass.encode("admin"));
		System.out.println("*************************");
		
		auth.jdbcAuthentication()
		.dataSource(datasource)
		.usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
		.authoritiesByUsernameQuery("select username as principal,roles as role from users_roles where username=?")
		.passwordEncoder(pass)
		.rolePrefix("ROLE_");
		
		/*auth.inMemoryAuthentication().withUser("admin").password(pass.encode("admin")).roles("USER","ADMIN");
		auth.inMemoryAuthentication().withUser("user").password(pass.encode("admin")).roles("USER");*/
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/login","/webjars/**").permitAll();
		http.authorizeRequests().antMatchers("/save**/**","/delete**/**","/form**/**").hasRole("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.csrf();
		http.exceptionHandling().accessDeniedPage("/notautho");
	}
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
