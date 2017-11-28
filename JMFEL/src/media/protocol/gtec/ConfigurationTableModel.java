/*
 * ConfigurationTableModel.java
 *
 * Created on 20. Juli 2007, 15:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Administrator
 */

public class ConfigurationTableModel extends AbstractTableModel {
    public static final int CHANNEL = 0;
    public static final int BIPOLAR = 1;
    public static final int FILTER = 2;
    public static final int NOTCH = 3;
    public static final int DRL = 4;
    
    private String[] columnNames = {"CH#", "Bipolar", "Filter", "Notch", "DRL"};
    private Object[][] data = {
        {new Integer(1), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(2), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(3), new Integer(0),"NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(4), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(5), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(6), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(7), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(8), new Integer(0),"NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(9), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(10), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(11), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(12), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(13), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(14), new Integer(0),"NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(15), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)},
        {new Integer(16), new Integer(0), "NO FILTER", "NO FILTER", new Integer(0)}
    };
    
    public ConfigurationTableModel(){
    }
    
    public final Object[] longValues = {"", "", new Object(), new Object(), ""};
    
    public int getColumnCount() {
        return columnNames.length;
    }
    
    public int getRowCount() {
        return data.length;
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }
    
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}