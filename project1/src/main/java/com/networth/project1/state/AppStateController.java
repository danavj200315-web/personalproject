package com.networth.project1.state;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/state")
public class AppStateController {

    private final AppStateService appStateService;

    public AppStateController(AppStateService appStateService) {
        this.appStateService = appStateService;
    }

    @GetMapping
    public ResponseEntity<JsonNode> getState() {
        return ResponseEntity.ok(appStateService.getState());
    }

    @PutMapping
    public ResponseEntity<JsonNode> saveState(@Valid @RequestBody JsonNode payload) {
        return ResponseEntity.ok(appStateService.saveState(payload));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearState() {
        appStateService.clearState();
        return ResponseEntity.noContent().build();
    }
}
