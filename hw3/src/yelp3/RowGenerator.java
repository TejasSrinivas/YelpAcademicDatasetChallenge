/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yelp3;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author tejas
 */

@SuppressWarnings("serial")
public class RowGenerator extends javax.swing.JFrame {
    DefaultTableModel model ;
	
	public RowGenerator() {
	//	initComponents();
		model = new DefaultTableModel();
		jTable1.setModel(model);
		model.addColumn("Id");
		model.addColumn("First Name");
		model.addColumn("Last Name");
		model.addColumn("Company Name");
		}
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) { 
		
		model.addRow(new Object[]{jTextField1.getText(), jTextField2.getText(),jTextField3.getText(),jTextField4.getText()});
	
	} 
	
	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { 
		// TODO add your handling code here:
		// remove all row from table
		if (jTable1.getRowCount() > 0) {
			for (int i = jTable1.getRowCount() - 1; i > -1; i--) {
				model.removeRow(i);
				}
			}
	} 
	
	/**
* @param args the command line arguments
*/
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				new RowGenerator().setVisible(true);
				}
			});
		}
	// Variables declaration - do not modify
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JTextField jTextField2;
	private javax.swing.JTextField jTextField3;
	private javax.swing.JTextField jTextField4;
	// End of variables declaration
}



