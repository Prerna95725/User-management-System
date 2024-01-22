package com.springboot.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.service.UserService;
import com.springboot.utility.JWTUtility;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private JWTUtility jwtUtility; 
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String authorization=httpServletRequest.getHeader("Authorization");
		String token=null;
		String userName=null;
		
		if(null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            userName = jwtUtility.getUsernameFromToken(token);
        }
		
		 if(null != userName) {
	            UserDetails userDetails
	                    = userService.loadUserByUsername(userName);
	            

	            if(jwtUtility.validateToken(token,userDetails)) {
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
	                        = new UsernamePasswordAuthenticationToken(userDetails,
	                        null, userDetails.getAuthorities());
	                
	                usernamePasswordAuthenticationToken.setDetails(                        
	                		new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
	                        );
	                
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);             
	}
	            filterChain.doFilter(httpServletRequest, httpServletResponse);
		 }
	}
}
