/*
 * FilterSpecList.java
 *
 * Created on 6. Juli 2007, 19:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class FilterSpecList {
    
    private Vector<FilterSpec> filterSpecs = new Vector();
    
    /** Creates a new instance of FilterSpecList */
    public FilterSpecList() {
    }

    public void addFilterSpec(FilterSpec aFilterSpec){
        filterSpecs.addElement(aFilterSpec);
    }
    
    public FilterSpec getFilterSpec(int index){
        return filterSpecs.get(index);
    }
    
    public void clear(){
        filterSpecs.clear();
    }
    
    public int getSize(){
        return filterSpecs.size();
    }
}