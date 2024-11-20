package com.padya.stepbuilder.properties;

import java.util.List;
import com.padya.stepbuilder.model.Property;

public interface PropertiesConsumer {
   void consume(List<Property> properties);
}
