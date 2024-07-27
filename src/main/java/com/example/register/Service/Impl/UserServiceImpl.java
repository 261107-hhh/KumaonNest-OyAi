package com.example.register.Service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.register.Controller.AuthController;
import com.example.register.Dto.UserDto;
import com.example.register.Entity.ERole;
import com.example.register.Entity.Role;
import com.example.register.Entity.User;
import com.example.register.Exception.ResourceNotFoundException;
import com.example.register.Payload.UserResponse;
import com.example.register.Payload.UserUpdateRequest;
import com.example.register.Payload.request.SignupRequest;
import com.example.register.Repo.RoleRepository;
import com.example.register.Repo.UserRepository;
import com.example.register.Service.UserService;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Value("${kumaonnest.app.mailer}")
	private String fromEmail;

	@Value("${kumaonnest.app.admin}")
	private String admin;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public BCryptPasswordEncoder encoder;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public User createUser(SignupRequest signUpRequest) {
		// TODO Auto-generated method stub

		String email = signUpRequest.getEmail();

		User user = mapper.map(new SignupRequest(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getPhone()), User.class);

		Set<Role> roles = new HashSet<>();
		logger.info("WOrking Sign Up");

		System.out.println(admin + ": THis is amdin: " + email);
		if (admin.equals(email)) {
			System.out.println(admin + ": THis is amdin111: " + email);

			Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found. 4"));
			roles.add(userRole);
		} else if (email.matches("himanshunainwal0@gmail.com")) {
			Role userRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found. 3"));
			roles.add(userRole);
		} else if (email.matches("@kumaonnest.com") || email.matches("pankukweera5@gmail.com")
				|| email.matches("dhruvkweera77@gmail.com")) {
			Role userRole = roleRepository.findByName(ERole.ROLE_STAFF)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found. 2"));
			roles.add(userRole);
		} else {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found. 1"));
			roles.add(userRole);
		}

		user.setRoles(roles);
		return userRepository.save(user);
//
//		String mail = user.getEmail();
//		Long roleID = 3333l;
////		&& userRepo.count() == 0
//		if ((mail.matches("kumaunretailstore@gmail.com") || mail.matches("himanshunainwal0@gmail.com"))) {
//			roleID = 1111l;
//		} else if (mail.contains("kumaunretailstore") || mail.contains("wal66")) {
//			roleID = 2222l;
//		}
//		Role role = this.roleRepository.findById(roleID).get();
//		user.getRoles().add(role);
//
////		user.setRole(role);
//		user.setPassword(encoder.encode(user.getPassword()));
//		return mapper.map(userRepository.save(user), UserDto.class);

	}

	@Override
	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

//	@Override
//	public void removeUser(int id user) {
//		// TODO Auto-generated method stub
//		if (!user.getverify()) {
//			userRepo.delete(mapper.map(user, User.class));
//
//		}
//		System.out.println(user.getEmail());
//	}
	@Override
	public UserResponse removeUser(Long id) {
		// TODO Auto-generated method stub
		UserResponse res = new UserResponse();
		try {
			Optional<User> user = userRepository.findById(id);
			List<UserDto> lst = new ArrayList<>();

			res.setUsers(lst);
			if (user != null) {
				userRepository.delete(mapper.map(user, User.class));
				lst.add(mapper.map(user, UserDto.class));
				return res;
			} else {
				return res;
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error while removing user");

			return res;
		}

	}

	@Override
	public void setOtp(String otp, String mail) {
//		Update user with otp
		Long userId = userRepository.getByEmail(mail).get().getId();
		User u = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
		u.setOtpverify(otp);
		u.setVerify(false);
		this.userRepository.save(u);
	}

	@Override
	public boolean sendMail(String to, String subject, String body) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(to);
//			mimeMessageHelper.setCc(cc);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);

//			for (int i = 0; i < file.length; i++) {
//				mimeMessageHelper.addAttachment(file[i].getOriginalFilename(),
//						new ByteArrayResource(file[i].getBytes()));
//			}
			int flag = 0;
			try {
				javaMailSender.send(mimeMessage);

			} catch (MailSendException e) {
				flag++;
				System.out.println("Error while sending the mail");
			} catch (Exception e) {
				flag++;
				System.out.println("Error while sending the mail or in Authentication");

			}
			if (flag == 0) {
				return true;

			} else {
				return false;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updatePassword(String mail, String password) {
//		Update user with otp
		Long userId = userRepository.getByEmail(mail).get().getId();
		User u = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found."));
		u.setVerify(true);
		u.setPassword(encoder.encode(password));
		this.userRepository.save(u);
	}

	@Override
	public UserResponse getAllUsers() {
		// TODO Auto-generated method stub
		List<User> us = userRepository.getAllUser();
		UserResponse res = new UserResponse();
		List<UserDto> data = us.stream().map((e) -> mapper.map(e, UserDto.class)).collect(Collectors.toList());
		System.out.println(us);
		res.setUsers(data);
		return res;
	}

	@Override
	public UserDto getUsers(String email) {
		// TODO Auto-generated method stub
		Optional<User> us = userRepository.getByEmail(email);
		UserDto ud = mapper.map(us, UserDto.class);
		System.out.println(us);
		return ud;
	}

	@Override
	public String updateUser(Long id, UserUpdateRequest userUpdate) {
		// TODO Auto-generated method stub
		System.out.println("Running Imp");
		try {

			User user = userRepository.getReferenceById(id);
			if (user.getEmail().matches(userUpdate.getEmail())) {
				System.out.println("GetUser" + ":" + user.getEmail() + " : " + userUpdate.getEmail());
				user.setUsername(userUpdate.getName());
				user.setAddress(userUpdate.getAddress());
				user.setPhone(userUpdate.getPhone());
				user.setActive(userUpdate.isActive());
				user.setVerify(userUpdate.isVerify());
				System.out.println("Details Set");
				user.getRoles().clear();
				user.getRoles().add(roleRepository.getReferenceById(userUpdate.getRole().iterator().next().getId()));
//				Set<Role> rl = userUpdate.getRole();
//				Iterator<Role> it = rl.iterator();
//				while (it.hasNext()) {
//					Role r = it.next();
//					Role role = roleRepository.getReferenceById(r.getId());
//					user.getRole().clear();
//					user.getRole().add(role);
//				}
				System.out.println("Role Set");
				userRepository.save(user);
				return "Update Successful";
			} else {
				return "Email id do not Match";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error Occur :" + e);
			return "Error Occured";
		}
	}

//	@Override
//	public UserDto getUsers(int id) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public String addUser(UserDto userDto) {
//		// TODO Auto-generated method stub
//
////		User u = repo.getByEmail(userDto.getEmail())
////				()->new ResourceNotFoundException("User not found by this Email"));
//
//		User user = repo.getByEmail(userDto.getEmail());
////		.orElseThrow(()->new ResourceNotFoundException("User not found by this id"));
////		if(user) {
////			return "Already Present";
////		}
//		User user1 = mapper.map(userDto, User.class);
//		repo.save(user1);
//		return user.getName();
//	}
//
//	@Override
//	public void validateOtp(int otp) {
//		// TODO Auto-generated method stub
//
////		User u = repo.getByEmail(userDto.getEmail())
////				()->new ResourceNotFoundException("User not found by this Email"));
//
////		User user = repo.getByEmail(userDto.getEmail());
////		.orElseThrow(()->new ResourceNotFoundException("User not found by this id"));
////		if(user) {
////			return "Already Present";
////		}
////		User user1 = mapper.map(userDto, User.class);
////		repo.save(user1);
////		return user.getName();
//	}
//
//
//
////	public void sendMail(String to, int otp) {
////		GEmailsender mailsender = new GEmailsender();
////
////		String msg = "This is to verify mail " + otp + " is your OTP";
////		String subject = "OTP verification";
////		String from = "kumaunretailstore@gmail.com";
////		boolean ds = mailsender.send(to, from, msg, subject);
////		if (ds) {
////			System.out.println("Email sent Successfully");
////		} else {
////			System.out.println("There is a problem in sending Email");
////
////		}
////	}
//7070022222
////	private boolean send(String to, String from, String msg, String subject) {
////		// TODO Auto-generated method stub
////		boolean flag = false;
////		String host = "smtp.gmail.com";
////
////		Properties prop = new Properties();
////		System.out.println("properties " + prop);
////
////		prop.put("mail.smtp.auth", true);
////		prop.put("mail.smtp.starttls.enable", true);
////		prop.put("mail.smtp.host", host);
////		prop.put("mail.smtp.port", "587");
////
////		String username = "kumaunretailstore";
////		String pwd = "asdfghjkl";
////
////		Session session = Session.getInstance(prop, new Authenticator() {
//////			@Override
////			protected PasswordAuthentication getPasswordAuthentication() {
////				return new PasswordAuthentication(username, pwd);
////			}
////
////		});
////
////		try {
////			Message message = new MimeMessage(session);
////			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
////			message.setFrom(new InternetAddress(from));
////			message.setSubject(subject);
////			message.setText(msg);
////			Transport.send(message);
////			flag = true;
////
////		} catch (Exception e) {
////			// TODO: handle exception
////			e.printStackTrace();
////		}
////		return flag;
////
////	}
//
//	public int generateOTP() {
//		int min = 1000;
//		int max = 10000;
//		int otp = (int) (Math.random() * (max - min + 1) + min);
//		return otp;
//	}
//
////	public boolean verifyOtp(String userEmail, int userInputOtp) {
////		// Get the previously stored OTP for the user
////		Integer storedOtp = otpStorage.get(userEmail);
////
////		if (storedOtp != null && storedOtp == userInputOtp) {
////			// OTP is valid, remove it from storage to prevent reuse
////			otpStorage.remove(userEmail);
////			return true;
////		} else {
////			// Invalid OTP
////			return false;
////		}
////	}

}
