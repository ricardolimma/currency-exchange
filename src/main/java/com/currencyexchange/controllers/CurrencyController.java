package com.currencyexchange.controllers;

import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.services.impl.CurrencyServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
@RequestMapping("currency/v1/api")
public class CurrencyController {
    private  final CurrencyServiceImpl currencyService;

    public CurrencyController(CurrencyServiceImpl currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<Page<CurrencyDTO>> getAllCurrencies (@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) throws CurrencyApiException {
        return currencyService.currencyFindAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDTO> getCurrenciesById(@PathVariable(value = "id") final Long id) {
        return currencyService.currencyFindById(id);
    }

}