package com.example.api.services.impl;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import com.example.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "Andre C";
    public static final String EMAIL = "andre@gmail.com";
    public static final String PASSWORD = "123";
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        startUser();

    }
    @Test
    void whenFindByIDThenReturnAnUserInstance() {
        //Enquanto o repositorio procurar por id encontrar um id retorne o usuario
       when(repository.findById(any())).thenReturn(optionalUser);

        //Verifica se o findById retorna um User
        User response = service.findById(ID);

        //Verifica se o objeto retornado pelo findById é do tipo User.class
        assertEquals(User.class,response.getClass());

        //Verifica se o response nao vai ser null
        assertNotNull(response);

        //Verifica se o response.id é == ID de parametro
        assertEquals(response.getId(),ID);

        //Verifica se o response.nome é == NAME de parametro
        assertEquals(response.getName(),NAME);

        //Verifica se o response.email é == EMAIL de parametro
        assertEquals(response.getEmail(),EMAIL);



    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByEmail() {
    }

    private void startUser(){

        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));

    }

}