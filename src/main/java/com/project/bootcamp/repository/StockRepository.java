package com.project.bootcamp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.bootcamp.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    public Optional<Stock> findByName(String name);

    @Query("SELECT stock FROM Stock stock WHERE stock.name = :name AND stock.id <> :id ")
    public Optional<Stock> findByName(String name, Long id);

    @Query("SELECT stock FROM Stock stock WHERE stock.date = CURRENT_DATE ")
    Optional<List<Stock>> findByCurrentDate();
}
