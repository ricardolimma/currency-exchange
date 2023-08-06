package com.currencyexchange.dtos;

import jakarta.validation.constraints.NotNull;

public record CurrencyDTO(@NotNull(message = "Id não pode ser nulo!") Long id,
                          @NotNull(message = "Currency code não pode ser nulo!") String currencyCode,
                          @NotNull(message = "ExtendRate não pode ser nulo!") Double extendRate) {

}