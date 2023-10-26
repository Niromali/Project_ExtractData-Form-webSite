package p04_ExtractData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class GetEntreprises {

	public GetEntreprises(String websiteUrl) {
		// TODO Auto-generated constructor stub

		// TODO Auto-generated constructor stub
		try {
			// Retrieve the HTML content of the page
            Document doc = Jsoup.connect(websiteUrl).get();

            // Extract the content of the section with class "content-nowon-newday"
            //Element content = doc.selectFirst("div.content-nowon-newday");
            Elements content = doc.select("div.content-nowon-newday");
            

            // Display the extracted content
            System.out.println(content);
            System.out.println("************************");
            int pos = content.select("h5").select("p").toString().indexOf("<br>");
            int pos2=pos+10;
            for (Element elem : content) {
            	// nom Entreprise 
            	//String nomEntrprise = elem.select("h2").text();
            	// Adresse Entreprise
            	int pos = elem.select("h5").select("p").toString().indexOf("<br>");
				//String adress = elem.select("h5").select("p").text().substring(0, pos-2);
            	
            	String numero = elem.select("h5").select("p").text().substring(pos, pos2);
            	System.out.println(numero);
				//System.out.println(elem.select("h5").select("p").first());
				
				//System.out.println(pos);
				
				//System.out.println(adress);
				//System.out.println(elem.select("h5").select("p").text().contains("www"));
				System.out.println("-------");
			}
            //System.out.println(content.nextElementSiblings());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		//new GetEntreprise("https://www.marocannuaire.org/Recherche/recherch_par_activite_ville.php?activite=Traiteurs+et+organisateurs+de+reception&ville=CASABLANCA");
	
	}

}
