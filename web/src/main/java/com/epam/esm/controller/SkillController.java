package com.epam.esm.controller;

import com.epam.esm.dto.SkillDto;
import com.epam.esm.service.SkillService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private static final Logger LOGGER = LogManager.getLogger(SkillController.class);

    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SkillDto>> showAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @RequestParam(name = "direction", defaultValue = "asc", required = false) String direction,
            @RequestParam(name = "property", defaultValue = "id") String property) {

        Sort.Direction direc = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(direc, property);
        Pageable pageRequest = PageRequest.of(page, size, sort);
        List<SkillDto> all = service.findAll(pageRequest);
        all.forEach(this::addLinks);
        return new ResponseEntity<>(all, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDto> show(@PathVariable(value = "id") Long id) {
        SkillDto dto = service.findById(id);
        addLinks(dto);
        return new ResponseEntity<>(dto, OK);
    }

    @PostMapping
    public ResponseEntity<List<SkillDto>> create(@RequestBody SkillDto dto) {
        service.save(dto);
        ResponseEntity<List<SkillDto>> responseEntity = showAll(0, 5, "asc", "id");
        return new ResponseEntity<>(responseEntity.getBody(), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDto> update(@PathVariable(value = "id") Long id,
                                           @RequestBody SkillDto dto) {
        dto.setId(id);
        dto = service.update(dto);
        addLinks(dto);
        return new ResponseEntity<>(dto, ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SkillDto> partialUpdate(@PathVariable(value = "id") Long id,
                                                  @RequestBody SkillDto dto) {
        dto.setId(id);
        dto = service.partialUpdate(dto);
        addLinks(dto);
        return new ResponseEntity<>(dto, ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(MessageFormat.format("Skill has been deleted, id={0}", id), ACCEPTED);
    }

    @GetMapping("/widely")
    public ResponseEntity<SkillDto> showWidelyUsedSkill() {
        SkillDto skill = service.findTheMostWidelyUsedSkillWithHighestSalary();
        addLinks(skill);
        return new ResponseEntity<>(skill, OK);
    }

    private void addLinks(SkillDto skillDto) {
        skillDto.add(linkTo(methodOn(SkillController.class).show(skillDto.getId())).withSelfRel());
        skillDto.add(linkTo(methodOn(SkillController.class).create(skillDto)).withRel("create"));
        skillDto.add(linkTo(methodOn(SkillController.class).delete(skillDto.getId())).withRel("delete"));
        skillDto.add(linkTo(methodOn(SkillController.class).partialUpdate(skillDto.getId(), skillDto)).withRel("update"));
    }
}
