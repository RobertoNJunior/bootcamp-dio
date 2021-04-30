package com.project.bootcamp.service;

import com.project.bootcamp.exceptions.BusinessException;
import com.project.bootcamp.exceptions.NotFoundException;
import com.project.bootcamp.mapper.ActiveMapper;
import com.project.bootcamp.model.Active;
import com.project.bootcamp.model.dto.ActiveDTO;
import com.project.bootcamp.repository.ActiveRepository;
import com.project.bootcamp.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ActiveService {

    @Autowired
    private ActiveMapper mapper;

    @Autowired
    private ActiveRepository repository;

    @Transactional
    public ActiveDTO save(ActiveDTO dto) {
        Optional<Active> optionalEntity = repository.findByName(dto.getName());
        if (optionalEntity.isPresent()) {
            throw new BusinessException(MessageUtils.ACTIVE_ALREADY_EXISTS);
        }
        Active active = mapper.toEntity(dto);
        repository.save(active);
        return mapper.toDto(active);
    }

    @Transactional
    public ActiveDTO update(ActiveDTO dto) {
        Optional<Active> optionalEntity = repository.findByName(dto.getName(), dto.getId());
        if (optionalEntity.isPresent()) {
            throw new BusinessException(MessageUtils.ACTIVE_ALREADY_EXISTS);
        }
        Active active = mapper.toEntity(dto);
        repository.save(active);
        return mapper.toDto(active);
    }

    @Transactional
    public ActiveDTO delete(Long id) {
        ActiveDTO activeDTO = findById(id);
        repository.deleteById(activeDTO.getId());
        return activeDTO;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ActiveDTO> findAll() {
        List<Active> list = repository.findAll();
        if (list.isEmpty()) {
            throw new NotFoundException();
        }
        return mapper.toDto(list);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ActiveDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ActiveDTO> findByCurrentDate() {
        return repository.findByCurrentDate()
                .map(mapper::toDto)
                .orElseThrow(NotFoundException::new);
    }
}
