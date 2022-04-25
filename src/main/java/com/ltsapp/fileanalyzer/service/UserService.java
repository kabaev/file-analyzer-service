package com.ltsapp.fileanalyzer.service;

import com.ltsapp.fileanalyzer.domain.User;
import com.ltsapp.fileanalyzer.dto.AddUserRequestDto;
import com.ltsapp.fileanalyzer.dto.AddUserResponseDto;
import com.ltsapp.fileanalyzer.dto.UserDto;
import com.ltsapp.fileanalyzer.dto.UserDtoList;
import com.ltsapp.fileanalyzer.exception.UserExistsException;
import com.ltsapp.fileanalyzer.exception.UserNotFoundException;
import com.ltsapp.fileanalyzer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDtoList findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = users.stream()
                .map(UserDto::new)
                .toList();
        return new UserDtoList(userDtoList);
    }

    public UserDto findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("There is no user with the email: " + email)
        );
        return new UserDto(user);
    }

    public AddUserResponseDto save(AddUserRequestDto user) {
        Optional<User> userInRepository = userRepository.findUserByEmail(user.email());
        if (userInRepository.isPresent()) {
            throw new UserExistsException("User with given email already exist: " + user.email());
        }
        User userToSave = new User();
        userToSave.setName(user.name());
        userToSave.setEmail(user.email());
        User userSaved = userRepository.save(userToSave);
        return new AddUserResponseDto(userSaved);
    }

}
