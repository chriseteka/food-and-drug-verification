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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

//This entity houses a user's details
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "The database generated user ID")
	@Column(name = "userid")
	private long userid;
	
	@Column(name = "fullname")
	@ApiModelProperty(notes = "The users fullname", required = true)
	private String name;
	
	@Column(name = "email")
	@ApiModelProperty(notes = "The users email address {its the user id}", required = true)
	private String email;
	
	@Column(name = "phone")
	@ApiModelProperty(notes = "The users phone number", required = true)
	private String phonenumber;
	
	@Column(name = "gender")
	@ApiModelProperty(notes = "The users gender, it is a char, 'M' or 'F'" , required = true)
	private char gender;
	
	@Column(name = "address")
	@ApiModelProperty(notes = "The users address", required = true)
	private String address;
	
	@Column(name = "password")
	@ApiModelProperty(notes = "The users password", required = true)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns=@JoinColumn(name = "userid"), inverseJoinColumns=@JoinColumn(name = "roleid"))
	@ApiModelProperty(notes = "The users roles, this is a set, and can take more than a role", required = false)
	private Set<Roles> roles = new HashSet<Roles>();
	
	@ManyToMany(mappedBy ="userid", fetch = FetchType.LAZY)
	@ApiModelProperty(notes = "The users products, it is a set and can take more than one product", required = false)
	private Set<Products> products = new HashSet<>();

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Transient
	@ApiModelProperty(notes = "The users about to change {oldpassword}, this is not stored in the database, it is called upon only when a user wants to change his password")
	private String oldpassword;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Transient
	@ApiModelProperty(notes = "The users to be password{newpassword}, this is not stored in the database, it is called upon only when a user wants to change his password")
	private String newpassword;
}