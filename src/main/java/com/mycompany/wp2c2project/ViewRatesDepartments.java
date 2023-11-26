/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.wp2c2project;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Windows
 */
public class ViewRatesDepartments extends javax.swing.JFrame {

    /**
     * Creates new form Modifications
     */

    static void centerTableValue(JTable tableName) {
        JTable JTable = tableName;
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tableName.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        tableName.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        tableName.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
    }
    
    static void cellEditor(JTable tableName) {
        JTable JTable  = tableName;
        tableName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row= tableName.rowAtPoint(e.getPoint());
                int col= tableName.columnAtPoint(e.getPoint());
                String cellValue = (String) tableName.getValueAt(row, col);
                String colName = tableName.getColumnName(col);
                if (col == 1) {
                    String newInput = JOptionPane.showInputDialog(null, "Enter new " + colName);

                    if (newInput != null) {
                        tableName.setValueAt(newInput, row, col);
                    } else {
                        tableName.setValueAt(cellValue, row, col);
                    }
                }
            }
         }
        );
    }
    
    static void cellEditorForShift(JTable tableName) {
        JTable JTable = tableName;
        tableName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row= tableName.rowAtPoint(e.getPoint());
                int col= tableName.columnAtPoint(e.getPoint());
                String cellValue = (String) tableName.getValueAt(row, col);
                String colName = tableName.getColumnName(col);
                if (col >= 2) {
                    String newInput = JOptionPane.showInputDialog(null, "Enter new " + colName);
                    
                    
                    if (newInput != null) {
                        try {
                            int inputValue = Integer.parseInt(newInput);
                            String numStr = Integer.toString(inputValue);

                            int numOfDigits = numStr.length();

                            if (numOfDigits == 3) {
                                int hours = Integer.parseInt(Integer.toString(inputValue).substring(0, 1));
                                int mins = Integer.parseInt(Integer.toString(inputValue).substring(1, 3));
                                
                                if (mins <= 59) {
                                    String time = Integer.toString(hours) + ":" + Integer.toString(mins);
                                    tableName.setValueAt(time, row, col);
                                } else {
                                    tableName.setValueAt(cellValue, row, col);
                                    JOptionPane.showMessageDialog(null, "Invalid input.","Error", JOptionPane.ERROR_MESSAGE);
                                }
                                
                            } else if (numOfDigits == 4) {
                                int hours = Integer.parseInt(Integer.toString(inputValue).substring(0, 2));
                                int mins = Integer.parseInt(Integer.toString(inputValue).substring(2, 4));
                                
                                if (mins <= 59) {
                                    String time = Integer.toString(hours) + ":" + Integer.toString(mins);
                                    tableName.setValueAt(time, row, col);
                                } else {
                                    tableName.setValueAt(cellValue, row, col);
                                    JOptionPane.showMessageDialog(null, "Invalid input.","Error", JOptionPane.ERROR_MESSAGE);
                                }
                                
                                if (hours <= 24) {
                                    String time = Integer.toString(hours) + ":" + Integer.toString(mins);
                                    tableName.setValueAt(time, row, col);
                                } else {
                                    tableName.setValueAt(cellValue, row, col);
                                    JOptionPane.showMessageDialog(null, "Invalid input.","Error", JOptionPane.ERROR_MESSAGE);
                                }
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid input.","Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException t) {
                            JOptionPane.showMessageDialog(null, "Invalid input.","Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        tableName.setValueAt(cellValue, row, col);
                    }
                }
            }
         }
        );
    }
        
    public ViewRatesDepartments() {
        initComponents();
        
        centerTableValue(Admin);
        centerTableValue(Accounting);
        centerTableValue(Sales);
        centerTableValue(HR);
        centerTableValue(QC);
        centerTableValue(Prepress);
        centerTableValue(Press);
        centerTableValue(Postpress);
        
        cellEditorForShift(Admin);
        cellEditorForShift(Accounting);
        cellEditorForShift(Sales);
        cellEditorForShift(HR);
        cellEditorForShift(QC);
        cellEditorForShift(Prepress);
        cellEditorForShift(Press);
        cellEditorForShift(Postpress);
        
        cellEditor(Admin);
        cellEditor(Accounting);
        cellEditor(Sales);
        cellEditor(HR);
        cellEditor(QC);
        cellEditor(Prepress);
        cellEditor(Press);
        cellEditor(Postpress);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        Admin = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        Accounting = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        Sales = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        HR = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        QC = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        Prepress = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        Press = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        Postpress = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Synergy Grafix Corporation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jTabbedPane1.setBackground(new java.awt.Color(102, 102, 102));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));

        Admin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Jon", "500", "10:00", "14:00"}
            },
            new String [] {
                "Name", "Rate", "Shift Start", "Shift End"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(Admin);
        if (Admin.getColumnModel().getColumnCount() > 0) {
            Admin.getColumnModel().getColumn(0).setPreferredWidth(150);
            Admin.getColumnModel().getColumn(1).setPreferredWidth(50);
            Admin.getColumnModel().getColumn(2).setPreferredWidth(50);
            Admin.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jTabbedPane1.addTab("Admin", jScrollPane7);

        Accounting.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Jher", "1000", "7:00", "16:00"}
            },
            new String [] {
                "Name", "Rate", "Shift Start", "Shift End"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        Accounting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AccountingMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Accounting);
        if (Accounting.getColumnModel().getColumnCount() > 0) {
            Accounting.getColumnModel().getColumn(0).setPreferredWidth(150);
            Accounting.getColumnModel().getColumn(1).setPreferredWidth(50);
            Accounting.getColumnModel().getColumn(2).setPreferredWidth(50);
            Accounting.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jTabbedPane1.addTab("Accounting", jScrollPane1);

        Sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Marc", "2", "14:00", "16:00"},
                {null, null, null, null}
            },
            new String [] {
                "Name", "Rate", "Shift Start", "Shift End"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(Sales);
        if (Sales.getColumnModel().getColumnCount() > 0) {
            Sales.getColumnModel().getColumn(0).setPreferredWidth(150);
            Sales.getColumnModel().getColumn(1).setPreferredWidth(50);
            Sales.getColumnModel().getColumn(2).setPreferredWidth(50);
            Sales.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jTabbedPane1.addTab("Sales", jScrollPane5);

        HR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Marlon", "1500", "1:00", "23:00"}
            },
            new String [] {
                "Name", "Rate", "Shift Start", "Shift End"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane6.setViewportView(HR);
        if (HR.getColumnModel().getColumnCount() > 0) {
            HR.getColumnModel().getColumn(0).setPreferredWidth(150);
            HR.getColumnModel().getColumn(1).setPreferredWidth(50);
            HR.getColumnModel().getColumn(2).setPreferredWidth(50);
            HR.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jTabbedPane1.addTab("Human Resources", jScrollPane6);

        QC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Jer", "999999", "7:30", "7:31"}
            },
            new String [] {
                "Name", "Rate", "Shift Start", "Shift End"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane8.setViewportView(QC);
        if (QC.getColumnModel().getColumnCount() > 0) {
            QC.getColumnModel().getColumn(0).setPreferredWidth(150);
            QC.getColumnModel().getColumn(1).setPreferredWidth(50);
            QC.getColumnModel().getColumn(2).setPreferredWidth(50);
            QC.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jTabbedPane1.addTab("Quality Control", jScrollPane8);

        Prepress.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Vince", "21", "2:00", "3:00"}
            },
            new String [] {
                "Name", "Rate", "Shift Start", "Shift End"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(Prepress);
        if (Prepress.getColumnModel().getColumnCount() > 0) {
            Prepress.getColumnModel().getColumn(0).setPreferredWidth(150);
            Prepress.getColumnModel().getColumn(1).setPreferredWidth(50);
            Prepress.getColumnModel().getColumn(2).setPreferredWidth(50);
            Prepress.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jTabbedPane1.addTab("Prepress", jScrollPane9);

        Press.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Laurence", "5", "7:30", "20:00"}
            },
            new String [] {
                "Name", "Rate", "Shift Start", "Shift End"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane10.setViewportView(Press);
        if (Press.getColumnModel().getColumnCount() > 0) {
            Press.getColumnModel().getColumn(0).setPreferredWidth(150);
            Press.getColumnModel().getColumn(1).setPreferredWidth(50);
            Press.getColumnModel().getColumn(2).setPreferredWidth(50);
            Press.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jTabbedPane1.addTab("Press", jScrollPane10);

        Postpress.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Glouyer", "99", "3:00", "3:30"}
            },
            new String [] {
                "Name", "Rate", "Shift Start", "Shift End"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane11.setViewportView(Postpress);
        if (Postpress.getColumnModel().getColumnCount() > 0) {
            Postpress.getColumnModel().getColumn(0).setPreferredWidth(150);
            Postpress.getColumnModel().getColumn(1).setPreferredWidth(50);
            Postpress.getColumnModel().getColumn(2).setPreferredWidth(50);
            Postpress.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jTabbedPane1.addTab("Postpress", jScrollPane11);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 79, Short.MAX_VALUE)
        );

        jLabel1.setText("Admin:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Dela Cruz, Juan");

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButton1)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AdminHome frame = new AdminHome();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void AccountingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccountingMouseClicked
//        JOptionPane.showInputDialog("Enter time");
    }//GEN-LAST:event_AccountingMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewRatesDepartments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewRatesDepartments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewRatesDepartments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewRatesDepartments.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewRatesDepartments().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable Accounting;
    private javax.swing.JTable Admin;
    private javax.swing.JTable HR;
    private javax.swing.JTable Postpress;
    private javax.swing.JTable Prepress;
    private javax.swing.JTable Press;
    private javax.swing.JTable QC;
    private static javax.swing.JTable Sales;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
