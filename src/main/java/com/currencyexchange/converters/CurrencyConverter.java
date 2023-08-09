package com.currencyexchange.converters;

import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.models.CurrencyModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter {

    public CurrencyDTO convertCurrencyModelToDto(CurrencyModel currencyModel) {
        return ICurrencyMapper.INSTANCE.convertCurrencyModelToDto(currencyModel);
    }

    public CurrencyModel convertCurrencyDtoToModel(CurrencyDTO currencyDTO) {
        return ICurrencyMapper.INSTANCE.convertCurrencyDtoToModel(currencyDTO);
    }

    public Page<CurrencyDTO> convertPageModelToDto(Page<CurrencyModel> currencyModel) {
        return currencyModel.map(ICurrencyMapper.INSTANCE::convertCurrencyModelToDto);
    }
}