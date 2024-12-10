package ru.project.user.service;

import org.springframework.data.domain.Pageable;
import ru.project.user.dto.UserRequestDto;
import ru.project.user.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> findAll(final List<Long> userIds,
                                  final Pageable pageable);

    UserResponseDto save(final UserRequestDto userRequestDto);

    void delete(final Long userId);
}
