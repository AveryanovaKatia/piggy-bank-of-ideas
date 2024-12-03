package ru.project.service;

import ru.project.StatDto;
import ru.project.StatResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    StatDto saveStat(final StatDto statDto);

    List<StatResponseDto> readStat(final LocalDateTime start,
                                   final LocalDateTime end,
                                   final List<String> uris,
                                   final boolean unique);
}
