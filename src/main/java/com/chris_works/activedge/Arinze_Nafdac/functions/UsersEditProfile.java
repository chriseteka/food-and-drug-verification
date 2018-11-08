package com.chris_works.activedge.Arinze_Nafdac.functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.UsersRepository;

@Service
public class UsersEditProfile {
	
	@Autowired
	UsersRepository userRepo;
	
	public ResponseEntity<?> editUserProfile(Users user, long userid){
		if(user != null) {
			if(userid < 0)
			{
				Users userFound = userRepo.findByUserid(userid);
				if(userFound != null) {
					userFound.setAddress(user.getAddress());
					userFound.setName(user.getName());
					userFound.setPhonenumber(user.getPhonenumber());
					Users editedUserProfile = userRepo.save(userFound);
					if(editedUserProfile != null) {
						return new ResponseEntity<Users>(editedUserProfile, HttpStatus.OK);
					}
					else {
						return new ResponseEntity<String>("WE COULDN'T UPDATE YOUR PROFILE", HttpStatus.NOT_MODIFIED);
					}
				}
				else {
					return new ResponseEntity<String>("WE CANNOT CONFIRM YOUR IDNTITY, PLEASE TRY AGAIN WITH A VALID ID", HttpStatus.NOT_FOUND);
				}
			}else {
				return new ResponseEntity<String>("WE CANNOT CONFIRM YOUR IDNTITY, PLEASE TRY AGAIN WITH A VALID ID", HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<String>("SOME FIELDS MAYBE EMPTY, PLEASE CONFIRM AND RESEND YOUR REQUEST", HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
