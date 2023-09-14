package com.msys.digitalwallet.notification.common.response;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.msys.digitalwallet.notification.common.exception.LowerCaseClassNameResolver;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class ApiResult<T>  implements Serializable{

    @JsonTypeIdResolver(LowerCaseClassNameResolver.class)
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM,visible = true)
    private T result;

    public ApiResult(T result) {
        this.result = result;
    }
}
