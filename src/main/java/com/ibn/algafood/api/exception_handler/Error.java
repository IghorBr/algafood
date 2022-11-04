package com.ibn.algafood.api.exception_handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Error {

    private Integer status;
    private String type;
    private String title;
    private String detail;

    private List<Field> fields;

    private String userMessage;
    private LocalDateTime timestamp;

    @Getter
    @Builder
    public static class Field {
        private String name;
        private String userMessage;
    }

}
