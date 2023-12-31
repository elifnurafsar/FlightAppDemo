package com.example.FlightAppDemo.Config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String auth_header = request.getHeader("Authorization");
        final String jwt;
        if (auth_header == null || auth_header.indexOf("Bearer") != 0){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = auth_header.substring("Bearer ".length());
        final String username = jwtService.extractUsername(jwt);


        if(username != null || SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = this.userDetailsService.loadUserByUsername(username);

            if(jwtService.isTokenValid(jwt, user)){
                UsernamePasswordAuthenticationToken upt = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                upt.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upt);
            }
        }

        filterChain.doFilter(request, response);

    }
}

