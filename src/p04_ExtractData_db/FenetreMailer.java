package p04_ExtractData_db;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FenetreMailer extends Thread{
	public FenetreMailer() {
		JFrame f = new JFrame();
		JLabel nbjour = new JLabel("Nombre par jour :");
		JTextField txtjour = new JTextField(10);
		JLabel Min = new JLabel("Min :");
		JTextField txtmin = new JTextField(10);
		JLabel Max = new JLabel("Max :");
		JTextField txtmax = new JTextField(10);
		JPanel plabel = new JPanel();
		plabel.add(nbjour);
		plabel.add(txtjour);
		plabel.add(Min);plabel.add(txtmin);plabel.add(Max);plabel.add(txtmax);
		
		JPanel p = new JPanel();
		BoxLayout bl = new BoxLayout(p,BoxLayout.Y_AXIS);
		p.setLayout(bl);
		
		JLabel Sujet = new JLabel("Sujet :");
		JTextField txtsujet = new JTextField(20);
		JPanel psuj=new JPanel();
		psuj.add(Sujet);psuj.add(txtsujet);
		
		JLabel message = new JLabel("Message :");
		JTextArea txtmessage = new JTextArea(5, 10);
		JPanel pmess=new JPanel();
		pmess.add(message);pmess.add(txtmessage);
		
		JButton benvoye = new JButton("Envoye");
		JPanel pb = new JPanel();
		pb.add(benvoye);
		
		benvoye.addActionListener(e->{
			
			
			
			
			int nbmail = Integer.parseInt(txtjour.getText());
			int min = Integer.parseInt(txtmin.getText());
			int max = Integer.parseInt(txtmax.getText());
			AutoMail au=new AutoMail(nbmail, min, max, txtsujet.getText(), txtmessage.getText(),benvoye);
			au.start();
			benvoye.setText("en cours");
		});
		p.add(plabel);p.add(psuj);p.add(pmess);p.add(pb);
		JPanel pf = new JPanel();
		pf.add(p);
		f.setContentPane(pf);
		f.setSize(500,500);
		
		f.setVisible(true);
	}
	public static void main(String[] args) {
		new FenetreMailer();
	}
}
