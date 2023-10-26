package p04_ExtractData_db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetVille {

   
   public static void main(String[] args) {
       String websiteUrl = "https://www.marocannuaire.org/index.php";

       try {
           List<String> villes = getVillesFromWebsite(websiteUrl);
           saveToDatabase(villes, "jdbc:mysql://localhost:3306/dbmarketing", "root", "");
       } catch (IOException | SQLException e) {
           e.printStackTrace();
       }
   }

   public static List<String> getVillesFromWebsite(String websiteUrl) throws IOException {
       List<String> villes = new ArrayList<>();
       Document doc = Jsoup.connect(websiteUrl).get();
       Elements links = doc.select("a[href]");
       for (Element link : links) {
           String href = link.attr("href");
           if (href.contains("Annuaire/annuaire_ville.php?ville=")) {
               String ville = href.substring(34);
               villes.add(ville);
               System.out.println(ville);
           }
       }
       return villes;
   }

   public static void saveToDatabase(List<String> villes, String url, String user, String password) throws SQLException {
       try (Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement()) {
           
           String insertSql = "INSERT INTO ville (nom) VALUES (?)";
           try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
               for (String ville : villes) {
                   pstmt.setString(1, ville.trim());
                   pstmt.executeUpdate();
               }
           }
       }
   }
}