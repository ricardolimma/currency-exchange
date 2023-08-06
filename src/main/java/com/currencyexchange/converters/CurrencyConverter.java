package com.currencyexchange.converters;

import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.models.CurrencyModel;
import com.currencyexchange.repositories.CurrencyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter {
    private final ModelMapper modelMapper;

    public CurrencyConverter(ModelMapper modelMapper, CurrencyRepository currencyRepository) {
        this.modelMapper = modelMapper;
    }

    public CurrencyDTO convertCurrencyModelToDto(CurrencyModel currencyModel) {
        return modelMapper.map(currencyModel, CurrencyDTO.class);
    }

    public CurrencyModel convertCurrencyDtoToModel(CurrencyDTO currencyDTO) {
        return modelMapper.map(currencyDTO, CurrencyModel.class);
    }

    public Page<CurrencyDTO> convertPageModelToDto(Page<CurrencyModel> currencyModel) {
        return currencyModel.map(this::convertCurrencyModelToDto);
    }
}
