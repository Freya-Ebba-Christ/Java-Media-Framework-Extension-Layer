package datasource;
import javax.media.*;
import javax.media.protocol.*;

public class CloneableDataSource{
    
    private DataSource cloneableDataSource = null;
    
    public CloneableDataSource() {
    }
    
    public void setDataSource(DataSource aDataSource){
        cloneableDataSource = Manager.createCloneableDataSource(aDataSource);
    }
    
    public DataSource getCloneableDataSource(){
        return cloneableDataSource;
    }
    
    public DataSource getClone(){
        return ((SourceCloneable)cloneableDataSource).createClone();
    }
}