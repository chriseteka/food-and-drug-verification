package com.chris_works.activedge.Arinze_Nafdac.ServicesImpls;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Admins;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Roles;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.AdminsRepository;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.ProductsRepository;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.RolesRepository;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.UsersRepository;
import com.chris_works.activedge.Arinze_Nafdac.Services.AdminServices.AdminServices;

@Service
@Transactional
public class AdminsServiceImpl implements AdminServices {

	@Autowired
	private AdminsRepository adminRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	ProductsRepository productRepo;
	
	@Autowired
	RolesRepository rolesRepo;

	@Autowired
	private AdminServices service;
	
	@Autowired
	private UsersRepository userRepo;
	
	//A simple function which creates a new admin user.
	@Override
	public ResponseEntity<?> createAdmin(Admins admin) {
		
		if(admin != null) {
			if(admin.getAdminmail().length() > 5
				&& admin.getAdminname().length() > 5) 
			{
				Admins adminFound = adminRepo.findByAdminmail(admin.getAdminmail());
				if(adminFound != null) {
					return new ResponseEntity<String>("A USER ALREADY EXIST WITH THE MAIL YOU SPECIFIED", HttpStatus.NOT_ACCEPTABLE);
				}
				else {
					admin.setAdminmail(admin.getAdminmail());
					admin.setAdminname(admin.getAdminname());
					admin.setAdminunhashedpassword(admin.getAdminpassword());
					admin.setAdminpassword(passwordEncoder.encode(admin.getAdminpassword()));
					Admins savedAdmin = adminRepo.save(admin);
					
					return new ResponseEntity<Admins>(savedAdmin, HttpStatus.CREATED);
				}
			}
			else 
			{
				return new ResponseEntity<String>("INPUTS IN FIELDS MUST BE ABOVE 5 CHARACTERS", HttpStatus.BAD_REQUEST);
			}
		}
		else
		{
			return new ResponseEntity<String>("FIELDS CANNOT BE EMPTY", HttpStatus.BAD_REQUEST);
		}
	}

	//This allows an admin login, at the moment, no security has been implemented here.
	@Override
	public ResponseEntity<?> loginAdmin(Admins admin) {
		
		if (admin.getAdminmail().isEmpty() || admin.getAdminpassword().isEmpty()) 
	    {
	    	return new ResponseEntity<String>("FIELDS ARE EMPTY", HttpStatus.BAD_REQUEST);
	    }
	    else
	    {
	    	Admins findAdminById = service.findAdminById(admin.getAdminmail());
	    	if(findAdminById == null) 
	    	{
				return new ResponseEntity<String>("NO ADMIN USER WITH SUCH ID IN THE DATABASE", HttpStatus.NOT_FOUND);
			}
			else if(findAdminById.getAdminmail().equalsIgnoreCase(admin.getAdminmail())) 
			{
				if(findAdminById.getAdminpassword().equals(admin.getAdminpassword())) 
				{
					return new ResponseEntity<Admins>(findAdminById, HttpStatus.OK);
				}
				else
				{
					return new ResponseEntity<String>("PASSWORD MISMATCH", HttpStatus.BAD_REQUEST);
				}
			}
			else
			{
				return new ResponseEntity<String>("UNKNOWN ERROR JUST OCCURED", HttpStatus.NOT_FOUND);
			}
	    }
	}

	//This retrieves all the admin users in the admin Repository.
	@Override
	public List<Admins> showAllAdmin() {
		List<Admins> listOfAdmins = adminRepo.findAll();
		return listOfAdmins;
	}

	//This helps find an admin user by their id(email)
	@Override
	public Admins findAdminById(String identity) {
		try{
			Admins findByAdminMail = adminRepo.findByAdminmail(identity);
			return findByAdminMail;
		}catch(Exception e) {
			return null;
		}
	}

	//This aids the admin to create a role which has a unique identity
	@Override
	public ResponseEntity<?> createRole(Roles role) {
		if(role != null) {
			if(!(role.getName().isEmpty()))
			{
				Roles roleFound = rolesRepo.findByName(role.getName());
				if(roleFound != null) {
					return new ResponseEntity<String>("A ROLE WITH THIS ID ALREADY EXIST", HttpStatus.NOT_ACCEPTABLE);
				}
				else {
					role.setName(role.getName().toUpperCase());
					Roles savedRole = rolesRepo.save(role);
					
					return new ResponseEntity<Roles>(savedRole, HttpStatus.CREATED);
				}
			}
			else
			{
				return new ResponseEntity<String>("CHECK YOUR INPUTS, ROLEID MUST BE A POSITIVE INTEGER, AND"
												  + " ROLE NAME MUST NOT BE EMPTY", HttpStatus.NOT_ACCEPTABLE);
			}
		}
		else {
			return new ResponseEntity<String>("FIELDS CANNOT BE EMPTY", HttpStatus.BAD_REQUEST);
		}
	}

	//This aids the admin to assign a role to a user
	@Override
	public ResponseEntity<?> assigRole(String userEmail, Set<Roles> roleid) {
		
		if(!roleid.isEmpty()) {
			Users userFound = userRepo.findByEmail(userEmail);
			if(userFound != null) {
				//Shii this is coming from farooq.
				roleid.addAll(userFound.getRoles());
				userFound.setRoles(roleid);
				Users updatedUser = userRepo.save(userFound);
				return new ResponseEntity<Users>(updatedUser, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("NO USER WITH SUCH EMAIL IN THE DATABASE", HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<String>("ROLE CANNOT BE EMPTY", HttpStatus.BAD_REQUEST);
		}
	}

	//This aids the admin to delete a given user
	@Override
	public ResponseEntity<?> deleteUser(Users user) {
		if(user != null) {
			Users userFoundByEmail = userRepo.findByEmail(user.getEmail());
			if(userFoundByEmail != null) {
				userRepo.delete(userFoundByEmail);
				return new ResponseEntity<String>("A USER WITH ID "+user.getEmail()+" HAS BEEN DELETED", HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>("NO USER WITH ID "+user.getEmail()+" EXIST IN THE DATABASE", HttpStatus.NOT_FOUND);
			}
		}
		else
		{
			return new ResponseEntity<String>("FIELD CANNOT BE EMPTY", HttpStatus.BAD_REQUEST);
		}
		
	}

	//This aids the admin to delete an admin
	@Override
	public ResponseEntity<?> deleteAdmin(Admins admin) {
		if(admin != null) {
			Admins adminFoundByAdminmail = adminRepo.findByAdminmail(admin.getAdminmail());
			if(adminFoundByAdminmail != null) {
				adminRepo.delete(adminFoundByAdminmail);
				return new ResponseEntity<String>("AN ADMIN WITH ID: "+admin.getAdminmail()+" HAS BEEN DELETED", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("NO USER WITH ID "+admin.getAdminmail()+" EXIST IN THE DATABASE", HttpStatus.NOT_FOUND);
			}
		}
		else
		{
			return new ResponseEntity<String>("FIELD CANNOT BE EMPTY", HttpStatus.BAD_REQUEST);
		}
		
	}

	//This aids the admin to delete a role
	@Override
	public ResponseEntity<?> deleteRole(Roles role) {
		if(role != null) {
			Optional<Roles> roleFoundById = rolesRepo.findById(role.getRoleid());
			if(roleFoundById.get() != null) {
				rolesRepo.delete(roleFoundById.get());
				return new ResponseEntity<String>("A ROLE HAS BEEN DELETED", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("NO ROLE WITH SUCH ID", HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<String>("FIELD CANNOT BE EMPTY", HttpStatus.BAD_REQUEST);
		}
		
	}

	//This retrieves all users from the database
	@Override
	public List<Users> showAllUsers() {
		List<Users> AllUsers = userRepo.findAll();
		return AllUsers;
	}

	//This Retrieves all products from the database
	@Override
	public List<Products> showAllProducts() {
		List<Products> AllProducts = productRepo.findAll();
		return AllProducts;
	}

	//This retrieves all Roles from the database
	@Override
	public List<Roles> showAllRoles() {
		List<Roles> AllRoles = rolesRepo.findAll();
		return AllRoles;
	}

	//This retrieves a user by its email address
	@Override
	public Users findUserByMail(String email) {
		if(email.length() < 5) {
			return null;
		}
		else {
			return userRepo.findByEmail(email);
		}
	}

	//This retrieves a product by its search code
	@Override
	public Products findProductBySearchCode(String productSearchCode) {
		if(productSearchCode.replaceAll("-", "").length() != 16) {
			return null;
		}
		else {
			return productRepo.findByProductsearchcode(productSearchCode);
		}
	}

	//This retrieves a role by it id
	@Override
	public Roles findRolesById(int roleid) {
		if(roleid < 0) {
			return null;
		}
		else
		{
			return rolesRepo.getOne(roleid);
		}
	}

	//This deletes a product from the database
	@Override
	public ResponseEntity<?> deleteProducts(Products product) {
		if(product != null) {
			Products productBySearchCode = productRepo.findByProductsearchcode(product.getProductsearchcode());
			if(productBySearchCode != null) {
				productRepo.delete(productBySearchCode);
				return new ResponseEntity<String>("AN PRODUCT WITH ID: "+product.getProductsearchcode()+" HAS BEEN DELETED", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("NO PRODUCT WITH ID "+product.getProductsearchcode()+" EXIST IN THE DATABASE", HttpStatus.NOT_FOUND);
			}
		}
		else
		{
			return new ResponseEntity<String>("FIELD CANNOT BE EMPTY", HttpStatus.BAD_REQUEST);
		}
	}

}
