
package com.chris_works.activedge.Arinze_Nafdac.Repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
	
	Products findByProductsearchcode(String productSearchCode);
	List<Products> findByproductname(String productName);
	List<Products> findByUserid(Set<Users> userid);
	Products findByProductnameAndUserid(String productName, Set<Users> id);
}
