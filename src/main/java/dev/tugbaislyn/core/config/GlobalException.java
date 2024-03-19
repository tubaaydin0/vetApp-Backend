package dev.tugbaislyn.core.config;
import dev.tugbaislyn.core.exception.AppointmentException;
import dev.tugbaislyn.core.exception.ConflictException;
import dev.tugbaislyn.core.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;


import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Değerlendirme Formu 25
@RestControllerAdvice
public class GlobalException  {
    //Girilen verinin idsi bulunamadığında.
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), e.getMessage(), "{404 Not Found}");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    //Aynı verinin kaydedilemediği durumlarda.(Conflict: çakışma durumlarında)
    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ExceptionResponse> handleConflictException(ConflictException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), e.getMessage(), "{409 Conflict}");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
    }

    //Randevu bulunamadığında veya o saatte randevu dolu olduğunda.
    @ExceptionHandler(AppointmentException.class)
    public final ResponseEntity<ExceptionResponse> handleAppointmentException(AppointmentException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), e.getMessage(), "{409 Conflict/422 Unprocessable Entity}");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    //Validation Hataları için.


    //Diğer kaşılaşabilecek tüm hatalar için kullanılabilir.
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleException(Exception e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), e.getMessage(), "{HTTP 500 Internal Server Error}");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
   /* @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleValidationErrors(MethodArgumentNotValidException e) {
        // Validation hataları birden çok olabilir.
        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        String errorDetails = String.join(", ", validationErrorList);

        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),"Validation hatası oluştu!", "{BAD REQUEST} "+errorDetails);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
*/

}
    

    



