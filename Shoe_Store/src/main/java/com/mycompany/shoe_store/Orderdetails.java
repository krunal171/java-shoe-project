package com.mycompany.shoe_store;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "orderdetails")
public class Orderdetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderdetailid")
    private Integer orderdetailid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "orderid")
    private int orderid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "shoeid")
    private int shoeid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @Basic(optional = false)
    @NotNull
    @Column(name = "Price")
    private int price;

    @Basic(optional = false)
    @NotNull
    @Column(name = "subtotal")
    private int subtotal;

    public Orderdetails() {
    }

    // Getters and Setters
    public Integer getOrderdetailid() { return orderdetailid; }
    public void setOrderdetailid(Integer orderdetailid) { this.orderdetailid = orderdetailid; }

    public int getOrderid() { return orderid; }
    public void setOrderid(int orderid) { this.orderid = orderid; }

    public int getShoeid() { return shoeid; }
    public void setShoeid(int shoeid) { this.shoeid = shoeid; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getSubtotal() { return subtotal; }
    public void setSubtotal(int subtotal) { this.subtotal = subtotal; }
}
