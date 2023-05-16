package com.ms.gestionSprints.services;

import com.ms.gestionSprints.entities.Sprint;
import com.ms.gestionSprints.repositories.SprintRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SprintService {
    private static final Logger logger = LogManager.getLogger(SprintService.class);
    @Autowired
    private SprintRepository sprintRepository;
    public List<Sprint> findAllSprintsByProductBacklog(Long id){
        return this.sprintRepository.findAllByProductBacklogId(id);
    }
    public List<Sprint> findAllSprints(){
        return this.sprintRepository.findAll();
    }
    public Sprint createSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public Sprint modifierSprint(Sprint sp){
       
        return sprintRepository.save(sp);
    }

    public void supprimerSprint(Long id){
        this.sprintRepository.deleteById(id);
    }

    public Sprint getSprint(Long id){
        return sprintRepository.findById(id).get();
    }
}
