/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package motorph_GUI;

import data_reader9.EmployeeDetailsReader;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.lang.System.Logger;
//import java.lang.System.Logger.Level;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import motorph9.ITUser;
import motorph9.User;
import password_reset9.PasswordCsvDataAccess;
import password_reset9.ResetPasswordProcessor;
import password_reset9.PasswordDataAccess;
import password_reset9.PasswordResetService;

/**
 *
 * @author Four Lugtu
 */
public class ITDashboard extends javax.swing.JFrame {
    private Timer timer;
    private ITUser itUser;
    private static final String FILE_PATH = "src/data9/Password_Reset_Requests.csv"; // CSV file path
    private ResetPasswordProcessor resetPasswordProcessor;

    /**
      * Creates new form ITDashboar
      * @param itUser*/
  public ITDashboard(ITUser itUser) {  // Pass ITUser object directly
      this.itUser = itUser; // Assign it correctly
      initComponents(); // Call initComponents() to initialize UI
      this.setResizable(false); // 💡 Prevent resizing
      this.setExtendedState(JFrame.NORMAL);
      setLocationRelativeTo(null); // Center the window
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define close behavior
      checkFileExists(); // Ensure the file exists
      startClock();
      setupTable(); // Set correct table headers
      loadPasswordResetRequests(); // Load data into JTable
      //setITUserDetails(); // Show IT User details
      //setupTableSelectionListener();
      initializeDependencies();

        // Debugging: Print ITUser details
        System.out.println("ITUser Data: ");
        System.out.println("Username: " + itUser.getUsername());
        System.out.println("First Name: " + itUser.getFirstName());
        System.out.println("Last Name: " + itUser.getLastName());

        jLabelGreet.setText("Welcome, " + itUser.getFirstName() + "!");
    }

    private ITDashboard() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    private void startClock() {
        timer = new Timer(1000, e -> updateTimeAndDate());
        timer.start();
    }
    
  
    private void updateTimeAndDate() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        jLabelTime.setText(timeFormat.format(new Date()));
        jLabelDate.setText(dateFormat.format(new Date()));
    }
    
    /**
     * Restricts jDateChooser to **only allow today's date** (prevents past & future selections).
     */
    
    public void checkFileExists() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            System.out.println("✅ CSV file found: " + FILE_PATH);
        } else {
            System.err.println("❌ CSV file not found: " + FILE_PATH);
        }
    }
    
    private void setupTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Employee Number", "Employee Name", "Date of Request", 
            "Status", "Admin Name", "Admin Employee No.", "Date of Reset"
        });
        jTablePasswordResetTickets.setModel(model); // Apply headers to table

        // Customize column widths
        TableColumnModel columnModel = jTablePasswordResetTickets.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(85);  // Employee Number
        columnModel.getColumn(1).setPreferredWidth(150);  // Employee Name
        columnModel.getColumn(2).setPreferredWidth(120);  // Date of Request
        columnModel.getColumn(3).setPreferredWidth(80);   // Status
        columnModel.getColumn(4).setPreferredWidth(122);  // Admin Name
        columnModel.getColumn(5).setPreferredWidth(85);  // Admin Employee No.
        columnModel.getColumn(6).setPreferredWidth(150);  // Date of Reset

        jTablePasswordResetTickets.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Prevent auto-resizing
    }
    
    public void loadPasswordResetRequests() {
        DefaultTableModel model = (DefaultTableModel) jTablePasswordResetTickets.getModel();
        model.setRowCount(0); // Clear table before loading new data
        jButtonResetPassword.setEnabled(false);

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true;
            int rowCount = 0;

            while ((line = br.readLine()) != null) {
                System.out.println("🔍 Reading line: " + line);

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] data = line.split(",", -1);

                String empNum = data.length > 0 ? data[0] : "";
                String empName = data.length > 1 ? data[1] : "";
                String dateRequest = data.length > 2 ? data[2] : "";
                String status = data.length > 3 ? data[3] : "Pending";
                String adminName = data.length > 4 ? data[4] : "";
                String adminEmpNum = data.length > 5 ? data[5] : "";
                String dateReset = data.length > 6 ? data[6] : "";

                model.addRow(new Object[]{empNum, empName, dateRequest, status, adminName, adminEmpNum, dateReset});
                rowCount++;
            }

            model.fireTableDataChanged(); // Explicitly refresh the table
            System.out.println("✅ Loaded " + rowCount + " requests into JTable.");

        } catch (FileNotFoundException e) {
            System.err.println("❌ Password Reset Requests file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("❌ Error loading password reset requests: " + e.getMessage());
        }
    }
    
    private void initializeDependencies() {
        try {
            PasswordDataAccess passwordDataAccess = new PasswordCsvDataAccess();
            PasswordResetService passwordResetService = new PasswordResetService(passwordDataAccess);
            resetPasswordProcessor = new ResetPasswordProcessor(passwordResetService);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing dependencies: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private String generateComplexDefaultPassword(String employeeNumber) {
        String basePassword = "Default" + employeeNumber;
        String specialChars = "!@#$%^&*";
        Random random = new Random();
        int randomIndex = random.nextInt(specialChars.length());
        char randomChar = specialChars.charAt(randomIndex);

        // Ensure two-digit random number (e.g., 07 instead of 7)
        String randomTwoDigit = String.format("%02d", random.nextInt(100));

        return basePassword + randomChar + randomTwoDigit;
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonPasswordResetTickets = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButtonCreateEmployeeAccount = new javax.swing.JButton();
        jPanelITMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelPH = new javax.swing.JLabel();
        jLabelMotor = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        jLabelGMT = new javax.swing.JLabel();
        jLabelTime = new javax.swing.JLabel();
        jLabelGreet = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jTabbedPaneIT = new javax.swing.JTabbedPane();
        jPanelPasswordReset = new javax.swing.JPanel();
        jScrollPanePasswordResetTickets = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanelCreateAccount = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabelEmployeeInformation = new javax.swing.JLabel();
        jTextFieldSelectedEmployeeName = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jTextFieldSelectedEmployeeNumber = new javax.swing.JTextField();
        jLabelEmployeeNumber = new javax.swing.JLabel();
        jButtonResetPassword = new javax.swing.JButton();
        jScrollPaneTableEmployeeRecords = new javax.swing.JScrollPane();
        jTableEmployeeRecords = new javax.swing.JTable();
        jLabelCreateEmployeesAccoun = new javax.swing.JLabel();
        jButtonDeleteAccount = new javax.swing.JButton();
        jButtonCreateAccount = new javax.swing.JButton();
        jLabelPasswordResetTickets = new javax.swing.JLabel();
        jTablePasswordResetTickets = new javax.swing.JTable();
        jButtonLogout1 = new javax.swing.JButton();

        jButtonPasswordResetTickets.setBackground(new java.awt.Color(0, 0, 51));
        jButtonPasswordResetTickets.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonPasswordResetTickets.setForeground(new java.awt.Color(255, 255, 255));
        jButtonPasswordResetTickets.setText("Password Reset Tickets");
        jButtonPasswordResetTickets.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonPasswordResetTickets.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonPasswordResetTickets.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonPasswordResetTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPasswordResetTicketsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jButtonCreateEmployeeAccount.setBackground(new java.awt.Color(0, 0, 51));
        jButtonCreateEmployeeAccount.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonCreateEmployeeAccount.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCreateEmployeeAccount.setText("Create Employee Account");
        jButtonCreateEmployeeAccount.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonCreateEmployeeAccount.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonCreateEmployeeAccount.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonCreateEmployeeAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateEmployeeAccountActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelITMain.setBackground(new java.awt.Color(215, 235, 255));
        jPanelITMain.setForeground(new java.awt.Color(0, 51, 102));
        jPanelITMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeader.setBackground(new java.awt.Color(255, 255, 255));
        jPanelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelPH.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabelPH.setForeground(new java.awt.Color(0, 51, 153));
        jLabelPH.setText("PH");
        jPanelHeader.add(jLabelPH, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, -10, 70, 120));

        jLabelMotor.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        jLabelMotor.setText("MOTOR");
        jPanelHeader.add(jLabelMotor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 150, 100));

        Logo.setBackground(new java.awt.Color(0, 0, 102));
        Logo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        Logo.setForeground(new java.awt.Color(255, 255, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photos/LogoMotorPH(small).png"))); // NOI18N
        Logo.setText("Username");
        jPanelHeader.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 100, 110));

        jLabelGMT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelGMT.setForeground(new java.awt.Color(0, 0, 51));
        jLabelGMT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGMT.setText("GMT+8 PH Time");
        jPanelHeader.add(jLabelGMT, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 20, 170, -1));

        jLabelTime.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTime.setForeground(new java.awt.Color(0, 0, 51));
        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTime.setText("12:12:12 AM");
        jPanelHeader.add(jLabelTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 40, 170, 32));

        jLabelGreet.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabelGreet.setForeground(new java.awt.Color(0, 0, 51));
        jLabelGreet.setText("Welcome!");
        jPanelHeader.add(jLabelGreet, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 250, -1));

        jLabelDate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelDate.setForeground(new java.awt.Color(0, 51, 153));
        jLabelDate.setText("Wednesday, December 25, 2012");
        jLabelDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanelHeader.add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, -1, 20));

        jPanelITMain.add(jPanelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 100));

        jPanelPasswordReset.setBackground(new java.awt.Color(215, 235, 255));
        jPanelPasswordReset.setMinimumSize(new java.awt.Dimension(1200, 560));
        jPanelPasswordReset.setName(""); // NOI18N
        jPanelPasswordReset.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPanePasswordResetTickets.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jPanelPasswordReset.add(jScrollPanePasswordResetTickets, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 910, 350));

        jPanel2.setBackground(new java.awt.Color(215, 235, 255));
        jPanel2.setForeground(new java.awt.Color(215, 235, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelCreateAccount.setBackground(new java.awt.Color(215, 235, 255));
        jPanelCreateAccount.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(215, 235, 255));
        jPanel6.setForeground(new java.awt.Color(0, 51, 102));
        jPanel6.setToolTipText("");
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelEmployeeInformation.setBackground(new java.awt.Color(0, 0, 51));
        jLabelEmployeeInformation.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabelEmployeeInformation.setForeground(new java.awt.Color(0, 51, 153));
        jLabelEmployeeInformation.setText(" RESET EMPLOYEE PASSWORD");
        jPanel6.add(jLabelEmployeeInformation, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 30));

        jTextFieldSelectedEmployeeName.setEditable(false);
        jTextFieldSelectedEmployeeName.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldSelectedEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSelectedEmployeeNameActionPerformed(evt);
            }
        });
        jPanel6.add(jTextFieldSelectedEmployeeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 170, 30));

        jLabelName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelName.setForeground(new java.awt.Color(0, 0, 51));
        jLabelName.setText("Employee Name:");
        jPanel6.add(jLabelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 20));

        jTextFieldSelectedEmployeeNumber.setEditable(false);
        jTextFieldSelectedEmployeeNumber.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldSelectedEmployeeNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSelectedEmployeeNumberActionPerformed(evt);
            }
        });
        jPanel6.add(jTextFieldSelectedEmployeeNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, 190, 30));

        jLabelEmployeeNumber.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelEmployeeNumber.setForeground(new java.awt.Color(0, 0, 51));
        jLabelEmployeeNumber.setText("Employee Number:");
        jPanel6.add(jLabelEmployeeNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 150, 20));

        jButtonResetPassword.setBackground(new java.awt.Color(0, 0, 51));
        jButtonResetPassword.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonResetPassword.setForeground(new java.awt.Color(255, 255, 255));
        jButtonResetPassword.setText("Reset Password");
        jButtonResetPassword.setMaximumSize(new java.awt.Dimension(132, 27));
        jButtonResetPassword.setMinimumSize(new java.awt.Dimension(132, 27));
        jButtonResetPassword.setPreferredSize(new java.awt.Dimension(90, 23));
        jButtonResetPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetPasswordActionPerformed(evt);
            }
        });
        jPanel6.add(jButtonResetPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 160, 30));

        jPanelCreateAccount.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 930, 140));

        jTableEmployeeRecords.setAutoCreateRowSorter(true);
        jTableEmployeeRecords.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTableEmployeeRecords.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee No.", "Last Name", "First name", "Birthday", "Address", "Phone No.", "SSS No.", "PhilHealth No.", "TIN No.", "Pagibig No.", "Status", "Position", "Supervisor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEmployeeRecords.setToolTipText("");
        jTableEmployeeRecords.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPaneTableEmployeeRecords.setViewportView(jTableEmployeeRecords);

        jPanelCreateAccount.add(jScrollPaneTableEmployeeRecords, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 820, 480));

        jLabelCreateEmployeesAccoun.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelCreateEmployeesAccoun.setText("CREATE EMPLOYEES ACCOUNT");
        jPanelCreateAccount.add(jLabelCreateEmployeesAccoun, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jButtonDeleteAccount.setBackground(new java.awt.Color(0, 0, 51));
        jButtonDeleteAccount.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonDeleteAccount.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDeleteAccount.setText("Delete Account");
        jPanelCreateAccount.add(jButtonDeleteAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 40, 130, 30));

        jButtonCreateAccount.setBackground(new java.awt.Color(0, 0, 51));
        jButtonCreateAccount.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonCreateAccount.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCreateAccount.setText("Create Account");
        jPanelCreateAccount.add(jButtonCreateAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 140, 30));

        jPanel2.add(jPanelCreateAccount, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 695));

        jPanelPasswordReset.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 150));

        jLabelPasswordResetTickets.setBackground(new java.awt.Color(0, 0, 0));
        jLabelPasswordResetTickets.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabelPasswordResetTickets.setForeground(new java.awt.Color(0, 0, 51));
        jLabelPasswordResetTickets.setText("Password Reset Requests");
        jPanelPasswordReset.add(jLabelPasswordResetTickets, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 250, -1));

        jTablePasswordResetTickets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee No.", "Employee Name", "Date of Request", "Status", "Admin Name", "Admin Employee No.", "Date & Time of Reset"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePasswordResetTickets.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePasswordResetTicketsMouseClicked(evt);
            }
        });
        jPanelPasswordReset.add(jTablePasswordResetTickets, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jTabbedPaneIT.addTab("PasswordReset", jPanelPasswordReset);

        jPanelITMain.add(jTabbedPaneIT, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 960, 720));

        jButtonLogout1.setBackground(new java.awt.Color(0, 0, 51));
        jButtonLogout1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonLogout1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogout1.setText("Logout");
        jButtonLogout1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogout1ActionPerformed(evt);
            }
        });
        jPanelITMain.add(jButtonLogout1, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 610, 100, 30));

        getContentPane().add(jPanelITMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCreateEmployeeAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateEmployeeAccountActionPerformed
       jTabbedPaneIT.setSelectedIndex(1);
       jButtonPasswordResetTickets.setBackground(new java.awt.Color(0,0,0));
       jButtonCreateEmployeeAccount.setBackground(Color.RED);
    }//GEN-LAST:event_jButtonCreateEmployeeAccountActionPerformed

    private void jButtonLogout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogout1ActionPerformed
        Login newClassInstance = new Login();
        newClassInstance.setVisible(true);

        dispose();
    }//GEN-LAST:event_jButtonLogout1ActionPerformed

    private void jButtonResetPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetPasswordActionPerformed
        int selectedRow = jTablePasswordResetTickets.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a request to process.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get employee number from the selected row
        String employeeNumber = (String) jTablePasswordResetTickets.getValueAt(selectedRow, 0);

        // IMPORTANT: Use the logged-in admin's information directly from itUser
        // This ensures the admin info is correct regardless of what's shown in the text fields
        String adminName = itUser.getFirstName() + " " + itUser.getLastName();
        String adminEmpNum = itUser.getEmployeeId();

        resetPasswordProcessor.resetPassword(employeeNumber, adminName, adminEmpNum, this);

        // Get temporary password
        EmployeeDetailsReader employeeReader = new EmployeeDetailsReader("src/data9/Employee.csv", "src/data9/Login.csv");
        String tempPassword = employeeReader.getPasswordByEmployeeNum(employeeNumber);

        JOptionPane.showMessageDialog(this, "Temporary Password: " + tempPassword + 
            "\n\n(This is a simulation. In a real system, the password would be delivered securely.)", 
            "Password Reset", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButtonResetPasswordActionPerformed

    private void jButtonPasswordResetTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPasswordResetTicketsActionPerformed
        jTabbedPaneIT.setSelectedIndex(0);
        
    }//GEN-LAST:event_jButtonPasswordResetTicketsActionPerformed

    private void jTextFieldSelectedEmployeeNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSelectedEmployeeNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSelectedEmployeeNumberActionPerformed

    private void jTablePasswordResetTicketsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePasswordResetTicketsMouseClicked
        int selectedRow = jTablePasswordResetTickets.getSelectedRow();
        if (selectedRow == -1) {
            jButtonResetPassword.setEnabled(false);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) jTablePasswordResetTickets.getModel();

        // Get the employee data from the selected row
        String employeeNumber = model.getValueAt(selectedRow, 0).toString(); 
        String employeeName = model.getValueAt(selectedRow, 1).toString();   
        String status = model.getValueAt(selectedRow, 3).toString();         

        // Update the selected employee fields (NOT the admin fields)
        jTextFieldSelectedEmployeeNumber.setText(employeeNumber);  
        jTextFieldSelectedEmployeeName.setText(employeeName);              

        // Enable/disable Reset Button based on status
        jButtonResetPassword.setEnabled(status.equalsIgnoreCase("Pending"));
    }//GEN-LAST:event_jTablePasswordResetTicketsMouseClicked

    private void jTextFieldSelectedEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSelectedEmployeeNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSelectedEmployeeNameActionPerformed

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
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ITDashboard().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Logo;
    private javax.swing.JButton jButtonCreateAccount;
    private javax.swing.JButton jButtonCreateEmployeeAccount;
    private javax.swing.JButton jButtonDeleteAccount;
    private javax.swing.JButton jButtonLogout1;
    private javax.swing.JButton jButtonPasswordResetTickets;
    private javax.swing.JButton jButtonResetPassword;
    private javax.swing.JLabel jLabelCreateEmployeesAccoun;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelEmployeeInformation;
    private javax.swing.JLabel jLabelEmployeeNumber;
    private javax.swing.JLabel jLabelGMT;
    private javax.swing.JLabel jLabelGreet;
    private javax.swing.JLabel jLabelMotor;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPH;
    private javax.swing.JLabel jLabelPasswordResetTickets;
    private javax.swing.JLabel jLabelTime;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelCreateAccount;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelITMain;
    private javax.swing.JPanel jPanelPasswordReset;
    private javax.swing.JScrollPane jScrollPanePasswordResetTickets;
    private javax.swing.JScrollPane jScrollPaneTableEmployeeRecords;
    private javax.swing.JTabbedPane jTabbedPaneIT;
    private javax.swing.JTable jTableEmployeeRecords;
    private javax.swing.JTable jTablePasswordResetTickets;
    private javax.swing.JTextField jTextFieldSelectedEmployeeName;
    private javax.swing.JTextField jTextFieldSelectedEmployeeNumber;
    // End of variables declaration//GEN-END:variables
}
