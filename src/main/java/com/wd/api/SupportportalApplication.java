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

import java.io.File;

import static com.wd.api.constant.FileConstant.USER_FOLDER;

@SpringBootApplication
public class SupportportalApplication {


	public static void main(String[] args) {
		SpringApplication.run(SupportportalApplication.class, args);
	    new File(USER_FOLDER).mkdir();

	}



	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

//password:75oyD4WxAZ

}
