package org.autentia.lab;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/element")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ElementResource {

	
	@Inject
	ElementService service;
	
    @GET
    @Path("/{symbol}")
    public Response get(@PathParam("symbol") String symbol) {
        return service.get(symbol);
    }
    	
    @POST
    public Response create(@Valid ElementDto dto) {
    	return service.create(dto);
    }
    
    @PUT
    public Response update(@Valid ElementDto dto) {
    	return service.update(dto);
    }
    
    @DELETE
    public Response delete(@Valid ElementDto dto) {
    	return service.delete(dto);
    }
    
    
}



















