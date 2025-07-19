package com.recepies_service.repository;

import com.recepies_service.entity.RecipeEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    // Get by exact name
    Optional<RecipeEntity> findByName(String name);

    Optional<RecipeEntity> findByNameAndCreatedBy(String name, String createdBy);

    List<RecipeEntity> findAllByCreatedBy(String username);

    // Delete by name
    @Transactional
    void deleteByName(String name);

    @Transactional
    void deleteById(Long id);

    // Find all with pagination and sorting
    @Override
    List<RecipeEntity> findAll();

}
