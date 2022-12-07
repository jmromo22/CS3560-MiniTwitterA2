import javax.swing.tree.DefaultMutableTreeNode;

public class UserComposite extends DefaultMutableTreeNode implements Visitable{
    protected String userId;
    protected long creationTime;

    public UserComposite( String id ){
        setUserObject( id );
        this.userId = id;
        this.creationTime = System.currentTimeMillis();
    }
    public String GetId(){
        return userId;
    }
    public void AcceptPositiveMessages( Visitor visitor ){  
    }
    public void NotifyFollowers( String tweet ){   
    }
    public void AddFollowers( Visitable obs ){  
    }
    public void AcceptMessages( Visitor visitor ){  
    }
    public void UpdateTweets( String tweet ){
    }
    
    public void AcceptLastUpdate( Visitor visitor ){
        //Default function, to be overridden when needed
    }

    public long GetCreationTime(){
        return creationTime;
    }
    
}
