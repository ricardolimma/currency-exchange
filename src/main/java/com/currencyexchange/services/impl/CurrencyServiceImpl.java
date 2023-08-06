package com.currencyexchange.services.impl;

import com.currencyexchange.converters.CurrencyConverter;
import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.models.CurrencyModel;
import com.currencyexchange.repositories.CurrencyRepository;
import com.currencyexchange.services.ICurrencyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CurrencyServiceImpl implements ICurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyConverter currencyConverter;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyConverter currencyConverter) {
        this.currencyRepository = currencyRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public ResponseEntity<CurrencyDTO> currencySave(@Valid final CurrencyDTO currencyDTO) {
        if(Objects.isNull(currencyDTO)) {
            return ResponseEntity.badRequest().build();
        }

        CurrencyModel currencyModel = currencyConverter.convertCurrencyDtoToModel(currencyDTO);
        currencyRepository.save(currencyModel);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Override
    public ResponseEntity<Page<CurrencyDTO>> currencyFindAll(final Pageable page) {

        try {
            Page<CurrencyModel> currencyModels = currencyRepository.findAll(page);
            Page<CurrencyDTO> currencyDTOS = currencyConverter.convertPageModelToDto(currencyModels);

            return ResponseEntity.ok(currencyDTOS);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public ResponseEntity<CurrencyDTO> currencyFindById(final Long id) {
        CurrencyModel optionalCurrencyModel = currencyRepository.findById(id).orElseThrow(RuntimeException::new);
        CurrencyDTO currencyDTO = currencyConverter.convertCurrencyModelToDto(optionalCurrencyModel);

        return ResponseEntity.ok(currencyDTO);
    }
}
