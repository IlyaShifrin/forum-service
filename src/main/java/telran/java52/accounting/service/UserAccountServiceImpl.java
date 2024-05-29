package telran.java52.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java52.accounting.dao.UserAccountRepository;
import telran.java52.accounting.dto.RolesDto;
import telran.java52.accounting.dto.UserDto;
import telran.java52.accounting.dto.UserEditDto;
import telran.java52.accounting.dto.UserRegisterDto;
import telran.java52.accounting.dto.exception.IncorrectRoleException;
import telran.java52.accounting.dto.exception.UserExistsException;
import telran.java52.accounting.dto.exception.UserNotFoundException;
import telran.java52.accounting.model.Role;
import telran.java52.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService, CommandLineRunner {

	final ModelMapper modelMapper;
	final UserAccountRepository userAccountRepository;
	final PasswordEncoder passwordEncoder;

	@Override
	public UserDto register(UserRegisterDto userRegisterDto) {
		if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
			throw new UserExistsException();
			// return null;
		}

		UserAccount user = modelMapper.map(userRegisterDto, UserAccount.class);
		String password = passwordEncoder.encode(userRegisterDto.getPassword());
		user.setPassword(password);
		userAccountRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto getUser(String login) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto removeUser(String login) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userAccountRepository.deleteById(login);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(String login, UserEditDto userEditDto) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);

		String firstName = userEditDto.getFirstName();
		if (firstName != null) {
			user.setFirstName(firstName);
		}

		String lastName = userEditDto.getLastName();
		if (lastName != null) {
			user.setLastName(lastName);
		}

		userAccountRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		boolean res;
		try {
			if (isAddRole) {
				res = user.addRole(role);
			} else {
				res = user.removeRole(role);
			}
		} catch (Exception e) {
			throw new IncorrectRoleException();
		}

		if (res) {
			userAccountRepository.save(user);
		}

		return modelMapper.map(user, RolesDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		String password = passwordEncoder.encode(newPassword);
		user.setPassword(password);
		userAccountRepository.save(user);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!userAccountRepository.existsById("admin")) {
			String password = passwordEncoder.encode("admin"); 
			UserAccount userAccount = new UserAccount("admin", "", "", password);
			userAccount.addRole(Role.MODERATOR.name());
			userAccount.addRole(Role.ADMINISTRATOR.name());
			userAccountRepository.save(userAccount);
		}
		
	}

}
