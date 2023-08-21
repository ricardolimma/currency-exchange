package com.currencyexchange;

import com.currencyexchange.converters.CurrencyConverter;
import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.models.CurrencyModel;
import com.currencyexchange.repositories.CurrencyRepository;
import com.currencyexchange.services.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CurrencyConverter currencyConverter;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void currencySave_ValidCurrencyDTO_ShouldReturnCreatedResponse() {
        CurrencyDTO currencyDTO = new CurrencyDTO(1L, "USD", 1.23);
        CurrencyModel currencyModel = new CurrencyModel();

        when(currencyConverter.convertCurrencyDtoToModel(currencyDTO)).thenReturn(currencyModel);

        ResponseEntity<CurrencyDTO> response = currencyService.currencySave(currencyDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(currencyRepository, times(1)).save(currencyModel);
    }

    @Test
    void currencyFindAll_ShouldReturnListOfCurrencies() throws CurrencyApiException {
        Page<CurrencyModel> currencyModels = mock(Page.class);
        Page<CurrencyDTO> currencyDTOS = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(currencyRepository.findAll(pageable)).thenReturn(currencyModels);
        when(currencyConverter.convertPageModelToDto(currencyModels)).thenReturn(currencyDTOS);

        ResponseEntity<Page<CurrencyDTO>> response = currencyService.currencyFindAll(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencyDTOS, response.getBody());
    }

    @Test
    void currencyFindById_ExistingId_ShouldReturnCurrencyDTO() {
        Long id = 1L;
        CurrencyModel currencyModel = new CurrencyModel();
        CurrencyDTO currencyDTO = new CurrencyDTO(1L, "USD", 1.23);

        when(currencyRepository.findById(id)).thenReturn(Optional.of(currencyModel));
        when(currencyConverter.convertCurrencyModelToDto(currencyModel)).thenReturn(currencyDTO);

        ResponseEntity<CurrencyDTO> response = currencyService.currencyFindById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencyDTO, response.getBody());
    }

    @Test
    void currencyFindById_NonExistingId_ShouldThrowException() {
        Long id = 1L;

        when(currencyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> currencyService.currencyFindById(id));
    }
}