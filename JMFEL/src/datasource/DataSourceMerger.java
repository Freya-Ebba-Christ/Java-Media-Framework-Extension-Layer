package datasource;
import javax.media.*;
import javax.media.protocol.*;
import java.util.Vector;

public class DataSourceMerger{
    
    private static DataSource[] dataSourceArray;
    private static DataSource mergedDataSource;
    
    public DataSourceMerger() {
    }
    
    public static DataSource mergeDataSources(Vector dataSources){
        dataSourceArray = new DataSource[dataSources.size()];
        dataSourceArray = (DataSource[])(dataSources.toArray(dataSourceArray));
      
        Vector muxes = PlugInManager.getPlugInList(null, null, PlugInManager.MULTIPLEXER);
        for (int i = 0; i < muxes.size(); i++) {
            String cname = (String)muxes.elementAt(i);
            if (cname.equals("com.sun.media.multiplexer.RawBufferMux")) {
                muxes.removeElementAt(i);
                break;
            }
        }
        PlugInManager.setPlugInList(muxes, PlugInManager.MULTIPLEXER);
 
        try {
            mergedDataSource = Manager.createMergingDataSource(dataSourceArray);
        } catch (IncompatibleSourceException ise) {
            System.out.println(ise);
            return null;
        }
        return mergedDataSource;
    }
}