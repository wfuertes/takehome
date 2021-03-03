package com.challenge.demo.site;

import com.challenge.demo.site.dto.SiteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sites")
public class SiteController {

    private final SiteRepository siteRepository;

    public SiteController(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @PostMapping
    public ResponseEntity<SiteDTO> createSite(@RequestBody SiteDTO siteDTO) {
        Site siteCreated = siteRepository.save(siteDTO.createSite(UUID.randomUUID()));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(SiteDTO.build(siteCreated));
    }

    @GetMapping
    public ResponseEntity<List<SiteDTO>> getSites() {
        return ResponseEntity.ok(siteRepository.findAll()
                                               .stream()
                                               .map(SiteDTO::build)
                                               .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteDTO> updateSite(@PathVariable(value = "id") Long siteId, @RequestBody SiteDTO siteDTO) {
        return siteRepository.findById(siteId)
                             .map(siteDTO::updateSite)
                             .map(siteRepository::save)
                             .map(SiteDTO::build)
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SiteDTO> deleteSite(@PathVariable(value = "id") Long siteId) {
        return siteRepository.findById(siteId)
                             .map(site -> {
                                 siteRepository.delete(site);
                                 return ResponseEntity.ok(SiteDTO.build(site));
                             })
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteDTO> getSiteById(@PathVariable(value = "id") Long siteId) {
        return siteRepository.findById(siteId)
                             .map(SiteDTO::build)
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
