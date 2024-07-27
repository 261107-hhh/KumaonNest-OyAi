package com.example.register.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.register.Entity.User;
import com.example.register.Exception.ResourceNotValidException;
import com.example.register.Payload.PasswordChangeRequest;
import com.example.register.Payload.request.LoginRequest;
import com.example.register.Payload.request.SignupRequest;
import com.example.register.Payload.response.JwtResponse;
import com.example.register.Payload.response.MessageResponse;
import com.example.register.Repo.RoleRepository;
import com.example.register.Repo.UserRepository;
import com.example.register.Security.JwtUtils;
import com.example.register.Service.UserService;
import com.example.register.Service.Impl.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Value("${kumaonnest.app.admin}")
	private String admin;

	@Autowired
	UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	ModelMapper mapper;

	@Autowired
	JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		System.out.println("Sign");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		System.out.println("In: " + authentication.getAuthorities().iterator().next().getAuthority());
		String authority = authentication.getAuthorities().iterator().next().getAuthority();

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt;
		Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

		if (!user.get().isActive()) {
			System.out.println("User is Not Active");
			throw new ResourceNotValidException("User is Not Active try again.");
		} else if (!user.get().isVerify()) {
			System.out.println("User is Not Verified");
			throw new ResourceNotValidException("Verify your account then try again with new password.");
		}
		if (authority == "ROLE_ADMIN") {
			jwt = jwtUtils.generateJwtTokenAdmin(authentication);
		} else if (authority == "ROLE_MODERATOR") {
			jwt = jwtUtils.generateJwtTokenModerator(authentication);
		} else if (authority == "ROLE_STAFF") {
			jwt = jwtUtils.generateJwtTokenStaff(authentication);
		} else {
			jwt = jwtUtils.generateJwtToken(authentication);
		}
		System.out.println("Hello Authenticated");
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		signUpRequest.setEmail(signUpRequest.getEmail().trim());
		signUpRequest.setPassword(signUpRequest.getPassword().trim());
		signUpRequest.setUsername(signUpRequest.getUsername());
		signUpRequest.setPhone(signUpRequest.getPhone());

		String email = signUpRequest.getEmail();

		if (signUpRequest.getUsername() == null || signUpRequest.getEmail() == null
				|| signUpRequest.getPassword() == null || signUpRequest.getPhone() == null
				|| signUpRequest.getPhone().equals("0000000000")) {
			System.out.println("Not Valid Data");
			return ResponseEntity.badRequest().body("Invalid input data");

		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		if (userRepository.existsByPhone(signUpRequest.getPhone())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone is already in use!"));
		}

		// Create new user's account
//		User user = new User(signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
		User user = userService.createUser(signUpRequest);
		if (user != null) {
			boolean mailsend = sendMail(email);
			if (mailsend) {
				System.out.println("Registered Success");
				return ResponseEntity.status(HttpStatus.CREATED)
						.body("User registered Suscessfully check Mail to verify!");
			} else {
				System.out.println("Removing Registered Data");
				userService.removeUser(user.getId());
				return ResponseEntity.status(HttpStatus.CONFLICT).body("User registered UnSuscessfully!");

			}
		} else {
			System.out.println("Server not working");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("User registered UnSuscessfully try again later!");

		}
//		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	boolean sendMail(String mail) {
		System.out.println("Hi there");
		String otp = getNumericString(6);
		String to = mail;
		String subject = "Mail Verification";
		String body = "T oversify the email register is verified personal only Otp paste it on" + "browser  " + otp;
		boolean verify = userService.sendMail(to, subject, body);
		userService.setOtp(otp, mail);
		return verify;
	}

	static String getNumericString(int n) {

		// choose a Character random from this String
		String NumericString = "0123456789";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (NumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(NumericString.charAt(index));
		}

		return sb.toString();
	}

	@PutMapping("/verify/{otp}")
	public ResponseEntity<String> saveUserAfterOtpVerification(@PathVariable("otp") String otp) {
		System.out.println(otp + " OTP IS");
		if (userRepository.existsByotpverify(otp)) {
			User user = userRepository.getByotpverify(otp);
			user.setVerify(true);
			user.setActive(true);
			user.setOtpverify("verified");
			userRepository.save(user);
			System.out.println("Verified user");
			return ResponseEntity.status(HttpStatus.CREATED).body("User Verified");
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).body("Wrong OTP");
	}

	@GetMapping("/request/new-otp/{mail}")
	public ResponseEntity<String> newOtp(@PathVariable("mail") String mail) {
		System.out.println(mail + " This is mail");
		if (userRepository.existsByEmail(mail)) {
			boolean mailsend = sendMail(mail);
			if (mailsend) {
				System.out.println("OTP send Success");
				return ResponseEntity.status(HttpStatus.OK).body("Check registered mail!");
			} else {
				System.out.println("Registered Data");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("User registered UnSuscessfully!");

			}
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("No user found with mail id");
		}
	}

	@PostMapping("/request/new-password")
	public ResponseEntity<String> newOtp(@RequestBody PasswordChangeRequest req) {
		String mail = req.getEmail();
		System.out.println(req.getEmail() + " This is mail");
		if (userRepository.existsByEmail(mail)) {
			Optional<User> user = userRepository.getByEmail(mail);
			if (user.get().getOtpverify().equals(req.getOtp())) {
				userService.updatePassword(mail, req.getPassword());
				return ResponseEntity.status(HttpStatus.CREATED).body("New password has been set");
			}
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Otp does not match");
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("No user found with mail id");
		}
	}

}