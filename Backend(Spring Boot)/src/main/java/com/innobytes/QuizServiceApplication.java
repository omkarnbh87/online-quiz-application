package com.innobytes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.innobytes.constants.Role;
import com.innobytes.dao.UserDao;
import com.innobytes.dto.User;
import com.innobytes.pojo.SignUpRequest;

@SpringBootApplication
public class QuizServiceApplication implements CommandLineRunner {

	@Autowired
	private UserDao userDao;

	public static void main(String[] args) {
		SpringApplication.run(QuizServiceApplication.class, args);

	}
	
	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userDao.getUserByRoleId(Role.ADMIN.getRoleId());
		if (adminAccount == null) {
			SignUpRequest user = new SignUpRequest();
			user.setEmail("admin@gmail.com");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setFirstName("Omkar");
			user.setLastName("Rajput");
			user.setRole(Role.ADMIN.getRoleId());
			userDao.saveUser(user);
		}

	}
}
