package com.currencyexchange.converters;

import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.models.CurrencyModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter {

    public CurrencyDTO convertCurrencyModelToDto(CurrencyModel currencyModel) {
        return new CurrencyDTO(currencyModel.getId(), currencyModel.getCurrencyCode(), currencyModel.getExchangeRate());
    }

    public CurrencyModel convertCurrencyDtoToModel(CurrencyDTO currencyDTO) {
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setId(currencyDTO.id());
        currencyModel.setCurrencyCode(currencyDTO.currencyCode());
        currencyModel.setExchangeRate(currencyDTO.extendRate());
        return currencyModel;
    }

    public Page<CurrencyDTO> convertPageModelToDto(Page<CurrencyModel> currencyModel) {
        return currencyModel.map(this::convertCurrencyModelToDto);
    }
}