package com.chris_works.activedge.Arinze_Nafdac.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name = "resetrequests")
@Data
public class PasswordResetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@ApiModelProperty(notes = "This is the resetcode id, it is auto generated", required = true)
	private int id;
	
	@Column(name = "email")
	@ApiModelProperty(notes = "This is the mail which sends in the request for password reset", required = true)
	private String email;
	
	@Column(name = "resetcode")
	@ApiModelProperty(notes = "This is the auto geenerated resetcode, stored in the DB against the email", required = true)
	private String resetcode;
}
