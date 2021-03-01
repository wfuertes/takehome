package com.challenge.demo.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Converter(autoApply = true)
public class UuidConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(final UUID entityValue) {
        return ofNullable(entityValue).map(UUID::toString).orElse(null);
    }

    @Override
    public UUID convertToEntityAttribute(final String databaseValue) {
        return ofNullable(databaseValue).map(UUID::fromString).orElse(null);
    }
}