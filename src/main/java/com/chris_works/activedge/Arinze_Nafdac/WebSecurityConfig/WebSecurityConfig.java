package com.chris_works.activedge.Arinze_Nafdac.WebSecurityConfig;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableWebSecurity
@Configuration
@EnableSwagger2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/*", "/*/*", "/*/*/*").permitAll()
            .antMatchers("/loginuser/**").hasRole("USER")
//            .hasAnyAuthority("USER")
            .antMatchers("/admin/**").hasRole("ADMIN")
//            .hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
//            .httpBasic();
    }
    
    //ITS IS WEIGH BETTER TO WRITE SEPERATE ALL CONFIG FILES ALTHOUGH ALL MUST BE IN SAME PACKAGE
    
    //THIS BEAN HELPS MY SWAGGER2 TO WORK
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.chris_works.activedge.Arinze_Nafdac.Controllers"))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(metaData());
    }
    
    //THIS  FUNCTION IS WORKS WITH MY SWAGGER DOC TOOL
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//          "My First REST API that is documented", 
//          "This API does R/W users of the application to the DB, allow registeration, authentication and search of products, it"
//          + "also allows the assignments, inclusion and removal of roles to a user. The App in general is meant for foods and drugs authentication", 
//          "API TOS", 
//          "Terms of service: NONE AVAILABLE AT THE TIME OF THIS DOCUMENTATION", 
//          new Contact("ETEKA CHRISTOPHER", "NO WEBSITE NOW", "christophere@activedgetechnologies.com"), 
//          "License of API", "API license URL", Collections.emptyList());
//   }
    //THIS WORKS WITH MY DOCUMENTATION WITH SWAGGER
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring Boot REST API")
                .description("\"Spring Boot REST API for Foods and Drug Authentication\"")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("ETEKA CHRISTOPHER", "http:/no_website.com", "christophere@activedgetechnologies.com"))
                .build();
    }
    
    //THIS HEELPS MY CACHE TO WORK
    @Bean
    public CacheManager cacheManager() {
    	return new ConcurrentMapCacheManager();
    }
    
    //THIS WORKS WITH MY PASSWORD ENCODER
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
    //THIS WORKS WITH MY SECURITY AUTHENTICATION
    @Bean
    public AuthenticationManager authenticationManager() {
    	AuthenticationManager autheticationManager = new AuthenticationManager() {
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return authentication;
			}
		};
		return autheticationManager;
    }
}

