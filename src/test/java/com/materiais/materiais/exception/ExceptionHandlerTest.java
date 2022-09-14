package com.materiais.materiais.exception;

import com.materiais.materiais.configuration.errorresponse.ErrorResponse;
import com.materiais.materiais.configuration.exception.ApiExceptionHandler;
import com.materiais.materiais.configuration.exception.IdNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(value = ApiExceptionHandler.class)
@AutoConfigureMockMvc
public class ExceptionHandlerTest {

    @InjectMocks
    private ApiExceptionHandler exceptionHandler;

    @Mock
    private ErrorResponse errorResponse;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName("Deve lançar IdNotFoundException")
    void apiNotFoundException() {
        ErrorResponse response = exceptionHandler
                .resourceNotFoundException(new IdNotFoundException(
                        errorResponse.getMessage()));

        assertNotNull(response);
        assertEquals("IdNotFoundException", response.getParameter());
        assertEquals("NOT_FOUND", response.getField());
        assertEquals("Dados não encontrados!", response.getMessage());
    }

    @Test
    @DisplayName("Deve lançar NullPointerException")
    void nullPointerException() {
        ErrorResponse response = exceptionHandler
                .nullPointerException(new NullPointerException(
                        errorResponse.getParameter()));
        assertNotNull(response);
        assertEquals("NullPointerException", response.getParameter());
        assertEquals("INTERNAL_SERVER_ERROR", response.getField());
    }

    @Test
    @DisplayName("Deve lançar HttpRequestMethodNotSupportedException")
    void shouldThowsHttpRequestMethodNotSupportedException() {
        ErrorResponse response = exceptionHandler
                .httpRequestMethodNotSupportedException(new HttpRequestMethodNotSupportedException(
                        errorResponse.getParameter()));
        assertNotNull(response);
        assertEquals("HttpRequestMethodNotSupportedException", response.getParameter());
        assertEquals("METHOD_NOT_ALLOWED", response.getField());
    }
}
