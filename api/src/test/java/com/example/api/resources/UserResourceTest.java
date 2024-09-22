package com.example.api.resources;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import com.example.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserResourceTest {

    public static final int ID = 1;
    public static final String NAME = "Andre C";
    public static final String EMAIL = "andre@gmail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;
    public static final String EMAIL_JA_CADASTRO_NO_SISTEMA = "E-mail já cadastrado no sistema";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

    @InjectMocks
    private UserResource resource;
    @Mock
    private UserService service;
    @Mock
    private ModelMapper mapper;
    private User user;
    private UserDTO userDTO;



    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSucess() {

        // Simula o comportamento do metodo findById do serviço, aceitando qualquer valor inteiro como argumento.
        // O metodo retorna um objeto User previamente configurado (user) quando chamado.
        // Isso permite testar o comportamento do controlador sem depender da implementação real do serviço.
       when(service.findById(anyInt())).thenReturn(user);
        // Simula o comportamento do mapper, que recebe dois parâmetros: um objeto de qualquer tipo (any()) e o tipo de destino (any()).
        // O mapper converte o objeto de origem para um UserDTO, retornando o objeto userDTO quando chamado.
        // Esse mock permite testar a lógica do controlador sem depender da lógica real de mapeamento.
       when(mapper.map(any(),any())).thenReturn(userDTO);

        // Chama o metodo findById do controlador (resource), passando o ID esperado.
        // O metodo deve retornar um ResponseEntity<UserDTO> contendo o UserDTO encontrado.
        // Aqui, estamos verificando como o controlador responde quando o serviço e o mapper funcionam corretamente.
        ResponseEntity<UserDTO> response = resource.findById(ID);

        // Verifica se o response nao é nulo
       assertNotNull(response);
       // Verifica se os dados do response nao é nulo
       assertNotNull(response.getBody());
       // Verifica se o objeto response é uam instancia de responseEntity
       assertEquals(ResponseEntity.class,response.getClass());
       // Verifica se o corpo do response é um objeto do tipo   userDto
       assertEquals(UserDTO.class,response.getBody().getClass());

       // Verifica se o campo ID é valor esperado
       assertEquals(ID,response.getBody().getId());
        // Verifica se o campo NAME é valor esperado
       assertEquals(NAME,response.getBody().getName());
        // Verifica se o campo EMAIL é valor esperado
       assertEquals(EMAIL,response.getBody().getEmail());
        // Verifica se o campo PASSWORD é valor esperado
       assertEquals(PASSWORD,response.getBody().getPassword());


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

    private void startUser(){

        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);


    }
}