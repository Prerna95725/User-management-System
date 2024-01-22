package com.springboot.filter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.service.UserService;
import com.springboot.utility.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	 @Autowired
	    private JwtUtil jwtUtil;
	 
	 @Autowired
	 private UserService service;
	 
	 @Value("${user.username}")
	 public String username;
	 
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	    String authorizationHeader = httpServletRequest.getHeader("Authorization");
	    
	    String token = null;
        String userName = null;
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            try {
            System.out.println("inside the request filter");	
            userName = jwtUtil.extractUsername(token);
            
            if(!userName.equals(username))
            {
            	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid username");
            }
            }catch(ResponseStatusException e) {
            	httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            	OutputStream oStream = httpServletResponse.getOutputStream();
            	ObjectMapper objectMapper = new ObjectMapper();
            	objectMapper.writeValue(oStream,"Error:400 Invalid Username");
            	oStream.flush();
            }
             catch(IllegalArgumentException e) {
            	System.out.println("Unable to get JWT token");	
            }
            catch(ExpiredJwtException e) {
            	httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            	OutputStream oStream = httpServletResponse.getOutputStream();
            	ObjectMapper objectMapper = new ObjectMapper();
            	objectMapper.writeValue(oStream,"Error:403 Jwt Token has expired Please regenerate the Token");
            	
            	System.out.println("Jwt Token has expired");
            }
        }
        
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = service.loadUserByUsername(userName);

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
	}
        filterChain.doFilter(httpServletRequest, httpServletResponse);
}
}
