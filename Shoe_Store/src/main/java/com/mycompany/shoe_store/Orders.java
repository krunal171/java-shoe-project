package com.mycompany.shoe_store;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderid")
    private Integer orderid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "userid")
    private int userid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "orderdate")
    @Temporal(TemporalType.DATE)
    private Date orderdate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "totalamount")
    private int totalamount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "paymentmethod")
    private String paymentmethod;

    @Basic(optional = false)
    @NotNull
    @Column(name = "orderstatus")
    private String orderstatus;

    @Column(name = "address_id")
    private Integer addressId;

    public Orders() {
    }

    // Getters and Setters
    public Integer getOrderid() { return orderid; }
    public void setOrderid(Integer orderid) { this.orderid = orderid; }

    public int getUserid() { return userid; }
    public void setUserid(int userid) { this.userid = userid; }

    public Date getOrderdate() { return orderdate; }
    public void setOrderdate(Date orderdate) { this.orderdate = orderdate; }

    public int getTotalamount() { return totalamount; }
    public void setTotalamount(int totalamount) { this.totalamount = totalamount; }

    public String getPaymentmethod() { return paymentmethod; }
    public void setPaymentmethod(String paymentmethod) { this.paymentmethod = paymentmethod; }

    public String getOrderstatus() { return orderstatus; }
    public void setOrderstatus(String orderstatus) { this.orderstatus = orderstatus; }

    public Integer getAddressId() { return addressId; }
    public void setAddressId(Integer addressId) { this.addressId = addressId; }
}
