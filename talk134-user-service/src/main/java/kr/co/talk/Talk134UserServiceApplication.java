package kr.co.talk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Talk134UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Talk134UserServiceApplication.class, args);
	}

}
