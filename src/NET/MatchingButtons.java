package NET;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;


public class MatchingButtons extends JButton implements ActionListener {
	boolean showed;
	boolean firsttime = true;
	BufferedImage img;
	JButton buttons;
	Timer timer = null;
	static int count = 0; 
	static int order = 0;
	static String storeNum;
	public MatchingButtons(){
		if (firsttime) {
			try {
				System.out.println(MainPage.cards);
				String imgname = MainClient.filename[MainPage.cards[order]];
			    img = ImageIO.read(new File("src/" + imgname));
			    order++;
			} catch (IOException e1) {
				System.out.println("hoooooo");
			}
			setEnabled(false);
			setIcon(new ImageIcon(img));
			setDisabledIcon(new ImageIcon(img));
			timer = new Timer(10000, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					MainPage.closeClick();
					setEnabled(true);
				}
			});
			timer.setRepeats(false);
			timer.start();
			firsttime = false;
		}
	addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					count++;
					if(count == 1)
					{
						MainPage.previous(ae.getActionCommand());
					}
					if(count == 2) 
					{
						storeNum = ae.getActionCommand();
						MainPage.compareNum();
						MainPage.disableButtons();
					}
					try {
						int pos = Integer.parseInt(ae.getActionCommand());
						MainPage.writeToStream(pos);
						String imgname = MainPage.filename[MainPage.cards[pos]];
					    img = ImageIO.read(new File("src/" + imgname));
					} catch (IOException e1) {
						System.out.println("hoooooo");
					}
					setIcon(new ImageIcon(img));
					if (count == 2) {
						timer = new Timer(2000, new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								count = 0;
								MainPage.closeClick();
								MainPage.enableButtons();
							}
						});
						timer.setRepeats(false);
						timer.start();
						
					}
				}

			});
			
	}	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		

	}
}
