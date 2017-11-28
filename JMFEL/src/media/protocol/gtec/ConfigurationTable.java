/*
 * ConfigurationTable.java
 *
 * Created on 20. Juli 2007, 15:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package media.protocol.gtec;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Administrator
 */

public class ConfigurationTable extends JTable{
    
    public ConfigurationTable(){
    }
    
    public ConfigurationTable(TableModel tableModel){
        super(tableModel);
    }
    
    public void initColumnSizes(JTable table) {
        ConfigurationTableModel model = (ConfigurationTableModel)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        column = table.getColumnModel().getColumn(0);
        column.setPreferredWidth(30);
        column.setWidth(30);
        column.setMaxWidth(30);
        column = table.getColumnModel().getColumn(1);
        column.setPreferredWidth(50);
        column.setWidth(50);
        column.setMaxWidth(50);
        column = table.getColumnModel().getColumn(2);
        column.setPreferredWidth(50);
        column = table.getColumnModel().getColumn(3);
        column.setPreferredWidth(50);
        column = table.getColumnModel().getColumn(4);
        column.setPreferredWidth(40);
        column.setWidth(40);
        column.setMaxWidth(40);
    }
    
    public void setUpChannelColumn(JTable table, TableColumn channelColumn) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(renderer.LEFT);
        channelColumn.setCellRenderer(renderer);
    }
    
    public void setUpFilterColumn(JTable table, TableColumn filterColumn, JComboBox filterComboBox) {
        filterColumn.setCellEditor(new DefaultCellEditor(filterComboBox));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(renderer.LEFT);
        filterColumn.setCellRenderer(renderer);
    }
    
    public void setUpNotchColumn(JTable table, TableColumn notchColumn, JComboBox notchComboBox) {
        notchColumn.setCellEditor(new DefaultCellEditor(notchComboBox));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        notchColumn.setCellRenderer(renderer);
    }
    
    public void setUpDRLColumn(JTable table, TableColumn drlColumn) {
        JComboBox comboBox = new JComboBox();
        comboBox.addItem(new Integer(0));
        comboBox.addItem(new Integer(1));
        drlColumn.setCellEditor(new DefaultCellEditor(comboBox));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        drlColumn.setCellRenderer(renderer);
    }
    
    public void setUpBipolarColumn(JTable table, TableColumn bipolarColumn) {
        JComboBox comboBox = new JComboBox();
        comboBox.addItem(new Integer(0));
        comboBox.addItem(new Integer(1));
        comboBox.addItem(new Integer(2));
        comboBox.addItem(new Integer(3));
        comboBox.addItem(new Integer(4));
        comboBox.addItem(new Integer(5));
        comboBox.addItem(new Integer(6));
        comboBox.addItem(new Integer(7));
        comboBox.addItem(new Integer(8));
        comboBox.addItem(new Integer(9));
        comboBox.addItem(new Integer(10));
        comboBox.addItem(new Integer(11));
        comboBox.addItem(new Integer(12));
        comboBox.addItem(new Integer(13));
        comboBox.addItem(new Integer(14));
        comboBox.addItem(new Integer(15));
        comboBox.addItem(new Integer(16));
        bipolarColumn.setCellEditor(new DefaultCellEditor(comboBox));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        bipolarColumn.setCellRenderer(renderer);
    }
}