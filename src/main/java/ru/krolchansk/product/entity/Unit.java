package ru.krolchansk.product.entity;

public enum Unit {
    PIECE("штука"),
    TEN("десяток"),
    KG("кг"),
    GRAM("гр"),
    LITER("литр"),
    ML("мл"),
    PACK("упаковка");

    private final String title;

    Unit(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
