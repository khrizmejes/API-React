package com.timesheet.record.TimeRecord;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class TimeRecordModel {

	@JsonIgnore
	@Id
	private String id;
	
	private String timeIn;
	
	private String timeOut;
	
	private String date;
	
	private String remarks;
	
	@JsonIgnore
	private String userId;
	
	@JsonIgnore
	private String dateFrom;
	
	@JsonIgnore
	private String dateTo;
}
