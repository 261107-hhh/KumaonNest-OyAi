package com.example.register.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.example.register.Entity.ERole;
import com.example.register.Entity.Role;
import com.example.register.Repo.RoleRepository;


@Configuration
public class LoadDatabase {

	@Autowired
	private RoleRepository roleRepository;

	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			initRoles();
		};
	}

	@Transactional
	void initRoles() {
		if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
			roleRepository.save(new Role(ERole.ROLE_USER));
		}

		if (roleRepository.findByName(ERole.ROLE_STAFF).isEmpty()) {
			roleRepository.save(new Role(ERole.ROLE_STAFF));
		}

		if (roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()) {
			roleRepository.save(new Role(ERole.ROLE_MODERATOR));
		}

		if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
			roleRepository.save(new Role(ERole.ROLE_ADMIN));
		}
	}

}
