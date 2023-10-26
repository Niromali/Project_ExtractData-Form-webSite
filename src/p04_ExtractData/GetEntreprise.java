package p04_ExtractData;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetEntreprise {

	public static void main(String[] args) {
		
		Vector<String> villes=new Vector<>();
		try (BufferedReader br = new BufferedReader(new FileReader("Ressources//ville.csv"))) {
			String lineville = br.readLine();
			while(lineville != null) {
				villes.add(lineville);
				lineville = br.readLine();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		Vector<String> activities=new Vector<>();
		try (BufferedReader br = new BufferedReader(new FileReader("Ressources//Activity.csv"))) {
			String lineville = br.readLine();
			while(lineville != null) {
				activities.add(lineville);
				lineville = br.readLine();
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		for (String activitie : activities) {
			for (String ville : villes) {
				new GetEntreprise("https://www.marocannuaire.org/Recherche/recherch_par_activite_ville.php?activite="+activitie+"&ville="+ville,"Ressources//Entreprise.csv");
			}
		}
		//new GetEntreprise("https://www.marocannuaire.org/Recherche/recherch_par_activite_ville.php?activite=Traiteurs+et+organisateurs+de+reception&ville=CASABLANCA","Ressources//Entreprise.csv");
	}
	public GetEntreprise(String websiteUrl,String csvFilePath) {
		// TODO Auto-generated constructor stub
		try (PrintWriter writer = new PrintWriter(new FileWriter(csvFilePath,true))) {
			// Retrieve the HTML content of the page
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
                	writer.append(nomEntrprise+";"+secteur+";"+ville+";"+numero+";"+email+";"+site+"\n");
                	System.out.println(nomEntrprise+";"+secteur+";"+ville+";"+numero+";"+email+";"+site+"\n");
				}
            	
			}
            //System.out.println(content.nextElementSiblings());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
}
