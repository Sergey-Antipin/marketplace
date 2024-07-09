package com.antipin.marketplace.util;

import com.antipin.marketplace.model.Product;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ProductMapSerializer extends JsonSerializer<Product> {

    @Override
    public void serialize(Product value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", value.getId());
        jsonGenerator.writeStringField("name", value.getName());
        jsonGenerator.writeStringField("description", value.getDescription());
        jsonGenerator.writeNumberField("price", value.getPrice().doubleValue());
        jsonGenerator.writeNumberField("quantityInStock", value.getQuantityInStock());
        jsonGenerator.writeEndObject();
    }
}
