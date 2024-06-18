package com.mysite.reactbbs.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.mysite.reactbbs.jwt.JwtAuthenticationEntryPoint;
import com.mysite.reactbbs.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity // Spring Security
@Configuration
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtRequestFilter;

    public WebSecurityConfig(
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
        JwtAuthenticationFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        	.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
        	.csrf(AbstractHttpConfigurer::disable)
        	.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers("/login").permitAll()
        );

        	

        http
           // .csrf().disable()
           // .cors()     
           // .and()
        	/*
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/bbs", "/comment").authenticated()
            .antMatchers(HttpMethod.PATCH, "/bbs", "/comment").authenticated()
            .antMatchers(HttpMethod.DELETE, "/bbs", "/comment").authenticated()            
            .anyRequest().permitAll();
			*/ 
        
        
        // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
        .authorizeHttpRequests( (authorizeHttpRequests)->authorizeHttpRequests
        		
        		// /bbs 요청은 인증된 사용자만 접근하고 그 외는 모두 접근을 허용하겠다. 
        		//.requestMatchers("/bbs").authenticated()
        		.anyRequest().permitAll()
        		) 
        ; 
        
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* CORS : Spring Boot 2.x 에서 셋팅  */
    /*
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8090"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    */
    
	//  CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("*")); // ⭐️ 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }




    /* 패스워드 암호화 */
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
//    
//    @Bean
//   public PasswordEncoder passwordEncoder() {
//    	
//       return PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
//    	//createDelegatingPasswordEncoder 가 실질적으로 PasswordEncoder 구현객체를 생성 
//    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
