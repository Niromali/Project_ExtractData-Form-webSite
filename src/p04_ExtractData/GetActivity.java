package p04_ExtractData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetActivity {
	static Vector<String> activities=new Vector<>();
	public static void main(String[] args) {
        String websiteUrl = "https://www.marocannuaire.org/Annuaire/detail_annuaire_ville.php?domaine=Service&ville=KHENIFRA";
        String csvFilePath = "Ressources//Activity.csv";

        try {
            new GetActivity(websiteUrl, csvFilePath);
           // System.out.println(activities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GetActivity(String websiteUrl, String csvFilePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
            Document doc = Jsoup.connect(websiteUrl).get();
            Elements links = doc.select("a[href]");
            
          
            for (Element link : links) {
                String href = link.attr("href");
                
                if (href.startsWith("activite_ville.php?activite=")) {
                	//System.out.println(href);
                	int pos = href.indexOf("&ville=");
                	//String domaine = href.substring(33);
                	//System.out.println(i+":"+href);
                	//i++;
                    String activite = href.substring(28,pos);
                    if(!activities.contains(activite)) {
                    writer.println(activite);
                   // System.out.println(activite);
                    activities.add(activite);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
