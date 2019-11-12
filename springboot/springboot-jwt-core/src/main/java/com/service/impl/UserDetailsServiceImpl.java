package com.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.entity.User;
import com.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException("Username is empty!");
		}
		final Optional<User> user = userRepository.findByUsername(username);
		final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
		user.ifPresent(detailsChecker::check);
		return user.orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
	}
}
