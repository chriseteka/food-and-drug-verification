package com.chris_works.activedge.Arinze_Nafdac.Services.UserServices;


import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;

public interface UserServices {

	Products addProducts(Products product);
	String createUser(Users user); //Lets take this to the general services
	List<Products> viewMyProducts(Set<Users> userid);
	Products editProduct(Products product, Set<Users> userid);
	ResponseEntity<?> editProfile(Users user, long userid);
	void deleteProduct(Products product);
	
	//BASICALLY FOR SUPPORTS
	Users UserByUserId(long id);
	Users UserByEmail(String identity);
//	List<Users> showAllRegisteredUsers();
	
	//THIS SHOULD BE MODIFIED TO SIMPLY STATE ALL WHAT A USER CAN DO, SOME FUNCTIONS EXIST IN THE GENERAL SERVICE WHICH BOTH ADMIN AND USERS CAN BOTH USE
}
