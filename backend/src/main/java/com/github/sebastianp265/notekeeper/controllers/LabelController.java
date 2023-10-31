package com.github.sebastianp265.notekeeper.controllers;

import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.services.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @GetMapping
    public Collection<Label> findAll() {
        log.debug("Finding all labels");
        return labelService.findAll();
    }

    @GetMapping("/{name}")
    public Label findById(@PathVariable String name) {
        log.debug("Finding label by name = " + name);
        return labelService.findById(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label create(@RequestBody Label label) {
        log.debug("Creating label with body = " + label);
        return labelService.create(label);
    }

    @PutMapping("/{name}")
    public Label update(@PathVariable String name, @RequestBody Label label) {
        log.debug("Updating label with id = {}\nand body = {}", name, label);
        return labelService.update(name, label);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        log.debug("Updating label with name = {}", name);
        labelService.deleteById(name);
    }
}
