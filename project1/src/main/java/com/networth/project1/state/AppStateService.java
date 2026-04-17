package com.networth.project1.state;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AppStateService {

    private static final String DEFAULT_PROFILE = "default";

    private final AppStateRepository repository;
    private final ObjectMapper objectMapper;

    public AppStateService(AppStateRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public JsonNode getState() {
        return repository.findByProfileKey(DEFAULT_PROFILE)
                .map(entity -> parseState(entity.getStateJson()))
                .orElseGet(this::defaultState);
    }

    @Transactional
    public JsonNode saveState(JsonNode payload) {
        ObjectNode stateToSave = payload != null && payload.isObject()
                ? (ObjectNode) payload
                : defaultState();

        AppStateEntity entity = repository.findByProfileKey(DEFAULT_PROFILE)
                .orElseGet(AppStateEntity::new);
        entity.setProfileKey(DEFAULT_PROFILE);
        entity.setStateJson(stateToSave.toString());
        repository.save(entity);
        return stateToSave;
    }

    @Transactional
    public void clearState() {
        repository.findByProfileKey(DEFAULT_PROFILE).ifPresent(repository::delete);
    }

    private ObjectNode parseState(String json) {
        try {
            JsonNode parsed = objectMapper.readTree(json);
            return parsed != null && parsed.isObject() ? (ObjectNode) parsed : defaultState();
        } catch (Exception ex) {
            return defaultState();
        }
    }

    private ObjectNode defaultState() {
        ObjectNode node = objectMapper.createObjectNode();
        node.putObject("user")
                .put("name", "User")
                .put("email", "user@gmail.com");
        node.putArray("transactions");
        node.putArray("assets");
        node.putArray("categories");
        node.putObject("settings")
                .put("currency", "₹")
                .put("dateFormat", "DD-MM-YYYY");
        node.put("currentPage", "dashboard");
        return node;
    }
}
