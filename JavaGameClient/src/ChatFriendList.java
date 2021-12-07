
// ChatFriendList.java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	ArrayList<String> userNameList = new ArrayList<String>();
	Map<String, PicturePanel> userPicturePanel = new HashMap<String, PicturePanel>();
	ArrayList<String> selectedNameList = new ArrayList<String>();
	ArrayList<String> sendSelectedNameList = new ArrayList<String>();
	ArrayList<ListPanel> userOneListPanel = new ArrayList<ListPanel>();
	// Map<Integer,ArrayList<String>> myMultiChat= new HashMap<>();
	ArrayList<MultiChat> myMultiChat = new ArrayList<MultiChat>();
	ArrayList<OneChatRoomPanel> oneChatRoomPanelList = new ArrayList<OneChatRoomPanel>();
	private JPanel contentPane;
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;

	public ChatFriendList chatFriendList;
	public MultiChat multiChat;

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
	private int i = 0;
	private int myRoomNum = 0;

	private ImageIcon icon; // 프로필 이미지

	public JPanel userImgPanel; // 내 프로필 이미지 패널
	public JLabel userImgLabel; // 내 프로필 이미지 라벨

	// private JCheckBox checkBox;

	// public ShowInvitedFriendsList showInvitedFriendsList;

	/**
	 * Create the frame.
	 */
	public ChatFriendList(String username, String ip_addr, String port_no) {
		chatFriendList = this;

		this.UserName = username;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 600);
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

		userImgPanel = new JPanel(); // 내 프로필 사진 패널
		userImgPanel.setBackground(Color.WHITE);
		userImgPanel.setBounds(23, 66, 42, 42);
		contentPane_1.add(userImgPanel);

		icon = new ImageIcon("src/basicProfileImg.jpg");

		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		userImgLabel = new JLabel(changeIcon);

		userImgPanel.add(userImgLabel);

		// JPanel friendImgPanel = new JPanel();
		// friendImgPanel.setBackground(Color.WHITE);
		// friendImgPanel.setBounds(23, 119, 42, 42);

		ImageIcon friendIcon = new ImageIcon("src/basicProfileImg.jpg");
		JLabel friendIconlabel = new JLabel(friendIcon);
		// friendImgPanel.add(friendIconlabel);

		// contentPane_1.add(friendImgPanel);

		JLabel friendNameLabel = new JLabel("");
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

		chatPanel = new JPanel();// 채팅창 목록 현재는 setvisible(false);
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

			}

		}
		ChatAction chatIconeve = new ChatAction();
		JLabel chatIconLabel = new JLabel(menuIcon2);
		chatIconPanel.add(chatIconLabel);
		chatIconPanel.addMouseListener(chatIconeve);// 이 아이콘 누르면 채팅창 뜨도록

		// txtIpAddress.addActionListener(action);
		// txtPortNumber.addActionListener(action);

		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");// connect
			// 현재 입장한 유저는 ㅇㅇㅇ 입니다.
			SendObject(obcm);// send
			EnterUserNetwork net = new EnterUserNetwork();
			net.start();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// AppendText("connect error");
		}

		// friendImgPanel.addMouseListener(my);
		JLabel label_1 = new JLabel((Icon) null);
		// friendImgPanel.add(label_1);

		// userListLabel = new JLabel("");
		// userListLabel.setBounds(68, 432, 178, 42);
		// contentPane_1.add(userListLabel);

		userListPanel = new JPanel();
		userListPanel.setBackground(Color.WHITE);
		userListPanel.setBounds(12, 183, 287, 273);
		userListPanel.setLayout(null);
		contentPane_1.add(userListPanel);

		JPanel whitePanel = new JPanel();
		whitePanel.setBounds(219, 0, 68, 273);
		userListPanel.add(whitePanel);
		whitePanel.setVisible(false);

		class SendNameList extends MouseAdapter {
			// private static final String ListPanel = null;

			public void mouseClicked(MouseEvent e) { // 단톡방 초대하기
				ChatMsg chatMsg = null;
				chatMsg = new ChatMsg(UserName, "500", "send selected string list");
				chatMsg.i = i;
				selectedNameList.add(UserName); // 본인까지 추가함
				sendSelectedNameList = selectedNameList;
				chatMsg.setList(sendSelectedNameList);

				// System.out.println(chatMsg.getList());

				i++; // 아이디 증가

				SendObject(chatMsg); // 단톡방에 초대된 유저들 리스트 서버로 보내기

				String selectedNameListStr = String.join(",", selectedNameList);

				sendSelectedNameList.clear();

				for (int j = 0; j < userOneListPanel.size(); j++) {
					userOneListPanel.get(j).getCheckBox().setSelected(false);

				}
				chatMsg = null;
				selectedNameList.clear();
				// (ListPanel)userListPanel.getCompontent(0);
			}

		}

		ImageIcon inviteImg = new ImageIcon("src/newRoomImg.png");
		testButton = new JButton(inviteImg);
		testButton.setBorderPainted(false);
		testButton.setContentAreaFilled(false);
		testButton.setFocusPainted(false);
		testButton.setBounds(253, 150, 30, 30);
		contentPane_1.add(testButton);
		SendNameList sendName = new SendNameList();
		testButton.addMouseListener(sendName);

		class ShowCheckBox extends MouseAdapter {
			// private static final String ListPanel = null;

			public void mouseClicked(MouseEvent e) { // 체크박스 보이게
				// checkBox.setVisible(true);
				whitePanel.setVisible(false);

			}

		}

		JButton checkboxVisibleBtn = new JButton("\uCD08\uB300\uD558\uAE30");
		checkboxVisibleBtn.setBounds(104, 150, 91, 23);
		contentPane_1.add(checkboxVisibleBtn);

		JLabel onlineFriendLabel = new JLabel("\uC811\uC18D\uC790");
		onlineFriendLabel.setFont(new Font("굴림", Font.BOLD, 14));
		onlineFriendLabel.setBounds(23, 134, 76, 34);
		contentPane_1.add(onlineFriendLabel);

		JButton changeProfileBtn = new JButton("\uD504\uB85C\uD544 \uBCC0\uACBD");
		//changeProfileBtn.setBorderPainted(false);
		changeProfileBtn.setContentAreaFilled(false);
		changeProfileBtn.setFocusPainted(false);
		changeProfileBtn.setBounds(197, 85, 102, 23);
		contentPane_1.add(changeProfileBtn);
		

		class RefreshProfile extends MouseAdapter { // 선택한 프사를 보내기

			public void mouseClicked(MouseEvent e) { // 단톡방 초대하기
				System.out.println("새로고침 누름!!");
				refreshPanel();
			}

		}

		RefreshProfile refreshProfile = new RefreshProfile();

		class SelectProfile extends MouseAdapter { // 선택한 프사를 보내기

			public void mouseClicked(MouseEvent e) { // 단톡방 초대하기
				System.out.println("프사 바꾸기 버튼 선택");

				repaint();

				if (e.getSource() == changeProfileBtn) {
					frame = new Frame("이미지첨부");
					fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
					// frame.setVisible(true);
					// fd.setDirectory(".\\");
					fd.setVisible(true);
					// System.out.println(fd.getDirectory() + fd.getFile());
					if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
						ChatMsg obcm = new ChatMsg(username, "700", "IMG"); // 코드 700과 함께 선택한 프로필 이미지 보냄
						ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
						System.out.println(fd.getDirectory() + fd.getFile());
						obcm.img = img;

						// refreshPanel();
						refreshMyProfile(fd.getDirectory() + fd.getFile()); // 자기 프로필 사진 업데이트 
						SendObject(obcm);

						
					}
				}
			}

		}

		SelectProfile selectProfile = new SelectProfile();
		changeProfileBtn.addMouseListener(selectProfile);

		ShowCheckBox showCheckBox = new ShowCheckBox();
		checkboxVisibleBtn.addMouseListener(showCheckBox);
		checkboxVisibleBtn.setVisible(false);

	}

	public void setMyImg(String img) {
	
		userImgPanel.revalidate();
		userImgPanel.repaint();
		userImgLabel.revalidate();
		userImgLabel.repaint();
		
		userImgPanel.remove(userImgLabel);
		revalidate();
		repaint();
		
		// ImageIcon icon1 = new ImageIcon("src/profilesPakage/"+UserName+".jpg");
		 ImageIcon icon1 = new ImageIcon(img);
		 Image img2 = icon1.getImage();
		Image changeImg1 = img2.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
		ImageIcon changeIcon1 = new ImageIcon(changeImg1);
			// JLabel picturelabel2 = new JLabel(changeIcon1);
			userImgLabel.setIcon(changeIcon1);
			userImgPanel.add(userImgLabel);
		// userImgPanel.setVisible(false);
		System.out.println(UserName + " 내이미지 바꾸기!!");

	}

	public void refreshMyProfile(String img) { // 내 프로필 사진 업데이트

		////this.revalidate();
		//this.repaint();
		setMyImg(img);

	}

	public void refreshPanel() { // a,b,c
		for (int i = 0; i < userNameList.size(); i++) {
			
			String eachUserName = userNameList.get(i);// arraylist get(0),get(1) => 0번째에 저장된 username
			
			System.out.println("나는 "+UserName+"그리고 eachUserName:"+eachUserName);
			
			if (eachUserName.equals(UserName))
				continue;
			userPicturePanel.get(userNameList.get(i)).setImg(eachUserName);// map key:string(username)
																			// value:picturepanel

		} // 나자신의 프로필 사진을 갱신
		this.revalidate();
		this.repaint();
	}

	public class CheckAction implements ItemListener {
		String userName;

		public CheckAction(String name) {
			this.userName = name;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			switch (e.getStateChange()) {
			case 1:
				// 선택이 됐으면 선택된 사람의 이름 보내기
				selectedNameList.add(userName);
				break;
			case 2:
				selectedNameList.remove(userName);

				break;
			}
		}

	}

	public class PicturePanel extends JPanel { // 프로필 사진 붙이기
		JLabel picturelabel;

		public PicturePanel(String name) {
			// revalidate();
			// repaint();

			// revalidate();
			// \src\profilesPakage\v.jpg
			// ImageIcon icon = new ImageIcon("src/basicProfileImg.jpg");
			ImageIcon icon = new ImageIcon("src/profilesPakage/" + name + ".jpg");
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			picturelabel = new JLabel(changeIcon);
			this.add(picturelabel); // 파넬에 label을 붙임
			userPicturePanel.put(name, this); // 이름과 파넬을 넣음
			// System.out.println("userPicturePanel: " + userPicturePanel);
			System.out.println("나는+"+UserName+" userpicturepanel 맵을 출력해보자:"+userPicturePanel);

		}

		public void setImg(String eachUserName) {

			this.remove(picturelabel);
			this.revalidate();

			// ImageIcon icon1 = new ImageIcon("src/icon1.jpg");
			ImageIcon icon1 = new ImageIcon("src/profilesPakage/" + eachUserName + ".jpg");
			System.out.println("src/profilesPakage/" + eachUserName + ".jpg");
			Image img2 = icon1.getImage();
			Image changeImg1 = img2.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
			ImageIcon changeIcon1 = new ImageIcon(changeImg1);
			// JLabel picturelabel2 = new JLabel(changeIcon1);
			picturelabel.setIcon(changeIcon1);
			this.add(picturelabel);

		}

	}

	class ChatPanelEvent extends MouseAdapter {
		int multiChatNum;

		public ChatPanelEvent(int multiChatNum) {
			this.multiChatNum = multiChatNum;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			for (int i = 0; i < myMultiChat.size(); i++) {
				if (myMultiChat.get(i).multiNum == multiChatNum) {
					myMultiChat.get(i).setVisible(true);
					System.out.println("멀티채팅번호" + multiChatNum);
				}
			}
		}
	}

	public class OneChatRoomPanel extends JPanel {
		int multiChatNum;
		MultiChat multiChat;

		public OneChatRoomPanel(int multiChatNum, MultiChat multiChat) {
			this.multiChatNum = multiChatNum;
			ChatPanelEvent chatPanelEvent = new ChatPanelEvent(multiChatNum);
			this.addMouseListener(chatPanelEvent);
		}

		int getMultiChatNum() {
			return multiChatNum;
		}

	}

	public class ListPanel extends JPanel {
		JCheckBox checkBox;
		PicturePanel picturePanel;

		public ListPanel(JLabel userNameLabel, int y, String name) { // 프로필 사진, 이름이 있는 한 줄
			this.setLayout(new GridLayout(1, 3));
			picturePanel = new PicturePanel(name);
			picturePanel.revalidate();
			picturePanel.repaint();
			picturePanel.setBackground(Color.WHITE);
			this.setBackground(Color.WHITE);
			// checkBox.setBackground(Color.WHITE);
			this.add(picturePanel);

			picturePanel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

				}
			});
			this.add(userNameLabel);
			this.setSize(280, 60);
			this.setLocation(0, y);

			this.checkBox = new JCheckBox();
			this.checkBox.setBackground(Color.WHITE);
			// checkBox = new JCheckBox();
			checkBox.setVisible(true);
			CheckAction checkAction = new CheckAction(name);
			checkBox.addItemListener(checkAction);
			this.add(checkBox);
		}

		public JCheckBox getCheckBox() {
			return this.checkBox;
			// return checkBox;
		}
	}
	
	public void saveProfileInClient(String img, String userName)
    {
  	  
        File oldFile = new File(img);
        //File newFile = new File("C:\\Users\\user\\Desktop\\KoKoaTalkProject\\JavaGameClient\\src\\profilesPakage\\"+userName+".jpg");
        //File newFile = new File("./"+userName+".jpg");
        //File newFile = new File("../JavaGameClient/src/profilesPakage/"+userName+".jpg");
        File newFile = new File("./src/profilesPakage/"+userName+".jpg");


        try {
            FileInputStream input = new FileInputStream(oldFile);
            FileOutputStream output = new FileOutputStream(newFile);


            byte[] buf = new byte[2048];

            int read;

            while((read = input.read(buf)) > 0)
            {
                output.write(buf, 0, read);
            }

            input.close();
            output.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
						System.out.println("코드101. 유저 리스트 받음+유저 리스트:" + cm.data);
						userNameList = cm.al;

						System.out.println(userNameList);
						String[] userListString = cm.data.split(",");

						// ShowInvitedFriendsList showInvitedFriendsList = new
						// ShowInvitedFriendsList(cm.data, UserName);

						ListPanel userOnePanel;
						JLabel userOneLabel;
						userListPanel.removeAll();
						userOneListPanel.clear();

						int j = 0;
						for (int i = 0; i < userListString.length; i++) {
							// System.out.println(userListString[i]);
							if (!UserName.equals(userListString[i])) {
								userOneLabel = new JLabel();

								userOneLabel.setText(userListString[i]);
								userOneLabel.setBackground(Color.WHITE);
								userOnePanel = new ListPanel(userOneLabel, j * 60, userListString[i]);
								userOnePanel.setBackground(Color.WHITE);
								// userOnePanel.setBorder(BorderFactory.createLineBorder(Color.black));
								j++;
								userListPanel.add(userOnePanel);// 위치 for문 돌면서 밑으로 내려가게 함.
								userOneListPanel.add(userOnePanel);

							}

						}
						userListPanel.revalidate();
						userListPanel.repaint();

						break;

					case "550": //

						String[] invitedUsersArr = cm.data.split(",");

						// invitedUsersArr: a,b,c
						// username: a

						multiChat = new MultiChat(UserName, cm.multiChatNum, invitedUsersArr, chatFriendList);

						myMultiChat.add(multiChat);
						for (int i = 0; i < myMultiChat.size(); i++) {
							System.out.print(myMultiChat.get(i).multiNum + " ");
						}
						System.out.println("패널리스트");

						OneChatRoomPanel oneChatRoomPanel = new OneChatRoomPanel(cm.multiChatNum, multiChat);
						for (int i = 0; i < oneChatRoomPanelList.size(); i++) {
							System.out.print(oneChatRoomPanelList.get(i).getMultiChatNum() + " ");
						}
						System.out.println("");
						for (int i = 0; i < invitedUsersArr.length; i++) { // 초대된 유저들을 돌면서
							if (UserName.equals(invitedUsersArr[i])) { // 자기 자신이 그 중 포함되어 있으면 단톡방을 추가한다.
								myRoomNum++;

								// 유저 하나당 파넬
								oneChatRoomPanel.setLayout(null);
								oneChatRoomPanel.setSize(280, 60);
								oneChatRoomPanel.setLocation(20, myRoomNum * 60);

								oneChatRoomPanel.setBorder(BorderFactory.createLineBorder(Color.black));

								String invitedUsersStr = String.join(", ", invitedUsersArr);

								JLabel oneChatRoomLabel = new JLabel(invitedUsersStr); // 유저 하나당 리스트
								oneChatRoomLabel.setSize(270, 30);
								oneChatRoomLabel.setLocation(30, 15);
								oneChatRoomPanel.add(oneChatRoomLabel);
								oneChatRoomPanel.setBackground(Color.WHITE);

								chatPanel.add(oneChatRoomPanel);
								break;
							}

						}
						oneChatRoomPanelList.add(oneChatRoomPanel);

						// MultiChat multiChat = new MultiChat(UserName,
						// cm.multiChatNum,invitedUsersArr,this);
						// number랑 list 전송
						// oneChatPanel에다가 addevent해서 누르면 multichat frame이 생성되도록

						break;
					case "210": // 단톡방에서 보낸 메시지가 서버를 통해 도착.
						System.out.println("chtFriendList 클래스 안 도착" + cm.data);
						// cm.multiChatNum로 구분해서 multiChat로 전송
						for (int i = 0; i < myMultiChat.size(); i++) {// 유저가 가지고 있는 멀티챗방중에 어떤 챗방에서 메시지가 온건지 확인
							MultiChat findMultiRoom = myMultiChat.get(i);
							if (findMultiRoom.getMultiNum() == cm.multiChatNum) {
								if (cm.UserName.equals(UserName)) {
									System.out.println("cm.data:" + cm.data);
									findMultiRoom.AppendTextR(cm.data); // 내 메세지는 우측에
								} else {
									findMultiRoom.AppendText(cm.data);
								}

							}
						}

						break;
					case "300": // Image 첨부

						// Image image = cm.img.getImage();

						// image.getScaledInstance(40,40,Image.SCALE_SMOOTH);
						// cm.img.setImage(image);

						for (int i = 0; i < myMultiChat.size(); i++) {// 유저가 가지고 있는 멀티챗방중에 어떤 챗방에서 메시지가 온건지 확인
							MultiChat findMultiRoom = myMultiChat.get(i);
							if (findMultiRoom.getMultiNum() == cm.multiChatNum) {

								if (cm.UserName.equals(UserName)) {
									findMultiRoom.AppendTextR("[" + cm.UserName + "]");
									findMultiRoom.AppendImage(cm.img);
								} else {
									findMultiRoom.AppendText("[" + cm.UserName + "]");
									findMultiRoom.AppendImage(cm.img);
								}
							}
						}

						break;

					case "700":
						
						
						refreshPanel();
					
						//refreshMyProfile();

						break;
						
					case "750":
						System.out.println("클라이언트에서 750으로 받은 프로필!"+cm.img);
						saveProfileInClient(cm.img.toString(), cm.UserName);
						refreshPanel();
						break;

					/*
					 * case "200": // chat message if (cm.UserName.equals(UserName))
					 * //AppendTextR(msg); // 내 메세지는 우측에 else //AppendText(msg); break; case "300":
					 * // Image 첨부 if (cm.UserName.equals(UserName)) //AppendTextR("[" + cm.UserName
					 * + "]"); else //AppendText("[" + cm.UserName + "]"); //AppendImage(cm.img);
					 * break; case "500": // Mouse Event 수신 //DoMouseEvent(cm); break;
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
		// textArea.setCaretPosition(len);
		// textArea.replaceSelection(msg + "\n");

		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.BLACK);
		doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		try {
			doc.insertString(doc.getLength(), msg + "\n", left);
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