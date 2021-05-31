package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> showAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @RequestParam(name = "direction", defaultValue = "asc", required = false) String direction,
            @RequestParam(name = "property", defaultValue = "id") String property) {

        Sort.Direction direc = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(direc, property);
        Pageable pageRequest = PageRequest.of(page, size, sort);
        List<UserDto> all = service.findAll(pageRequest);
        all.forEach(this::addLinks);
        return new ResponseEntity<>(all, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> show(@PathVariable(value = "id") Long id) {
        UserDto userDto = service.findById(id);
        addLinks(userDto);
        return new ResponseEntity<>(userDto, OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> partialUpdate(@PathVariable(value = "id") Long id,
                                                 @RequestBody UserDto userDto) {
        userDto.setId(id);
        userDto = service.partialUpdate(userDto);
        addLinks(userDto);
        return new ResponseEntity<>(userDto, ACCEPTED);
    }

    private void addLinks(UserDto userDto) {
        userDto.add(linkTo(methodOn(UserController.class).show(userDto.getId())).withSelfRel());
    }
}
