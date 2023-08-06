package com.currencyexchange.services;

import com.currencyexchange.dtos.CurrencyDTO;
import com.currencyexchange.exceptions.CurrencyApiException;
import com.currencyexchange.models.CurrencyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICurrencyService {

    /**
     * Salva uma nova moeda.
     *
     * @param currencyDTO O objeto CurrencyDTO contendo as informações da moeda a ser salva.
     * @return ResponseEntity com o objeto CurrencyDTO da moeda recém-salva e o status HTTP resultante.
     */
    ResponseEntity<CurrencyDTO> currencySave(CurrencyDTO currencyDTO);


    /**
     * Retorna uma lista de todas as moedas cadastradas.
     *
     * @return ResponseEntity com a lista de objetos CurrencyDTO representando todas as moedas e o status HTTP resultante.
     */
    ResponseEntity<Page<CurrencyDTO>> currencyFindAll(Pageable page) throws CurrencyApiException;



    /**
     * Retorna uma moeda com base no seu ID.
     *
     * @param id O ID da moeda a ser buscada.
     * @return ResponseEntity com o objeto CurrencyDTO da moeda encontrada e o status HTTP resultante.
     */
    ResponseEntity<CurrencyDTO> currencyFindById(Long id);



}
