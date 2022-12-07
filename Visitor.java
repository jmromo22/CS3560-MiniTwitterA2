public interface Visitor{
    public void VisitMessages( User user );
    public void VisitPositiveMessages( User user );
    public void VisitLastUpdate( User user );
}
