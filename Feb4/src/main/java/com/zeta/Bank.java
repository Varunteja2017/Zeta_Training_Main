package com.zeta;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    public List<ICreditcard> cards = new ArrayList<>();

    ICreditcard issuecard(Customer customer, CardProvider type) {
        ICreditcard card;

        switch (type) {
            case Emerald:
                card = new EmeraldCreditCard(customer.name);
                break;

            case Infenium:
                card = new InfiniaCreditcard(customer.name);
                break;

            default:
                throw new IllegalArgumentException("Unsupported card provider: " + type);
        }

        cards.add(card);
        return card;
    }
}
