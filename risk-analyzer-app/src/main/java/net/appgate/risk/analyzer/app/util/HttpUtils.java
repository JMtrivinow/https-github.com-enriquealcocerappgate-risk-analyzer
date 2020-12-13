package net.appgate.risk.analyzer.app.util;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpUtils {

    private HttpUtils() {
        throw new IllegalAccessError("Utility class");
    }

    public static HttpHeaders noCacheHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Cache-Control", "no-cache");
        return responseHeaders;
    }

    @SuppressWarnings("UnusedParameters")
    public static ResponseEntity<Object> internalErrorResponse(Exception e) {
        return new ResponseEntity<>(new ErrorReport(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"), HttpUtils.noCacheHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Object> badRequestResponse(String message, String detail) {
        return new ResponseEntity<>(new ErrorReport(HttpStatus.BAD_REQUEST, message, detail), HttpUtils.noCacheHeaders(), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Object> notFoundResponse(String message, String detail) {
        return new ResponseEntity<>(new ErrorReport(HttpStatus.NOT_FOUND, message, detail), HttpUtils.noCacheHeaders(), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Object> notFoundResponse(String detail) {
        return new ResponseEntity<>(new ErrorReport(HttpStatus.NOT_FOUND, detail), HttpUtils.noCacheHeaders(), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Object> forbiddenResponse(String message) {
        return new ResponseEntity<>(new ErrorReport(HttpStatus.FORBIDDEN, message), HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<Object> unauthorizedResponse(String message) {
        return new ResponseEntity<>(new ErrorReport(HttpStatus.UNAUTHORIZED, message), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<Object> conflictResponse(String message) {
        return new ResponseEntity<>(new ErrorReport(HttpStatus.CONFLICT, message), HttpStatus.CONFLICT);
    }

}
