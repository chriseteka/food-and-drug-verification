package com.chris_works.activedge.Arinze_Nafdac.Controllers.Rest;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Admins;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Roles;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Services.AdminServices.AdminServices;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(path = "/admin")
@Api(value="Foods_And_Drugs_Verification App", description="Operations pertaining to admin in Foods_And_Drugs_Verification App")
public class AdminsController {
	
	@Autowired
	AdminServices adminService;
	
	//SIGN IN FUNCTION HAS TO BE RE FIXED FOR PROPER STATUS CODE REPORTS, FOR NOW, GOOD
	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST, 
					consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> adminlogin(@RequestBody Admins admin) {
		ResponseEntity<?> loginAdminResponse = adminService.loginAdmin(admin);
	    return loginAdminResponse;
	}

	//FUNCTION THAT CREATES A NEW ADMIN ACCOUNT
	@CrossOrigin
	@RequestMapping(path = "/signupadmin", method = RequestMethod.POST)
	public ResponseEntity<?> createAdminAccount(@RequestBody Admins admin){
		if(admin != null) {
			ResponseEntity<?> createAdminResponse = adminService.createAdmin(admin);
			return createAdminResponse;
		}else {
			return new ResponseEntity<String>("ONE OR MORE FIELDS MAY BE MISSING", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//FUNCTION THAT CREATES A NEW ROLE
	@CrossOrigin
	@RequestMapping(path = "/createrole", method = RequestMethod.POST)
	public ResponseEntity<?> createRole(@RequestBody Roles role){
		ResponseEntity<?> createRoleResponse = adminService.createRole(role);
		return createRoleResponse;
	}
	
	//FUNCTION THAT ASSIGNS A ROLE TO A USER
	@CrossOrigin
	@RequestMapping(path = "/assignrole", method = RequestMethod.PUT)
	public ResponseEntity<?> assigRole(@RequestBody Users user) {
		String email = user.getEmail();
		Set<Roles> roles = user.getRoles();
		ResponseEntity<?> assigRoleResponse = adminService.assigRole(email, roles);
		return assigRoleResponse;
	}
	
	//FUNCTION THAT DELETES A USER
	@CrossOrigin
	@RequestMapping(path = "/user/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUserEntity(@RequestBody Users user){
		ResponseEntity<?> deleteUserResponse = adminService.deleteUser(user);
		return deleteUserResponse;
	}
	
	//FUNCTION THAT DELETES AN ADMIN
	@CrossOrigin
	@RequestMapping(path = "/admin/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAdminEntity(@RequestBody Admins admin){
		ResponseEntity<?> deleteAdminResponse = adminService.deleteAdmin(admin);
		return deleteAdminResponse;
	}
	
	//FUNCTION THAT DELETES AN PRODUCT
	@CrossOrigin
	@RequestMapping(path = "/product/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteProductEntity(@RequestBody Products product){
		ResponseEntity<?> deleteProductResponse = adminService.deleteProducts(product);
		return deleteProductResponse;
	}
	
	//FUNCTION THAT DELETES A ROLE
	@CrossOrigin
	@RequestMapping(path = "/role/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRoleEntity(@RequestBody Roles role){
		ResponseEntity<?> deleteRoleResponse = adminService.deleteRole(role);
		return deleteRoleResponse;
	}

	//FUNCTION THAT FETCH USERS BY THEIR EMAIL
	@CrossOrigin
	@RequestMapping(path = "/finduser", method = RequestMethod.POST)
	public ResponseEntity<?> fetchUserByMail(@RequestBody Users user){
		if(user != null) {
			Users findUserByMail = adminService.findUserByMail(user.getEmail());
			if(findUserByMail != null) {
				return new ResponseEntity<Users>(findUserByMail, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("NO USER WITH THE EMAIL ENTERED", HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<String>("FIELD CANNOT BE EMPTY", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//FUNCTION THAT RETRIEVES A PRODUCT BY ITS SEARCH CODE
	@CrossOrigin
	@RequestMapping(path = "/findproduct", method = RequestMethod.POST)
	public ResponseEntity<?> fetchProductBySearchCode(@RequestBody Products products){
		if(products != null) {
			Products findProductBySearchCode = adminService.findProductBySearchCode(products.getProductsearchcode());
			if(findProductBySearchCode != null) {
				return new ResponseEntity<Products>(findProductBySearchCode, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("PRODUCT WITH SUCH SEARCH CODE DOES NOT EXIST", HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<String>("FIELDS CANNOT BE EMPTY", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//FUNCTION THAT RETRIEVES AN ADMIN BY HIS EMAIL.
	@CrossOrigin
	@RequestMapping(path = "/findadmin", method = RequestMethod.POST)
	public ResponseEntity<?> findAdminByMail(@RequestBody Admins admin){
		if(admin != null) {
			Admins findAdminById = adminService.findAdminById(admin.getAdminmail());
			if(findAdminById != null) {
				return new ResponseEntity<Admins>(findAdminById, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("NO ADMIN WITH SUCH EMAIL IN THE DATABASE", HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<String>("FIELDS CANNOT BE EMPTY", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	//FUNCTION THAT RETRIEVES A ROLE BY THE ROLE ID
	@CrossOrigin
	@RequestMapping(path = "/findrole", method = RequestMethod.POST)
	public ResponseEntity<?> findRoleById(@RequestBody Roles role){
		if(role != null) {
			Roles findRolesById = adminService.findRolesById(role.getRoleid());
			if(findRolesById != null) {
				return new ResponseEntity<Roles>(findRolesById, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("NO ROLE WITH SUCH ROLEID IN THE DATABASE", HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<String>("FIELDS CANNOT BE EMPTY", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//FUNCTION THAT RETRIEVES ALL USERS FROM THE DATABASE
	@CrossOrigin
	@RequestMapping(path = "/allusers", method = RequestMethod.GET)
	public ResponseEntity<?> fetchAllUser(){
		
		List<Users> allUsers = adminService.showAllUsers();
		if(!allUsers.isEmpty()) {
			return new ResponseEntity<List<Users>>(allUsers, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("NO USER EXISTS IN THE DATABASE", HttpStatus.NOT_FOUND);
		}
	}

	//FUNCTION THAT RETRIEVES ALL ADMIN FROM THE DATABASE
	@CrossOrigin
	@RequestMapping(path = "/alladmin", method = RequestMethod.GET)
	public ResponseEntity<?> fetchAllAdmin(){
		
		List<Admins> allAdmin = adminService.showAllAdmin();
		if(!allAdmin.isEmpty()) {
			return new ResponseEntity<List<Admins>>(allAdmin, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("NO ADMIN EXIST IN THE DATABASE", HttpStatus.NOT_FOUND);
		}
	}
	
	//FUNCTION THAT RETRIEVES ALL PRODUCTS FROM THE DATABASE
	@CrossOrigin
	@RequestMapping(path = "/allproducts", method = RequestMethod.GET)
	public ResponseEntity<?> fetchAllProducts(){
		List<Products> allProducts = adminService.showAllProducts();
		if(!allProducts.isEmpty()) {
			return new ResponseEntity<List<Products>>(allProducts, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("NO PRODUCTS EXIST IN THE DATABASE", HttpStatus.NOT_FOUND);
		}
	}
	
	//FUNCTION THAT RETRIEVES ALL ROLES FROM THE DATABASE.
	@CrossOrigin
	@RequestMapping(path = "/allroles", method = RequestMethod.GET)
	public ResponseEntity<?> fetchAllRoles(){
		List<Roles> allRoles = adminService.showAllRoles();
		if(!allRoles.isEmpty()) {
			return new ResponseEntity<List<Roles>>(allRoles, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("NO ROLE EXIST IN THE DATABASE", HttpStatus.NOT_FOUND);
		}
		
	}
}