// JavaChatClientMain.java
// Java Client ????import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JavaGameClientMain extends JFrame {

   private JPanel contentPane;
   private JTextField txtUserName;
   private JTextField txtIpAddress;
   private JTextField txtPortNumber;

   /**
    * Launch the application.
    */
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
            	JavaGameClientMain frame = new JavaGameClientMain();
               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the frame.
    */
   public JavaGameClientMain() {
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 254, 321);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JLabel lblNewLabel = new JLabel("?̸?");
      lblNewLabel.setBounds(12, 39, 82, 33);
      contentPane.add(lblNewLabel);
      
      txtUserName = new JTextField();
      txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
      txtUserName.setBounds(101, 39, 116, 33);
      contentPane.add(txtUserName);
      txtUserName.setColumns(10);
      
      JButton btnConnect = new JButton("Connect");
      btnConnect.setBounds(12, 223, 205, 38);
      contentPane.add(btnConnect);
      Myaction action = new Myaction();
      btnConnect.addActionListener(action);
      txtUserName.addActionListener(action);
      //txtIpAddress.addActionListener(action);
      //txtPortNumber.addActionListener(action);
   }
   class Myaction implements ActionListener // ????Ŭ?????? ?׼? ?̺?Ʈ ó?? Ŭ????
   {
      @Override
      public void actionPerformed(ActionEvent e) {
         String username = txtUserName.getText().trim();
         //String ip_addr = txtIpAddress.getText().trim();
         //String port_no = txtPortNumber.getText().trim();
        // JavaGameClientView view = new JavaGameClientView(username, "127.0.0.1", "30000");
         ChatFriendList list = new ChatFriendList(username, "127.0.0.1", "30000");
         list.setVisible(true);
         setVisible(false);
      }
   }
}