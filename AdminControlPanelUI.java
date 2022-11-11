import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.*;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeModel;
import java.util.*;

public class AdminControlPanelUI implements ActionListener,Visitor{

    //Singleton Pattern
    //Positive messages
    private static final String[] positiveWords = { "good", "great", "excellent", "amazing", "wonderful"};
    private static AdminControlPanelUI adminManager;
    
    private Group root;
    private HashMap<String,Visitable> users;
    private UserComposite selectedUser;
    private int totalMessages, NumUsers, NumGroups, positiveTotal;

    private JFrame mainFrame;
    private JTextArea consoleOutput;
    private JTextField userIdInput, groupIdInput;
    private JButton userIdButton, groupIdButton, openUserButton, totalUserButton, totalgroupButton, totalMessageButton, positiveMessageButton;
    private JTree userList;
    private DefaultTreeModel userListModel;

    //User Interface of the Control Panel
    private AdminControlPanelUI(){
        users = new HashMap<String,Visitable>();
        NumUsers = 0;
        NumGroups = 1;

        mainFrame = new JFrame( "Admin Control Panel" );
        consoleOutput = new JTextArea( "Welcome to Mini-Twitter Everyone!\n" );
        consoleOutput.setBounds( 405, 100, 370, 290 );
        
        mainFrame.add( consoleOutput );
        
        userIdInput = new JTextField( "Enter UserId" );
        groupIdInput = new JTextField( "Enter GroupId" );
        
        userIdInput.setBounds( 400, 10, 260, 20 );
        userIdInput.setEditable(true);
        
        groupIdInput.setBounds( 400, 40, 260, 20 );
        groupIdInput.setEditable( true );
        
        mainFrame.add( userIdInput );
        mainFrame.add( groupIdInput );
       

        //Buttons required for the UI
        userIdButton = new JButton( "Add User" );
        groupIdButton = new JButton( "Add Group" );
        openUserButton = new JButton( "Open User View" );
        totalUserButton = new JButton( "Show Total User" );
        totalgroupButton = new JButton( "Show Total Group" );
        totalMessageButton = new JButton( "Show Total Messages" );
        positiveMessageButton = new JButton( "Show Positive Percentage" );
       
        //Dimensions of the buttons.
        userIdButton.setBounds( 670, 10, 100, 20 );
        groupIdButton.setBounds( 670, 40, 100, 20 );
       
        openUserButton.setBounds( 400, 70, 370, 20 );
        
        totalUserButton.setBounds( 400, 400, 180, 20 );
        totalgroupButton.setBounds( 595, 400, 180, 20 );
        totalMessageButton.setBounds( 400, 430, 180, 20 );
        
        positiveMessageButton.setBounds( 595, 430, 180, 20 );
        
        userIdButton.addActionListener( this );
        groupIdButton.addActionListener( this );
        openUserButton.addActionListener( this );
        totalUserButton.addActionListener( this );
        totalgroupButton.addActionListener( this );
        totalMessageButton.addActionListener( this );
        positiveMessageButton.addActionListener( this );
       
        mainFrame.add( userIdButton );
        mainFrame.add( groupIdButton );
        mainFrame.add( openUserButton );
        mainFrame.add( totalUserButton );
        mainFrame.add( totalgroupButton );
        mainFrame.add( totalMessageButton );
        mainFrame.add( positiveMessageButton );
       
        //Root is where the tree of groups and users begins from
        root = new Group( "root" );
        selectedUser = root;
        userList = new JTree( root );
        userListModel = (DefaultTreeModel)userList.getModel();
        userList.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION );
        userList.setBounds( 10, 10, 385, 440 );
        mainFrame.add( userList );
        mainFrame.setSize( 800,500 );
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

    public static AdminControlPanelUI Start(){
        if( adminManager == null ){
            adminManager = new AdminControlPanelUI();
        }
        return adminManager;
    }

    public User GetUser( String id ){
        return (User)users.get( id );
    }

    //Prints out # of Users.
    public void GetUserTotal(){
        consoleOutput.append( "There are " + NumUsers + " users!\n" );
        consoleOutput.update( consoleOutput.getGraphics() );
    }

    //Prints out the # of groups
    public void GetGroupTotal(){
        consoleOutput.append( "There are " + NumGroups + " groups!\n" );
        consoleOutput.update( consoleOutput.getGraphics() );
    }

    //Prints out the total # of messages
    public void GetMessagesTotal(){
        totalMessages = 0;
        for( Visitable user: users.values() ){
            user.AcceptMessages( this );
        }
        consoleOutput.append( "There are " + totalMessages + " messages!\n" );
        consoleOutput.update( consoleOutput.getGraphics() );
    }

    //Prints out the positive messages.
    public void GetPositiveTotal(){
        positiveTotal = 0;
        totalMessages = 0;
        for( Visitable user: users.values() ){
            user.AcceptPositiveMessages( this );
        }
        if( totalMessages == 0 ){
            totalMessages = 1; //To prevent divide by 0 error
        }
        consoleOutput.append( ( (double)positiveTotal/totalMessages * 100 ) + " of the messages are positive!\n" );
        consoleOutput.update( consoleOutput.getGraphics() );
    }

    public void VisitMessages( User user ){
        totalMessages += user.GetMyTweets().size();
    }

    public void VisitPositiveMessages( User user ){
        ArrayList<String> tweets = user.GetMyTweets();
        totalMessages += tweets.size();
        for( String tweet: tweets ){
            for( String positive: positiveWords ){
                if( tweet.toLowerCase().contains( positive ) ){
                    positiveTotal++;
                    break;
                }
            }
        }
    }

    public void actionPerformed( ActionEvent e ){
        if( e.getSource() == userIdButton ){
            String userId = userIdInput.getText();
           
            if( users.containsKey( userId ) )
                return;
            User user = new User( userId );
            try{
                Group selectedUser = (Group)userList.getLastSelectedPathComponent();
                
                if( selectedUser == null )
                    return;
                selectedUser.add( user );
                users.put( user.GetId(), user );
                userListModel.reload( selectedUser );
                ++NumUsers;
            } catch ( Exception ex ){}
        }
        else if( e.getSource() == groupIdButton ){
            Group user = new Group( groupIdInput.getText() );
            try{
                Group selectedUser = (Group)userList.getLastSelectedPathComponent();
                if( selectedUser == null )
                    return;
                selectedUser.add( user );
                users.put( user.GetId(), user );
                userListModel.reload( selectedUser );
                ++NumGroups;
            } catch ( Exception ex ){}
        }
        else if( e.getSource() == openUserButton ){
            try{
                UserWindow newUserview = new UserWindow( (User)userList.getLastSelectedPathComponent(), this );
            } catch ( Exception ex ){}
        }
        else if( e.getSource() == totalUserButton ){
            GetUserTotal();
        }
        else if( e.getSource() == totalgroupButton ){
            GetGroupTotal();
        }
        else if( e.getSource() == totalMessageButton ){
            GetMessagesTotal();
        }
        else if( e.getSource() == positiveMessageButton ){
            GetPositiveTotal();
        }

    }
}