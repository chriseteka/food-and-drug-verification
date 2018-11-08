package com.chris_works.activedge.Arinze_Nafdac.ServicesImpls;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.ProductsRepository;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.UsersRepository;
import com.chris_works.activedge.Arinze_Nafdac.Services.UserServices.UserServices;
import com.chris_works.activedge.Arinze_Nafdac.functions.UsersEditProfile;

@Service
@Transactional
public class UsersServiceImpl implements UserServices {
	
	//PORTIONS OF THIS PROGRAM THAT ARE COMMENTED WON'T BE NEEDED FOR A USER
	
	@Autowired
	private UsersEditProfile userEdit;
	
	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private ProductsRepository productRepo;

	@Override
	public String createUser(Users user) {
		try{
			usersRepo.save(user);
			return "A NEW USER HAS BEEN CREATED";
		}catch(Exception e) {
			return null;
		}
	}

//	@Override
//	public List<Users> showAllRegisteredUsers() {
//		List<Users> listOfUsers = usersRepo.findAll();
//		return listOfUsers;
//	}

	@Override
	public Users UserByEmail(String identity) {
		try{
			Users findByemail = usersRepo.findByEmail(identity);
			return findByemail;
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public Products addProducts(Products product) {
		try {
			productRepo.save(product);
			return product;
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public Users UserByUserId(long id) {
		Users findById = usersRepo.findByUserid(id);
		return findById;
	}

	@Override
	public void deleteProduct(Products product) {
		productRepo.delete(product);
	}

	
	//NEWLY ADDED, ARINZE TAKE NOTE.
	@Override
	public ResponseEntity<?> editProfile(Users user, long userid) {
		ResponseEntity<?> editedUserProfile = userEdit.editUserProfile(user, userid);
		return editedUserProfile;
	}

	@Override
	public List<Products> viewMyProducts(Set<Users> userid) {
		if(!userid.isEmpty()) {
			List<Products> myProducts = productRepo.findByUserid(userid);
			return myProducts;
		}
		else {
			return null;
		}
	}

	@Override
	public Products editProduct(Products product, Set<Users> userid) {
		if(userid != null) {
			Products productFound = productRepo.findByProductsearchcode(product.getProductsearchcode());
			if(productFound != null) {
				try {
					userid.addAll(product.getUserid());
					productFound.setProductcategory(product.getProductcategory());
					productFound.setProductid(product.getProductid());
					productFound.setProductname(product.getProductname());
					productFound.setProductsearchcode(product.getProductsearchcode());
					productFound.setUserid(userid);
					productRepo.save(productFound);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else {
				return null;
			}
		}
		return product;
	}

}
