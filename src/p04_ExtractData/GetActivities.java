package p04_ExtractData;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetActivities {
	
	public static void main(String[] args) throws IOException {
	    String csvFilePath = "Ressources//Activities.csv";
	    Map<String, Vector<String>> domainActivities = new HashMap<>();
	    
	    try (BufferedReader br = new BufferedReader(new FileReader("Ressources//Domaine.csv"))) {
	        String domaine;
	        while ((domaine = br.readLine()) != null) {
	            String websiteUrl = "https://www.marocannuaire.org/Annuaire/detail_annuaire_ville.php?domaine=" + domaine + "&ville=KHENIFRA";
	            try {
	                Vector<String> activities = new Vector<>();
	                new GetActivities(websiteUrl, activities);
	                domainActivities.put(domaine, activities);
	                
	               // System.out.println(domainActivities);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
        	
        	for (Map.Entry<String, Vector<String>> entry : domainActivities.entrySet()) {
    	        System.out.println(entry.getKey() + " " + entry.getValue());
    	        writer.print(entry.getKey()+";");
    	        for (String value : entry.getValue()) {
    	        	writer.print(value+";");
				}
    	        writer.println();
    	    }
        }
	    
	}

	public GetActivities(String websiteUrl, Vector<String> activities) throws IOException {
	 
	        Document doc = Jsoup.connect(websiteUrl).get();
	        Elements links = doc.select("a[href]");
	        
	        for (Element link : links) {
	            String href = link.attr("href");
	            
	            if (href.startsWith("activite_ville.php?activite=")) {
	            	int pos = href.indexOf("&ville=");
	                String activite = href.substring(28, pos);
	                if(!activities.contains(activite)) {
	                  
	                    activities.add(activite);
	                }
	            }
	        }
	   
	}
}