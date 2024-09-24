package com.example.api.resources;


import com.example.api.domain.dto.UserDTO;
import com.example.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserResource {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){

        return ResponseEntity.ok().body(modelMapper.map(userService.findById(id),UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){


        return ResponseEntity.ok()
                .body(userService.findAll()
                        .stream()
                        .map(x -> modelMapper.map(x,UserDTO.class))
                        .collect(Collectors.toList()));

    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj){
     URI url = ServletUriComponentsBuilder.fromCurrentRequest()
             .path("/{id}").buildAndExpand(userService.create(obj).getId()).toUri();
        return ResponseEntity.created(url).build();

    }

    @PutMapping(value="/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id,@RequestBody UserDTO obj){
        obj.setId(id);
        return ResponseEntity.ok().body(modelMapper.map(userService.update(obj),UserDTO.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id){

        userService.delete(id);
        return ResponseEntity.noContent().build();

    }


}
