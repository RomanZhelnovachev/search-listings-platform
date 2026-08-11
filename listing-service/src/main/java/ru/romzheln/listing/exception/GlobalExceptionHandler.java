package ru.romzheln.listing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.romzheln.listing.exception.badRequest.*;
import ru.romzheln.listing.exception.notFound.*;
import ru.romzheln.listing.exception.serverError.InternalServerException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(NotFoundException.class)
public ResponseEntity<AppError> catchNotFound(NotFoundException e){
return buildError(HttpStatus.NOT_FOUND, e.getMessage());
}

@ExceptionHandler(BadRequestException.class)
public ResponseEntity<AppError> catchBadRequest(BadRequestException e){
    return buildError(HttpStatus.BAD_REQUEST, e.getMessage());
}

@ExceptionHandler(InternalServerException.class)
public ResponseEntity<AppError> catchServerError(InternalServerException e){
    return buildError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppError> catchValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildError(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppError> catchException(Exception e) {
        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error"
        );
    }

    private ResponseEntity<AppError> buildError(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new AppError(status.value(), message));
    }
}
