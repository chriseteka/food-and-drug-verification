package com.chris_works.activedge.Arinze_Nafdac.Entities;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "The database generated product ID")
	@Column(name = "productid")
	private int productid;
	
	@Column(name = "productcategory")
	@ApiModelProperty(notes = "This is the product category", required = true)
	private String productcategory;
	
	@Column(name = "productname")
	@ApiModelProperty(notes = "This is the product name", required = true)
	private String productname;
	
	@Column(name = "productsearchcode")
	@ApiModelProperty(notes = "This is the product search code", required = true)
	private String productsearchcode;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_products", joinColumns=@JoinColumn(name = "productid"), inverseJoinColumns=@JoinColumn(name = "userid"))
	@ApiModelProperty(notes = "The userids that added each product, this is a set, and can take more than a user", required = false)
	private Set<Users> userid = new HashSet<>();
}