package by.clevertec.check.entity;

import java.util.Objects;

public class DiscountCard {

    private int id;
    private String itemId;
    private String name;
    private double discount;

    public DiscountCard(int id, String itemId, String name, double discount) {
        this.id = id;
        this.itemId = itemId;
        this.name = name;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        private int id;
        private String itemId;
        private String name;
        private double discount;


        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder itemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public DiscountCard build() {
            return new DiscountCard(id, itemId, name, discount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard card = (DiscountCard) o;
        return id == card.id && Double.compare(card.discount, discount) == 0 && itemId.equals(card.itemId) && name.equals(card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, name, discount);
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                '}';
    }
}
