package com.web.api.exception;

import com.web.api.model.ErrorMessage;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;

public class GenericExceptionHandler implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMsg = new ErrorMessage(ex.getMessage(),500,"no description available");
		return Response.status(Status.INTERNAL_SERVER_ERROR).
							   entity(errorMsg).
							   build();
	}

}
