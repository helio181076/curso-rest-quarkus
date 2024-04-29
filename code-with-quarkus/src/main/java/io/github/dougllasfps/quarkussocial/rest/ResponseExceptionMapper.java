package io.github.dougllasfps.quarkussocial.rest;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

public interface ResponseExceptionMapper<T> {
    boolean handles(int status, MultivaluedMap<String, Object> headers);

    ConstraintViolationException toThrowable(Response response);
}
