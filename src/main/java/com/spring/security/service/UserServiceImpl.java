package com.spring.security.service;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.spring.security.entity.Employee;
import com.spring.security.repository.EmpRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
	private EmpRepository repo;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private BCryptPasswordEncoder passencoder;

	@Override
	public Employee saveEmployee(Employee emp , String url) {
		
	String password =	passencoder.encode(emp.getPassword());
	emp.setPassword(password);
	emp.setEnable(false);
	emp.setVerificationCode(UUID.randomUUID().toString());
	    Employee newemp = repo.save(emp); 
	    if (newemp != null) {
			sendEmail(newemp, url);
		}

		return newemp;// ✅ Hibernate will automatically handle ID assignment
	}

	/*
	 * @Override public void sendEmail(Employee emp, String path) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	@Override
	public boolean verifyAccount(String verificationCode) {
		// TODO Auto-generated method stub
		Employee user = repo.findByVerificationCode(verificationCode);

		if (user == null) {
			return false;
		} else {

			user.setEnable(true);
			user.setVerificationCode(null);
			user.setAccountNonLocked(true);
			user.setFailedAttempt(0);
			user.setLockTime(null);

			repo.save(user);

			return true;
		}
	}
	@Override
	public void sendEmail(Employee user, String url) {
	    String from = "yashbhosale555@gmail.com";
	    String to = user.getEmail();
	    String subject = "Account Verification";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>Becoder";

	    try {
	        System.out.println("📨 Preparing to send email to: " + to);

	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        helper.setFrom(from, "Light Yash");
	        helper.setTo(to);
	        helper.setSubject(subject);

	        content = content.replace("[[name]]", user.getName());
	        String siteUrl = url + "/verify?code=" + user.getVerificationCode();
	        content = content.replace("[[URL]]", siteUrl);

	        System.out.println("🔗 Verification Link: " + siteUrl);
	        helper.setText(content, true);

	        mailSender.send(message);
	        System.out.println("✅ Email sent successfully to: " + to);

	    } catch (Exception e) {
	        System.err.println("❌ Error while sending email: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	@Override
	public void increaseFailedAttempt(Employee user) {

		int attempt = user.getFailedAttempt() + 1;

		repo.updateFailedAttempt(attempt, user.getEmail());

	}

	 private static final long lock_duration_time=24*60*60*1000;
	//private static final long lock_duration_time = 30000;

	public static final long ATTEMPT_TIME = 3;

	@Override
	public void resetAttempt(String email) {
		repo.updateFailedAttempt(0, email);
	}

	@Override
	public void lock(Employee user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		repo.save(user);
	}

	@Override
	public boolean unlockAccountTimeExpired(Employee user) {

		long lockTimeInMills = user.getLockTime().getTime();
		long currentTimeMillis = System.currentTimeMillis();

		if (lockTimeInMills + lock_duration_time < currentTimeMillis) {
			user.setAccountNonLocked(true);
			user.setLockTime(null);
			user.setFailedAttempt(0);
			repo.save(user);
			return true;
		}
		return false;
	}



}
