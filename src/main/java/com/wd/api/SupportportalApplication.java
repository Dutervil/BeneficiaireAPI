package com.wd.api;

import com.wd.api.domain.User;
import com.wd.api.repository.UserRepository;
import com.wd.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.io.File;

import static com.wd.api.constant.FileConstant.USER_FOLDER;

@SpringBootApplication
public class SupportportalApplication {

	@Autowired
 private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(SupportportalApplication.class, args);
	    new File(USER_FOLDER).mkdir();

	}


	@PostConstruct
	public void createAdminUser(){
		User user=new User();
		user.setFirstName("Peterson");
		user.setLastName("Pierre");
		user.setUsername("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRole("SUPER_ADMIN");
		user.setActive(true);
		user.setNotLocked(true);
		this.userRepository.save(user);
	}


	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//password:dqVsor4ueV

}
