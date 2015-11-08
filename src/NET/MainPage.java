package NET;
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
 
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
 
public class MainPage extends JFrame {
        JPanel top_panel;
        JPanel matching_panel;
        BufferedImage img;
        String input;
        Boolean firsttime = true;
        String playerName;
        static MatchingButtons my_buttons[] = new MatchingButtons[36];
        static String[] filename = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
                        "10", "11", "12", "13", "14", "15", "16", "17", "18" };
        static int[] cards;
        static ObjectOutputStream out;
        public MainPage(String s) {
                super(s);
        }
 
        public void CreateGameAndShowGUI() {
                // ServerSocket serverSocket = WaitingPage.serverSocket;
                MainPage frame = new MainPage("Card Game");
                frame.setPreferredSize(new Dimension(800, 800));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                // INFO
                try {
                        System.out.println("helloooofrom the other pageeeee");
                        final Socket server = WaitingPage.server;
                        System.out.println(server);
 
                        System.out.println("input in");
                        out = new ObjectOutputStream(server.getOutputStream());
                        System.out.println("create thread");
                        Thread readThread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                        // TODO Auto-generated method stub
                                        try{
                                                final ObjectInputStream in;
                                                in = new ObjectInputStream(server.getInputStream());
                                        while (true) {
                                                input = in.readObject().toString();
                                                // to click buttons
                                                int click = Integer.parseInt(input);
                                                my_buttons[click].doClick();
                                        }
                                        }catch(Exception e){
                                                System.out.println("Catchhhhh");
                                        }
 
                                }
                        });
                        readThread.start();
                }
                catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        System.out.println("errorrr");
                }
 
                randomButton();
                top_panel = new JPanel(new GridLayout(2, 2, 2, 0));
                JLabel my_name = new JLabel(" Alice : 0 ");
                JLabel turn_label = new JLabel(" This is " + " Bob's " + "turn");
                JLabel oppo_name = new JLabel(" Bob : 1 ");
                JLabel time_label = new JLabel("Time left : 3 sec");
                top_panel.add(my_name);
                top_panel.add(oppo_name);
                top_panel.add(turn_label);
                top_panel.add(time_label);
                frame.add(top_panel, BorderLayout.NORTH);
                // Creating Buttons
                matching_panel = new JPanel(new GridLayout(6, 6, 4, 4));
                // String[] filename =
                // {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18"};
 
                for (int i = 0; i < 36; i++) {
                        my_buttons[i] = new MatchingButtons();
                        my_buttons[i].setActionCommand(i + "");
                        matching_panel.add(my_buttons[i]);
                }
                frame.add(matching_panel);
                frame.pack();
                frame.setVisible(true);
               
               
        }
 
        private void randomButton() {
                if (firsttime) {
                        ImageIcon[] img = new ImageIcon[18];
                        for (int i = 0; i < 18; i++) {
                                filename[i] += ".jpg";
                                img[i] = new ImageIcon(filename[i]);
                        }
 
                        cards = new int[36];
                        for (int i = 0; i < 36; i++) {
                                cards[i] = -1;
                        }
                        for (int pic = 0; pic < 18; pic++) {
                                for (int j = 0; j < 2; j++) {
                                        int pos = 0;
                                        do {
                                                pos = (int) (Math.random() * 36);
                                        } while (cards[pos] != -1); // if card[pos] is not -1, it
                                                                                                // already has a flag in it
                                        cards[pos] = pic;
                                }
                        }
                }
                firsttime = false;
                writeToStream(cards);
        }
        public static void writeToStream(Object o){
                try {
                        out.writeObject(o);
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
        public static void closeClick() {
                for (int i = 0; i < 36; i++) {
                        my_buttons[i].setIcon(null);
                }
        }
 
        public static void disableButtons() {
                for (int i = 0; i < 36; i++) {
                        my_buttons[i].setEnabled(false);
                }
        }
 
        public static void enableButtons() {
                for (int i = 0; i < 36; i++) {
                        my_buttons[i].setEnabled(true);
                }
        }
 
        static String temp = "";
 
        public static void previous(String a) {
                temp = a;
        }
 
        public static String getPrevious() {
                return temp;
        }
 
        public static void compareNum() {
                String nameCurrent = MatchingButtons.storeNum;
                String namePrevious = getPrevious();
                // compare if they are equal
                if (cards[Integer.parseInt(nameCurrent)] == cards[Integer
                                .parseInt(namePrevious)] && (nameCurrent != namePrevious)) {
                        System.out.print("Hello from the other side");
                        Timer t = new Timer(2000, new ActionListener(){
 
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        // TODO Auto-generated method stub
                                        my_buttons[Integer.parseInt(nameCurrent)].setVisible(false);
                                        my_buttons[Integer.parseInt(namePrevious)].setVisible(false);
                                }
                               
                        });
                        t.start();
                } else {
                        // not equal
                }
 
        }
 
        public static void main(String[] args) {
                final ChoosePage choose = new ChoosePage("");
                choose.CreateAndShowGUI();
//              final MainPage begin = new MainPage("");
//              begin.CreateGameAndShowGUI();
 
        }
 
}