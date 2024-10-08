package com.example.api.resources.exceptions;

import com.example.api.services.impl.exception.DataIntegratyViolationException;
import com.example.api.services.impl.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ResourceExceptionHandlerTest {

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final String E_MAIL_JA_CADASTRADO = "E-mail já cadastrado";
    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void whenObjectNotFoundThenRturnAReponseEntity() {

        ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO)
        ,new MockHttpServletRequest());

       assertNotNull(response);
       assertNotNull(response.getBody());
       assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
       assertEquals(ResponseEntity.class,response.getClass());
       assertEquals(StandardError.class,response.getBody().getClass());
       assertEquals(OBJETO_NAO_ENCONTRADO,response.getBody().getErro());
       assertEquals(404,response.getBody().getStatus());


    }

    @Test
    void dataIntegrityViolationException() {

        ResponseEntity<StandardError> response = exceptionHandler.dataIntegrityViolationException(new DataIntegratyViolationException(E_MAIL_JA_CADASTRADO)
                ,new MockHttpServletRequest());


        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(StandardError.class,response.getBody().getClass());
        assertEquals(E_MAIL_JA_CADASTRADO,response.getBody().getErro());
        assertEquals(400,response.getBody().getStatus());


    }
}