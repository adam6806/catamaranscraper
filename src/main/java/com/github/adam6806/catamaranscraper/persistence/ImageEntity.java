package com.github.adam6806.catamaranscraper.persistence;

import javax.persistence.*;

@Entity
@Table(name = "image", schema = "catamarans")
public class ImageEntity {

    private int id;
    private String url;
    private BoatEntity boat;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "boat", referencedColumnName = "id")
    public BoatEntity getBoat() {
        return boat;
    }

    public void setBoat(BoatEntity boat) {
        this.boat = boat;
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

    @Override
    public String toString() {
        return "ImageEntity{" +
                "url='" + url + '\'' +
                '}';
    }
}
