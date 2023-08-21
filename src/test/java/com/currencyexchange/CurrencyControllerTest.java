package com.currencyexchange;

import com.currencyexchange.controllers.CurrencyController;
import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.services.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

class CurrencyControllerTest {

    @Mock
    private CurrencyServiceImpl currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCurrencies_ValidPageable_ShouldReturnListOfCurrencies() throws CurrencyApiException {
        Page<CurrencyDTO> currencyDTOS = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(currencyService.currencyFindAll(pageable)).thenReturn(ResponseEntity.ok(currencyDTOS));

        ResponseEntity<Page<CurrencyDTO>> response = currencyController.getAllCurrencies(pageable);

        verify(currencyService, times(1)).currencyFindAll(pageable);
    }

    @Test
    void getCurrenciesById_ExistingId_ShouldReturnCurrencyDTO() {
        Long id = 1L;
        CurrencyDTO currencyDTO = new CurrencyDTO(1L, "USD", 1.23);

        when(currencyService.currencyFindById(id)).thenReturn(ResponseEntity.ok(currencyDTO));

        ResponseEntity<CurrencyDTO> response = currencyController.getCurrenciesById(id);

        verify(currencyService, times(1)).currencyFindById(id);
    }
}
