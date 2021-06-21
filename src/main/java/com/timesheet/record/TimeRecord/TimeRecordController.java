package com.timesheet.record.TimeRecord;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.timesheet.record.exception.ResourceFound;
import com.timesheet.record.exception.ResourceNotFoundException;

@RestController
public class TimeRecordController {
	
	// TO-DO:
	// insert--
	// view all--
	// view one--
	// view range--
	// update
	// delete
	
	@Autowired
	TimeRecordService timeRecordService;
	
	//get particular date per username
	@SuppressWarnings("unchecked")
	@RequestMapping(method=RequestMethod.GET, value="/time/{username}")
	public List<TimeRecordModel> viewTimeRecord(
			@PathVariable String username ,
			@RequestBody TimeRecordModel TimeRecordModel) throws ParseException {

		        String date = TimeRecordModel.getDate();
		        String dateFrom  = TimeRecordModel.getDateFrom();
		        String dateTo =  TimeRecordModel.getDateTo();
		        TimeRecordModel model = null;
		        List<TimeRecordModel> modelList =  new ArrayList<>();

		        if (date != null) {
		        	 model = timeRecordService.view(username, date);  //specific date 
					 if (model == null ){
						 throw new ResourceNotFoundException("No Record on this date : " + date);
					 }
					 modelList.add(model);
		        }else if(dateFrom != null  && dateTo != null) {
		        	
						return timeRecordService.viewDatesBetween(username, dateFrom, dateTo); //record between 2 dates
		        }else {
		        	return timeRecordService.viewAll(username); // get all records
		        }

				 return modelList;
	}

	//save
	@RequestMapping(method=RequestMethod.POST, value="/time/{username}")
	public ResponseEntity <TimeRecordModel> saveTimeRecord(
			@PathVariable String username,
			@RequestBody TimeRecordModel timeRecordModel  ) throws ParseException, ResourceFound {
		timeRecordService.save(timeRecordModel, username);
		return new ResponseEntity<TimeRecordModel>(HttpStatus.CREATED);
	}
	

	// update date per username 
	@RequestMapping(method=RequestMethod.PUT, value="/time/{username}")
	public ResponseEntity<TimeRecordModel> updateTimeRecord(
			@PathVariable String username,
			@RequestBody TimeRecordModel timeRecordModel ) throws Exception {
		timeRecordService.update(timeRecordModel, username);
		return new ResponseEntity<TimeRecordModel>(HttpStatus.OK);
		
	}
	
	
/*
	//we do not delete record 
	@RequestMapping(method=RequestMethod.DELETE, value="/timerecord")
	public void deleteTimeRecord(@RequestBody String id) {
		timeRecordService.delete(id);
	}
*/
}
