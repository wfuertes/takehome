package com.challenge.demo.site;

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

@RestController
@RequestMapping("/sites")
public class SiteController {

	private final SiteRepository siteRepository;

    public SiteController(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @PostMapping
	public ResponseEntity<Site> createSite(@RequestBody Site createSite) {
		createSite.setSiteUUID(UUID.randomUUID());

		return ResponseEntity.status(HttpStatus.CREATED)
                             .body(siteRepository.save(createSite));
	}

	@GetMapping
	public ResponseEntity<List<Site>> getSites() {
        return ResponseEntity.ok(siteRepository.findAll());
	}

    @PutMapping("/{id}")
    public ResponseEntity<Site> updateSite(@RequestBody Site updatedSite, @PathVariable(value = "id") Long siteId) {
        return siteRepository.findById(siteId)
                             .map(site -> {
                                 site.setUrl(updatedSite.getUrl());
                                 return new ResponseEntity<>(siteRepository.save(site), HttpStatus.OK);
                             })
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Site> deleteSite(@PathVariable(value = "id") Long siteId) {
        return siteRepository.findById(siteId)
                             .map(site -> {
                                 siteRepository.delete(site);
                                 return ResponseEntity.ok(site);
                             })
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable(value = "id") Long siteId) {
        return siteRepository.findById(siteId)
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
