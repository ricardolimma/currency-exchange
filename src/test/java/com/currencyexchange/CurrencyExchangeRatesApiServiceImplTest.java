package com.currencyexchange;

import com.currencyexchange.models.CurrencyModel;
import com.currencyexchange.repositories.CurrencyRepository;
import com.currencyexchange.services.impl.CurrencyExchangeRatesApiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencyExchangeRatesApiServiceImplTest {

    @Mock
    private WebClient webClient;

    @Mock
    private CurrencyRepository currencyRepository;

    private CurrencyExchangeRatesApiServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyService = new CurrencyExchangeRatesApiServiceImpl(webClient, currencyRepository);
    }

    @Test
    void saveCurrenciesToDatabase_CurrencyExists_ShouldUpdateExchangeRate() {
        CurrencyModel existingCurrency = new CurrencyModel();
        existingCurrency.setCurrencyCode("USD");

        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.23);

        when(currencyRepository.findByCurrencyCode("USD")).thenReturn(existingCurrency);

        currencyService.saveCurrenciesToDatabase(rates);

        verify(currencyRepository, times(1)).save(existingCurrency);
    }

    @Test
    void saveCurrenciesToDatabase_CurrencyDoesNotExist_ShouldSaveNewCurrency() {
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.89);

        when(currencyRepository.findByCurrencyCode("EUR")).thenReturn(null);

        currencyService.saveCurrenciesToDatabase(rates);

        verify(currencyRepository, times(1)).save(any(CurrencyModel.class));
    }
}