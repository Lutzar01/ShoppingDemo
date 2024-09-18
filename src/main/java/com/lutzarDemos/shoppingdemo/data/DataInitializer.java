package com.lutzarDemos.shoppingdemo.data;

import com.lutzarDemos.shoppingdemo.model.User;
import com.lutzarDemos.shoppingdemo.model.Role;
import com.lutzarDemos.shoppingdemo.repository.RoleRepository;
import com.lutzarDemos.shoppingdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Generates mock data to streamline security testing
 *      NOT INTENDED FOR PRODUCTION
 *
 * @author      Lutzar
 * @version     1.1, 2024/09/17
 */
@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Runs methods once application has been created
     *      ROLEs must be created and exist before USERs
     *
     * @param event     application created
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNoneExist(defaultRoles);
        createDefaultUserIfNoneExist();
        createDefaultAdminIfNoneExist();

    }

    /**
     * Generates 5 generic USERs with credentials
     *      if none exist in the DB
     */
    private void createDefaultUserIfNoneExist() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("A User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default user " + i + " created successfully!");
        }
    }

    /**
     * Generates 2 administrator USERs with credentials
     *      if none exist in the DB
     */
    private void createDefaultAdminIfNoneExist() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("An Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin " + i + " created successfully!");
        }
    }

    /**
     * Generates "X" amount of roles and saves to ROLE REPOSITORY
     *      where "X" is the number of ROLEs in a defined set
     *      
     * @param roles     defined set of ROLEs
     */
    private void createDefaultRoleIfNoneExist(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
    }
}
