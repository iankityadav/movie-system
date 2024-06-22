package com.priya.movie_system.repository;

import com.priya.movie_system.model.ProductionCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionCrewRepository extends JpaRepository<ProductionCrew, Long> {
}
