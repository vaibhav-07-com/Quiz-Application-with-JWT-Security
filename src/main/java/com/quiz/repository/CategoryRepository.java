package com.quiz.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.quiz.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Optional<Category> findByCName(String cName);

    void deleteByCName(String cName);
}