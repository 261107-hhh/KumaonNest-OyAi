package com.example.register;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.register.Repo.RoleRepository;

@SpringBootApplication
//(exclude = SecurityAutoConfiguration.class)
public class SumitUserRegisterApplication
//implements CommandLineRunner
{

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SumitUserRegisterApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


//	public void run(String... args) throws Exception {
//		try {
//			Role role0=new Role();
//			role0.setId(1111);
//			role0.setName("ROLE_ADMIN");
//
//			Role role1=new Role();
//			role1.setId(2222);
//			role1.setName("ROLE_STAFF");
//
//
//			Role role2=new Role();
//			role2.setId(3333);
//			role2.setName("ROLE_GUEST");
//
//			List<Role> role=new ArrayList<>();
//			  role.add(role0);
//			  role.add(role1);
//			  role.add(role2);
//			 this.roleRepository.saveAll(role);
//
//
//
//		}catch(Exception e) {
//
//			System.out.println("User already exist");
//			e.printStackTrace();
//
//		}
//	}


}
