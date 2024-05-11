package com.github.springsneaker.repository.payment;



import com.github.springsneaker.service.exceptions.InvalidValueException;

public enum PaymentType {
    CARD("카드"),
    ACCOUNT("계좌 이체");

    private final String term;

    PaymentType(String term) {
        this.term = term;
    }

    public static PaymentType valueOfTerm(String str){
        for(PaymentType paymentType: values()){
            if (paymentType.term.equals(str)) return paymentType;
        }
        throw new InvalidValueException("Type 은 '계좌 이체' 나 '카드' 여야 합니다");
    }
}
