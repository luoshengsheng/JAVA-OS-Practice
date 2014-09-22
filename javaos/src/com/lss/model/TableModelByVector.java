/**
*author：罗圣盛
*target:
*version:
*time:2014-9-11下午7:58:56
*other:
*/
package com.lss.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TableModelByVector extends AbstractTableModel {  
  private Vector<String> columnNames = null ;
  private Vector<Vector<String>> dataVector = null ;
 
  public TableModelByVector(Vector<Vector<String>> dataVector,Vector<String> columnNames) {
		// TODO Auto-generated constructor stub
  	this.dataVector = dataVector ;
  	this.columnNames = columnNames ;
	}
  public int getColumnCount() {  
      return columnNames.size();  
  }  

  public int getRowCount() {  
      return dataVector.size();  
  }  
  public String getColumnName(int col) {  
      return columnNames.get(col);  
  }  

  public Object getValueAt(int row, int col) {  
      return dataVector.get(row).get(col);  
  }  

  public Class getColumnClass(int c) {  
      return getValueAt(0, c).getClass();  
  }

  /* 
   * Don't need to implement this method unless your table's 
   * editable. 
   */  
  public boolean isCellEditable(int row, int col) {  
      //Note that the data/cell address is constant,  
      //no matter where the cell appears on screen.  
      if (col < 2) {  
          return false;  
      } else {  
          return true;  
      } 
  }  
  
  /* 
   * Don't need to implement this method unless your table's 
   * data can change. 
   */  
  public void setValueAt(String value, int row, int col) {  
      fireTableCellUpdated(row, col);  
  }  
} 
