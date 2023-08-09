package com.currencyexchange.converters;

import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.models.CurrencyModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ICurrencyMapper {

    ICurrencyMapper INSTANCE = Mappers.getMapper(ICurrencyMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "currencyCode", source = "currencyCode")
    @Mapping(target = "exchangeRate", source = "exchangeRate")
    CurrencyDTO convertCurrencyModelToDto(CurrencyModel currencyModel);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "currencyCode", source = "currencyCode")
    @Mapping(target = "exchangeRate", source = "exchangeRate")
    CurrencyModel convertCurrencyDtoToModel(CurrencyDTO currencyDTO);
}