package com.currencyexchange.controllers;

import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.services.impl.CurrencyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("currency/v1/api")
public class CurrencyController {
    private final CurrencyServiceImpl currencyService;
    private static final Logger log = LogManager.getLogger(CurrencyController.class);

    public CurrencyController(CurrencyServiceImpl currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<Page<CurrencyDTO>> getAllCurrencies(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) throws CurrencyApiException {
        log.info("Requisição para obter todas as moedas recebida.");
        ResponseEntity<Page<CurrencyDTO>> response = currencyService.currencyFindAll(pageable);
        log.info("Obtidas todas as moedas com sucesso.");
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDTO> getCurrenciesById(@PathVariable(value = "id") final Long id) {
        log.info("Requisição para obter moeda pelo ID recebida.");
        ResponseEntity<CurrencyDTO> response = currencyService.currencyFindById(id);
        log.info("Moeda obtida com sucesso.");
        return response;
    }
}