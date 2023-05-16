package com.ms.notificationservice.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ms.notificationservice.model.Sprint;

@FeignClient(name="gestion-sprints-service")
public interface SprintFeignClient {
    
    @GetMapping("/sprints")
    public List<Sprint> getAllSprint();


    
}
