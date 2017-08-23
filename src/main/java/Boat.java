import java.util.Date;

public class Boat {

    private String price;
    private String makeModel;
    private String location;
    private String image;
    private String link;
    private int length;
    private int year;
    private Date date;

    public Boat(String price, String makeModel, String location, String image, String link, int length, int year, Date date) {
        this.price = price;
        this.makeModel = makeModel;
        this.location = location;
        this.image = image;
        this.link = link;
        this.length = length;
        this.year = year;
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHTML() {
        return "<div>$" + getPrice() + " - " + getLength() + "ft " + getYear() + " " + getMakeModel() + " - " + getLocation() + " <br /> <a href=\"" + getLink() + "\"><img src=\"" + getImage() + "\"></a><br /><br />";
    }
}
