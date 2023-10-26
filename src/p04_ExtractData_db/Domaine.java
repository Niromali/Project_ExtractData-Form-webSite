package p04_ExtractData_db;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Domaine {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/dbmarketing";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws IOException {
    	
        String websiteUrl = "https://www.marocannuaire.org/Annuaire/annuaire_ville.php?ville=KHENIFRA";
        getAndSaveDomaine(websiteUrl);

     
    }

    public static Vector<String> getDomaine(String websiteUrl) throws IOException {
        Document doc = Jsoup.connect(websiteUrl).get();
        Elements links = doc.select("a[href]");
        Vector<String> domains=new Vector<>();
        for (Element link : links) {
            String href = link.attr("href");

            if (href.contains("detail_annuaire_ville.php?domaine=")) {
                int pos = href.indexOf("&ville=");
                String domaine = href.substring(34, pos);
                if (!domains.contains(domaine)) {
                    domains.add(domaine);
                }
            }
        }
        return domains;
    }

    public static void getAndSaveDomaine(String websiteUrl) {
        try {
            Vector<String> domains = getDomaine(websiteUrl);

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO domaine (nom) VALUES (?)");
            for (String domaine : domains) {
            	
                stmt.setString(1, domaine);
                stmt.executeUpdate();
            }
            System.out.println(domains);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
