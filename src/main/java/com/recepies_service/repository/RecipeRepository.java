package com.recepies_service.repository;

import com.recepies_service.entity.RecipeEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    // Get by exact name
    Optional<RecipeEntity> findByName(String name);

    // Delete by name
    @Transactional
    void deleteByName(String name);

    // Find all with pagination and sorting
    @Override
    List<RecipeEntity> findAll();

    // Find by name containing (search by partial name)
    Page<RecipeEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
