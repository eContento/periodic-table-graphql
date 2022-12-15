package org.autentia.lab;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;


@QuarkusTest
public class CollaborativeTest {
    
    @InjectSpy
    ElementService service;
    
    @BeforeEach
    public void beforeEach() {
        PanacheMock.mock(Element.class);
    }
    

    @Test
    public void when_getElementBySymbol_then_return_the_element() {
        //given
        GraphQLQuery query = new GraphQLQuery(); 
        query.setQuery("{ element(symbol:\"H\"){ symbol name atomicNumber  } }");
        
        Element hydrogen = spyHydrogen();
        when(Element.findBySymbol("H")).thenReturn(hydrogen);
        
        //when
        Element actual = 
                given()
                .when()
                    .header("Content-Type", "application/json")
                    .body(query)
                    .post("/graphql")
                .then()
                    .assertThat()
                    .statusCode(200)
                    .extract()
                    .jsonPath()
                    .getObject("data.element", Element.class);
        
        //then
        verify(service, times(1)).get("H");
        
        assertEquals("H", actual.symbol);
        assertEquals("hydrogen", actual.name);
        assertEquals(1, actual.atomicNumber);
    }
    
    private Element spyHydrogen() {
        Element hydrogen = new Element();
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

