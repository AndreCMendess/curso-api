package com.example.api.services.impl;

import com.example.api.domain.User;
import com.example.api.domain.dto.UserDTO;
import com.example.api.repositories.UserRepository;
import com.example.api.services.impl.exception.DataIntegratyViolationException;
import com.example.api.services.impl.exception.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class UserServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "Andre C";
    public static final String EMAIL = "andre@gmail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;
    public static final String EMAIL_JA_CADASTRO_NO_SISTEMA = "E-mail já cadastrado no sistema";
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
    void whenfindAllThenReturnListUsers() {
        //Simula o comportamento do repositorio para que , que o metodo findAll for chamado retorne uma lista de usuario
        when(repository.findAll()).thenReturn(List.of(user));

        //Chama o metodo  findAll e armazena a resposta em uma lista de user
        List<User> response = service.findAll();

        // Verifica se a resposta da lista nao é nula
        assertNotNull(response);

        // Verifica se o numero de usuarios retornados da lista é igual ao esperado
        assertEquals(1,response.size());

        // Verifica se o tipo da resposta retornada é um user
        assertEquals(User.class,response.get(0).getClass());

        // Verifica se o id do primeiro objeto na lista é o esperado
        assertEquals(ID,response.get(INDEX).getId());
        // Verifica se o nome do primeiro objeto na lista é o esperado
        assertEquals(NAME,response.get(INDEX).getName());
        // Verifica se o email do primeiro objeto na lista é o esperado
        assertEquals(EMAIL,response.get(INDEX).getEmail());
        // Verifica se o password do primeiro objeto na lista é o esperado
        assertEquals(PASSWORD,response.get(INDEX).getPassword());

    }

    @Test
    void whenCreateThenReturnSucess() {

        // Simula o comportamento do repositorio para que,
        // quando o metodo save for chamado com qualquer objeto, ele retorne um User
        when(repository.save(any())).thenReturn(user);
        // Chama o metodo service create passando um userDTO e
        // armazena o resultado em um objeto do tipo user
        User response = service.create(userDTO);

        // Verifica se a resposta nao é nula, ou seja, o objeto user foi criado com sucesso
        assertNotNull(response);
        // Verifica se o tipo da resposta é user
        assertEquals(User.class,response.getClass());
        // Verifica se o id do objeto criado é o esperado
        assertEquals(ID,response.getId());
        // Verifica se o nome do objeto criado é o esperado
        assertEquals(NAME,response.getName());
        // Verifica se o email do objeto criado é o esperado
        assertEquals(EMAIL,response.getEmail());
        // Verifica se o password do objeto criado é o esperado
        assertEquals(PASSWORD,response.getPassword());


    }

    @Test
    void whenCreateReturnDataIntegrityViolationException(){
        // Simula o comportamento do repositorio para que, quando o metodo findByEmail
        // for chamado com qualquer string de email , ele retorna um optionalUser
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            // Altera o ID do usuário retornado para 2, simulando um cenário onde o ID do usuário encontrado
            // é diferente do ID do objeto que está sendo criado ou atualizado, o que causaria um conflito de email já cadastrado.
            optionalUser.get().setId(2);
            // Chama o service  o service para criar o usuario usando o userDTO , oque deve lançar uma exceçao devido ao email ja cadastrado
            service.findByEmail(userDTO);
        }catch (Exception ex){
            // Verifica se a exceção lançada é do tipo dataIntegratyViolationException
            assertEquals(DataIntegratyViolationException.class,ex.getClass());
            // Verifica se a mensagem exceção é "Email ja cadastrado no sistema"
            assertEquals(EMAIL_JA_CADASTRO_NO_SISTEMA,ex.getMessage());
        }
    }

    @Test
    void WhenUpdateThenReturnSucess() {
        // Simula o comportamento do repositorio para que ,
        // quando o metodo save for chamado com qualquer objeto , ele retorna um user
    when(repository.save(any())).thenReturn(user);
    // Chama o metodo service update passando um userDTO de parametro e retorna um user
    User response = service.update(userDTO);

        // Verifica se a resposta nao é nula, ou seja, o objeto user foi criado ocm sucesso
        assertNotNull(response);
        // Verifica se o tipo da resposta é user
        assertEquals(User.class,response.getClass());
        // Verifica se o id do objeto criado é o esperado
        assertEquals(ID,response.getId());
        // Verifica se o nome do objeto criado é o esperado
        assertEquals(NAME,response.getName());
        // Verifica se o email do objeto criado é o esperado
        assertEquals(EMAIL,response.getEmail());
        // Verifica se o password do objeto criado é o esperado
        assertEquals(PASSWORD,response.getPassword());

    }

    @Test
    void WhenUpdateThenReturnDataIntegrityViolationExceptoin() {
        // Simula o comportamento do repositorio para que ,
        // quando o metodo findbYEMAILamado com qualquer objeto , ele retorna um optionalUser
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{

            optionalUser.get().setId(2);
            // Chama o metodo service update passando um userDTO de parametro
            service.update(userDTO);
        }catch(Exception ex){
            // Verifica se a exceção lançada é do tipo dataIntegratyViolationException
            assertEquals(DataIntegratyViolationException.class,ex.getClass());
            // Verifica se a mensagem exceção é "Email ja cadastrado no sistema"
            assertEquals(EMAIL_JA_CADASTRO_NO_SISTEMA,ex.getMessage());
        }




    }

    @Test
    void deleteWithSucess() {
        // Simula  o repositorio utilizando uma metodo findById qualquer metodo inteiro e retorna um optionalUser
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        // Nao faça nada enquanto o repositorio utilizar o metodo deletarById com qualquer numero inteiro
        doNothing().when(repository).deleteById(anyInt());
        // Chama o metodo service delete utilizando o ID como parametro
        service.delete(ID);
        // Verifica se no repositorio , o metodo deleteById esta sendo chamado apenas uma vez
        verify(repository,times(1)).deleteById(anyInt());
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