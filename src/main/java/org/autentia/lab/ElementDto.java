package org.autentia.lab;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ElementDto {
	@NotNull(message = "Symbol cannot be null")
	@Size(min = 1, max = 2, message = "Symbol is 1 or 2 chars")
	public String symbol;
	
	@NotNull(message = "Name cannot be null")
	public String name;
	
	@NotNull(message = "Group cannot be null")
	@Min(value = 1, message = "Min group value is 1") 
	@Max(value = 18, message = "Max group value is 18")
	public Integer group;
	
	@NotNull(message = "Period cannot be null")
	@Min(value = 1, message = "Min period value is 1") 
	@Max(value = 18, message = "Max period value is 18")
	public Integer period;
	
	@NotNull(message = "Atomic Number cannot be null")
	@Min(value = 1, message = "Min Atomic Number value is 1") 
	@Max(value = 118, message = "Max Atomic Number value is 118")
	public Integer atomicNumber;
	
	@NotNull(message = "Atomic Mass be null")
	@Min(value = 1, message = "Min Atomic Mass value is 1") 
	@Max(value = 294, message = "Max Atomic Mass value is 294")
	public Double atomicMass;
	
	@NotNull(message = "Electron Configuration cannot be null")
	public String electronConfiguration;	
}
