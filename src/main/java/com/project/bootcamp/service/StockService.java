package com.project.bootcamp.service;

import com.project.bootcamp.exceptions.BusinessException;
import com.project.bootcamp.exceptions.NotFoundException;
import com.project.bootcamp.mapper.StockMapper;
import com.project.bootcamp.model.Stock;
import com.project.bootcamp.model.dto.StockDTO;
import com.project.bootcamp.repository.StockRepository;
import com.project.bootcamp.util.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockMapper mapper;

    @Autowired
    private StockRepository repository;

    @Transactional
    public StockDTO save(StockDTO dto) {
        Optional<Stock> optionalEntity = repository.findByNameAndDate(dto.getName(), dto.getDate());
        if (optionalEntity.isPresent()) {
            throw new BusinessException(MessageUtils.ACTIVE_ALREADY_EXISTS);
        }
        Stock active = mapper.toEntity(dto);
        repository.save(active);
        return mapper.toDto(active);
    }

    @Transactional
    public StockDTO update(StockDTO dto) {
        Optional<Stock> optionalEntity = repository.findByName(dto.getName(), dto.getId(), dto.getDate());
        if (optionalEntity.isPresent()) {
            throw new BusinessException(MessageUtils.ACTIVE_ALREADY_EXISTS);
        }
        Stock active = mapper.toEntity(dto);
        repository.save(active);
        return mapper.toDto(active);
    }

    @Transactional
    public StockDTO delete(Long id) {
        StockDTO activeDTO = findById(id);
        repository.deleteById(activeDTO.getId());
        return activeDTO;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<StockDTO> findAll() {
        List<Stock> list = repository.findAll();
        if (list.isEmpty()) {
            throw new NotFoundException();
        }
        return mapper.toDto(list);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public StockDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<StockDTO> findByCurrentDate() {
        return repository.findByCurrentDate()
                .map(mapper::toDto)
                .orElseThrow(NotFoundException::new);
    }
}
