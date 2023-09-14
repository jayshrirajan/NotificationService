package com.msys.digitalwallet.notification.common.exception;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

import java.util.List;

public class LowerCaseClassNameResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(Object value) {
        if(value instanceof List<?> list){
            Object o = list.get(0);
            return o.getClass().getSimpleName().toLowerCase();
        }
        return value.getClass().getSimpleName().toLowerCase();

    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return idFromValue(value);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}