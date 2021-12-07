/*import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ChatFriendList.CheckAction;
import ChatFriendList.ListPanel;
import ChatFriendList.PicturePanel;

import java.awt.BorderLayout;

public class ShowInvitedFriendsList extends JFrame{
	
	private final JPanel allUserPanel = new JPanel();
	 ArrayList<ListPanel> userOneListPanel = new ArrayList<ListPanel>();
	 
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
	  
	  
	 public class ListPanel extends JPanel{
         JCheckBox checkBox;
       public ListPanel(JLabel userNameLabel,int y,String name) {
          this.setLayout(new GridLayout(1,3));
          PicturePanel picturePanel = new PicturePanel();
          
          this.add(picturePanel);
			
			 picturePanel.addMouseListener(new MouseAdapter() { public void
			 mouseClicked(MouseEvent e) { System.out.println("asf"); } });
			 
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
	 
	
	public ShowInvitedFriendsList(String data, String UserName) {
		allUserPanel.setBackground(Color.WHITE);
		getContentPane().add(allUserPanel, BorderLayout.CENTER);
		
		 String[] userListString= data.split(",");
		 
         ListPanel userOnePanel;
         JLabel userOneLabel;
         userListPanel.removeAll();

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
		
	
	}

}
*/
