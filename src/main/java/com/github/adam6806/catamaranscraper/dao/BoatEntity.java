package com.github.adam6806.catamaranscraper.dao;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "boat", schema = "catamarans", catalog = "")
public class BoatEntity {

    private int id;
    private String location;
    private String description;
    private String url;
    private String makeModel;
    private long price;
    private Integer length;
    private Integer year;
    private int dougRating;
    private int adamRating;
    private byte active;
    private Date timestamp;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "location", nullable = false, length = 200)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 20000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "url", nullable = false, length = 500)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "make_model", nullable = true, length = 200)
    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Basic
    @Column(name = "length", nullable = true)
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Basic
    @Column(name = "year", nullable = true)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "doug_rating", nullable = false)
    public int getDougRating() {
        return dougRating;
    }

    public void setDougRating(int dougRating) {
        this.dougRating = dougRating;
    }

    @Basic
    @Column(name = "adam_rating", nullable = false)
    public int getAdamRating() {
        return adamRating;
    }

    public void setAdamRating(int adamRating) {
        this.adamRating = adamRating;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public byte getActive() {
        return active;
    }

    public void setActive(byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "timestamp", nullable = false)
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoatEntity that = (BoatEntity) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (dougRating != that.dougRating) return false;
        if (adamRating != that.adamRating) return false;
        if (active != that.active) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (makeModel != null ? !makeModel.equals(that.makeModel) : that.makeModel != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (makeModel != null ? makeModel.hashCode() : 0);
        result = 31 * result + (int) (price ^ (price >>> 32));
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + dougRating;
        result = 31 * result + adamRating;
        result = 31 * result + (int) active;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
