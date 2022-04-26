package com.ltsapp.fileanalyzer.service;

import com.ltsapp.fileanalyzer.domain.User;
import com.ltsapp.fileanalyzer.dto.AddUserRequestDto;
import com.ltsapp.fileanalyzer.dto.AddUserResponseDto;
import com.ltsapp.fileanalyzer.dto.UserDto;
import com.ltsapp.fileanalyzer.dto.UserDtoList;
import com.ltsapp.fileanalyzer.exception.UserExistsException;
import com.ltsapp.fileanalyzer.exception.UserNotFoundException;
import com.ltsapp.fileanalyzer.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @InjectMocks
    private UserService underTest;

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Name");
        testUser.setEmail("name@mail.com");
    }

    @Test
    void findAllReturnUserDtoList() {
        // Given
        UserDto testUserDto = new UserDto(testUser);
        UserDtoList expectedUserDtoList = new UserDtoList(List.of(testUserDto));
        given(userRepository.findAll()).willReturn(List.of(testUser));

        // When
        UserDtoList actualUserDtoList = underTest.findAll();

        // Then
        assertThat(actualUserDtoList).isEqualTo(expectedUserDtoList);
    }

    @Test
    void findUserByEmailThrowsExceptionWhenThereIsNoUserWithGivenEmail() {
        // Given
        String email = "name@mail.com";
        given(userRepository.findUserByEmail(email)).willReturn(Optional.empty());

        // When

        // Then
        UserNotFoundException thrown = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> underTest.findUserByEmail(email)
        );
        assertEquals(
                "There is no user with the email: " + email,
                thrown.getMessage()
        );
    }

    @Test
    void findUserByEmailReturnsUserWhenThereIsInRepository() {
        // Given
        String email = "name@mail.com";
        UserDto expectedUser = new UserDto(testUser);
        given(userRepository.findUserByEmail(email)).willReturn(Optional.of(testUser));

        // When
        UserDto actualUser = underTest.findUserByEmail(email);

        // Then
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void saveThrowsExceptionWhenUserAlreadyExists() {
        // Given
        AddUserRequestDto userToSave =
                new AddUserRequestDto(testUser.getName(), testUser.getEmail());
        given(userRepository.findUserByEmail(userToSave.email())).willReturn(Optional.of(testUser));

        // When

        // Then
        UserExistsException thrown = Assertions.assertThrows(
                UserExistsException.class,
                () -> underTest.save(userToSave)
        );
        assertEquals(
                "User with given email already exist: " + userToSave.email(),
                thrown.getMessage()
        );
    }

    @Test
    void saveReturnsSavedUserDto() {
        // Given
        AddUserRequestDto userToSave =
                new AddUserRequestDto(testUser.getName(), testUser.getEmail());
        given(userRepository.findUserByEmail(userToSave.email())).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(testUser);
        AddUserResponseDto expectedUser = new AddUserResponseDto(testUser);

        // When
        AddUserResponseDto actualUser = underTest.save(userToSave);

        // Then
        assertThat(expectedUser).isEqualTo(actualUser);
    }

}
