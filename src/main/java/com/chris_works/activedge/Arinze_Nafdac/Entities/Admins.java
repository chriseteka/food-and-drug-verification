package com.chris_works.activedge.Arinze_Nafdac.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class Admins {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "The database generated admin ID")
	@Column(name = "adminid")
	private int adminid;
	
	@Column(name = "adminname")
	@ApiModelProperty(notes = "The admin fullname", required = true)
	private String adminname;
	
	@Column(name = "adminmail")
	@ApiModelProperty(notes = "The admin email address {its the user id}", required = true)
	private String adminmail;
	
	@Column(name = "adminpassword")
	@ApiModelProperty(notes = "The admin hashed password", required = true)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String adminpassword;
	
	@Column(name = "adminunhashed")
	@ApiModelProperty(notes = "The admin raw password", required = true)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String adminunhashedpassword;
}
