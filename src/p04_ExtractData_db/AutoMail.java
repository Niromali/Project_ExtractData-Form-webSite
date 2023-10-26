package p04_ExtractData_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;

public class AutoMail  extends Thread{
	private static final String DB_URL = "jdbc:mysql://localhost:3306/dbmarketing";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private int nbemail;
    private int min;
    private int max;
    private String objet;
    private String message;
    private JButton b;
	public static Vector<String> getEmails() {
		Vector<String> emails=new Vector<>();
		try {
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement  st=conn.createStatement();
			ResultSet rs=st.executeQuery("select email from entreprise order by id desc");
			while(rs.next()) {
				emails.add(rs.getString(1));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emails;
	}
	public static int getNumber(int min,int max) {
		Random random = new Random();
 
        int randomNumber = random.nextInt((max*60000)-(min*60000)+60000) + (min*60000);
        return randomNumber;
        
	}
	public AutoMail(int nbemail,int min,int max,String objet,String message,JButton b) {
		this.nbemail=nbemail;
		this.min=min;
		this.max=max;
		this.objet=objet;
		this.message=message;
		this.b=b;
	}
	@Override
	public void run() {
		int cp=0;
		Vector<String> mails=getEmails();
		for (String mail : mails) {
			if (cp < nbemail) {
				Mailer.send("comptepro995@gmail.com","xghwdzgkdzybzaov" ,mail ,objet ,message );
				cp++;
				try {
					Thread.sleep(getNumber(min,max));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
			  b.setText("Terminer");	
			
				return;
			}
	}

	}
}
