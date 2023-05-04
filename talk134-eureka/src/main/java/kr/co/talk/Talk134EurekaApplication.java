package kr.co.talk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Talk134EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Talk134EurekaApplication.class, args);
	}

}
