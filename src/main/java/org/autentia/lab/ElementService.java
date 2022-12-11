package org.autentia.lab;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class ElementService {
	
	@Inject
	public MapperService mapper;
	
	public Response create(ElementDto dto) {		
		if (alreadyExists(dto)) {
			return Response.status(201).build();
		}
		
		ElementEntity entity = mapper.toEntity(dto);
		entity.persist();
		return Response.ok().build();
	}
	
	public Response update(@Valid ElementDto dto) {
		ElementEntity entity = ElementEntity.findBySymbol(dto.symbol);
		if (entity != null) {
			entity.atomicMass = dto.atomicMass;
			entity.atomicNumber = dto.atomicNumber;
			entity.electronConfiguration = dto.electronConfiguration;
			entity.group = dto.group;
			entity.period = dto.period;
			entity.name = dto.name;
			entity.update();
			return Response.status(201).build();
		}
		return Response.status(400).build();
	}
	
	public Response delete(@Valid ElementDto dto) {
		ElementEntity entity = ElementEntity.findBySymbol(dto.symbol);
		if (entity != null) {
			entity.delete();
		}
		return Response.ok().build();
	}

	private boolean alreadyExists(ElementDto dto) {
		return (ElementEntity.findBySymbol(dto.symbol) != null);
	}

	public Response get(String symbol) {
		ElementEntity entity = ElementEntity.findBySymbol(symbol);
		if (entity != null) {
			ElementDto dto = mapper.toDto(entity);
			return Response.ok().entity(dto).build();
		}
		return Response.status(404).build();
	}

	

	
}
