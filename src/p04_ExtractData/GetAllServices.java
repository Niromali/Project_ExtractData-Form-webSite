package p04_ExtractData;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAllServices {
	static Hashtable<String,String> Activities= new Hashtable<>();
	static Vector<String> domains = new Vector<>();
	static GetDomaine getDomaine;
	public GetAllServices(GetDomaine getDomaine) {
		this.getDomaine=getDomaine;
		String csvFilePath = "Ressources//Activity.csv";
		try {
			domains = getDomaine("https://www.marocannuaire.org/Annuaire/annuaire_ville.php?ville=KHENIFRA", "Ressources//Domaine.csv");
			for (String domaine : domains) {
				getAllServices("https://www.marocannuaire.org/Annuaire/detail_annuaire_ville.php?domaine="+domaine+"&ville=KHENIFRA", csvFilePath);
			}
			
			//String websiteUrl = "https://www.marocannuaire.org/Annuaire/detail_annuaire_ville.php?domaine="+domaine+"&ville=KHENIFRA";
	        

//	        new GetAllServices(websiteUrl, csvFilePath);
			//System.out.println(Activities);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void main(String[] args) {
		new GetAllServices(getDomaine);
	}
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
                    //System.out.println(domaine);
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
	public void getAllServices(String websiteUrl, String csvFilePath) {
		// TODO Auto-generated constructor stub
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
		                    String Activite = href.substring(28,pos);
		                   // System.out.println(websiteUrl);
		                    String domaine=websiteUrl.substring(75, pos);
		                    System.out.println(domaine);
		                    if(!Activities.contains(Activite)) {
			                    writer.println(Activite);
			                  Activities.put(domaine, Activite);
		                    }
		                }
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
	}
	
}
