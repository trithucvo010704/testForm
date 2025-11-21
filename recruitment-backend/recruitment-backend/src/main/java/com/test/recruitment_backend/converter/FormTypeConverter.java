package com.test.recruitment_backend.converter;

import com.test.recruitment_backend.entity.Form;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FormTypeConverter implements AttributeConverter<Form.Type, String> {

    @Override
    public String convertToDatabaseColumn(Form.Type attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name().toLowerCase();
    }

    @Override
    public Form.Type convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return Form.Type.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle case where database value doesn't match enum
            throw new IllegalArgumentException("Invalid Form.Type value: " + dbData, e);
        }
    }
}

