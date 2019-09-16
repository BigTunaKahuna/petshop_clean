package com.petshop.mapper;

public interface Mapper <T,V>{
	V mapEntityToDto(T entity);
	T mapDtoToEntity(V dto);
}
