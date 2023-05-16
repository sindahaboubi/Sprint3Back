package com.ms.gestionSprints.controllers;

import com.ms.gestionSprints.entities.ProductBacklog;
import com.ms.gestionSprints.entities.Sprint;
import com.ms.gestionSprints.services.ProductBacklogService;
import com.ms.gestionSprints.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/sprints")
public class SprintController {
    @Autowired
    private SprintService sprintService;
    @Autowired
    private ProductBacklogService productBacklogService;
    /*@GetMapping("/productBacklog/{id}")
    public List<Sprint> getSprintsByProductBacklog(@PathVariable(name="id") Long id) throws SQLException {
        ProductBacklog productBacklog  = this.productBacklogService.findProductBacklogById(id);
        List<Sprint> sprints = this.sprintService.findAllSprintsByProductBacklog(id);
        Collections.sort(sprints, new Comparator<Sprint>() {
            @Override
            public int compare(Sprint s1, Sprint s2) {
                return s1.getDateFin().compareTo(s2.getDateFin());
            }
        });
        for(Sprint sprint:sprints){
            sprint.setProductBacklog(productBacklog);
        }
        return sprints;
    }*/


    @GetMapping("/productBacklog/{id}")
    public List<Sprint> getSprintsByProductBacklog(@PathVariable(name="id") Long id) throws SQLException {
        ProductBacklog productBacklog  = this.productBacklogService.findProductBacklogById(id);
        List<Sprint> sprints = this.sprintService.findAllSprintsByProductBacklog(id);
        Collections.sort(sprints, new Comparator<Sprint>() {
            @Override
            public int compare(Sprint s1, Sprint s2) {
                return s1.getDateFin().compareTo(s2.getDateFin());
            }
        });
        for(Sprint sprint:sprints){
            sprint.setProductBacklog(productBacklog);
            sprint.setDateLancement(sprint.getDateLancement());
            sprint.setDateFin(sprint.getDateFin());
        }
        return sprints;
    }


    @PostMapping
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint, @RequestParam Long productBacklogId) {
        sprint.setProductBacklogId(productBacklogId);
        sprint.setVelocite(0);
        sprint.setEtat("en attente");
        Sprint createdSprint = sprintService.createSprint(sprint);
        return new ResponseEntity<>(createdSprint, HttpStatus.CREATED);
    }
    @PutMapping
    public Sprint modifierSprintDate(@RequestBody Sprint sprint){
        System.out.println(sprint);
        ProductBacklog productBacklog  = this.productBacklogService.findProductBacklogById(sprint.getProductBacklogId());
        sprint.setProductBacklog(productBacklog);
        return this.sprintService.modifierSprint(sprint);
    }
    @DeleteMapping("/{id}")
    public void supprimerSprint(@PathVariable("id") Long id){
        this.sprintService.supprimerSprint(id);
    }
    @GetMapping("/{id}")
    public Sprint getSprint(@PathVariable("id") Long id){
        return this.sprintService.getSprint(id);
    }

    private Date truncateDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
