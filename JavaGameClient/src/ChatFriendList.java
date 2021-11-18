// JavaChatClientMain.java
import java.util.Arrays;
// Java Client ����import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import com.sun.tools.javac.Main;
/*
import JavaGameClientView.ImageSendAction;
import JavaGameClientView.ListenNetwork;
import JavaGameClientView.MyMouseEvent;
import JavaGameClientView.MyMouseWheelEvent;
import JavaGameClientView.TextSendAction;*/

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.awt.*;
import javax.swing.Icon;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ChatFriendList extends JFrame {
   ArrayList userNameList = new ArrayList();

   private JPanel contentPane;
   private JTextField txtIpAddress;
   private JTextField txtPortNumber;

   /**
    * Launch the application.
    */
   private static final long serialVersionUID = 1L;
   
   
   private String UserName;
   private JButton btnSend;
   private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
   private Socket socket; // �������
   private InputStream is;
   private OutputStream os;
   private DataInputStream dis;
   private DataOutputStream dos;

   private ObjectInputStream ois;
   private ObjectOutputStream oos;

   private JLabel lblUserName;
   // private JTextArea textArea;
   private JTextPane textArea;

   private Frame frame;
   private FileDialog fd;
   private JButton imgBtn;
   JPanel chatPanel;
   JPanel panel;
   private JLabel lblMouseEvent;
   private Graphics gc;
   private int pen_size = 2; // minimum 2
   // �׷��� Image�� �����ϴ� �뵵, paint() �Լ����� �̿��Ѵ�.
   private Image panelImage = null; 
   private Graphics gc2 = null;
   
   private JLabel userListLabel;
   
   private JPanel userListPanel;
   
   private JButton testButton;
  
   /**
    * Create the frame.
    */
   public ChatFriendList(String username, String ip_addr, String port_no) {
     this.UserName = username;
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(400,600);
      setBounds(100, 100, 386, 512);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      JPanel contentPane_1 = new JPanel();
      contentPane_1.setBackground(Color.WHITE);
      contentPane_1.setLayout(null);
      contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane_1.setBounds(61, 0, 311, 485);
      contentPane.add(contentPane_1);
      
      JLabel FriendLabel = new JLabel("\uCE5C\uAD6C");
      FriendLabel.setFont(new Font("����", Font.BOLD, 18));
      FriendLabel.setBounds(23, 23, 76, 34);
      contentPane_1.add(FriendLabel);
      
      JLabel userNameLabel = new JLabel(username);
      userNameLabel.setFont(new Font("����", Font.BOLD, 14));
      userNameLabel.setBounds(77, 75, 68, 15);
      contentPane_1.add(userNameLabel);
      
      JPanel userImgPanel = new JPanel();
      userImgPanel.setBackground(Color.WHITE);
      userImgPanel.setBounds(23, 66, 42, 42);
      contentPane_1.add(userImgPanel);
//      MyPanel panel = new MyPanel("src/icon1.jpg");
//      contentPane_1.add(panel);
      ImageIcon icon = new ImageIcon("src/basicProfileImg.jpg");
      Image img = icon.getImage();
      Image changeImg = img.getScaledInstance(41,41, Image.SCALE_SMOOTH);
      ImageIcon changeIcon = new ImageIcon(changeImg);
      JLabel label1 = new JLabel(changeIcon);
      JLabel label2 = new JLabel(changeIcon);
      userImgPanel.add(label1);
      
      JPanel friendImgPanel = new JPanel();
      friendImgPanel.setBackground(Color.WHITE);
      friendImgPanel.setBounds(23, 119, 42, 42);
     
      ImageIcon friendIcon = new ImageIcon("src/basicProfileImg.jpg");
      JLabel friendIconlabel = new JLabel(friendIcon);
      friendImgPanel.add(friendIconlabel);
      
      contentPane_1.add(friendImgPanel);
      
   
     
      
      JLabel friendNameLabel = new JLabel("<dynamic>");
      friendNameLabel.setFont(new Font("����", Font.BOLD, 14));
      friendNameLabel.setBounds(77, 134, 68, 15);
      contentPane_1.add(friendNameLabel);
      
      class FriendAction extends MouseAdapter // ����Ŭ������ �׼� �̺�Ʈ ó�� Ŭ����
      {
        
         public void mouseClicked(MouseEvent e) {
            contentPane_1.setVisible(true);
            chatPanel.setVisible(false);
            
           }
      }
      
      FriendAction friendAction = new FriendAction();
      JPanel friendIconPanel = new JPanel();
      friendIconPanel.setBounds(12, 47, 40, 40);
      contentPane.add(friendIconPanel);
      friendIconPanel.addMouseListener(friendAction);
      ImageIcon menuIcon1 = new ImageIcon("src/menuIcon1.png");
      JLabel MenuIconlabel = new JLabel(menuIcon1);
      friendIconPanel.add(MenuIconlabel);
      
      
      JPanel chatIconPanel = new JPanel();
      chatIconPanel.setBounds(12, 119, 40, 40);
      contentPane.add(chatIconPanel);
      
      ImageIcon menuIcon2 = new ImageIcon("src/menuIcon2.png");
      
      chatPanel = new JPanel();//ä��â ����� setvisible(false);
      chatPanel.setBounds(61, 0, 311, 485);
      chatPanel.setVisible(false);
      chatPanel.setBackground(Color.WHITE);
      chatPanel.setLayout(null);
      
      class ChatAction extends MouseAdapter // ����Ŭ������ �׼� �̺�Ʈ ó�� Ŭ����
      {
        
         public void mouseClicked(MouseEvent e) {
            contentPane_1.setVisible(false);
            chatPanel.setVisible(true);
            contentPane.add(chatPanel);
            JLabel dd = new JLabel("dd?????");
            dd.setSize(50,50);
            dd.setLocation(150,150);
            chatPanel.add(dd);
           }
     
      }
      ChatAction chatIconeve = new ChatAction();
      JLabel chatIconLabel = new JLabel(menuIcon2);
      chatIconPanel.add(chatIconLabel);
      chatIconPanel.addMouseListener(chatIconeve);//�� ������ ������ ä��â �ߵ���
      
     
      //txtIpAddress.addActionListener(action);
      //txtPortNumber.addActionListener(action);


      try {
         socket = new Socket(ip_addr, Integer.parseInt(port_no));

         oos = new ObjectOutputStream(socket.getOutputStream());
         oos.flush();
         ois = new ObjectInputStream(socket.getInputStream());

         ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");//connect
         //���� ������ ������ ������ �Դϴ�.
         SendObject(obcm);//send
         EnterUserNetwork net = new EnterUserNetwork();
         net.start();
      


      } catch (NumberFormatException | IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         //AppendText("connect error");
      }
      
      
      
      class MyAction extends MouseAdapter // ����Ŭ������ �׼� �̺�Ʈ ó�� Ŭ����
      {
         
         public void mouseClicked(MouseEvent e) {
           
            ChatRoom chatrom = new ChatRoom(username, ip_addr, port_no, socket); 
            chatrom.setVisible(true);
         }     
      }
      
      MyAction my = new MyAction();
      friendImgPanel.addMouseListener(my);
      JLabel label_1 = new JLabel((Icon) null);
      friendImgPanel.add(label_1);
      
      userListLabel = new JLabel("");
      userListLabel.setBounds(66, 280, 178, 148);
      contentPane_1.add(userListLabel);
      
      userListPanel = new JPanel();
      userListPanel.setBounds(12, 197, 287, 51);
      contentPane_1.add(userListPanel);
      
      testButton = new JButton("New button");
      testButton.setBounds(208, 149, 91, 23);
      contentPane_1.add(testButton);
      
      
      
     
   }
   class EnterUserNetwork extends Thread {
      public void run() {
         while (true) {
            try {

               Object obcm = null;
               String msg = null;
               ChatMsg cm;
               try {
                  obcm = ois.readObject();
               } catch (ClassNotFoundException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                  break;
               }
               if (obcm == null)
                  break;
               if (obcm instanceof ChatMsg) {
                  cm = (ChatMsg) obcm;
                  msg = String.format("[%s]\n%s", cm.UserName, cm.data);
               } else
                  continue;
               switch (cm.code) {
                     case "100":
                    
                        // �����迭�� ���� ���� ������ �߰���
                       // userNameList.add(cm.UserName);
                        //String str = String.join(",", userNameList);
                        // userListLabel.setText(str);
                         
                        // obcm = new ChatMsg(UserName, "101", str); // ���� ����Ʈ�� ���� 
                           
                          // SendObject(obcm);//send
                         break;
                         
                     case "101": // ���� ����Ʈ �޾Ҵٸ�
                        userListLabel.setText(cm.data);
                        //String userListStr = cm.data;
                        //String userListStr = "user1, user2, user3, user4";
                       // System.out.println(cm.data);
                        String[] userListString= cm.data.split(",");
                        
                      //  System.out.println(Arrays.toString(userListString));
                        
                				 JPanel userOnePanel;
                                 JLabel userOneLabel;
                                 userListPanel.removeAll();
                                 
                                 for (int i=0; i<userListString.length; i++) {
                                	// System.out.println(userListString[i]);
                                	 if(!UserName.equals(userListString[i])) {
	                                	 userOnePanel = new JPanel();
	                                     
	                                     userOneLabel = new JLabel();
	                                     userListPanel.add(userOnePanel);
	                                     userOnePanel.add(userOneLabel);
	                                    
	                                     userOneLabel.setText(userListString[i]);
                                	 }
                                }
							  
							  break;
							  
 
                                /* testButton.addActionListener(new ActionListener() {
                                	 public void actionPerformed(ActionEvent e) {
                                		 JPanel userOnePanel;
                                         JLabel userOneLabel;
                                         for (int i=0; i<userListString.length; i++) {
                                        	 System.out.println(userListString[i]);
                                        	 
                                        	 userOnePanel = new JPanel();
                                             
                                             userOneLabel = new JLabel();
                                             userListPanel.add(userOnePanel);
                                             userOnePanel.add(userOneLabel);
                                        	
                                             userOneLabel.setText(userListString[i]);
                                        }
                        			
                                	 }
                                 }); */
                                 
								
								 /* userOnePanel = new JPanel();
								  
								  userOneLabel = new JLabel(); 
								  userListPanel.add(userOnePanel);
								  userOnePanel.add(userOneLabel);
								  
								  userOneLabel.setText(" ");
								  userOneLabel.setText(userListString[0]);*/
                                 
								  
								  
								 
                                 
                            
                			
                		
                        
                        
                        
                        
                  
            /*case "200": // chat message
               if (cm.UserName.equals(UserName))
                  //AppendTextR(msg); // �� �޼����� ������
               else
                  //AppendText(msg);
               break;
            case "300": // Image ÷��
               if (cm.UserName.equals(UserName))
                  //AppendTextR("[" + cm.UserName + "]");
               else
                  //AppendText("[" + cm.UserName + "]");
               //AppendImage(cm.img);
               break;
            case "500": // Mouse Event ����
               //DoMouseEvent(cm);
               break;
               */
            }
            } catch (IOException e) {
               AppendText("ois.readObject() error");
               try {
//                  dos.close();
//                  dis.close();
                  ois.close();
                  oos.close();
                  socket.close();

                  break;
               } catch (Exception ee) {
                  break;
               } // catch�� ��
            } // �ٱ� catch����

         }
      }
   }
   public void AppendText(String msg) {
      // textArea.append(msg + "\n");
      // AppendIcon(icon1);
      msg = msg.trim(); // �յ� blank�� \n�� �����Ѵ�.
      int len = textArea.getDocument().getLength();
      // ������ �̵�
      //textArea.setCaretPosition(len);
      //textArea.replaceSelection(msg + "\n");
      
      StyledDocument doc = textArea.getStyledDocument();
      SimpleAttributeSet left = new SimpleAttributeSet();
      StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
      StyleConstants.setForeground(left, Color.BLACK);
       doc.setParagraphAttributes(doc.getLength(), 1, left, false);
      try {
         doc.insertString(doc.getLength(), msg+"\n", left );
      } catch (BadLocationException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }
   
   public void SendObject(Object ob) { // ������ �޼����� ������ �޼ҵ�
      try {
         oos.writeObject(ob);
      } catch (IOException e) {
         // textArea.append("�޼��� �۽� ����!!\n");
         AppendText("SendObject Error");
      }
   }
}

//class MyMouse implements MouseListener{
//   @Override
//   public void mouseClicked(MouseEvent e) {
//      (Window) e.setVisible(false);
//   }
//   @Override
//      public void mouseExited(MouseEvent e){
//
//   } 
//   @Override
//   public void mouseReleased(MouseEvent e){
//
//   } 
//   @Override
//   public void mousePressed(MouseEvent e ) {
//      
//   }
//
//}