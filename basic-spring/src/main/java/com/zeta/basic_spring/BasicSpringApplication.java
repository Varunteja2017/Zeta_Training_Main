package com.zeta.basic_spring;

import com.zeta.basic_spring.repositor.Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.zeta.basic_spring.services.Service;

@SpringBootApplication
public class BasicSpringApplication {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(BasicSpringApplication.class, args);
//		Service service =context.getBean(Service.class);
//		Repository repository=context.getBean(Repository.class);
//		service.addOrder();
	}

}
