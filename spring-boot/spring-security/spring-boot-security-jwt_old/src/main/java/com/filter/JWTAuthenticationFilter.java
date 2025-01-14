package com.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.util.TokenHandler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;

	public JWTAuthenticationFilter(UserDetailsService service) {
		this.userDetailsService = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String token = null, username = null;
		String header = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (header != null && header.startsWith(TokenHandler.TOKEN_PREFIX)) {
			token = header.replace(TokenHandler.TOKEN_PREFIX, "");
			try {
				username = TokenHandler.getUsername(token);
			} catch (IllegalArgumentException e) {
				LOG.error("An error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				LOG.warn("The token is expired and not valid anymore", e);
			} catch (SignatureException e) {
				LOG.error("Authentication Failed. Username or password not valid.");
			}
		} else {
			LOG.warn("Couldn't find bearer string, will ignore the header");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (username != null && auth == null) {
			UserDetails user = userDetailsService.loadUserByUsername(username);
			if (TokenHandler.validateToken(user, token)) {
				UsernamePasswordAuthenticationToken authToken = getAuthentication(token, user);
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				LOG.info("Authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(final String token, final UserDetails userDetails) {
		// claims = {sub=huyennv, scopes=ROLE_USER,ROLE_ADMIN, ...}
		Claims claims = TokenHandler.getClaims(token);
		final Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (claims.containsKey(TokenHandler.AUTHORITIES_KEY)) {
			String scopes = claims.get(TokenHandler.AUTHORITIES_KEY).toString();
			// authorities = [ROLE_USER, ROLE_ADMIN]
			Arrays.stream(scopes.split(",")).forEach(t -> {
				authorities.add(new SimpleGrantedAuthority(t));
			});
			// authorities = Arrays.stream(scopes.split(",")) //
			// .map(SimpleGrantedAuthority::new) //
			// .collect(Collectors.toList());
		}
		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

}
