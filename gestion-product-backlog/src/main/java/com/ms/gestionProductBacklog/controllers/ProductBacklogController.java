package com.ms.gestionProductBacklog.controllers;

import com.ms.gestionProductBacklog.entities.ProductBacklog;
import com.ms.gestionProductBacklog.models.HistoireTicket;
import com.ms.gestionProductBacklog.models.Projet;
import com.ms.gestionProductBacklog.services.ProductBacklogService;
import com.ms.gestionProductBacklog.services.ProjetClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product-backlogs")
public class ProductBacklogController {
    @Autowired
    private ProductBacklogService productBacklogService;
    @Autowired
    private ProjetClientService projetClientService;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/{id}")
    public ProductBacklog getProductBacklogById(@PathVariable(name="id") Long id){
        try {
            return this.productBacklogService.getProductBacklogById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    @GetMapping("/projet/{idProjet}")
    public ProductBacklog getProductBacklogByIdProjet(@PathVariable(name="idProjet") Long idProjet) throws SQLException{
        Projet projet  = this.projetClientService.findProjetById(idProjet);
        ProductBacklog productBacklog = this.productBacklogService.findProductBacklogByProjetId(idProjet);
        productBacklog.setProjet(projet);
        return productBacklog;
    }
    @GetMapping("/{productBacklogId}/histoiresTickets")
    public List<HistoireTicket> getHistoireTicketsByProductBacklogId(@PathVariable Long productBacklogId) {
        String url = "http://localhost:9999/gestion-histoire-ticket/histoireTickets/productBacklog/" + productBacklogId;
        return restTemplate.getForObject(url, List.class);
    }
    @PostMapping
    public ResponseEntity<ProductBacklog> create(@RequestBody ProductBacklog backlog, @RequestParam Long projectId) {
        backlog.setProjetId(projectId);
        ProductBacklog createdBacklog = productBacklogService.createNewProductBacklog(backlog);
        return new ResponseEntity<>(createdBacklog, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> elevateProductBacklogVelocity(@RequestBody Map<String, Object> request) {
        Long productBacklogId = Long.parseLong(request.get("productBacklogId").toString());
        int effort = Integer.parseInt(request.get("effort").toString());

        try {
            productBacklogService.elevateProductBacklogVelocity(productBacklogId, effort);
            return ResponseEntity.ok("Product backlog velocity elevated successfully.");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/")
    public ResponseEntity<String> decreaseProductBacklogVelocity(@RequestBody Map<String, Long> requestBody) {
        Long productBacklogId = requestBody.get("productBacklogId");
        Long histoireTicketId = requestBody.get("histoireTicketId");
        productBacklogService.decreaseProductBacklogVelocity(productBacklogId, histoireTicketId);
        return ResponseEntity.ok("Effort removed from Product Backlog velocity");
    }




}
