package com.timesheet.record.TimeRecord;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timesheet.record.exception.ResourceFound;
import com.timesheet.record.exception.ResourceNotFoundException;

@Service
public class TimeRecordService {

	// TO-DO:
	// insert
	// view all
	// view one
	// update
	// delete - we don't delete record

	@Autowired
	private TimeRecordRepository timeRecordRepository;

	// get all records per username
	public List<TimeRecordModel> viewAll(String username) {

		List<TimeRecord> recordList = timeRecordRepository.findByUsername(username);

		if (recordList == null) {
			throw new ResourceNotFoundException("No Record for user : " + username);
		}

		List<TimeRecordModel> TimeRecordModelList = new ArrayList<>();

		for (TimeRecord record : recordList) {

			TimeRecordModel model = new TimeRecordModel();
			String o = null, i = null;

			model.setId(record.getId());
			model.setRemarks(record.getRemarks());
			model.setUserId(record.getUserId());

			if (!(record.getTimeIn() == null)) {
				i = convertTimeToString(record.getTimeIn());
			}

			if (!(record.getTimeOut() == null)) {
				o = convertTimeToString(record.getTimeOut());
			}

			model.setDate(convertDateToString(record.getDate()));
			model.setTimeIn(i);
			model.setTimeOut(o);

			TimeRecordModelList.add(model);
		}

		return TimeRecordModelList;
	}

	// get particular date only
	public TimeRecordModel view(String username, String date) throws ParseException {

		TimeRecord record = timeRecordRepository.findByLogDate(username, convertStringToDate(date));

		if (record == null) {
			throw new ResourceNotFoundException("No Record for this Date: " + date);
		}

		TimeRecordModel model = new TimeRecordModel();
		String o = null, i = null;

		model.setId(record.getId());
		model.setRemarks(record.getRemarks());
		model.setUserId(record.getUserId());

		if (!(record.getTimeIn() == null)) {
			i = convertTimeToString(record.getTimeIn());
		}

		if (!(record.getTimeOut() == null)) {
			o = convertTimeToString(record.getTimeOut());
		}

		model.setDate(convertDateToString(record.getDate()));
		model.setTimeIn(i);
		model.setTimeOut(o);

		return model;

	}

	// get date range
	public List<TimeRecordModel> viewDatesBetween(String username, String dateFrom, String dateTo)
			throws ParseException {

		List<TimeRecord> recordList = timeRecordRepository.findByRangeDate(username, convertStringToDate(dateFrom),
				convertStringToDate(dateTo));

		if (recordList == null) {
			throw new ResourceNotFoundException("No Record for this Range dates : " + dateFrom + " - " + dateTo);
		}

		List<TimeRecordModel> TimeRecordModelList = new ArrayList<>();

		for (TimeRecord record : recordList) {

			TimeRecordModel model = new TimeRecordModel();
			String o = null, i = null;

			model.setId(record.getId());
			model.setRemarks(record.getRemarks());
			model.setUserId(record.getUserId());

			if (!(record.getTimeIn() == null)) {
				i = convertTimeToString(record.getTimeIn());
			}

			if (!(record.getTimeOut() == null)) {
				o = convertTimeToString(record.getTimeOut());
			}

			model.setDate(convertDateToString(record.getDate()));
			model.setTimeIn(i);
			model.setTimeOut(o);

			TimeRecordModelList.add(model);
		}

		return TimeRecordModelList;
	}

	// save
	public void save(TimeRecordModel timeRecordModel, String username) throws ParseException, ResourceFound {

		// check if record for the day exist
		TimeRecord record = timeRecordRepository.findByLogDate(username,
				convertStringToDate(timeRecordModel.getDate()));

		if (!(record == null)) {
			throw new ResourceFound("Record for " + timeRecordModel.getDate() + " already exist.");
		}

		String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
		String millis = Long.toString(new Timestamp(System.currentTimeMillis()).getTime());
		String id = date.concat(".").concat(millis);

		TimeRecord timeRecord = new TimeRecord();

		String d = timeRecordModel.getDate();
		String in = timeRecordModel.getTimeIn();
		String out = timeRecordModel.getTimeOut();

		Date log_date, timeOut, timeIn;

		if (!(in == null)) {

			timeIn = convertStringToTime(d.concat(" ").concat(in));
			timeRecord.setTimeIn(timeIn);
		}

		if (!(out == null)) {

			timeOut = convertStringToTime(d.concat(" ").concat(out));
			timeRecord.setTimeOut(timeOut);
		}

		log_date = convertStringToDate(d);
		timeRecord.setDate(log_date);

		timeRecord.setId(id);
		timeRecord.setUserId(username);
		timeRecord.setRemarks(timeRecordModel.getRemarks());

		timeRecordRepository.save(timeRecord);
	}

	public void update(TimeRecordModel timeRecordModel, String username) throws ParseException {

		TimeRecord origRecord = timeRecordRepository.findByLogDate(username,
				convertStringToDate(timeRecordModel.getDate()));

		if (origRecord == null) {
			throw new ResourceNotFoundException("No Record for this Date: " + timeRecordModel.getDate());
		}

		TimeRecord newRecord = new TimeRecord();

		newRecord.setId(origRecord.getId());
		newRecord.setUserId(origRecord.getUserId());
		newRecord.setDate(origRecord.getDate());

		String d = timeRecordModel.getDate();
		String in = timeRecordModel.getTimeIn();
		String out = timeRecordModel.getTimeOut();

		Date timeIn, timeOut;

		if (!(in == null)) {

			timeIn = convertStringToTime(d.concat(" ").concat(in));
			newRecord.setTimeIn(timeIn);
		}

		if (!(out == null)) {

			timeOut = convertStringToTime(d.concat(" ").concat(out));
			newRecord.setTimeOut(timeOut);
		}

		if (!(timeRecordModel.getRemarks() == null)) {
			newRecord.setRemarks(timeRecordModel.getRemarks());
		}

		timeRecordRepository.save(newRecord);
	}

	// date only
	private String convertDateToString(Date date) {
		return new SimpleDateFormat("MM/dd/yyyy").format(date);
	}

	// date only
	private Date convertStringToDate(String date) throws ParseException {
		return (Date) new SimpleDateFormat("MM/dd/yyyy").parse(date);
	}

	// time only
	private String convertTimeToString(Date date) {
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}

	// time only
	private Date convertStringToTime(String date) throws ParseException {
		return (Date) new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(date);
	}

}
