/*
 * AbstractDatabaseSinkHandler.java
 *
 * Created on 17. Juli 2007, 13:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package datasink.database;

import datasink.delimited.AbstractFileDataSourceHandler;
import java.sql.*;
import javax.media.MediaLocator;

/**
 *
 * @author Administrator
 */
public abstract class AbstractDatabaseSourceHandler extends AbstractFileDataSourceHandler{
    private MediaLocator locator;
    private boolean append = false;
    private Connection conn = null;
    private Statement stmt = null;
    
    public AbstractDatabaseSourceHandler() {
    }
    
    public boolean isAppendEnabled() {
        return append;
    }
    
    private void Create_Connection() {
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            //Get a connection
            setConn(DriverManager.getConnection(getOutputLocator().toExternalForm()));
            stmt = getConn().createStatement();
        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public synchronized void setConn(Connection conn) {
        this.conn = conn;
    }

    public synchronized Connection getConn() {
        return conn;
    }
    
    private void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (getConn() != null) {
                DriverManager.getConnection(getOutputLocator().toExternalForm() + ";shutdown=true");
                getConn().close();
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    
    /** execute takes executes a Statement defined by the String parameter
     *
     * @param Statement
     */
    public void execute(String Statement){
        try {
            stmt.execute(Statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /** executeBatch takes a Statement Array and sends them to the server
     *
     * @param Statement
     */
    public void executeBatch(String[] statement){
        try {
            stmt.clearBatch();
            for(int i =0;i<statement.length;i++) {
                stmt.addBatch(statement[i]);
            }
            if(!getConn().isClosed()){
                stmt.executeBatch();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setAppendEnabled(boolean append) {
        this.append = append;
    }
    
    public void setOutputLocator(MediaLocator ml) {
        locator = ml;
    }
    
    public abstract void process();
    
    public MediaLocator getOutputLocator() {
        return locator;
    }
    
    public void open() {
        System.out.println("opening database");
        Create_Connection();
    }
    
    public void close() {
        // close all sources
        System.out.println("closing database");
        super.close();
        shutdown();
    }
}