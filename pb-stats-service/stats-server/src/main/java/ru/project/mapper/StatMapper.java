package ru.project.mapper;

import org.springframework.stereotype.Component;
import ru.project.StatDto;
import ru.project.model.Stat;

@Component
public class StatMapper {

    public Stat toStat(StatDto statDto) {

        final Stat stat = new Stat();

        stat.setIp(statDto.getIp());
        stat.setUri(statDto.getUri());
        stat.setTimestamp(statDto.getTimestamp());
        stat.setApp(statDto.getApp());

        return stat;
    }

    public StatDto toStatDto(Stat stat) {

        final StatDto statDto = new StatDto();

        statDto.setIp(stat.getIp());
        statDto.setUri(stat.getUri());
        statDto.setTimestamp(stat.getTimestamp());
        statDto.setApp(stat.getApp());

        return statDto;
    }
}