package com.example.register.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.register.Entity.ERole;
import com.example.register.Entity.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

	Optional<Role> findByName(ERole name);


}
