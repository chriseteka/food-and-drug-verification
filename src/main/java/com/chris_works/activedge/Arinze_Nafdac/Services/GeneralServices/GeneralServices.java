package com.chris_works.activedge.Arinze_Nafdac.Services.GeneralServices;


import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;

public interface GeneralServices {
	Products findProductByName(String productName, Set<Users> userid);
	ResponseEntity<?> searchProduct(String productSearchCode);
	Users changeOfPassword(String email, String oldPassword, String newPassword);
}
