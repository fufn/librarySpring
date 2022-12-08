package com.example.demo.dto.mapper;

/**
 * interface responsible for creating the Classes which will convert DTO to entity and vice versa
 */
public interface Mapper<T, G> {
    /**
     * Transfers the entity to DTO version
     * @param entity - initial entity object
     * @return the Dto version of created book object
     */
    public T toDto(G entity);
    /**
     * Transfers the entity to Entity version
     * @param dto - initial dto object
     * @return the Entity version of created book object
     */
    public G toEntity(T dto);

}