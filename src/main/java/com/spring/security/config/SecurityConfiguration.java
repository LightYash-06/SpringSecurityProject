package com.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.security.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
	
	
	@Autowired
	public CustomFailureHandler failureHandler;
	//Creating BCryptPasswordencoderBean
	@Bean
	public BCryptPasswordEncoder passwordencoder()
	{
		
		return new BCryptPasswordEncoder();
	}
//Step 2 Create DAOProvider Bean -> UserDetails obj and password encoder
	@Bean
	public DaoAuthenticationProvider authenticationprovider()
	{
		DaoAuthenticationProvider daoauthenticationprovider = new DaoAuthenticationProvider();
		daoauthenticationprovider.setUserDetailsService(getDetailsService());
		daoauthenticationprovider.setPasswordEncoder(passwordencoder());
		return daoauthenticationprovider;
		
	}
	@Bean
	public UserDetailsService getDetailsService(){
		
		return new CustomUserDetailService();
	}
	/*In Memory Authentication code snippet
	 * @Bean public UserDetailsService userdetailservice() {
	 * 
	 * UserDetails user2
	 * =User.withUsername("Mohitya").password(passwordencoder().encode("4321")).
	 * roles("ADMIN").build();
	 * 
	 * UserDetails user1
	 * =User.withUsername("light").password(passwordencoder().encode("1234")).roles(
	 * "USER").build();
	 * 
	 * return new InMemoryUserDetailsManager(user1,user2); }
	 */
	
	@Configuration
	@EnableWebSecurity
	public class SecurityConfig {

	    // Bean for Security Filter Chain
	    @Bean
	    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
	        return http
	            .csrf(csrf -> csrf.disable()) // Disables CSRF (useful for APIs)
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/index").permitAll() 
	                .requestMatchers("/signin").permitAll() 
	                .requestMatchers("/Register").permitAll()
	                
	                // Allows public access to "/index"
	                .anyRequest().authenticated()  // Secures all other endpoints
	            )
	            .formLogin(form -> form.loginPage("/signin").permitAll().defaultSuccessUrl("/about", true).failureUrl("/invalid").failureHandler(failureHandler).permitAll()) // Enables form login with a default redirect to "/home" after success
	            .build();
	    }
	}

}


/*
 * Notes for Future Reference
 * 
 * Understanding DaoAuthenticationProvider, UserDetailsService, and Password Encoding in Spring Security
Your tutor’s code is configuring Spring Security authentication using:
✅ DaoAuthenticationProvider → Handles authentication using a database (via UserDetailsService).
✅ UserDetailsService → Loads user details from a custom service (CustomUserDetailService).
✅ PasswordEncoder → Encrypts and verifies passwords.

1. What is DaoAuthenticationProvider?
DaoAuthenticationProvider is a built-in authentication provider in Spring Security that:
🔹 Retrieves user details using UserDetailsService.
🔹 Verifies passwords using a PasswordEncoder.

📌 It’s responsible for authenticating users based on username & password stored in a database.

🛠️ How It Works?
1️⃣ The user enters a username & password.
2️⃣ Spring Security calls DaoAuthenticationProvider.
3️⃣ It fetches user details from UserDetailsService.
4️⃣ It compares the entered password with the stored password using PasswordEncoder.
5️⃣ If passwords match, authentication is successful; otherwise, authentication fails.

2. Breaking Down Your Tutor’s Code
java
Copy
Edit
@Bean
public DaoAuthenticationProvider authenticationprovider() {
    DaoAuthenticationProvider daoauthenticationprovider = new DaoAuthenticationProvider();
    daoauthenticationprovider.setUserDetailsService(getDetailsService());
    daoauthenticationprovider.setPasswordEncoder(passwordencoder());
    return daoauthenticationprovider;
}
🛠️ Explanation:
1️⃣ Creates a DaoAuthenticationProvider Bean → This is registered in Spring Security for authentication.
2️⃣ Sets the UserDetailsService → Calls getDetailsService(), which returns CustomUserDetailService().
3️⃣ Sets the PasswordEncoder → Calls passwordencoder(), which provides password hashing.
4️⃣ Returns the configured DaoAuthenticationProvider, which Spring Security uses for authentication.

3. What is UserDetailsService and Why Is It Used?
Spring Security does not store user data—it uses a UserDetailsService to fetch user details from a database or other sources.

🛠️ UserDetailsService in Your Tutor’s Code
java
Copy
Edit
@Bean
public UserDetailsService getDetailsService() {
    return new CustomUserDetailService();
}
🛠️ Explanation:
1️⃣ Defines a Spring Bean for UserDetailsService.
2️⃣ Returns an instance of CustomUserDetailService, which loads user details from the database.

4. What is CustomUserDetailService?
CustomUserDetailService is a custom implementation of UserDetailsService that loads users from a database.

📌 Example Implementation:

java
Copy
Edit
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new CustomUserDetails(user);
    }
}
🛠️ Explanation:
🔹 loadUserByUsername(String username) → Fetches the user from the database using UserRepository.
🔹 If the user exists → Converts it into UserDetails.
🔹 If the user doesn’t exist → Throws UsernameNotFoundException.

5. What is PasswordEncoder and Why Is It Used?
Spring Security never stores passwords in plain text—it encrypts passwords before storing them.

📌 A PasswordEncoder is used to:
✔ Securely store passwords in the database.
✔ Compare entered passwords with stored hashed passwords.

🛠️ Example of PasswordEncoder Bean
java
Copy
Edit
@Bean
public PasswordEncoder passwordencoder() {
    return new BCryptPasswordEncoder();
}
🛠️ Explanation:
✔ Uses BCryptPasswordEncoder, which is a strong hashing algorithm.
✔ Automatically hashes passwords when storing them.
✔ Compares hashed passwords during login.

6. Full Authentication Flow in Your Tutor’s Code
1️⃣ The user enters username & password in the login form.
2️⃣ Spring Security calls DaoAuthenticationProvider.
3️⃣ It calls UserDetailsService (CustomUserDetailService) to fetch user details.
4️⃣ It calls PasswordEncoder to check if the password matches.
5️⃣ If everything is correct, the user is authenticated.

7. Complete SecurityConfig.java (Putting Everything Together)
java
Copy
Edit
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public DaoAuthenticationProvider authenticationprovider() {
        DaoAuthenticationProvider daoauthenticationprovider = new DaoAuthenticationProvider();
        daoauthenticationprovider.setUserDetailsService(getDetailsService());
        daoauthenticationprovider.setPasswordEncoder(passwordencoder());
        return daoauthenticationprovider;
    }

    @Bean	
    public UserDetailsService getDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationprovider())
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .logout(Customizer.withDefaults());

        return http.build();
    }
}
8. Summary
🔹 DaoAuthenticationProvider
✔ Handles authentication using UserDetailsService & PasswordEncoder.

🔹 UserDetailsService (CustomUserDetailService)
✔ Fetches user details from the database.

🔹 PasswordEncoder (BCryptPasswordEncoder)
✔ Hashes passwords for security.

🔹 SecurityFilterChain
✔ Defines login, logout, and access rules.
 */

