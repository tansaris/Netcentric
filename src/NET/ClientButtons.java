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

public class ClientButtons extends JButton {
	boolean showed;
	boolean firsttime = true;
	BufferedImage img;
	JButton buttons;
	Timer timer = null;
	static int count = 0;
	static int order = 0;
	static String storeNum;
	private static int ten_sec = 10000;
	static Timer countdown = new javax.swing.Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if (ten_sec >= 0) {
				MainClient.time_label.setText("Time left:" + (ten_sec / 1000));
				ten_sec -= 1000;
			} else {
				// Score
				// countdown.setRepeats(false);
				countdown.stop();
				ten_sec = 10000;
			}
		}
			});
	public ClientButtons() {
		if (firsttime) {
			try {
				String imgname = MainClient.filename[MainClient.cards[order]];
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
					MainClient.closeClick();
					setEnabled(true);
				}
			});
			timer.setRepeats(false);
			timer.start();
			firsttime = false;
		}
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					System.out.println("My turn: " + MainClient.myTurn);
					count++;
					System.out.println("Count: " + count);
					int pos = Integer.parseInt(ae.getActionCommand());
					if (count == 1 && MainClient.myTurn) {
						countdown.start();
						MainClient.writeToStream(pos);
//						MainClient.myTurn = false;
						MainClient.changeTurn();
					} else if (count == 1 && !MainClient.myTurn) {
						countdown.start();
//						MainClient.myTurn = true;
						MainClient.changeTurn();
					} else if (count == 2 && MainClient.myTurn) {
						countdown.stop();
						MainClient.time_label.setText("Time left: 10 sec");
						ten_sec = 10000;
						MainClient.writeToStream(pos);
						count = 0;
					} else if (count == 2 && !MainClient.myTurn) {
						countdown.stop();
						ten_sec = 10000;
						count = 0;
					}
					String imgname = MainClient.filename[MainClient.cards[pos]];
					img = ImageIO.read(new File("src/" + imgname));
				} catch (IOException e1) {
					System.out.println("hoooooo");
				}
				setIcon(new ImageIcon(img));

			}

		});

	}

}
