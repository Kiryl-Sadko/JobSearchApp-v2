package com.epam.esm.controller;

import com.epam.esm.dto.VacancyDto;
import com.epam.esm.service.VacancyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyService service;

    public VacancyController(VacancyService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VacancyDto>> showAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "3", required = false) int size,
            @RequestParam(name = "direction", defaultValue = "ASC", required = false) String direction,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String parameters,
            @RequestParam(name = "skills", required = false) String skills) {

        Sort.Direction direc = Sort.Direction.fromString(direction);
        String[] properties = parameters.split(",");
        Sort sort = Sort.by(direc, properties);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<VacancyDto> result;

        if (skills == null || skills.isBlank()) {
            result = service.findAll(pageable);
        } else {
            List<Long> skillIdList = Arrays.stream(skills.trim().split(","))
                    .filter(s -> !s.trim().isBlank())
                    .map(s -> Long.valueOf(s.trim()))
                    .collect(Collectors.toList());
            result = service.findAllBySkill(skillIdList, pageable);
        }
        result.forEach(this::addLinks);
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacancyDto> show(@PathVariable(value = "id") Long id) {
        VacancyDto dto = service.findById(id);
        addLinks(dto);
        return new ResponseEntity<>(dto, OK);
    }

    @PostMapping
    public ResponseEntity<List<VacancyDto>> create(@RequestBody VacancyDto dto) {
        service.save(dto);
        ResponseEntity<List<VacancyDto>> responseEntity = showAll(0, 3, "asc", "id", "");
        return new ResponseEntity<>(responseEntity.getBody(), CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(MessageFormat.format("Skill has been successfully deleted, id={0}", id), ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VacancyDto> update(@PathVariable(value = "id") Long id,
                                             @RequestBody VacancyDto dto) {
        dto.setId(id);
        dto = service.update(dto);
        addLinks(dto);
        return new ResponseEntity<>(dto, ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VacancyDto> partialUpdate(@PathVariable(value = "id") Long id,
                                                    @RequestBody VacancyDto dto) {
        dto.setId(id);
        dto = service.partialUpdate(dto);
        addLinks(dto);
        return new ResponseEntity<>(dto, ACCEPTED);
    }

    private void addLinks(VacancyDto vacancyDto) {
        vacancyDto.add(linkTo(methodOn(VacancyController.class).show(vacancyDto.getId())).withSelfRel());
        vacancyDto.add(linkTo(methodOn(VacancyController.class).create(vacancyDto)).withRel("create"));
        vacancyDto.add(linkTo(methodOn(VacancyController.class).partialUpdate(vacancyDto.getId(), vacancyDto)).withRel("update"));
        vacancyDto.add(linkTo(methodOn(VacancyController.class).delete(vacancyDto.getId())).withRel("delete"));
    }
}
