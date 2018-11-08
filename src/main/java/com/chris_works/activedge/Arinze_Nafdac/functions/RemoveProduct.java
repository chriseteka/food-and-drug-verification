package com.chris_works.activedge.Arinze_Nafdac.functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Repositories.ProductsRepository;
import com.chris_works.activedge.Arinze_Nafdac.Services.UserServices.UserServices;

@Service
public class RemoveProduct {

	@Autowired
	ProductsRepository productRepo;
	
	@Autowired
	UserServices service;
	
	public ResponseEntity<?> deleteProduct(Products product)
	{
		if(product != null)
		{
			Products productRetrieved = productRepo.findByProductsearchcode(product.getProductsearchcode());
			if(productRetrieved != null)
			{
				service.deleteProduct(productRetrieved);
				return new ResponseEntity<Products>(product, HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<String>("NO PRODUCT FOUND WITH SUCH SEARCH CODE", HttpStatus.NOT_FOUND);
			}
		}
		else
		{
			return new ResponseEntity<String>("null", HttpStatus.BAD_REQUEST);
		}
	}
}
