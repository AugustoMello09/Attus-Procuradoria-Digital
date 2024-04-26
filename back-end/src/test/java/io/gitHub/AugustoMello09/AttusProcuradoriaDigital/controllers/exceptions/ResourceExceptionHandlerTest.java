package io.gitHub.AugustoMello09.AttusProcuradoriaDigital.controllers.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import io.gitHub.AugustoMello09.AttusProcuradoriaDigital.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest
public class ResourceExceptionHandlerTest {
	
	private static final String NAO_ENCONTRADO = "não encontrado";

	@MockBean
	private HttpServletRequest request;

	@InjectMocks
	private ResourceExceptionHandler exceptionHandler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void whenObjectNotFoundThenReturnAResponseEntity() {
		ResponseEntity<StandardError> response = exceptionHandler
				.objectNotFound(new ObjectNotFoundException(NAO_ENCONTRADO), new MockHttpServletRequest());
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals(NAO_ENCONTRADO, response.getBody().getError());
		assertEquals(404, response.getBody().getStatus());
	}
	
	@Test
	void whenValidationErrorsThenReturnAResponseEntity() throws NoSuchMethodException, SecurityException {
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "object");
	    bindingResult.addError(new FieldError("object", "field", "default message"));
	    MethodParameter methodParameter = new MethodParameter(
	        this.getClass().getDeclaredMethod("whenValidationErrorsThenReturnAResponseEntity"), -1);
	    MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);
	    ResponseEntity<StandardError> response = exceptionHandler.validation(ex, request);
	    assertNotNull(response);
	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	    assertNotNull(response.getBody());
	    assertEquals(ValidationError.class, response.getBody().getClass());
	    assertEquals(ex.getMessage(), response.getBody().getError());
	    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
	    ValidationError error = (ValidationError) response.getBody();
	    assertNotNull(error.getErrors());
	    assertFalse(error.getErrors().isEmpty());
	    boolean containsFieldError = error.getErrors().stream()
	        .anyMatch(fieldMessage -> "field".equals(fieldMessage.getFieldName()) && "default message".equals(fieldMessage.getMessage()));
	    assertTrue(containsFieldError);
	}


	


}
