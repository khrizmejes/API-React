package com.timesheet.record.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name= "tbl_user")
public class User {
	
	@Id
	private String id;

	private String username;
	
	private String password;
	
	private String name;
	
}
