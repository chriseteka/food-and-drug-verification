package com.chris_works.activedge.Arinze_Nafdac.functions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.PasswordResetEntity;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.PasswordResetRepository;
import com.chris_works.activedge.Arinze_Nafdac.Services.UserServices.UserServices;
import com.chris_works.activedge.mailingAPI.services.MailServices;

@Service
public class PasswordResetter {
	
	@Autowired
	PasswordResetRepository resetRepo;
	
	@Autowired
	private UserServices service;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MailServices mailService;
	
	//NEED THIS TWO HERE FOR NOW, WILL RIP THEM OFF WHEN THE TIME COMES
	private final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		private boolean validate(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		        return matcher.find();
		}

	//THIS VALIDATE EMAIL THAT SENDS IN A REQUEST FOR PASSWORD RESET AND THEN SEND THE VALIDATED EMAIL A RESET CODE
	public ResponseEntity<?> passwordResetRequest(PasswordResetEntity resetEntity)
	{
		String errorMessage = "ERROR SENDING YOU A RESET CODE, PLEASE TRY AGAIN";
		String successMessage = "AN EMAIL HAS BEEN SENT TO YOU FOR YOUR PASSWORD RESET";
		String errorMessage1 = "WE COULDN'T FIND A USER WITH THE EMAIL YOU ENTERED, PLEASE CHECK THE EMAIL AND TRY AGAIN";
		String errorMessage2 = "YOU MAY HAVE INPUTTED AN INVALID EMAIL ADDRESS";
		
		String email = resetEntity.getEmail();
		//Check if inputed String is a valid mail address, with the email regex
		if(validate(email)) 
		{
			
			Users findUserById = service.UserByEmail(email);
			if(findUserById != null)
			{
				String sendMessageResponse = mailService.sendMessageReceivedFromSite(email);
				if(sendMessageResponse.equals(null))
				{
					return new ResponseEntity<String>(errorMessage, HttpStatus.PRECONDITION_FAILED);
				}
				else 
				{
					resetEntity.setEmail(email);
					resetEntity.setResetcode(sendMessageResponse);
					resetRepo.save(resetEntity);
					return new ResponseEntity<String>(successMessage, HttpStatus.OK);
				}
			}
			else
			{
				return new ResponseEntity<String>(errorMessage1, HttpStatus.PRECONDITION_FAILED);
			}
		}
		else
		{
			return new ResponseEntity<String>(errorMessage2, HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	
	//THIS PERFROMS THE PASSWORD RESET AND SETS THE USER WITH A DEFAULT PASSWORD, IT TAKES IN A RESET CODE TO DO THIS
	public String passwordResetter(String resetCode)
	{
		if(resetCode.length() != 7) 
		{
			return "INVALID RESET CODE ENTERED";
		}
		else
		{
			PasswordResetEntity findByResetCode = resetRepo.findByResetcode(resetCode);
			if(findByResetCode != null)
			{
				String email = findByResetCode.getEmail();
				Users findUserById = service.UserByEmail(email);
				if(findUserById != null)
				{
					final String defaultPassword = "Start12345";
					findUserById.setPassword(passwordEncoder.encode(defaultPassword));
					service.createUser(findUserById);
					
					return "YOUR PASSWORD HAS BEEN RESET TO: " + defaultPassword;
				}
				else 
				{
					return "WRONG RESET CODE ENTERED";
				}
			}
		}
		return null;
	}

}
