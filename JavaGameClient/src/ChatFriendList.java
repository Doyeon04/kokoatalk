// ChatFriendList.java
import java.util.Arrays;
import javax.swing.*;
// Java Client 시작import java.awt.BorderLayout;
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

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTextArea;
public class ChatFriendList extends JFrame {
   ArrayList userNameList = new ArrayList();
   ArrayList<String> selectedNameList = new ArrayList<String>();
   ArrayList<String> sendSelectedNameList = new ArrayList<String>();
   ArrayList<ListPanel> userOneListPanel = new ArrayList<ListPanel>();
   private JPanel contentPane;
   private JTextField txtIpAddress;
   private JTextField txtPortNumber;

   /**
    * Launch the application.
    */
   private static final long serialVersionUID = 1L;
   
   
   private String UserName;
   private JButton btnSend;
   private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
   private Socket socket; // 연결소켓
   private InputStream is;
   private OutputStream os;
   private DataInputStream dis;
   private DataOutputStream dos;

   private ObjectInputStream ois;
   private ObjectOutputStream oos;
   JPanel contentPane_1;
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
   // 그려진 Image를 보관하는 용도, paint() 함수에서 이용한다.
   private Image panelImage = null; 
   private Graphics gc2 = null;
   
   private JLabel userListLabel;
   
   private JPanel userListPanel;
   
   private JButton testButton;
  int i=0;
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
      
     contentPane_1 = new JPanel();
      contentPane_1.setBackground(Color.WHITE);
      contentPane_1.setLayout(null);
      contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
      contentPane_1.setBounds(61, 0, 311, 485);
      contentPane.add(contentPane_1);
      
      JLabel FriendLabel = new JLabel("\uCE5C\uAD6C");
      FriendLabel.setFont(new Font("굴림", Font.BOLD, 18));
      FriendLabel.setBounds(23, 23, 76, 34);
      contentPane_1.add(FriendLabel);
      
      JLabel userNameLabel = new JLabel(username);
      userNameLabel.setFont(new Font("굴림", Font.BOLD, 14));
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
      friendNameLabel.setFont(new Font("굴림", Font.BOLD, 14));
      friendNameLabel.setBounds(77, 134, 68, 15);
      contentPane_1.add(friendNameLabel);
      
      class FriendAction extends MouseAdapter // 내부클래스로 액션 이벤트 처리 클래스
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
      
      chatPanel = new JPanel();//채팅창 목록 현재는 setvisible(false);
      chatPanel.setBounds(61, 0, 311, 485);
      chatPanel.setVisible(false);
      chatPanel.setBackground(Color.WHITE);
      chatPanel.setLayout(null);
      contentPane.add(chatPanel);
      
      JLabel chatLabel = new JLabel("\uCC44\uD305");
      chatLabel.setFont(new Font("굴림", Font.BOLD, 18));
      chatLabel.setBounds(23, 23, 76, 34);
      chatPanel.add(chatLabel);
      
      class ChatAction extends MouseAdapter // 내부클래스로 액션 이벤트 처리 클래스
      {
        
         public void mouseClicked(MouseEvent e) { // chat 아이콘 누르면 채팅창 목록 화면으로 가게 
            contentPane_1.setVisible(false);
            chatPanel.setVisible(true); // 채팅창 목록 화면 보여지게 함 
           // contentPane.add(chatPanel);
            //JLabel dd = new JLabel("dd?????");
            //dd.setSize(50,50);
            //dd.setLocation(150,150);
            //chatPanel.add(dd);
           }
     
      }
      ChatAction chatIconeve = new ChatAction();
      JLabel chatIconLabel = new JLabel(menuIcon2);
      chatIconPanel.add(chatIconLabel);
      chatIconPanel.addMouseListener(chatIconeve);//이 아이콘 누르면 채팅창 뜨도록
      
     
      //txtIpAddress.addActionListener(action);
      //txtPortNumber.addActionListener(action);


      try {
         socket = new Socket(ip_addr, Integer.parseInt(port_no));

         oos = new ObjectOutputStream(socket.getOutputStream());
         oos.flush();
         ois = new ObjectInputStream(socket.getInputStream());

         ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");//connect
         //현재 입장한 유저는 ㅇㅇㅇ 입니다.
         SendObject(obcm);//send
         EnterUserNetwork net = new EnterUserNetwork();
         net.start();
      


      } catch (NumberFormatException | IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         //AppendText("connect error");
      }
      
      
      
      class MyAction extends MouseAdapter // 내부클래스로 액션 이벤트 처리 클래스
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
      userListLabel.setBounds(68, 432, 178, 42);
      contentPane_1.add(userListLabel);
      
      userListPanel = new JPanel();
      userListPanel.setBounds(12, 183, 287, 238);
      userListPanel.setLayout(null);
      contentPane_1.add(userListPanel);
      
      
      class SendNameList extends MouseAdapter {
         //private static final String ListPanel = null;

      public void mouseClicked(MouseEvent e) { // 단톡방 초대하기 
            ChatMsg chatMsg=null;
            chatMsg = new ChatMsg(UserName,"500","send selected string list");
            chatMsg.i = i;
            selectedNameList.add(UserName); // 본인까지 추가함
            sendSelectedNameList=selectedNameList;
            chatMsg.setList(sendSelectedNameList);
            
           //System.out.println(chatMsg.getList());

            i++; // 아이디 증가
            
            SendObject(chatMsg);  // 단톡방에 초대된 유저들 리스트 서버로 보내기 
           
          
          // JLabel dd = new JLabel("dd?????");
           // dd.setSize(50,50);
           // dd.setLocation(150,150);
           //chatPanel.add(dd);
            
            String selectedNameListStr = String.join(",", selectedNameList);
            
            System.out.println("selectedNameList:"+ selectedNameList);
            System.out.println("selectedNameListStr:"+ selectedNameListStr);
            
            //contentPane.add(chatPanel);
            //패널 + 라벨 -> chatPanel에 붙이기
            JPanel oneChatRoomPanel = new JPanel(); // 유저 하나당 파넬
            oneChatRoomPanel.setLayout(null);
            oneChatRoomPanel.setSize(280,60);
            oneChatRoomPanel.setLocation(20,i*60);
            
            oneChatRoomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            
            JLabel oneChatRoomLabel = new JLabel(selectedNameListStr); // 유저 하나당 리스트
            oneChatRoomLabel.setSize(270,30);
            oneChatRoomLabel.setLocation(30,15);
            oneChatRoomPanel.add(oneChatRoomLabel);
            
            chatPanel.add(oneChatRoomPanel);
            
           
            
            
            
            sendSelectedNameList.clear();
            
            
           /* if(!UserName.equals(userListString[i])) {
                userOneLabel = new JLabel();
                userOneLabel.setText(userListString[i]);
                userOnePanel = new ListPanel(userOneLabel,j*60,userListString[i]);                                                                              
                userOnePanel.setBorder(BorderFactory.createLineBorder(Color.black));
                j++;
                userListPanel.add(userOnePanel);//위치 for문  돌면서 밑으로 내려가게 함. 
               userOneListPanel.add(userOnePanel);
                   
                
            }
            참고----*/
            
            //JavaGameClientView view = new JavaGameClientView(username, "127.0.0.1", "30000");
           // ChatRoom room = new ChatRoom(username, "127.0.0.1", "30000", socket);
            
            for(int j=0;j<userOneListPanel.size();j++) {
            userOneListPanel.get(j).getCheckBox().setSelected(false);
            
            }
            chatMsg=null;
            selectedNameList.clear();
          //  (ListPanel)userListPanel.getCompontent(0);
         }
         
      }
      
      testButton = new JButton("단톡방");
      testButton.setBounds(208, 149, 91, 23);
      contentPane_1.add(testButton);
      SendNameList sendName = new SendNameList();
      testButton.addMouseListener(sendName);
      
      
     
   }
  public class CheckAction implements ItemListener{
     String userName;
   public CheckAction(String name) {
      this.userName= name;
   }
     @Override
   public void itemStateChanged(ItemEvent e) {
      // TODO Auto-generated method stub
      switch(e.getStateChange()) {
      case 1:
         //선택이 됐으면 선택된 사람의 이름 보내기
         selectedNameList.add(userName);
         System.out.println(selectedNameList);
         break;
      case 2:
         selectedNameList.remove(userName);
         System.out.println(selectedNameList);
         break;
      }
   }
     
  }
  public class PicturePanel extends JPanel{
     public PicturePanel() {
        ImageIcon icon = new ImageIcon("src/basicProfileImg.jpg");
         Image img = icon.getImage();
         Image changeImg = img.getScaledInstance(41,41, Image.SCALE_SMOOTH);
         ImageIcon changeIcon = new ImageIcon(changeImg);
         JLabel picturelabel = new JLabel(changeIcon);
         
         this.add(picturelabel);
         
     }
     
  }
  public class ListPanel extends JPanel{
           JCheckBox checkBox;
         public ListPanel(JLabel userNameLabel,int y,String name) {
            this.setLayout(new GridLayout(1,3));
            PicturePanel picturePanel = new PicturePanel();
            
            this.add(picturePanel);
            picturePanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                  System.out.println("asf");            
               }            
            });
            this.add(userNameLabel);
            this.setSize(280,60);
            this.setLocation(0,y);
            
            this.checkBox = new JCheckBox();
            CheckAction checkAction= new CheckAction(name);
            checkBox.addItemListener(checkAction);
            this.add(checkBox);
         }
         public JCheckBox getCheckBox() {
            return this.checkBox;
         }
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
                    
             
                         break;
                         
                     case "101": // 유저 리스트 받았다면
                        userListLabel.setText(cm.data);
                        //String userListStr = cm.data;
                        //String userListStr = "user1, user2, user3, user4";
                       // System.out.println(cm.data);
                        String[] userListString= cm.data.split(",");
                        
                      //  System.out.println(Arrays.toString(userListString));
                        
                               ListPanel userOnePanel;
                                 JLabel userOneLabel;
                                 userListPanel.removeAll();
//                                 userListPanel.revalidate();
//                                 userListPanel.repaint();
                                 int j=0;
                                 for (int i=0; i<userListString.length; i++) {
                                   // System.out.println(userListString[i]);
                                    if(!UserName.equals(userListString[i])) {
                                        userOneLabel = new JLabel();
                                        userOneLabel.setText(userListString[i]);
                                        userOnePanel = new ListPanel(userOneLabel,j*60,userListString[i]);                                                                              
                                        userOnePanel.setBorder(BorderFactory.createLineBorder(Color.black));
                                        j++;
                                        userListPanel.add(userOnePanel);//위치 for문  돌면서 밑으로 내려가게 함. 
                                       userOneListPanel.add(userOnePanel);
                                           
                                        
                                    }
                                   
                                }
                                 userListPanel.revalidate();
                                 userListPanel.repaint();
                       
                       break;
                       
                     case "550":
                    	 System.out.println("단톡방 유저들"+cm.data);
                    	 
                    	 String[] invitedUsersArr= cm.data.split(",");
                    	 
                    	 // invitedUsersArr: a,b,c
                    	 // username: a
                    	 
                    	 for(int i=0; i<invitedUsersArr.length; i++) {
                    		 if(UserName.equals(invitedUsersArr[i])) {
                    			 System.out.println("맞음"+invitedUsersArr[i]);
                    		 }
                    	 }
                     		
                    	 break;
                       
     
                        
                  
            /*case "200": // chat message
               if (cm.UserName.equals(UserName))
                  //AppendTextR(msg); // 내 메세지는 우측에
               else
                  //AppendText(msg);
               break;
            case "300": // Image 첨부
               if (cm.UserName.equals(UserName))
                  //AppendTextR("[" + cm.UserName + "]");
               else
                  //AppendText("[" + cm.UserName + "]");
               //AppendImage(cm.img);
               break;
            case "500": // Mouse Event 수신
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
               } // catch문 끝
            } // 바깥 catch문끝

         }
      }
   }
   public void AppendText(String msg) {
      // textArea.append(msg + "\n");
      // AppendIcon(icon1);
      msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
      int len = textArea.getDocument().getLength();
      // 끝으로 이동
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
   
   public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
      try {

         oos.writeObject(ob);
         oos.reset();
      } catch (IOException e) {
         // textArea.append("메세지 송신 에러!!\n");
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