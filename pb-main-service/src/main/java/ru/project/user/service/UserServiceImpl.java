package ru.project.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.exception.ConflictException;
import ru.project.user.dto.UserRequestDto;
import ru.project.user.dto.UserResponseDto;
import ru.project.user.mapper.UserMapper;
import ru.project.user.model.User;
import ru.project.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> findAll(final List<Long> userIds,
                                         final Pageable pageable) {
        log.info("Запрос на получение списка пользователей");
        final List<User> users;
        if (Objects.isNull(userIds) || userIds.isEmpty()) {
            users = userRepository.findAll(pageable).getContent();
            log.info("Получен списк всех пользователей");
        } else {
            users = userRepository.findByIdIn(userIds, pageable);
            log.info("Получен списк пользователей по заданным id");
        }
        return users.stream().map(UserMapper::toUserResponseDto).toList();
    }

    @Override
    public UserResponseDto save(final UserRequestDto userRequestDto) {
        log.info("Запрос на добавление пользователя ");
        try {
            final User user = userRepository.save(UserMapper.toUser(userRequestDto));
            log.info("Пользователь успешно добавлен под id {}", user.getId());
            return UserMapper.toUserResponseDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Адрес электронной почты уже занят почты");
        }
    }

    @Override
    public void delete(final Long userId) {
        log.info("Запрос на удаление пользователя с id {}", userId);
        userRepository.deleteById(userId);
        log.info("Пользователь с id {} успешно удален ", userId);
    }
}
