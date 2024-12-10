package ru.project.user;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.project.user.dto.UserRequestDto;
import ru.project.user.dto.UserResponseDto;
import ru.project.user.mapper.UserMapper;
import ru.project.user.model.User;
import ru.project.user.repository.UserRepository;
import ru.project.user.service.UserService;
import ru.project.user.service.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceTest {

    UserService userService;

    @Mock
    UserRepository userRepository;

    UserRequestDto userRequestDto1;

    User user1;

    User user2;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);

        userRequestDto1 = new UserRequestDto();
        userRequestDto1.setName("Mia");
        userRequestDto1.setEmail("midnight@mail.ru");

        UserRequestDto userRequestDto2 = new UserRequestDto();
        userRequestDto2.setName("Katia");
        userRequestDto2.setEmail("night@mail.ru");

        user1 = new User();
        user1.setName("Mia");
        user1.setEmail("midnight@yandex.ru");
        user1.setId(1L);

        user2 = new User();
        user2.setName("Katia");
        user2.setEmail("night@yandex.ru");
        user2.setId(2L);
    }

    @Test
    @DisplayName("UserService_findAll")
    void testFindAll() {

        when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(user1, user2)));

        final Pageable pageable = PageRequest.of(0, 10);

        final List<UserResponseDto> users = userService.findAll(List.of(), pageable);

        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("UserService_findAllWithParamList")
    void testFindAllWithParamList() {

        final Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findByIdIn(List.of(1L), pageable)).thenReturn((List.of(user1)));

        final List<UserResponseDto> users = userService.findAll(List.of(1L), pageable);

        assertEquals(1, users.size());
    }

    @Test
    @DisplayName("UserService_findAllWithParam")
    void testFindAllWithParam() {

        final Pageable pageable = PageRequest.of(0, 2);

        when(userRepository.findByIdIn(List.of(1L), pageable)).thenReturn((List.of(user1)));

        final List<UserResponseDto> users = userService.findAll(List.of(1L), pageable);

        assertEquals(1, users.size());
    }

    @Test
    @DisplayName("UserService_create")
    void testCreate() {

        when(userRepository.save(UserMapper.toUser(userRequestDto1))).thenReturn(user1);

        final UserResponseDto userResponseDto = userService.save(userRequestDto1);

        assertEquals("Mia", userResponseDto.getName());
        assertEquals(1, userResponseDto.getId());
    }

    @Test
    @DisplayName("UserService_delete")
    void testDelete() {

        userService.delete(1L);

        verify(userRepository).deleteById(any(Long.class));
    }
}
