package p04_ExtractData_db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Activite {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/dbmarketing";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws IOException {
    	//System.out.println(getUserWhereName("Service"));
       
        try {
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement  st=conn.createStatement();
			ResultSet rs=st.executeQuery("select * from domaine");
			while(rs.next()) {
				 String websiteUrl = "https://www.marocannuaire.org/Annuaire/detail_annuaire_ville.php?domaine="+rs.getString(2)+"&ville=KHENIFRA";
			        getAndSaveActivite(websiteUrl,rs.getString(1));
				
			}
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        

     
    }
    
    public static Vector<String> getActivite(String websiteUrl) throws IOException {
        Document doc = Jsoup.connect(websiteUrl).get();
        Elements links = doc.select("a[href]");
        Vector<String> domains=new Vector<>();
        for (Element link : links) {
            String href = link.attr("href");

            if (href.contains("activite_ville.php?activite=")) {
                int pos = href.indexOf("&ville=");
                String activite = href.substring(28, pos);
                if (!domains.contains(activite)) {
                    domains.add(activite);
                }
            }
        }
        return domains;
    }
    
    
    public static void getAndSaveActivite(String websiteUrl, String iddomaine) {
        try {
            Vector<String> activities = getActivite(websiteUrl);
            Map<String, Vector<String>> domainActivities = new HashMap<>();

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO activite (nom,iddomaine) VALUES (?,?)");
            for (String activite : activities) {
            	
                stmt.setString(1, activite);
                stmt.setString(2, iddomaine);
                
               // stmt.setString(2, );
                stmt.executeUpdate();
            }
            System.out.println(activities);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}