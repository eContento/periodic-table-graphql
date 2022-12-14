package org.autentia.lab;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class ElementResource {

	
	@Inject
	ElementService service;
	
	@Query
    @Description("Get a Element by the symbol")
    public Element getElement(@Name("symbol") String symbol) {
        return service.get(symbol);
    }
	
    @Query
    @Description("Get all Elements")
    public List<Element> allElements() {
        return service.allElements();
    }
    
    @Mutation
    @Description("Create an Element")
    public Element createElement(Element element) {
        return service.create(element);
    }
    
    @Mutation
    @Description("Update an Element")
    public Element updateElement(Element element) {
        return service.update(element);
    }
    
    @Mutation
    @Description("Delete an Element")
    public Element deleteElement(String symbol) {
        return service.delete(symbol);
    }

    
}














	
    /*
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
    */
    
    




















