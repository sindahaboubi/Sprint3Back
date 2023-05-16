package com.ms.gestionProductBacklog.repositories;

import com.ms.gestionProductBacklog.entities.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductBacklogRepository extends JpaRepository<ProductBacklog,Long> {
    public ProductBacklog findProductBacklogByProjetId(Long id);
}
