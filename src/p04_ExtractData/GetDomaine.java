package p04_ExtractData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetDomaine {

	/*public static void main(String[] args) {
        String websiteUrl = "https://www.marocannuaire.org/Annuaire/annuaire_ville.php?ville=KHENIFRA";
        String csvFilePath = "Ressources//Domaine.csv";

        try {
            //new GetDomaine(websiteUrl, csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GetDomaine(String websiteUrl, String csvFilePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
            Document doc = Jsoup.connect(websiteUrl).get();
            Elements links = doc.select("a[href]");
            Vector<String> domains=new Vector<>();
            for (Element link : links) {
                String href = link.attr("href");
                
                if (href.contains("detail_annuaire_ville.php?domaine=")) {
                	//System.out.println(href);
                	int pos = href.indexOf("&ville=");
                	//String domaine = href.substring(33);
                    String domaine = href.substring(34,pos);
                    if(!domains.contains(domaine)) {
                    writer.println(domaine);
                    System.out.println(domaine);
                    domains.add(domaine);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public Vector<String> getDomaine(String websiteUrl, String csvFilePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
            Document doc = Jsoup.connect(websiteUrl).get();
            Elements links = doc.select("a[href]");
            Vector<String> domains=new Vector<>();
            for (Element link : links) {
                String href = link.attr("href");
                
                if (href.contains("detail_annuaire_ville.php?domaine=")) {
                	//System.out.println(href);
                	int pos = href.indexOf("&ville=");
                	//String domaine = href.substring(33);
                    String domaine = href.substring(34,pos);
                    if(!domains.contains(domaine)) {
                    writer.println(domaine);
                    System.out.println(domaine);
                    domains.add(domaine);
                    }
                }
            }
            return domains;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
    }
}
