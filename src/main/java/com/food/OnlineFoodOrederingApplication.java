package com.food;

import com.food.model.USER_ROLE;
import com.food.model.User;
import com.food.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class OnlineFoodOrederingApplication implements Runnable {



	public static void main(String[] args) {

		SpringApplication.run(OnlineFoodOrederingApplication.class, args);
	}


	@Override
	public void run() {
	}
}
