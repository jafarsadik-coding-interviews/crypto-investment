package com.xm.crypto.repository;

import com.xm.crypto.exceptions.UnknownSymbolRuntimeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CryptoRepositoryImplTest {

    @Autowired
    CryptoRepository repository;

    @Test
    void shouldReturnNonEmptyListOSupportedCryptoSymbols() {
        assertThat(repository.getSupportedSymbols()).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"btc", "doge", "eth", "ltc", "xrp"})
    void shouldReturnNonEmptyPriceHistoryForEachSupportedCrypto(String symbol) {
        StepVerifier.create(repository.loadFullPriceHistory(symbol))
                .expectNextMatches(priceSnapshot -> priceSnapshot.getPrice().compareTo(BigDecimal.ZERO) > 0)
                .thenConsumeWhile(priceSnapshot -> true)
                .expectComplete()
                .verify();
    }

    @Test
    void unknownSymbolTriggersRuntimeException() {
        Assertions.assertThatThrownBy(() -> repository.loadFullPriceHistory("unknown_symbol"))
                .isInstanceOf(UnknownSymbolRuntimeException.class);
    }

}