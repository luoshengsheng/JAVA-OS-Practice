/**
*author：罗圣盛
*target:
*version:
*time:2014-9-11下午7:56:07
*other:
*/
package com.lss.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

//model类
public class TableModelByArray extends AbstractTableModel {  
  private String [] columnNames = null ;
  private Object [][] data = null ;
  public TableModelByArray(Object[][] data,String [] columnNames) {
		// TODO Auto-generated constructor stub
  	this.data = data ;
  	this.columnNames = columnNames ;
	}
  public int getColumnCount() {  
      return columnNames.length;  
  }  

  public int getRowCount() {  
      return data.length;  
  }  
  public void setColumnNames(String [] columnNames) {  
      this.columnNames = columnNames;  
  }  

  public void setData(Object [][] data) {  
      this.data = data; 
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
  public void setValueAt(Object value, int row, int col) {  
      data[row][col] = value;  
      fireTableCellUpdated(row, col);  
  }  
} 
