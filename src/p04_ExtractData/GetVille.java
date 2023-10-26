package p04_ExtractData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetVille {

    public static void main(String[] args) {
    	
        String websiteUrl = "https://www.marocannuaire.org/index.php";
        String csvFilePath = "Ressources//ville.csv";

        try {
            new GetVille(websiteUrl, csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GetVille(String websiteUrl, String csvFilePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath))) {
            Document doc = Jsoup.connect(websiteUrl).get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String href = link.attr("href");
                
                if (href.contains("Annuaire/annuaire_ville.php?ville=")) {
                    String ville = href.substring(34);
                    writer.println(ville);
                    System.out.println(ville);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}