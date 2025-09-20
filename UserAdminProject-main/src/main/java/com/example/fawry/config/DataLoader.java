package com.example.fawry.config;


import com.example.fawry.entity.User;
import com.example.fawry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create or update admin user
        User admin = userRepository.findByUsername("admin").orElse(new User());
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole("ADMIN");
        userRepository.save(admin);
        
        // Create or update regular user
        User user = userRepository.findByUsername("user").orElse(new User());
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRole("USER");
        userRepository.save(user);
    }
}
