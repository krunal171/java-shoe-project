package com.mycompany.shoe_store;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * Entity class for Cart.
 */
@Entity
@Table(name = "cart")
@NamedQueries({
    @NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c"),
    @NamedQuery(name = "Cart.findByUserid", query = "SELECT c FROM Cart c WHERE c.userid = :userid")
})
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cartid")
    private Integer cartid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "userid")
    private Integer userid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "shoeid")
    private Integer shoeid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @Size(max = 20)
    @Column(name = "selected_color")
    private String selectedColor;

    @Size(max = 20)
    @Column(name = "selected_size")
    private String selectedSize;

    public Cart() {
    }

    public Cart(Integer userid, Integer shoeid, Integer quantity, String selectedColor, String selectedSize) {
        this.userid = userid;
        this.shoeid = shoeid;
        this.quantity = quantity;
        this.selectedColor = selectedColor;
        this.selectedSize = selectedSize;
    }

    public Integer getCartid() {
        return cartid;
    }

    public void setCartid(Integer cartid) {
        this.cartid = cartid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getShoeid() {
        return shoeid;
    }

    public void setShoeid(Integer shoeid) {
        this.shoeid = shoeid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartid != null ? cartid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.cartid == null && other.cartid != null) || (this.cartid != null && !this.cartid.equals(other.cartid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.shoe_store.Cart[ cartid=" + cartid + " ]";
    }
}
