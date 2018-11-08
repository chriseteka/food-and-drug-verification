package com.chris_works.activedge.Arinze_Nafdac.ServicesImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Services.SecurityServices.SecurityServices;


@Service
public class SecurityServiceImpl implements SecurityServices {

	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Override
	public boolean login(String username, String password) {
		UserDetails userDetails = userService.loadUserByUsername(username);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, 
				userDetails.getAuthorities());
		if(passwordEncoder.matches(password, userDetails.getPassword())) 
		{
			authenticationManager.authenticate(token);
			boolean authenticationResult = token.isAuthenticated();
			if(authenticationResult) 
			{
				SecurityContextHolder.getContext().setAuthentication(token);
				return authenticationResult;
			}
			return authenticationResult;
		}
		else {
			return false;
		}
	}

}
