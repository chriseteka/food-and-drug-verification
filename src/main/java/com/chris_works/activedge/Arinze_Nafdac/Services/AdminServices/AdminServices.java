package com.chris_works.activedge.Arinze_Nafdac.Services.AdminServices;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Admins;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Roles;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;

public interface AdminServices {
	
	List<Admins> showAllAdmin();
	List<Users> showAllUsers();
	List<Products> showAllProducts();
	List<Roles> showAllRoles();
	Admins findAdminById(String identity);
	Users findUserByMail(String email);
	Products findProductBySearchCode(String productSearchCode);
	Roles findRolesById(int roleid);
	ResponseEntity<?> createAdmin(Admins admin);
	ResponseEntity<?> loginAdmin(Admins admin);
	ResponseEntity<?> createRole(Roles role);
	ResponseEntity<?> assigRole(String userEmail, Set<Roles> roleid);
	ResponseEntity<?> deleteUser(Users user);
	ResponseEntity<?> deleteAdmin(Admins admin);
	ResponseEntity<?> deleteProducts(Products product);
	ResponseEntity<?> deleteRole(Roles role);
}
