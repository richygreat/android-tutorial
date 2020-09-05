package com.richygreat.tutorialone.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.richygreat.tutorialone.model.Product;

@Entity(tableName = "products")
public class ProductEntity implements Product {
    @PrimaryKey
    private int id;
    private String name;

    public ProductEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductEntity(Product product) {
        this.id = product.getId();
        this.name = product.getName();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
