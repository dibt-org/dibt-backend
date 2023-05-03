package com.kim.dibt;

import com.kim.dibt.security.models.Role;
import com.kim.dibt.security.models.RoleType;
import com.kim.dibt.security.repo.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class DibtApplication {

    public static void main(String[] args) {
        SpringApplication.run(DibtApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RoleRepository repository) {
        return args -> Arrays.stream(RoleType.values()).forEach(roleType -> {
            if (!repository.existsByRoleName(roleType)) {
                repository.save(new Role(roleType));
            }
        });
    }

}
