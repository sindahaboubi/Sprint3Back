package com.ms.gestionHistoireTicket.repositories;

import com.ms.gestionHistoireTicket.entities.HistoireTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoireTicketRepository extends JpaRepository<HistoireTicket, Long> {
    public List<HistoireTicket> findAllByProductBacklogId(Long id);
    public List<HistoireTicket> findByMembreId(Long membreId);
    public List<HistoireTicket> findByproductBacklogId(Long productBacklogId);

    public List<HistoireTicket> findBySprintId(Long sprintId);

    HistoireTicket findByProductBacklogIdAndTitreAndDescriptionAndEffortAndPriorite(
            Long productBacklogId,
            String titre,
            String description,
            Integer effort,
            String priorite
    );
    
}
