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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application {

	@Autowired ItemRepository itemRepository;
	@Value("${user.login}")	String userLogin;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	public @PostConstruct void init() {
	}

	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@EnableWebSecurity
	class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			String[] strs = userLogin.split(":");
			auth.inMemoryAuthentication().
					withUser(strs[0]).password(strs[1]).roles("USER", "ADMIN");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic().and().authorizeRequests().
					antMatchers(HttpMethod.GET, "/items/login").hasRole("ADMIN").
					and().csrf().disable();
		}
	}
}
