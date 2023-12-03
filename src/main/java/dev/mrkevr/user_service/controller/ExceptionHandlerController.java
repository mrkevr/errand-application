package dev.mrkevr.user_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//    ProblemDetail handleNotFoundException(MethodArgumentNotValidException e) {
//		
//		List<String> errors = e.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
//		
//		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//		problemDetail.setProperty("errors", errors);
//		
//        return problemDetail;
//    }

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<String> errors = ex.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());		
		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setProperty("errors", errors);
		
		return ResponseEntity.badRequest().body(problemDetail);
	}
	
	
}
