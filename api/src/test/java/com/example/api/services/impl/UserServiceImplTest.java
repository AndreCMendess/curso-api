package com.example.api.services.impl;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.impl.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    void whenFindByIdThenReturnAnObectNotFoundException(){

        // Simula o comportamento do repositório para que, quando o metodo findById for chamado com qualquer argumento, ele lance uma ObjectNotFoundException com a mensagem "Objeto não encontrado"
        when(repository.findById(any())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));


        try{
            // Chama o metodo service.findById e espera que ele lance uma exceção do tipo ObjectNotFoundException
                service.findById(ID);
        }catch(Exception ex){
            // Verifica se o tipo da exceção capturada é ObjectNotFoundException
            assertEquals(ObjectNotFoundException.class,ex.getClass());

            // Verifica se a mensagem da exceção é "Objeto não encontrado"
            assertEquals("Objeto não encontrado",ex.getMessage());
        }

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