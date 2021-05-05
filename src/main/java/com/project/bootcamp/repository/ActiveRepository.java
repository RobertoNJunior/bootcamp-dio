package com.project.bootcamp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.bootcamp.model.Active;

@Repository
public interface ActiveRepository extends JpaRepository<Active, Long> {

    public Optional<Active> findByName(String name);

    @Query("SELECT active FROM Active active WHERE active.name = :name AND active.id <> :id ")
    public Optional<Active> findByName(String name, Long id);

    @Query("SELECT active FROM Active active WHERE active.date = CURRENT_DATE ")
    Optional<List<Active>> findByCurrentDate();
}
