package telran.java52.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java52.accounting.dao.UserAccountRepository;
import telran.java52.accounting.dto.RolesDto;
import telran.java52.accounting.dto.UserDto;
import telran.java52.accounting.dto.UserEditDto;
import telran.java52.accounting.dto.UserRegisterDto;
import telran.java52.accounting.dto.exception.UserNotFoundException;
import telran.java52.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

	final ModelMapper modelMapper;
	final UserAccountRepository userAccountRepository; 
	
	@Override
	public UserDto register(UserRegisterDto userRegisterDto) {
		if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
			return null;
		}
		
		UserAccount user = modelMapper.map(userRegisterDto, UserAccount.class);
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
		
		if (isAddRole) {
			user.addRole(role);
		} else {
			user.removeRole(role);
		}
		userAccountRepository.save(user);
		
		return modelMapper.map(user, RolesDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		UserAccount user = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		user.setPassword(newPassword);
		userAccountRepository.save(user);
	}

}