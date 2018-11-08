package com.chris_works.activedge.Arinze_Nafdac.functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.UsersRepository;
import com.chris_works.activedge.Arinze_Nafdac.Services.SecurityServices.SecurityServices;

@Service
public class LoginUsers {
	
	@Autowired
	UsersRepository userRepos;

	@Autowired
	private SecurityServices securityService;

	public ResponseEntity<?> userSignIn(Users user)
	{
		String errorMessage = "USERNAME OR PASSWORD FIELD IS EMPTY";
		String errorMessage1 = "LOGIN NOT SUCCESSFUL, PLEASE REVIEW YOUR INPUTTED DATA";
		
		if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) 
	    {
	    	return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
	    }
	    else
	    {
	    	//VARIABLES USED FOR THE SECURE LOGIN
	    	String username = user.getEmail();
	    	String password = user.getPassword();
	    	
	    	//SECURED LOGIN CODE
	    	boolean loginresponse = securityService.login(username, password);
	    	if(loginresponse)
	    	{
	    		Users foodsUser = userRepos.findByEmail(username);
	    		return new ResponseEntity<Users>(foodsUser, HttpStatus.OK);
	    	}
	    	else
	    	{
	    		return new ResponseEntity<String>(errorMessage1, HttpStatus.BAD_REQUEST);
	    	}
	    }
	}
}
