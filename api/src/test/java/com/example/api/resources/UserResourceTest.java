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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

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
    void whenFindAllThenReturnAlistOfUserDTO() {

        // Simula o retorna da lista de usuarios do service
       when(service.findAll()).thenReturn(List.of(user));
       // Simula o mapeamento de qualquer entrada para userDTO
       when(mapper.map(any(),any())).thenReturn(userDTO);

       // Cria uma instancia de um responseEntity de uma lista de userDTO utilizando o metodo que busca todos os usuarios da api
       ResponseEntity<List<UserDTO>> response = resource.findAll();

       // Verifica se o response não é nulo
        assertNotNull(response);
        // Verifica se o corpo do resposne nao é nulo (ou seja , a lista de usuarios)
        assertNotNull(response.getBody());
        // Verifica se o status da resposta é OK (200)
        assertEquals(HttpStatus.OK,response.getStatusCode());
        // Verifica se o tipo da reposta é ResponseEntity
        assertEquals(ResponseEntity.class,response.getClass());
        // Verifica se o corpo da resposta é uma ArrayList
        assertEquals(ArrayList.class,response.getBody().getClass());
        // Verifica se a classe do objeto encontrado no corpo pelo index é do tipo UuserDTO
        assertEquals(UserDTO.class,response.getBody().get(INDEX).getClass());

        // Verifica se o ID do primeiro usuario na lista é o esperado
        assertEquals(ID,response.getBody().get(INDEX).getId());
        // Verifica se o nome do primeiro usuário é o esperado
        assertEquals(NAME,response.getBody().get(INDEX).getName());
        // Verifica se o e-mail do primeiro usuário é o esperado
        assertEquals(EMAIL,response.getBody().get(INDEX).getEmail());

        // Verifica se a senha do primeiro usuário é a esperada
        assertEquals(PASSWORD,response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {

        // Simula um retorna de usuario utilizando o metodo create do service
        when(service.create(any())).thenReturn(user);

        // Cria uma instancia de um ResponseEntity<UserDto> utilizando o metodo create do resource
        ResponseEntity<UserDTO> response = resource.create(userDTO);

        // Verifica se a clase esperada pelo responde é do tipo ResponseEntity
        assertEquals(ResponseEntity.class,response.getClass());

        // Verifica se o status da resposta é 201 CREATED(usuario foi criado com sucesso)
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        // Verifica se o cabeçalho da resposta contem a chave "Localtion"(localização do novo recurso)
        assertNotNull(response.getHeaders().get("Location"));

    }

    @Test
    void whenUpdateThenReturnSucess() {

        // Simula a atualização de um usuario no service,retornado o objeto user atualizado
        when(service.update(userDTO)).thenReturn(user);
        // Simula o mapeamento de um objeto e o retornado como um UserDTO
        when(mapper.map(any(),any())).thenReturn(userDTO);

        // Instancia um objeto do tipo ResponseEntity<UserDTO> , atualiza um usuario registrado passando  o id e um userDTO como parametro
        ResponseEntity<UserDTO> response = resource.update(ID,userDTO);

        // Verifica se a resposta não é nula
        assertNotNull(response);
        // Verifica se o corpo da resposta (dados do usuário) não é nulo
        assertNotNull(response.getBody());
        // Verifica se o status da resposta é ok (200) (indicando sucesso na atualizaçao do usuario)
        assertEquals(HttpStatus.OK,response.getStatusCode());
        // Verifica se o tipo da resposta é ResponseEntity
        assertEquals(ResponseEntity.class,response.getClass());
        // Verifica se o tipo do corpo da resposta é UserDTO
        assertEquals(UserDTO.class,response.getBody().getClass());
        // Verifica se o ID retornado no UserDTO é o mesmo esperado
        assertEquals(ID,response.getBody().getId());
        // Verifica se o nome retornado no UserDTO é o esperado
        assertEquals(NAME,response.getBody().getName());
        // Verifica se o e-mail retornado no UserDTO é o esperado
        assertEquals(EMAIL,response.getBody().getEmail());

    }

    @Test
    void delete() {
    }

    private void startUser(){

        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);


    }
}