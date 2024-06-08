package library.controller.error;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerErrorHandler {
	
	@Data
	private class ExceptionMessage {
		private String message;
		private String statusReason;
		private Integer statusCode;
		private String timestamp;
		private String uri;
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionMessage handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
		return exceptionMessageBuilder(e, HttpStatus.NOT_FOUND, request);
	}

	private ExceptionMessage exceptionMessageBuilder(NoSuchElementException e, HttpStatus status,
			WebRequest request) {
		
		String message = e.toString();
		String statusReason = status.getReasonPhrase();
		Integer statusCode = status.value();
		String uri;
		String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
		
		uri = request instanceof ServletWebRequest swr ? swr.getRequest().getRequestURI() : null;
		
		log.error("Exception: {}", message);
		
		ExceptionMessage eMsg = new ExceptionMessage();
		
		eMsg.setMessage(message);
		eMsg.setStatusReason(statusReason);
		eMsg.setStatusCode(statusCode);
		eMsg.setTimestamp(timestamp);
		eMsg.setUri(uri);
		
		return eMsg;
	}
}
