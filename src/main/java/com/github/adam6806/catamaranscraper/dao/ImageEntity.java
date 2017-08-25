package com.github.adam6806.catamaranscraper.dao;

import javax.persistence.*;

@Entity
@Table(name = "image", schema = "catamarans", catalog = "")
public class ImageEntity {

    private int id;
    private String url;
    private BoatEntity boatByBoat;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url", nullable = false, length = 500)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageEntity that = (ImageEntity) o;

        if (id != that.id) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "boat", referencedColumnName = "id")
    public BoatEntity getBoatByBoat() {
        return boatByBoat;
    }

    public void setBoatByBoat(BoatEntity boatByBoat) {
        this.boatByBoat = boatByBoat;
    }
}
