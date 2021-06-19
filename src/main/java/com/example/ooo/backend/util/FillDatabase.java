package com.example.ooo.backend.util;

import com.example.ooo.backend.repository.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FillDatabase {

    public static void saveRolesConstant(RoleRepo roleRepo) {
        String[] roles = {Constants.ROLE_ADMIN, Constants.ROLE_USER};
        String[] links = {Constants.LINK_ADMIN, Constants.LINK_USER};
        for (int i = 0; i < roles.length; i++) {
            roleRepo.insertRoleWithId(Long.parseLong(Integer.toString(i + 1)), roles[i], links[i]);
        }
    }

    @Bean
    CommandLineRunner fillFullDatabase(RoleRepo roleRepo) {
        return (args) -> {
            roleRepo.deleteAll();

            saveRolesConstant(roleRepo);
        };
    }
}

