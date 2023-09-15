package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTest {

    @Test
    public void cardNumberIsCreated() {
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber, is(not(emptyOrNullString())));
    }
    @Test
    public void cardNumberIsCorrectLength(){
        String cardNumber = CardUtils.getCardNumber();
        int blankSpaces = 3;
        assertThat(cardNumber.length(), is(16 + blankSpaces));
    }

    @Test
    public void randomNumberIsBetweenRange(){
        int min = 5;
        int max = 10;
        int random = CardUtils.getRandomNumber(5, 10);
        assertThat(random, greaterThanOrEqualTo(min));
        assertThat(random, lessThanOrEqualTo(max));
    }


}
