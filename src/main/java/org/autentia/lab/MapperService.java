package org.autentia.lab;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MapperService {

	public ElementEntity toEntity(ElementDto dto) {
		if (dto!=null) {
			ElementEntity entity = new ElementEntity();
			entity.symbol = dto.symbol;
			entity.name = dto.name;
			entity.group = dto.group;
			entity.period = dto.period;
			entity.atomicNumber = dto.atomicNumber;
			entity.atomicMass = dto.atomicMass;
			entity.electronConfiguration = dto.electronConfiguration;
			return entity;
		}
		return null;
	}

	public ElementDto toDto(ElementEntity entity) {
		if (entity!=null) {
			ElementDto dto = new ElementDto();
			dto.symbol = entity.symbol;
			dto.name = entity.name;
			dto.group = entity.group;
			dto.period = entity.period;
			dto.atomicNumber = entity.atomicNumber;
			dto.atomicMass = entity.atomicMass;
			dto.electronConfiguration = entity.electronConfiguration;
			return dto;
		}
		return null;
	}

}
