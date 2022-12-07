import java.util.*;
import javax.swing.*;

//
public class User extends UserComposite{
    private long lastUpdateTime;
    private ArrayList<String> tweets;
    private ArrayList<Visitable> followers;
    private DefaultListModel<User> following;
    private DefaultListModel<String> tweetFeed;

    public User( String id ){
        super( id );
        lastUpdateTime = GetCreationTime();
        following = new DefaultListModel<User>();
        followers = new ArrayList<Visitable>();
        
        tweets = new ArrayList<String>();
        tweetFeed = new DefaultListModel<String>();
       
        followers.add( this );
    }
    public void AddFollowing( User user ){
        following.addElement( user );
    }
    public void AddFollowers( Visitable obs ){
        followers.add( obs );
    }
    public void Tweet( String tweet ){
        tweets.add( tweet );
        NotifyFollowers( tweet );
    }
    public void NotifyFollowers( String tweet ){
        for( Visitable user: followers ){
            user.UpdateTweets( tweet );
        }
    }
    public void UpdateTweets( String tweet ){
        lastUpdateTime = System.currentTimeMillis();
        tweetFeed.addElement( tweet );
    }
    public DefaultListModel GetFollowing(){
        return following;
    }
    public DefaultListModel GetTweets(){
        return tweetFeed;
    }
    public ArrayList<String> GetMyTweets(){
        return tweets;
    }

    public long GetLastUpdate(){
        return lastUpdateTime;
    }

    public void AcceptMessages( Visitor visitor ){
        visitor.VisitMessages( this );
    }
    public void AcceptPositiveMessages( Visitor visitor ){
        visitor.VisitPositiveMessages( this );
    }
    public void AcceptLastUpdate( Visitor visitor ){
        visitor.VisitLastUpdate( this );
    }
}
