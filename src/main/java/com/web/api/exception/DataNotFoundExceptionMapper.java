package com.web.api.exception;

import com.web.api.model.ErrorMessage;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errorMsg = new ErrorMessage(ex.getMessage(),404,"no description available");
		return Response.status(Status.NOT_FOUND).
							   entity(errorMsg).
							   build();
	}

}
