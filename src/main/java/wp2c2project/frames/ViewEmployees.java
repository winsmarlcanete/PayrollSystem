/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package wp2c2project.frames;

import wp2c2project.classes.Main;
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
import wp2c2project.classes.Summary;

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

    int selectedId;

    public ViewEmployees() {
        initComponents();
        showTableContent();
    }

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
        } catch (SQLException e) {
            System.out.println("error par");
        }

        // Centers the value of table (loop)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int x = 0; x < 4; x++) {
            employeeTable.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel34 = new javax.swing.JPanel();
        jButton114 = new javax.swing.JButton();
        jButton115 = new javax.swing.JButton();
        jButton116 = new javax.swing.JButton();
        jButton117 = new javax.swing.JButton();
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
        departmentTF = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WP2C2 Payroll");

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));

        jButton114.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jButton114.setText("Back");
        jButton114.setFocusable(false);
        jButton114.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton114.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton114ActionPerformed(evt);
            }
        });

        jButton115.setBackground(new java.awt.Color(232, 115, 26));
        jButton115.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jButton115.setForeground(new java.awt.Color(255, 255, 255));
        jButton115.setText("Employees");
        jButton115.setFocusable(false);
        jButton115.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton115.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton115ActionPerformed(evt);
            }
        });

        jButton116.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jButton116.setText("Rates");
        jButton116.setFocusable(false);
        jButton116.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton116.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton116ActionPerformed(evt);
            }
        });

        jButton117.setFont(new java.awt.Font("Inter Medium", 0, 14)); // NOI18N
        jButton117.setText("About");
        jButton117.setToolTipText("");
        jButton117.setFocusable(false);
        jButton117.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton117.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton117ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        employeeTable.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        employeeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

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
        employeeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                employeeTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(employeeTable);
        if (employeeTable.getColumnModel().getColumnCount() > 0) {
            employeeTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        }

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel4.setText("Department");

        jLabel5.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel5.setText("Employee Name");

        employeeNameTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel6.setText("Salary Rate Per Day");

        rateTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel7.setText("Shift Start");

        shiftStartTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel8.setText("Shift End");

        shiftEndTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel9.setText("TIN");

        jLabel10.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel10.setText("PhilHealth");

        jLabel11.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel11.setText("SSS");

        jLabel12.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel12.setText("PAG-IBIG FUND");

        jLabel13.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel13.setText("Tax Status");

        jLabel14.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N
        jLabel14.setText("Status");

        tinTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        philhealthTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        sssTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        pagibigTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        taxStatusTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        statusTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        departmentTF.setFont(new java.awt.Font("Inter", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(departmentTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(employeeNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(41, 41, 41)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(rateTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7)
                                                    .addComponent(shiftStartTF, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(shiftEndTF, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(pagibigTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10)
                                            .addComponent(philhealthTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(41, 41, 41)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(sssTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(statusTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(41, 41, 41)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tinTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(taxStatusTF, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13))))
                                .addGap(0, 202, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shiftStartTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(shiftEndTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(departmentTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rateTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(philhealthTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sssTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(taxStatusTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pagibigTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tinTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton3.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        jButton3.setText("Delete");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(232, 115, 26));
        jButton2.setFont(new java.awt.Font("Inter", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton114, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton116, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton115, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(56, 56, 56)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 32, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator2)
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addComponent(jButton115, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton116, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jButton117, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(458, 458, 458)
                        .addComponent(jButton114, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            pstmt.setInt(12, selectedId);
            pstmt.executeUpdate();

            pstmt = sgconn.prepareStatement("SELECT * FROM `summary` WHERE `empId` = ?");
            pstmt.setInt(1, selectedId);
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();

            Summary summary = new Summary(0, selectedId, null, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
            summary.calcTime();
            summary.updateTime();
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
            String sqlStatement = "DELETE FROM employee WHERE empId = ?";

            PreparedStatement updateQuery = sgconn.prepareStatement(sqlStatement);
            updateQuery.setInt(1, selectedId);
            updateQuery.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);

        }

        showTableContent();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void employeeTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employeeTableMousePressed
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

        selectedId = Integer.parseInt(idTable);
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
    }//GEN-LAST:event_employeeTableMousePressed

    private void jButton114ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton114ActionPerformed
        SummaryHome frame = new SummaryHome();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton114ActionPerformed

    private void jButton115ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton115ActionPerformed
        ViewEmployees frame = new ViewEmployees();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton115ActionPerformed

    private void jButton116ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton116ActionPerformed
        ViewRates frame = new ViewRates();
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton116ActionPerformed

    private void jButton117ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton117ActionPerformed
        AboutFrame frame = new AboutFrame();
        frame.setVisible(true);

        dispose();
    }//GEN-LAST:event_jButton117ActionPerformed

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
    private javax.swing.JButton jButton114;
    private javax.swing.JButton jButton115;
    private javax.swing.JButton jButton116;
    private javax.swing.JButton jButton117;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
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
}
