package com.chris_works.activedge.Arinze_Nafdac.Entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Data
@Table(name = "role")
public class Roles implements GrantedAuthority{

	@Id
	@Column(name = "roleid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "This is the roleid, it is unique and not auto generated", required = true)
	private int roleid;
	
	@Column(name = "rolename")
	@ApiModelProperty(notes = "This is the role name, it is unique also", required = true)
	private String name;
	
	@Override
	public String getAuthority() {
		return name;
	}
}
