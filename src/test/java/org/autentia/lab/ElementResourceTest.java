package org.autentia.lab;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
class ElementResourceTest {
	
	private static final String BASE_PATH = "/element";
	
	@InjectMock
	MapperService mapper;
	
	@BeforeEach
	public void beforeEach() {
		PanacheMock.mock(ElementEntity.class);
	}
	
	@Test
    void cuando_POST_de_un_elemento_nuevo_entonces_lo_crea_y_OK_200() {
		ElementDto hydrogenDto = createHydrogen();
		ElementEntity hydrogenEntity = spyHydrogenEntity();
		
		when(ElementEntity.findBySymbol("H")).thenReturn(null);
		when(mapper.toEntity(any(ElementDto.class))).thenReturn(hydrogenEntity);
		doNothing().when(hydrogenEntity).persist();
		
		given()
		.when()
			.header("Content-Type", "application/json")
			.body(hydrogenDto)
			.post(BASE_PATH)
		.then()
			.statusCode(200);
		
		verify(hydrogenEntity, times(1)).persist();
    }

	
	@Test
    void cuando_POST_de_un_elemento_que_ya_existe_entonces_no_lo_persiste_y_OK_201() {
		ElementDto hydrogenDto = createHydrogen();
		ElementEntity hydrogenEntity = spyHydrogenEntity();
		
		when(ElementEntity.findBySymbol("H")).thenReturn(hydrogenEntity);
		
		given()
		.when()
			.header("Content-Type", "application/json")
			.body(hydrogenDto)
			.post(BASE_PATH)
		.then()
			.statusCode(201);
		
		verify(mapper,never()).toEntity(any());
		verify(hydrogenEntity, never()).persist();
    }
	
	@Test
    void cuando_POST_de_un_elemento_sin_symbol_entonces_error_400() {
		ElementDto hydrogenDto = createHydrogen();
		hydrogenDto.symbol = null;
		
		given()
		.when()
			.header("Content-Type", "application/json")
			.body(hydrogenDto)
			.post(BASE_PATH)
		.then()
			.statusCode(400)
			.body(containsString("Symbol cannot be null"));
	}	
		
	
	@Test
    void cuando_PUT_de_un_elemento_nuevo_entonces_NO_lo_crea_y_ERROR_400() {
		ElementDto hydrogenDto = createHydrogen();
		
		when(ElementEntity.findBySymbol("H")).thenReturn(null);
		
		given()
		.when()
			.header("Content-Type", "application/json")
			.body(hydrogenDto)
			.put(BASE_PATH)
		.then()
			.statusCode(400);
		
		verify(mapper,never()).toEntity(any());
    }
	
	@Test
    void cuando_PUT_de_un_elemento_existente_entonces_lo_actualiza_y_OK_201() {
		ElementDto hydrogenDto = createHydrogen();
		ElementEntity hydrogenEntity = spyHydrogenEntity();
		
		doNothing().when(hydrogenEntity).update();
		when(ElementEntity.findBySymbol("H")).thenReturn(hydrogenEntity);
		
		given()
		.when()
			.header("Content-Type", "application/json")
			.body(hydrogenDto)
			.put(BASE_PATH)
		.then()
			.statusCode(201);
		
		verify(hydrogenEntity,times(1)).update();
    }

	@Test
    void cuando_DELETE_de_un_elemento_existente_entonces_lo_borra_y_OK_200() {
		ElementDto hydrogenDto = createHydrogen();
		ElementEntity hydrogenEntity = spyHydrogenEntity();
		
		doNothing().when(hydrogenEntity).delete();
		when(ElementEntity.findBySymbol("H")).thenReturn(hydrogenEntity);
		
		given()
		.when()
			.header("Content-Type", "application/json")
			.body(hydrogenDto)
			.delete(BASE_PATH)
		.then()
			.statusCode(200);
		
		verify(hydrogenEntity,times(1)).delete();
    }

    @Test
    void cuando_DELETE_de_un_elemento_que_NO_existe_entonces_no_borro_nada_pero_OK_200() {
    	ElementDto hydrogenDto = createHydrogen();
		
		when(ElementEntity.findBySymbol("H")).thenReturn(null);
		
		given()
		.when()
			.header("Content-Type", "application/json")
			.body(hydrogenDto)
			.delete(BASE_PATH)
		.then()
			.statusCode(200);
    }

    @Test
    void cuando_GET_de_un_simbolo_que_existe_entonces_devuelve_el_elemento_y_OK_200() {
		ElementEntity hydrogenEntity = spyHydrogenEntity();
		
		when(ElementEntity.findBySymbol("H")).thenReturn(hydrogenEntity);
		when(mapper.toDto(hydrogenEntity)).thenReturn(createHydrogen());
		
		ElementDto dto = given()
		.when()
			.header("Content-Type", "application/json")
			.get(BASE_PATH + "/H")
		.then()
			.statusCode(200)
			.extract()
			.body()
			.as(ElementDto.class);
		
		assertEquals(dto.name, hydrogenEntity.name);
		assertEquals(dto.atomicMass, hydrogenEntity.atomicMass);
		assertEquals(dto.atomicNumber, hydrogenEntity.atomicNumber);
		assertEquals(dto.electronConfiguration, hydrogenEntity.electronConfiguration);
		assertEquals(dto.group, hydrogenEntity.group);
		assertEquals(dto.period, hydrogenEntity.period);
    }
    
    @Test
    void cuando_GET_de_un_simbolo_que_NO_existe_entonces_ERROR_404() {
		when(ElementEntity.findBySymbol("H")).thenReturn(null);
		
		given()
		.when()
			.header("Content-Type", "application/json")
			.get(BASE_PATH + "/H")
		.then()
			.statusCode(404);		
    }
    
    private ElementDto createHydrogen() {
		ElementDto hydrogen = new ElementDto();
		hydrogen.symbol = "H";
		hydrogen.name = "hydrogen";
		hydrogen.group = 1;
		hydrogen.period = 1;
		hydrogen.atomicNumber = 1;
		hydrogen.atomicMass = 1.008;
		hydrogen.electronConfiguration = "1s1";
		return hydrogen;
	}
    
    private ElementEntity spyHydrogenEntity() {
		ElementEntity hydrogen = new ElementEntity();
		hydrogen.symbol = "H";
		hydrogen.name = "hydrogen";
		hydrogen.group = 1;
		hydrogen.period = 1;
		hydrogen.atomicNumber = 1;
		hydrogen.atomicMass = 1.008;
		hydrogen.electronConfiguration = "1s1";
		return spy(hydrogen);
	}
}