package net.appgate.risk.analyzer.app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorReport {

    @JsonProperty("code")
    int errorCode;

    @JsonProperty("status")
    String status;

    @JsonProperty("message")
    String message;

    @JsonProperty("data")
    String data;

    public ErrorReport(HttpStatus errorCode, String message) {
        if (errorCode.value() < 400 || errorCode.value() >= 600) throw new RuntimeException("Not an http error: " + errorCode.value());     //NOSONAR

        this.errorCode = errorCode.value();
        this.status = this.errorCode < 500 ? "error" : "fail";
        this.message = message;
    }

    public ErrorReport(HttpStatus errorCode, String message, String data) {
        this(errorCode, message);
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

}
