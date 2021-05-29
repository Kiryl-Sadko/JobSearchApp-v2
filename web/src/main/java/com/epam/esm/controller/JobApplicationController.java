package com.epam.esm.controller;

import com.epam.esm.dto.JobApplicationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.VacancyDto;
import com.epam.esm.service.JobApplicationService;
import com.epam.esm.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class JobApplicationController {

    private final JobApplicationService service;
    private final UserService userService;

    public JobApplicationController(JobApplicationService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/{id}/applications")
    public ResponseEntity showAll(
            @PathVariable(value = "id") Long userId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "3", required = false) int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<JobApplicationDto> all = service.findByUserId(userId, pageable);
        if (all.isEmpty()) {
            UserDto user = userService.findById(userId);
            String message = MessageFormat.format("This user {0} has no job applications", user);
            return new ResponseEntity<>(message, OK);
        }
        all.forEach(this::addLinks);
        return new ResponseEntity<>(all, OK);
    }

    @GetMapping("/{userId}/applications/{id}")
    public ResponseEntity<JobApplicationDto> show(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "id") Long id) {

        JobApplicationDto dto = service.findById(id);
        addLinks(dto);
        return new ResponseEntity<>(dto, OK);
    }

    @PostMapping("/{id}/applications")
    public ResponseEntity<List<JobApplicationDto>> create(@PathVariable(value = "id") Long userId,
                                                          @RequestBody VacancyDto vacancyDto) {

        service.save(userId, vacancyDto.getId());
        ResponseEntity<List<JobApplicationDto>> responseEntity = showAll(userId, 0, 3);
        return new ResponseEntity<>(responseEntity.getBody(), CREATED);
    }

    @DeleteMapping("/{userId}/applications/{id}")
    public ResponseEntity<List<JobApplicationDto>> delete(@PathVariable(value = "userId") Long userId,
                                                          @PathVariable(value = "id") Long id) {

        service.deleteById(id);
        ResponseEntity<List<JobApplicationDto>> responseEntity = showAll(userId, 0, 3);
        return new ResponseEntity<>(responseEntity.getBody(), ACCEPTED);
    }

    private void addLinks(JobApplicationDto dto) {
        Long userId = dto.getUserDto().getId();
        dto.add(linkTo(methodOn(JobApplicationController.class).show(userId, dto.getId()))
                .withSelfRel());
    }
}
