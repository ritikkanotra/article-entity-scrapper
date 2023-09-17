package com.example.scrapper.controller;

import com.example.scrapper.model.Entity;
import com.example.scrapper.model.EntityRelation;
import com.example.scrapper.model.request.GetEntityRequest;
import com.example.scrapper.service.EntityService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EntityController {

    @Autowired
    private EntityService entityService;

    @PostMapping("/get-entities")
    public ResponseEntity<List<Entity>> getEntities(@RequestBody GetEntityRequest request) {
        String url = request.getUrl();
        if (!new UrlValidator().isValid(url)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Entity> entityList = entityService.getEntities(url);
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @PostMapping("/get-entities-relation")
    public ResponseEntity<List<EntityRelation>> getEntitiesRelation(@RequestBody GetEntityRequest request) {
        String url = request.getUrl();
        if (!new UrlValidator().isValid(url)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<EntityRelation> entityRelationList = entityService.getEntitiesRelation(url);
        return new ResponseEntity<>(entityRelationList, HttpStatus.OK);
    }

}
