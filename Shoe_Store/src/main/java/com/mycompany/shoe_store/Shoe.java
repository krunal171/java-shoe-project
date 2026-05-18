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
 * Entity class for Shoes.
 */
@Entity
@Table(name = "shoes")
@NamedQueries({
    @NamedQuery(name = "Shoe.findAll", query = "SELECT s FROM Shoe s"),
    @NamedQuery(name = "Shoe.findByShoeId", query = "SELECT s FROM Shoe s WHERE s.shoeId = :shoeId"),
    @NamedQuery(name = "Shoe.findByCategory", query = "SELECT s FROM Shoe s WHERE s.category = :category")
})
public class Shoe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "shoesId")
    private Integer shoeId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "shoename")
    private String shoeName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "categoryid")
    private Integer category;

    @Basic(optional = false)
    @NotNull
    @Column(name = "brandid")
    private Integer brand;

    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private Double price;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "image")
    private String imageUrl;

    @Column(name = "colorid")
    private String colorId;

    @Size(max = 20)
    @Column(name = "status")
    private String status;

    public Shoe() {
    }

    public Shoe(Integer shoeId) {
        this.shoeId = shoeId;
    }

    public Shoe(Integer shoeId, String shoeName, Integer category, Integer brand, Double price) {
        this.shoeId = shoeId;
        this.shoeName = shoeName;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    // Getters and Setters
    public Integer getShoeId() {
        return shoeId;
    }

    public void setShoeId(Integer shoeId) {
        this.shoeId = shoeId;
    }

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (shoeId != null ? shoeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Shoe)) {
            return false;
        }
        Shoe other = (Shoe) object;
        if ((this.shoeId == null && other.shoeId != null) || (this.shoeId != null && !this.shoeId.equals(other.shoeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.shoe_store.Shoe[ shoeId=" + shoeId + " ]";
    }
}
