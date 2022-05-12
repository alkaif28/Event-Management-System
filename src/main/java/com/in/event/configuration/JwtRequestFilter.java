package com.in.event.configuration;

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

import com.in.event.service.JwtUserDetailsService;
import com.in.event.utility.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		System.out.println(requestTokenHeader);
		String userName = null;
		String jwtToken = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			System.out.println("checking token validity");

			jwtToken = requestTokenHeader.substring(7);
			System.out.println("jwtToken jo token without bearer: " + jwtToken);
			try {
				userName = this.jwtUtils.extractUsername(jwtToken);
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
				System.out.println("jwt token Expire");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		} else {

			System.out.println("Jwt token doesnot starts with Bearer");
		}
// validate
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			final UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);

			if (this.jwtUtils.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}
		} else {
			System.out.println("Token is not valid");
		}

		filterChain.doFilter(request, response);
	}

}
