package com.msys.digitalwallet.notification.common.response.apierror;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationErrorDetails extends ApiErrorDetails {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationErrorDetails(String object, String message) {
        this.object = object;
        this.message = message;
    }
}