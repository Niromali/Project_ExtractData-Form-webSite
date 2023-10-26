package p04_ExtractData_db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import p04_ExtractData.GetEntreprise;

public class Entreprise {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/dbmarketing";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws IOException {
    	//System.out.println(getUserWhereName("Service"));
       
        try {
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement  st=conn.createStatement();
			Statement  st1=conn.createStatement();
			ResultSet rsactivite=st.executeQuery("select * from activite");

			ResultSet rsville=null;
			while(rsactivite.next()) {
				 rsville=st1.executeQuery("select * from ville");
				while(rsville.next()) {
					String websiteUrl = "https://www.marocannuaire.org/Recherche/recherch_par_activite_ville.php?activite="+rsactivite.getString(2)+"&ville="+rsville.getString(2); 
					getAndSaveEntreprise(websiteUrl, rsactivite.getString(1), rsville.getString(1));
				}

			}
			
			rsactivite.close();
			rsville.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public static Vector<String> getEntreprise(String websiteUrl) throws IOException {
    	// Retrieve the HTML content of the page
    	Vector<String> entreprises = new Vector<>();
    	try {
	        Document doc = Jsoup.connect(websiteUrl).get();
	        Elements content = doc.select("div.content-nowon-newday");
	        for (Element elem : content) {
	        	// nom Entreprise 
	        	String nomEntrprise = elem.select("h2").text();
	        	// Adresse Entreprise
	        	int pos = elem.select("h5").select("p").toString().indexOf("<br>");
				String adress = elem.select("h5").select("p").text().substring(0, pos-2);
	        	
	        	String numero = "";
	            Element brElement = elem.selectFirst("h5 br");
	            if (brElement != null) {
	            	numero = brElement.nextSibling().toString().trim();
	            }
	            
	        	String emails = elem.select("h5").select("p").select("a").text();
	        	Elements villesecteur = elem.select("h5").select("ul").select("li");
	        	
	        	String secteur = villesecteur.first().text();
	        	String ville =villesecteur.last().text();
	        	String []sss=emails.split(" ");
	        	if (sss.length== 2) {
	        		String email = sss[0].toString();
	        		String site=sss[1].toString();
	            	//writer.append(nomEntrprise+";"+secteur+";"+ville+";"+numero+";"+email+";"+site+"\n");
	            	System.out.println(nomEntrprise+";"+secteur+";"+ville+";"+numero+";"+email+";"+site+"\n");
	            	//entreprises.add(nomEntrprise+";"+secteur+";"+ville+";"+numero+";"+email+";"+site);
	            	entreprises.add(nomEntrprise+";"+numero+";"+email+";"+site);
				}
	        	
			}
	        //System.out.println(content.nextElementSiblings());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return entreprises;
    }
    
    public static void getAndSaveEntreprise(String websiteUrl, String idactivite , String idville) {
        try {
            Vector<String> entreprises = getEntreprise(websiteUrl);

            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO entreprise (raisonsociale, telephone, email, siteweb, idactivite, idville) VALUES (?,?,?,?,?,?)");
            for (String entreprise : entreprises) {
            	String [] ent=entreprise.split(";");
            	
                stmt.setString(1, ent[0].toString());
                stmt.setString(2, ent[1].toString());
                stmt.setString(3, ent[2].toString());
                stmt.setString(4, ent[3].toString());
                stmt.setString(5, idactivite);
                stmt.setString(6, idville);
                
               // stmt.setString(2, );
                stmt.executeUpdate();
            }
            //System.out.println(entreprises);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
