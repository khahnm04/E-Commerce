package com.khahnm04.ecommerce.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @Builder.Default
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Builder.Default
    private boolean success = false;

    private Integer code;
    private String message;
    private String path;
    private String method;
    private List<FieldErrorResponse> errors;

    @Getter
    @Setter
    @Builder
    public static class FieldErrorResponse implements Serializable {
        private String field;
        private String message;
    }

}
