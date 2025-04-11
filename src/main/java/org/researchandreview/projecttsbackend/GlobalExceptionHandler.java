package org.researchandreview.projecttsbackend;

import org.apache.ibatis.javassist.NotFoundException;
import org.researchandreview.projecttsbackend.dto.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                "자원을 찾을 수 없음",
                ex.getMessage()
        );

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                "필요한 헤더가 없습니다.",
                ex.getMessage()
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                "필요한 URL Parameter가 없습니다.",
                ex.getMessage()
        );

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestPartException(MissingServletRequestPartException ex, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                "필요한 Multipart 요소가 없습니다.",
                ex.getMessage()
        );

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                "처리하지 못한 예외 발생: " + ex.getClass().getCanonicalName(),
                ex.getMessage()
        );

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
