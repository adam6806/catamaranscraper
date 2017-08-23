import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    private static final String URL = "http://www.yachtworld.com/core/listing/cache/searchResults.jsp?toPrice=175000&fromPrice=25000&enid=101&Ntk=boatsEN&type=%28Sail%29+Catamaran&searchtype=advancedsearch&hmid=0&sm=3&enid=0&cit=true&luom=126&currencyid=100&boatsAddedSelected=-1&ftid=0&slim=quick&No=0&rid=100&rid=104&rid=105&rid=107&rid=112&rid=115&rid=125&fracts=1&ps=2000&Ns=PBoat_sortByPriceDesc|1";
    private static final String MAIN_URL = "http://www.yachtworld.com";

    public static void main (String[] args) throws IOException {

        Document doc = Jsoup.connect(URL).get();
        System.out.println(doc.title());
        Elements listRows = doc.select(".listing-container");
        ArrayList<Boat> boats = new ArrayList<>();
        for (Element element : listRows) {
            if (element.select(".price").text().startsWith("US")) {
                Element link = element.select("a[href]").first();
                String boatLink = MAIN_URL + link.attr("href");
                String price = element.select(".price").text();
                price = price.substring(3);
                price = price.replaceAll(",", "");
                String makeModel = element.select(".make-model").text();
                makeModel = makeModel.replaceAll("'", "");
                int length = Integer.parseInt(makeModel.substring(0,2));
                int year = Integer.parseInt(makeModel.substring(6,10));
                makeModel = makeModel.substring(11);
                String location = element.select(".location").text();
                String image = element.select("img").attr("src");
                Date date = new Date();
                Boat boat = new Boat(price, makeModel, location, image, boatLink, length, year, date);
                try (
                    Connection databaseConnection = Persistence.getDatabaseConnection();
                    Statement statement = databaseConnection.createStatement();
                ){
                    String selectSQL = "select id from boat where url = '" + boatLink +"';";
                    ResultSet resultSet = statement.executeQuery(selectSQL);
                    if (!resultSet.next()) {
                        String insertSQL = "insert into boat (location, url, make_model, price, length, year, timestamp) VALUES ('" + location + "','" + boatLink + "','" + makeModel + "'," + price + "," + length + "," + year + ",CURDATE());";
                        int countInserted = statement.executeUpdate(insertSQL);
                        System.out.println(countInserted + " rows inserted");
                        resultSet = statement.executeQuery(selectSQL);
                        if (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            insertSQL = "INSERT INTO image (url, boat) values ('" + image + "'," + id + ");";
                            countInserted = statement.executeUpdate(insertSQL);
                            System.out.println(countInserted + " rows inserted");
                            boats.add(boat);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (boats.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Boat boat : boats) {
                stringBuilder.append(boat.getHTML());
            }
            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(stringBuilder.toString(), "asmith0935@gmail.com");
            emailSender.sendEmail(stringBuilder.toString(), "dsmith.mbe@gmail.com");
        }
    }
}
