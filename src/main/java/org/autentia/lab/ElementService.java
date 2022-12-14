package org.autentia.lab;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ElementService {
	
	
	public Element get(String symbol) {
        return Element.findBySymbol(symbol);
    }
	
	public List<Element> allElements() {
	    return Element.listAll();
    }

    public Element create(Element element) {
        Element stored = Element.findBySymbol(element.symbol);
        if (stored == null) {
            element.persist();
            return element;
        } 
        return stored;
    }

    public Element update(Element element) {
        Element stored = Element.findBySymbol(element.symbol);
        if (stored == null) {
            element.persist();
            return element;
        } 
        stored.name = element.name;
        stored.group = element.group;
        stored.period = element.period;
        stored.atomicNumber = element.atomicNumber;
        stored.atomicMass = element.atomicMass;
        stored.electronConfiguration = element.electronConfiguration;
        stored.update();
        return stored;
    }

    public Element delete(String symbol) {
        Element stored = Element.findBySymbol(symbol);
        if (stored != null) {
            stored.delete();
        }
        return stored;
    }
}
