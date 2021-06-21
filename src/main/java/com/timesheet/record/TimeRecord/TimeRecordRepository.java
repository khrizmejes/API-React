package com.timesheet.record.TimeRecord;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord, String> {
	
	@Query(value = "select a from TimeRecord a where a.userId = ?1 and  a.date = ?2")
	TimeRecord findByLogDate(String username, Date date);
	
	@Query(value = "select a from TimeRecord a where a.userId = ?1")
	List<TimeRecord> findByUsername(String username);

	@Query(value = "select a from TimeRecord a where a.userId = ?1 and date between ?2 and ?3")
	List<TimeRecord> findByRangeDate(String username ,Date from,  Date to);

}
