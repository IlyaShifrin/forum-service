package telran.java52.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoPermissionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}