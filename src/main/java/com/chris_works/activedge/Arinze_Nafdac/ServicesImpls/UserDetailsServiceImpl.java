package com.chris_works.activedge.Arinze_Nafdac.ServicesImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.UsersRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository userRepos;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users foodsUser = userRepos.findByEmail(username);
		if(foodsUser == null)
		{
			throw new UsernameNotFoundException("USER WITH MAIL " +username+ " WAS NOT FOUND");
		}
		else 
		{
			return new org.springframework.security.core.userdetails.User(foodsUser.getEmail(), foodsUser.getPassword(), foodsUser.getRoles());
		}
	}
}
