package com.mindhub.homebanking.utils;

final public class  CardUtils {

    private CardUtils() {
    }

    public static String getCardNumber(){
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i<4; i++){
            cardNumber.append(Integer.toString(getRandomNumber(1000, 9999)));
            if (i < 3 ){
                cardNumber.append(" ");
            }

        }
        return cardNumber.toString();
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
