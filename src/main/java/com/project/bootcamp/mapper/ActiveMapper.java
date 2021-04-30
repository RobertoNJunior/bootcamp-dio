package com.project.bootcamp.mapper;

import com.project.bootcamp.model.Active;
import com.project.bootcamp.model.dto.ActiveDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActiveMapper {

    public Active toEntity(ActiveDTO dto) {
        Active active = new Active();
        active.setId(dto.getId());
        active.setName(dto.getName());
        active.setDate(dto.getDate());
        active.setPrice(dto.getPrice());
        active.setVariation(dto.getVariation());
        return active;
    }

    public ActiveDTO toDto(Active active) {
        ActiveDTO activeDTO = new ActiveDTO();
        activeDTO.setId(active.getId());
        activeDTO.setName(active.getName());
        activeDTO.setDate(active.getDate());
        activeDTO.setPrice(active.getPrice());
        activeDTO.setVariation(active.getVariation());
        return activeDTO;
    }

    public List<ActiveDTO> toDto(List<Active> list) {
        return list.stream().map(this::toDto).collect(Collectors.toList());
    }
}
