package com.ltsapp.fileanalyzer.repository;

import com.ltsapp.fileanalyzer.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    private User user;

    @BeforeEach
    void setUp() {
        String name = "Name";
        String email = "name@mail.com";

        user = new User();
        user.setEmail(email);
        user.setName(name);

        underTest.save(user);
    }

    @Test
    void temp() {
        assertThat(underTest).isNotNull();
    }

    @Test
    void findUserByEmailReturnsEmpty() {
        // Given
        String email = "nonexistinguser@mail.com";

        // When
        Optional<User> userByEmail = underTest.findUserByEmail(email);

        // Then
        assertThat(userByEmail)
                .isNotPresent();
    }

    @Test
    void findUserByEmailReturnsUser() {
        // Given
        String email = "name@mail.com";

        // When
        Optional<User> actualUser = underTest.findUserByEmail(email);

        // Then
        assertThat(actualUser)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c)
                        .usingRecursiveComparison()
                        .isEqualTo(user));
    }

}
