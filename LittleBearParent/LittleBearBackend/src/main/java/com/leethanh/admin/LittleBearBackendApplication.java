package com.leethanh.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.leethanh.common.entity","com.leethanh.admin.user"})
public class LittleBearBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LittleBearBackendApplication.class, args);
	}

}
