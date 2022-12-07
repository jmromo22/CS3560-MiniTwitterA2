public interface Visitable{

    public void AddFollowers( Visitable obs );

    public void NotifyFollowers( String tweet );
    
    public void AcceptMessages( Visitor visitor );

    public void AcceptPositiveMessages( Visitor visitor );

    public void UpdateTweets( String tweet );

    public void AcceptLastUpdate( Visitor visitor );

    
}
