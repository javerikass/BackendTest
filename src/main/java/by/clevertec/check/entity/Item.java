package by.clevertec.check.entity;

import java.util.Objects;

public class Item {

    private String id;
    private String name;
    private double price;
    private boolean promotionalItem;
    private double discount;

    public Item(String id, double price, String name, boolean promotionalItem, double discount) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.promotionalItem = promotionalItem;
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPromotionalItem() {
        return promotionalItem;
    }

    public void setPromotionalItem(boolean promotionalItem) {
        this.promotionalItem = promotionalItem;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private double price;
        private double discount;
        private boolean promotionalItem;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder promotionalItem(boolean promotionalItem) {
            this.promotionalItem = promotionalItem;
            return this;
        }

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public Item build() {
            return new Item(id, price, name, promotionalItem, discount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.price, price) == 0 && promotionalItem == item.promotionalItem && Double.compare(item.discount, discount) == 0 && id.equals(item.id) && name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, promotionalItem, discount);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", promotionalItem=" + promotionalItem +
                ", discount=" + discount +
                '}';
    }
}
