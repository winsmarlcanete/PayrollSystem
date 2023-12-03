/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wp2c2project.frames;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Windows
 */
public class ViewEmployees extends javax.swing.JFrame {

    /**
     * Creates new form Modifications
     */
    static void centerTableValue(JTable tableName) {
        JTable JTable = tableName;
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableName.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableName.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableName.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    }

    static void cellEditor(JTable tableName) {
        JTable JTable = tableName;
        tableName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableName.rowAtPoint(e.getPoint());
                int col = tableName.columnAtPoint(e.getPoint());
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
                int row = tableName.rowAtPoint(e.getPoint());
                int col = tableName.columnAtPoint(e.getPoint());
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
                                    JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                                }

                            } else if (numOfDigits == 4) {
                                int hours = Integer.parseInt(Integer.toString(inputValue).substring(0, 2));
                                int mins = Integer.parseInt(Integer.toString(inputValue).substring(2, 4));

                                if (mins <= 59) {
                                    String time = Integer.toString(hours) + ":" + Integer.toString(mins);
                                    tableName.setValueAt(time, row, col);
                                } else {
                                    tableName.setValueAt(cellValue, row, col);
                                    JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                                }

                                if (hours <= 24) {
                                    String time = Integer.toString(hours) + ":" + Integer.toString(mins);
                                    tableName.setValueAt(time, row, col);
                                } else {
                                    tableName.setValueAt(cellValue, row, col);
                                    JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException t) {
                            JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        tableName.setValueAt(cellValue, row, col);
                    }
                }
            }
        }
        );
    }

    public ViewEmployees() {
        initComponents();

        showTableContent();

        employeeTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //scv means selected cell value
                String idTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 0);
                String nameTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 1);
                String departmentTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 2);
                String rateTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 3);
                String shiftSTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 4);
                String shiftETable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 5);
                String statusTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 6);
                String tinTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 7);
                String philhealthTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 8);
                String sssTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 9);
                String pagibigTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 10);
                String taxSTable = (String) employeeTable.getValueAt(employeeTable.getSelectedRow(), 11);

                idTF.setText(idTable);
                employeeNameTF.setText(nameTable);
                departmentTF.setText(departmentTable);
                rateTF.setText(rateTable);
                shiftStartTF.setText(shiftSTable);
                shiftEndTF.setText(shiftETable);
                tinTF.setText(tinTable);
                philhealthTF.setText(philhealthTable);
                sssTF.setText(sssTable);
                pagibigTF.setText(pagibigTable);
                taxStatusTF.setText(taxSTable);
                statusTF.setText(statusTable);
                

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

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
        jScrollPane2 = new javax.swing.JScrollPane();
        employeeTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        employeeNameTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        rateTF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        shiftStartTF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        shiftEndTF = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tinTF = new javax.swing.JTextField();
        philhealthTF = new javax.swing.JTextField();
        sssTF = new javax.swing.JTextField();
        pagibigTF = new javax.swing.JTextField();
        taxStatusTF = new javax.swing.JTextField();
        statusTF = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        idTF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        departmentTF = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
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

        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, "7:00", "16:00", null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Department", "Rate", "Shift Start", "Shift End", "Status", "TIN", "PhilHealth", "SSS", "Pag-Ibig Fund", "taxStatus"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        employeeTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(employeeTable);
        if (employeeTable.getColumnModel().getColumnCount() > 0) {
            employeeTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        }

        jLabel4.setText("Department");

        jLabel5.setText("Employee Name");

        employeeNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeNameTFActionPerformed(evt);
            }
        });

        jLabel6.setText("Salary Rate Per Day");

        rateTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rateTFActionPerformed(evt);
            }
        });

        jLabel7.setText("Shift Start");

        shiftStartTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shiftStartTFActionPerformed(evt);
            }
        });

        jLabel8.setText("Shift End");

        shiftEndTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shiftEndTFActionPerformed(evt);
            }
        });

        jLabel9.setText("TIN");

        jLabel10.setText("PhilHealth");

        jLabel11.setText("SSS");

        jLabel12.setText("PAG-IBIG FUND");

        jLabel13.setText("Tax Status");

        jLabel14.setText("Status");

        tinTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tinTFActionPerformed(evt);
            }
        });

        jButton2.setText("UPDATE");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("DELETE");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        idTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTFActionPerformed(evt);
            }
        });

        jLabel15.setText("ID");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(philhealthTF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(140, 140, 140)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sssTF, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(idTF, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton3)))))
                    .addComponent(jLabel10)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(employeeNameTF, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                            .addComponent(departmentTF)
                            .addComponent(rateTF))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(shiftStartTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(shiftEndTF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tinTF, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(statusTF, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(pagibigTF, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(140, 140, 140)
                        .addComponent(taxStatusTF)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(shiftStartTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(employeeNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(shiftEndTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(departmentTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(tinTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(rateTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(philhealthTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sssTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pagibigTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taxStatusTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(idTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setText("Back");
        jButton1.setFocusable(false);
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
                .addGap(33, 33, 33)
                .addComponent(jButton1)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(41, 41, 41))
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
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

    private void employeeNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeNameTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employeeNameTFActionPerformed

    private void shiftStartTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shiftStartTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shiftStartTFActionPerformed

    private void shiftEndTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shiftEndTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_shiftEndTFActionPerformed

    private void tinTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tinTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tinTFActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
            Connection sgconn = Main.connectSG();
            try {
                String en = employeeNameTF.getText();
                String dep = departmentTF.getText();
                Double rate = Double.valueOf(rateTF.getText());
                String shiftS = shiftStartTF.getText();
                String shiftE = shiftEndTF.getText();
                String tin = tinTF.getText();
                Integer pH = Integer.valueOf(philhealthTF.getText());
                String sss = sssTF.getText();
                Integer pagIbig = Integer.valueOf(pagibigTF.getText());
                String taxS = taxStatusTF.getText();
                String status = statusTF.getText();
                Integer id = Integer.valueOf(idTF.getText());

                PreparedStatement pstmt = sgconn.prepareStatement("UPDATE employee SET name = ?, department = ?, rate = ? "
                        + ", shiftStart = ?, shiftEnd = ?, tin = ?, philHealth = ?, sss = ?, pagibig = ?, taxStatus = ?, status = ? WHERE empId = ?");
                pstmt.setString(1, en);
                pstmt.setString(2, dep);
                pstmt.setDouble(3, rate);
                pstmt.setString(4, shiftS);
                pstmt.setString(5, shiftE);
                pstmt.setString(6, tin);
                pstmt.setInt(7, pH);
                pstmt.setString(8, sss);
                pstmt.setInt(9, pagIbig);
                pstmt.setString(10, taxS);
                pstmt.setString(11, status);
                pstmt.setInt(12, id);
                pstmt.executeUpdate();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "An error has occured.", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex);

                // Code block of sending data to database ends here
            }
        
        showTableContent();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Connection sgconn = Main.connectSG();
        try {
            int val4 = Integer.parseInt(idTF.getText());

            String sqlStatement = "DELETE FROM employee WHERE empId = ?";

            PreparedStatement updateQuery = sgconn.prepareStatement(sqlStatement);
            updateQuery.setInt(1, val4);
            updateQuery.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        showTableContent();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void idTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idTFActionPerformed

    private void rateTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rateTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rateTFActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewEmployees().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField departmentTF;
    private javax.swing.JTextField employeeNameTF;
    public javax.swing.JTable employeeTable;
    private javax.swing.JTextField idTF;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField pagibigTF;
    private javax.swing.JTextField philhealthTF;
    private javax.swing.JTextField rateTF;
    private javax.swing.JTextField shiftEndTF;
    private javax.swing.JTextField shiftStartTF;
    private javax.swing.JTextField sssTF;
    private javax.swing.JTextField statusTF;
    private javax.swing.JTextField taxStatusTF;
    private javax.swing.JTextField tinTF;
    // End of variables declaration//GEN-END:variables

    private void showTableContent() {
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.setRowCount(0);

        try {
            String sql = "SELECT * FROM employee";
            Connection sgconn = Main.connectSG();
            PreparedStatement pst = sgconn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new String[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                    rs.getString(10), rs.getString(11), rs.getString(12)});
            }
        } catch (Exception e) {
            System.out.println("error par");
        }

        // Centers the value of table (loop)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int x = 0; x < 4; x++) {
            employeeTable.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }
    }
}
