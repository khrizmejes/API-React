package com.timesheet.record.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timesheet.record.exception.ResourceFound;
import com.timesheet.record.exception.ResourceNotFoundException;

@Service
public class UserService {

	// view --
	// view1--
	// delete--
	// save--
	// update--

	@Autowired
	private UserRepository userRepository;

	public List<User> viewAll() {
		return userRepository.findAll();
	}

	public void delete(String username) {

		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("Cannot delete, Userame Not Exist: " + username);
		}
		userRepository.deleteById(user.getId());
	}

	public void save(User user) throws ResourceFound {

		if (userRepository.findByUsername(user.getUsername()) == null) {
			String datePattern = "yyyy.MM.dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
			String date = simpleDateFormat.format(new Date());
			String millis = Long.toString(new Timestamp(System.currentTimeMillis()).getTime());
			String id = date.concat(".").concat(millis);
			user.setId(id);
			userRepository.save(user);
		}else {
			throw new ResourceFound("Userame Already Exist: " + user.getUsername());
		}
	}

	public User findByUsername(String username) {

		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("Userame Not Exist: " + username);
		}
		return userRepository.findByUsername(username);
	}

	public void update(String username, User user) {

		if (userRepository.findByUsername(username) == null) {
			throw new ResourceNotFoundException("Cannot update, Userame Not Exist: " + username);
		}
		User origUserInfo = userRepository.findByUsername(username);
		user.setId(origUserInfo.getId());
		userRepository.save(user);
	}

}
