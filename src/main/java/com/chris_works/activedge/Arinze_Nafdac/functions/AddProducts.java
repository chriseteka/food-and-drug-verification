package com.chris_works.activedge.Arinze_Nafdac.functions;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Services.GeneralServices.GeneralServices;
import com.chris_works.activedge.Arinze_Nafdac.Services.UserServices.UserServices;

@Service
public class AddProducts {
	
	@Autowired
	private UserServices service;
	
	@Autowired
	private GeneralServices generalService;

	public ResponseEntity<?> Product(Products product, long id, Set<Users> uid)
	{
		String errorMessage = "A PRODUCT WITH SAME NAME HAS ALREADY BEEN REGISTERED BY YOU";
		String errorMessage1 = "NOT CREATED, SOME FIELDS MISSING";
		String errorMessage2 = "YOU ARE NOT YET A USER";
		String errorMessage3 = "FIELDS CANNOT BE EMPTY";
		
		if(product != null)
		{
			Users userFound = service.UserByUserId(id);
			if(userFound != null)
			{
				if(product.getProductcategory() != null
						&& product.getProductname() != null)
				{
					Products productsByName = generalService.findProductByName(product.getProductname(), uid);
					if(productsByName != null)
					{
						if(productsByName.getUserid().equals(uid))
						{	
							return new ResponseEntity<String>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
						}
						else
							{
								product.setUserid(uid);
								product.setProductcategory(product.getProductcategory());
								product.setProductname(product.getProductname());
								product.setProductsearchcode(RandomUniqueIdGen.formatString(
															RandomUniqueIdGen.uniqueCurrentTimeMS()));
								service.addProducts(product);
								return new ResponseEntity<Products>(product, HttpStatus.CREATED);
							}
					}
					else if(productsByName == null)
					{
						product.setUserid(uid);
						product.setProductcategory(product.getProductcategory());
						product.setProductname(product.getProductname());
						product.setProductsearchcode(RandomUniqueIdGen.formatString(
													RandomUniqueIdGen.uniqueCurrentTimeMS()));
						service.addProducts(product);
						return new ResponseEntity<Products>(product, HttpStatus.CREATED);
					}
				}
				else
				{
					return new ResponseEntity<String>(errorMessage1, HttpStatus.NOT_IMPLEMENTED);
				}
			}
			else
			{
				return new ResponseEntity<String>(errorMessage2, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return new ResponseEntity<String>(errorMessage3, HttpStatus.NOT_ACCEPTABLE);
	}


}
