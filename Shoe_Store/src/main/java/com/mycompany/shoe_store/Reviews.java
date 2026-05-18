package com.mycompany.shoe_store;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reviews")
public class Reviews implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "reviewid")
    private Integer reviewid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "userid")
    private int userid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "shoeid")
    private int shoeid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private int rating;

    @Size(max = 500)
    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    public Reviews() {
    }

    public Integer getReviewid() {
        return reviewid;
    }

    public void setReviewid(Integer reviewid) {
        this.reviewid = reviewid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getShoeid() {
        return shoeid;
    }

    public void setShoeid(int shoeid) {
        this.shoeid = shoeid;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
