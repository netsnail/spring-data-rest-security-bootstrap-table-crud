/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.rest.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This example shows various ways to secure Spring Data REST applications using Spring Security
 *
 * @author Greg Turnquist
 */
@SpringBootApplication
public class Application {

	@Autowired ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	/**
	 * Pre-load the system with employees and items.
	 */
	public @PostConstruct void init() {
	}

	/**
	 * This application is secured at both the URL level for some parts, and the method level for other parts. The URL
	 * security is shown inside this code, while method-level annotations are enabled at by
	 * {@link EnableGlobalMethodSecurity}.
	 *
	 * @author Greg Turnquist
	 * @author Oliver Gierke
	 */
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@EnableWebSecurity
	static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		/**
		 * This section defines the user accounts which can be used for authentication as well as the roles each user has.
		 * 
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
		 */
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {

			auth.inMemoryAuthentication().//
					withUser("user").password("user").roles("USER").and().//
					withUser("admin").password("admin").roles("USER", "ADMIN");
		}

		/**
		 * This section defines the security policy for the app.
		 * <p>
		 * <ul>
		 * <li>BASIC authentication is supported (enough for this REST-based demo).</li>
		 * <li>/employees is secured using URL security shown below.</li>
		 * <li>CSRF headers are disabled since we are only testing the REST interface, not a web one.</li>
		 * </ul>
		 * NOTE: GET is not shown which defaults to permitted.
		 *
		 * @param http
		 * @throws Exception
		 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.httpBasic().and().authorizeRequests().//
					antMatchers(HttpMethod.GET, "/login").hasRole("ADMIN").//
					and().csrf().disable();
		}
	}
}
