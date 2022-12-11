package org.autentia.lab;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class ElementEntity extends PanacheMongoEntity {

	public String symbol;
	public String name;
	public Integer group;
	public Integer period;
	public Integer atomicNumber;
	public Double atomicMass;
	public String electronConfiguration;
	
	public static ElementEntity findBySymbol(String symbol) {
		return find("symbol", symbol).firstResult();
	} 	
	
}
