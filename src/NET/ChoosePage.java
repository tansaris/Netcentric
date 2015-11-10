package NET;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChoosePage extends JFrame{
	JPanel top_panel;
	JPanel main_panel;
	static String name;
	public void CreateAndShowGUI(){
		ChoosePage frame = new ChoosePage("Card Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		top_panel = new JPanel(new BorderLayout());
		main_panel = new JPanel(new GridLayout(1,2,0,0));
		JLabel name_lbl = new JLabel("Player's name:");
		JTextField in_name = new JTextField(1);
		JButton host_btn = new JButton("Host a game");
		host_btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				name= in_name.getText();
				changeToHost();
				
			}
		});
		JButton join_btn = new JButton("Join a game");
		join_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				name= in_name.getText();
				changePage();
			}
			
		});
		top_panel.add(name_lbl,BorderLayout.WEST);
		top_panel.add(in_name,BorderLayout.CENTER);
		main_panel.add(host_btn);
		main_panel.add(join_btn);
		frame.add(top_panel,BorderLayout.NORTH);
		frame.add(main_panel);
		frame.pack();
		frame.setVisible(true);	
	}
	public static void main(String[] args) {
		final ChoosePage begin = new ChoosePage("");
		begin.CreateAndShowGUI();
		
    }
	public ChoosePage(String s){
		super(s);
	}
	public void changePage(){
		ConnectPage x = new ConnectPage("");
		x.CreateAndShowGUI();
		dispose();
		this.setVisible(false);
	}
	public void changeToHost(){
		WaitingPage y = new WaitingPage("");
		y.createPage();
		dispose();
		this.setVisible(false);
		
	}

}
