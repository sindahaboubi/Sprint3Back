package com.ms.gestionSprints.repositories;

import com.ms.gestionSprints.entities.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    public List<Sprint> findAllByProductBacklogId(Long id);
}
