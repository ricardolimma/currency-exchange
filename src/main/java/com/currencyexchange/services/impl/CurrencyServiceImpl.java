package com.currencyexchange.services.impl;

import com.currencyexchange.converters.CurrencyConverter;
import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.models.CurrencyModel;
import com.currencyexchange.repositories.CurrencyRepository;
import com.currencyexchange.services.ICurrencyService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CurrencyServiceImpl implements ICurrencyService {

    private static final Logger log = LogManager.getLogger(CurrencyServiceImpl.class);

    private final CurrencyRepository currencyRepository;
    private final CurrencyConverter currencyConverter;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyConverter currencyConverter) {
        this.currencyRepository = currencyRepository;
        this.currencyConverter = currencyConverter;
    }

    @Override
    public ResponseEntity<CurrencyDTO> currencySave(@Valid final CurrencyDTO currencyDTO) {
        if (Objects.isNull(currencyDTO)) {
            return ResponseEntity.badRequest().build();
        }

        CurrencyModel currencyModel = currencyConverter.convertCurrencyDtoToModel(currencyDTO);
        currencyRepository.save(currencyModel);

        log.info("Moeda salva com sucesso: {}", currencyDTO.currencyCode());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Page<CurrencyDTO>> currencyFindAll(final Pageable page) throws CurrencyApiException {
        try {
            Page<CurrencyModel> currencyModels = currencyRepository.findAll(page);
            Page<CurrencyDTO> currencyDTOS = currencyConverter.convertPageModelToDto(currencyModels);

            log.info("Listagem de moedas realizada com sucesso.");
            return ResponseEntity.ok(currencyDTOS);
        } catch (Exception e) {
            log.error("Erro ao listar moedas: {}", e.getMessage());
            throw new CurrencyApiException("Erro ao encontrar moedas!");
        }
    }

    @Override
    public ResponseEntity<CurrencyDTO> currencyFindById(final Long id) {
        CurrencyModel optionalCurrencyModel = currencyRepository.findById(id).orElseThrow(RuntimeException::new);
        CurrencyDTO currencyDTO = currencyConverter.convertCurrencyModelToDto(optionalCurrencyModel);

        log.info("Moeda encontrada com sucesso: {}", currencyDTO.currencyCode());
        return ResponseEntity.ok(currencyDTO);
    }
}
