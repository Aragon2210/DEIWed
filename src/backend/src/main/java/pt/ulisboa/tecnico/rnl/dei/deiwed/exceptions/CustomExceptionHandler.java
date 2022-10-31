package pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(DeiwedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public DeiwedExceptionDto deiwedException(DeiwedException e) {
		return new DeiwedExceptionDto(e);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public DeiwedExceptionDto unexpectedException(Exception e) {
		logger.error(e.getMessage(), e);
		return new DeiwedExceptionDto(e);
	}
}
