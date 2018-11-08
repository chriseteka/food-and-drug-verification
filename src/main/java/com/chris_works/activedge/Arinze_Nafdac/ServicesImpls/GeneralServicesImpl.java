package com.chris_works.activedge.Arinze_Nafdac.ServicesImpls;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.ProductsRepository;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.UsersRepository;
import com.chris_works.activedge.Arinze_Nafdac.Services.GeneralServices.GeneralServices;
import com.chris_works.activedge.Arinze_Nafdac.functions.RandomUniqueIdGen;

@Service
public class GeneralServicesImpl implements GeneralServices {

	@Autowired
	ProductsRepository productRepo;
	
	@Autowired
	UsersRepository usersRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;


	@Override
	public Products findProductByName(String productName, Set<Users> userid) 
	{
		Products findByProductName = productRepo.findByProductnameAndUserid(productName, userid);
		return findByProductName;
	}

	//THIS AIDS AN ALREADY LOGGED IN USER TO CHANGE HIS PASSWORD
	@Override
	public Users changeOfPassword(String email, String oldPassword, String newPassword)
	{
		Users userFound = usersRepo.findByEmail(email);
		if(userFound != null)
		{
			if(passwordEncoder.matches(oldPassword, userFound.getPassword()))
			{
				userFound.setPassword(passwordEncoder.encode(newPassword));
				Users savedUser = usersRepo.save(userFound);
				return savedUser;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

	
	//THIS AIDS IN THE SEARCH OF ANY PRODUCT THAT EXISTS IN THE DATABASE USING A GIVEN PRODUCT ID.
	public ResponseEntity<?> searchProduct(String productId)
	{
		//THIS CONDITION HANDLES INPUTS TO THE SEARCH AREA WITH HYPHEN INCLUDED IN IT.
		if(productId.contains("-")) {
			String newProductId = productId.replaceAll("-", "");
			if(newProductId.length() != 16) //A TEST TO VERIFY THAT INCOMING REQUEST IS A 16-DIGIT CHARACTER
			{
				return new ResponseEntity<String>("INVALID PRODUCT ID INPUTTED", HttpStatus.NOT_ACCEPTABLE);
			}
			else 
			{
				//SEARCH THE DB AND RETURN RESULTS IF IT EXISTS
				Products findProductBySearchCode = productRepo.findByProductsearchcode(productId);
				if(findProductBySearchCode != null) 
				{
					return new ResponseEntity<Products>(findProductBySearchCode, HttpStatus.OK);
				}
				else 
				{
					return new ResponseEntity<String>("PRODUCT IS NOT AUTHENTICATED", HttpStatus.NOT_ACCEPTABLE);
				}
			}
		}
		
		
		//THIS CONDITION HANDLES INPUT TO THE SEARCH AREA WITHOUT HYPHEN
		else 
		{
			if(productId.length() != 16) //A TEST TO VERIFY THAT INCOMING REQUEST IS A 16-DIGIT CHARACTER
			{
				return new ResponseEntity<String>("INVALID PRODUCT ID INPUTTED", HttpStatus.NOT_ACCEPTABLE);
			}
			else 
			{
				
				//FORMAT THE STRING TO CONTAIN HYPHEN BEFORE SEARCH BEGINS
				String formattedProductId = RandomUniqueIdGen.formatString(productId);
				
				//SEARCH THE DB AND RETURN RESULTS IF IT EXISTS
				Products findProductBySearchCode = productRepo.findByProductsearchcode(formattedProductId);
				if(findProductBySearchCode != null) 
				{
					return new ResponseEntity<Products>(findProductBySearchCode, HttpStatus.OK);
				}
				else 
				{
					return new ResponseEntity<String>("PRODUCT IS NOT AUTHENTICATED", HttpStatus.NOT_ACCEPTABLE);
				}
			}
		}
	}
	
}
