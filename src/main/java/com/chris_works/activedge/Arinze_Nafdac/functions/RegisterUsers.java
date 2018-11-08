package com.chris_works.activedge.Arinze_Nafdac.functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Services.UserServices.UserServices;

@Service
public class RegisterUsers {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserServices service;

	@SuppressWarnings("unused")
	public ResponseEntity<?> signUp(Users user)
	{
		//ERROR MESSAGES
		String errorMessage = "A USER WITH THE EMAIL "+user.getEmail()+" ALREADY EXIST";
		String errorMessage1 = "FIELDS CANNOT BE PROCESSED BECAUSE DATA ENTERED ARE INVALID OR TOO SHORT TO BE CORRECT";
		String errorMessage2 = "YOU ARE REQUIRED TO INPUT 'F' OR 'M' FOR GEENDER";
		String errorMessage3 = "FIELDS CANNOT BE EMPTY";
		
		if(user != null)//Tests to see that some or all fields are filled
		{
			Users findUserById = service.UserByEmail(user.getEmail());
			if(findUserById == null)//Checks using email if new user being created already exist
			{
				if(
					user.getName().length() > 3
					&& user.getEmail().length() > 5
					&& user.getPhonenumber().length() > 10
					&& user.getAddress().length() > 10
					&& user.getPassword().length() > 4
					) 
				{
						if( user.getGender() == 'F' || user.getGender() == 'M')
					{
						user.setName(user.getName());
						user.setEmail(user.getEmail());
						user.setPhonenumber(user.getPhonenumber());
						user.setAddress(user.getAddress());
						user.setGender(user.getGender());
						user.setPassword(passwordEncoder.encode(user.getPassword()));
						service.createUser(user);
						
						return new ResponseEntity<Users>(user, HttpStatus.CREATED);
					}
					else 
					{
						return new ResponseEntity<String>(errorMessage2, HttpStatus.BAD_REQUEST);
					}
				}
				else
				{
					return new ResponseEntity<String>(errorMessage1, HttpStatus.BAD_REQUEST);
				}
			}
			else
			{
				return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			return new ResponseEntity<String>(errorMessage3, HttpStatus.BAD_REQUEST);
		}
	}
}
