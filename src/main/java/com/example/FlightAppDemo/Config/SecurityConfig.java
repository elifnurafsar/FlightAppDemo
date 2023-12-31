package com.example.FlightAppDemo.Config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/Flights/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/Airports/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/Airports/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/Airports/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/Airports/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/Flights/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/Flights/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/Flights/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/User/get_all", "/User/get_blocked_accounts", "/User/filter_blocked_accounts").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/User/enable_account").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/User/block_account/**").hasAuthority("USER")
                        .requestMatchers(HttpMethod.POST, "/User", "/User/login").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/User").hasAnyAuthority("ADMIN", "USER")
                        //.requestMatchers("/swagger-ui.html/**", "/swagger-ui/**", "/v3/api-docs/**", "/v2/api-docs/**").permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}
