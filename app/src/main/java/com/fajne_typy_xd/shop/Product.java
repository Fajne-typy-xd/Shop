package com.fajne_typy_xd.shop;

import android.text.Editable;

public class Product {
    Double price;
    String name;
    Boolean limitedSale;

    public Product(Double price, String name, Boolean limitedSale) {
        this.price = price;
        this.name = name;
        this.limitedSale = limitedSale;
    }

    public Product(Editable text, String name, Boolean limitedSale) {
        this.price = Double.parseDouble(text.toString());

        this.name = name;
        this.limitedSale = limitedSale;
    }
}
