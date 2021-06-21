package com.timesheet.record.TimeRecord;

import java.util.Date;

import javax.persistence.Column;
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
@Table(name="time_record")
public class TimeRecord {
	
	@Id
	private String id;
	
	@Column(name= "time_in")
	private Date timeIn;
	
	@Column(name= "time_out")
	private Date timeOut;
	
	@Column(name= "log_date")
	private Date date;
	
	private String remarks;
	
	@Column(name= "user_id")
	private String userId;
}
