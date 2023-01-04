package com.example.demo.dto.mapper;

import java.util.List;

/**
 * interface responsible for creating the Classes which will convert DTO to entity and vice versa
 */
public interface Mapper<T, G> {

    /**
     * Transfers the entity to DTO version
     *
     * @param entity - initial entity object
     * @return the Dto version of created object
     */
    T toDto(G entity);

    /**
     * Transfers the entity to Entity version
     *
     * @param dto - initial dto object
     * @return the Entity version of created object
     */
    G toEntity(T dto);

    /**
     * Transfers list of entities to list of dtos
     *
     * @param entities - initial list of entity
     * @return list of Dtos
     */
    List<T> listToDto(List<G> entities);

    /**
     * Transfers list of dtos to list of entities
     *
     * @param dtos - initial list of dtos
     * @return list of entities
     */
    List<G> listToEntity(List<T> dtos);

}