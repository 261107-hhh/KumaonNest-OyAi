package com.example.register.Controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.register.Dto.UserDto;
import com.example.register.Entity.Role;
import com.example.register.Payload.UserResponse;
import com.example.register.Payload.UserUpdateRequest;
import com.example.register.Repo.RoleRepository;
import com.example.register.Repo.UserRepository;
import com.example.register.Security.JwtUtils;
import com.example.register.Service.Impl.UserDetailsServiceImpl;
import com.example.register.Service.Impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class HomeController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private ModelMapper mapper;
//
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/current-user")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('MODERATOR') or hasRole('ADMIN')")
	private ResponseEntity<?> currentUser(Principal p) {
		System.out.println(p.getName() + " this is current user");
//		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
		return ResponseEntity.ok().body(p.getName());

	}

	@GetMapping("/get-all-users")
	@PreAuthorize("hasRole('ADMIN')")
	private ResponseEntity<UserResponse>getAllUser() {
		System.out.println(" this is all user");
		UserResponse u1 = userService.getAllUsers();
		System.out.println(u1);
		return new ResponseEntity<>(u1,HttpStatus.ACCEPTED);
	}

	@GetMapping("/get-user/{email}")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('MODERATOR') or hasRole('ADMIN')")
	private ResponseEntity<UserDto> getUser(@PathVariable String email) {
		System.out.println(" this is get user");
		UserDto u1 = userService.getUsers(email);
		System.out.println(u1);
		return new ResponseEntity<>(u1, HttpStatus.OK);
	}

	@PutMapping("/update-user/{email}")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('MODERATOR') or hasRole('ADMIN')")
	private ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody UserUpdateRequest userUpdate) {
		System.out.println(" this is update user: " + userUpdate.getName() + ":" + userUpdate.getRole());
		UserDto ud = new UserDto();
//		System.out.println(roleRepo.getReferenceById(id)+" role");
//		System.out.println(userRepo.getById(id).get().getRole()+" role");
//		ud.setRole(userUpdate.getRole());
		try {
			Set<Role> s = userRepository.getByEmail(email).get().getRoles();
			Iterator<Role> it = s.iterator();
//
//			while (it.hasNext()) {
////				it.next().setId(2222);
//				it.next().setId(2222);
//				System.out.println("Update Role");
////				System.out.println(it.next().getName()+" Name");
////				System.out.println(it.next().getId() + " rrr");
////				it.next().setName("ROLE_STAFF");
////				it.next().setName("ASDD");
//
//			}

		} catch (Exception e) {

			// TODO: handle exception
			System.out.println("Thrown by update: " + e);
		}
		try {
			Set<Role> s1 = userRepository.getByEmail(email).get().getRoles();
			Iterator<Role> it1 = s1.iterator();

			while (it1.hasNext()) {
//				it.next().setId(2222);
//				it1.next().setId(2222);
//				System.out.println("Update Role");
//				System.out.println(it1.next().getName()+" Name");
				System.out.println(it1.next().getId() + " rrr");
//				it.next().setName("ROLE_STAFF");
//				System.out.println(it.next().getId() + " rrr");
//				it.next().setName("ASDD");

			}

		} catch (Exception e) {

			// TODO: handle exception
			System.out.println("Thrown by update: " + e);
		}
//		Integer.parseInt(userUpdate.getRole())
//		Set<Role> s1 = userRepo.getById(id).get().getRole();
//		Iterator<Role> it1 = s1.iterator();
//		while(it.hasNext()) {
//			System.out.println(it1.next().getId()+" rrr");
////			System.out.println(it1.next().getId()+" rrr");
//		}

//		UserDto u1 = userService.getUsers(id);
//		System.out.println(u1);
		return ResponseEntity.ok().body("Updateddd");
//		return ResponseEntity.ok().body("Updateddd");
	}

	@GetMapping("/logout")
	@PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('MODERATOR') or hasRole('ADMIN')")
	private String logout() {
		return "LOgout called";
//	private String logout(Principal p) {
//		return p.getName();
//		return "This is current";

//import org.springframework.web.bind.annotation.GetMapping;
//
//import org.springframework.security.access.prepost.PreAuthorize;

	}

}