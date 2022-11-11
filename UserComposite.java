import javax.swing.tree.DefaultMutableTreeNode;

public class UserComposite extends DefaultMutableTreeNode implements Visitable{
    protected String userId;

    public UserComposite( String id ){
        setUserObject( id );
        this.userId = id;
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
    
}
