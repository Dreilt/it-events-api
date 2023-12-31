package pl.dreilt.iteventsapi.event.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.dreilt.iteventsapi.event.exception.EventNotFoundException;
import pl.dreilt.iteventsapi.core.AbstractExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class EventNotFoundExceptionHandler extends AbstractExceptionHandler {
    private static final String MESSAGE_CODE = "exception.EventNotFoundException.message";

    public EventNotFoundExceptionHandler(MessageSource messageSource, ObjectMapper objectMapper) {
        super(messageSource, MESSAGE_CODE, objectMapper);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public void handle(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        writeErrorAsJson(request, response, ex);
    }
}
