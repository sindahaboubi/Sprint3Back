package com.ms.corbeilleservice.service;

import com.ms.corbeilleservice.model.Sprint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="gestion-sprints-service")

public interface SprintFeignClient {
    @GetMapping("/sprints/{id}?projection=fullSprint")
    public Sprint getSprintById(@PathVariable("id") Long id);

}
