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
	boolean firsttime1 = true;
	BufferedImage img;
	JButton buttons;
	Timer timer = null;
	static int count = 0;
	static int order = 0;
	static String storeNum;
	static String actionEvent;
	public static boolean myTurn = true;
	private static int ten_sec = 10000;
	static Timer countdown = new javax.swing.Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			if(ten_sec >= 0){
				MainPage.time_label.setText("Time left:" + (ten_sec/1000) + " sec");
				ten_sec -= 1000;
			}else{
				// Score
				//countdown.setRepeats(false);
				countdown.stop();
				ten_sec = 10000;
				MainPage.time_label.setText("Time left:" + (ten_sec/1000)+" sec");
				if (MatchingButtons.myTurn) {
					MainPage.myScore--;
				} else {
					MainPage.clientScore--;
				}
				// change turn
				if (MatchingButtons.myTurn) {
					MatchingButtons.myTurn = false;
				} else {
					MatchingButtons.myTurn = true;
				}
				MatchingButtons.changeTurn();
				// not equal
				Answer ans = new Answer(false, -1, -1, MainPage.myScore, MainPage.clientScore);
				MainPage.writeToStream(ans);
				MainPage.my_name.setText(ChoosePage.name + ": " + MainPage.myScore);
				MainPage.oppo_name.setText(MainPage.clientName+":" + MainPage.clientScore);
				MainPage.closeClick();
				count = 0;
			}
//			
		}
	});

	public MatchingButtons() {
		if (firsttime) {
			try {
				String imgname = MainPage.filename[MainPage.cards[order]];
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
				try {
					int pos = Integer.parseInt(ae.getActionCommand());
					if (count == 1 && myTurn) {
						storeNum = ae.getActionCommand();
						MainPage.writeToStream(pos);
						// System.out.println("Sent :" + pos);
						myTurn = false;
						//System.out.println("Called changeTurn1");
						changeTurn();
						//countdown.setRepeats(false);
						countdown.restart();
						ten_sec = 10000;
						System.out.println("Timer Restart");

					} else if (count == 1 && !myTurn) {
						myTurn = true;
						//System.out.println("Called changeTurn2");
						changeTurn();
						//countdown.setRepeats(false);
						countdown.restart();
						ten_sec = 10000;
						System.out.println("Timer Restart");
					}
					String imgname = MainPage.filename[MainPage.cards[pos]];
					img = ImageIO.read(new File("src/" + imgname));
				} catch (IOException e1) {
					System.out.println("hoooooo");
				}
				setIcon(new ImageIcon(img));

				if (count == 2 && myTurn) {
					//countdown.setRepeats(false);
					MainPage.time_label.setText("Time left: 10 sec");
					countdown.stop();
					ten_sec = 10000;
					storeNum = ae.getActionCommand();
					//System.out.println("Called cpn from actionL1111111111");
					count = 0;
					MainPage.writeToStream(ae.getActionCommand());
					MainPage.compareNum();
					timer = new Timer(2000, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							MainPage.closeClick();
						}
					});
					timer.setRepeats(false);
					timer.start();
				} else if (count == 2 && !myTurn) {
					//countdown.setRepeats(false);
					MainPage.time_label.setText("Time left: 10 sec");
					countdown.stop();
					ten_sec = 10000;
					//System.out.println("Called cpn from actionL22222222");
					MainPage.compareNum();
					count = 0;
					timer = new Timer(2000, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							MainPage.closeClick();
						}
					});
					timer.setRepeats(false);
					timer.start();
				}
				// }
			}

		});

	}

	public static void changeTurn() {
		if (myTurn) {
			MainPage.enableButtons();
			MainPage.writeToStream("Stop");
			MainPage.turn_label.setText("This is " + ChoosePage.name +" 's turn");
			//System.out.println("Disable Clients' Buttons It is My Turn");
		} else {
			//System.out.println("Disable My Buttons Client's Turn");
			MainPage.turn_label.setText("This is " + MainPage.clientName + " 's turn");
			MainPage.disableButtons();
			MainPage.writeToStream("Start");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
