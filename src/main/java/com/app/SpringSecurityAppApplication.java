package com.app;

import com.app.config.SecurityConfig;
import com.app.persistence.entity.PermissionEntity;
import com.app.persistence.entity.RoleEntity;
import com.app.persistence.entity.RoleEnum;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAppApplication.class, args);
	}

	@Autowired
	SecurityConfig securityConfig;

	@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args -> {

			// Create Permissions
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();

			// Create ROLES
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission,
							readPermission, updatePermission, deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission, readPermission))
					.build();

			RoleEntity roleInvited = RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissionList(Set.of(readPermission))
					.build();

			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission,
							readPermission, updatePermission,
							deletePermission, refactorPermission))
					.build();

			// Create USERS

			UserEntity user01 = UserEntity.builder()
					.username("santiago")
					.password(securityConfig.passwordEncoder().encode("3214"))
					.isEnabled(true)
					.accountNotExpired(true)
					.accountNotLocked(true)
					.credentialsNotExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity user02 = UserEntity.builder()
					.username("daniel")
					.password("$2a$10$3.iW7RQeMIjwvWwQJJXXVehM8YaJjKu.8143uB5N81Lc4Ra7VTKYW")
					.isEnabled(true)
					.accountNotExpired(true)
					.accountNotLocked(true)
					.credentialsNotExpired(true)
					.roles(Set.of(roleUser))
					.build();

			UserEntity user03 = UserEntity.builder()
					.username("andrea")
					.password("$2a$10$3.iW7RQeMIjwvWwQJJXXVehM8YaJjKu.8143uB5N81Lc4Ra7VTKYW")
					.isEnabled(true)
					.accountNotExpired(true)
					.accountNotLocked(true)
					.credentialsNotExpired(true)
					.roles(Set.of(roleInvited))
					.build();

			UserEntity user04 = UserEntity.builder()
					.username("anya")
					.password("$2a$10$3.iW7RQeMIjwvWwQJJXXVehM8YaJjKu.8143uB5N81Lc4Ra7VTKYW")
					.isEnabled(true)
					.accountNotExpired(true)
					.accountNotLocked(true)
					.credentialsNotExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();


			userRepository.saveAll(List.of(user01, user02, user03, user04));
		};

	}

}
