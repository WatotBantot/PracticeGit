import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class Activity8 extends JFrame {

    private JTextField jtfSID, jtfName;
    private JRadioButton jrbMale, jrbFemale;
    private JComboBox<String> jcbProgram;
    private JButton btnAddNew, btnSaveData;
    private DefaultTableModel model;
    ButtonGroup sexGroup;

    public Activity8() {
        initializeUI();
        setEnabledInputFields(false);
        btnAddNew.requestFocus();
    }

    private void initializeUI() {
        String[] columns = { "Student ID", "Name", "Sex", "Program" };
        model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel formPanel = new JPanel(new GridBagLayout());
        JPanel sexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        GridBagConstraints gbc = new GridBagConstraints();

        jtfSID = new JTextField();
        jtfName = new JTextField();

        jrbMale = new JRadioButton("Male");
        jrbFemale = new JRadioButton("Female");

        sexGroup = new ButtonGroup();
        sexGroup.add(jrbMale);
        sexGroup.add(jrbFemale);
        sexPanel.add(jrbMale);
        sexPanel.add(jrbFemale);

        jcbProgram = new JComboBox<>(new String[] {
                "Select Program",
                "BSIT", "BSDS", "BSEE", "BSCE", "BSME"
        });

        btnAddNew = new JButton("Add New");
        btnSaveData = new JButton("Save Data");

        btnAddNew.addActionListener(e -> setEnabledInputFields(true));
        btnAddNew.addActionListener(e -> clearForm());
        btnSaveData.addActionListener(e -> {
            saveData();
            clearForm();
        });

        btnPanel.add(btnAddNew);
        btnPanel.add(btnSaveData);

        table.setEnabled(false);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        scrollPane.setBorder(new TitledBorder("Student Records"));
        formPanel.setBorder(new TitledBorder("Student Information"));
        formPanel.setPreferredSize(new Dimension(350, 0));

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Student ID:"), gbc);

        gbc.gridx = 1;
        formPanel.add(jtfSID, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        formPanel.add(jtfName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Sex:"), gbc);

        gbc.gridx = 1;
        formPanel.add(sexPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Program:"), gbc);

        gbc.gridx = 1;
        formPanel.add(jcbProgram, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem clearFormItem = new JMenuItem("Clear Form");
        clearFormItem.addActionListener(e -> {
            setEnabledInputFields(true);
            clearForm();
        });
        JMenuItem enableInputItem = new JMenuItem("Enable Input Fields");
        enableInputItem.addActionListener(e -> setEnabledInputFields(true));
        editMenu.add(clearFormItem);
        editMenu.add(enableInputItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Student Management System\nVersion 1.0\n\nA simple application to manage student records.",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(aboutItem);
        
        // Goon Menu
        JMenu  goonMenu = new JMenu("Goon");
        JMenuItem goonerItem = new JMenuItem("Goon");
        goonerItem.addActionListener(e ->{
            JOptionPane.showMessageDialog(this,
                    "By Pressing this you decided to Goon and opose Joshua Garcia",
                    "Goon",
                    JOptionPane.QUESTION_MESSAGE);
        });
       goonMenu.add(goonerItem);         
                
        // Add menus to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        menuBar.add(goonMenu);
        
        // Set menu bar
        setJMenuBar(menuBar);

        setLayout(new BorderLayout(15, 15));
        add(formPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        setTitle("Student Management System");
        setSize(850, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setEnabledInputFields(boolean bool) {
        jtfSID.setEnabled(bool);
        jtfName.setEnabled(bool);
        jrbMale.setEnabled(bool);
        jrbFemale.setEnabled(bool);
        jcbProgram.setEnabled(bool);
        btnSaveData.setEnabled(bool);
    }

    private void clearForm() {
        jtfSID.setText("");
        jtfName.setText("");
        sexGroup.clearSelection();
        jcbProgram.setSelectedIndex(0);
        btnAddNew.requestFocus();
    }

    private void saveData() {

        String sid = jtfSID.getText();
        String name = jtfName.getText();
        String sex = null;
        String program = (String) jcbProgram.getSelectedItem();

        if (jrbMale.isSelected()) {
            sex = "Male";
        } else if (jrbFemale.isSelected()) {
            sex = "Female";
        } else {
            sex = null;
        }

        if (sid.isEmpty()) {
            showError("Student ID is required.");
            return;
        }

        if (name.isEmpty()) {
            showError("Name is required.");
            return;
        }

        if (sex == null) {
            showError("Please select sex.");
            return;
        }

        if (program.equals("Select Program")) {
            showError("Please select a program.");
            return;
        }

        model.addRow(new Object[] { sid, name, sex, program });

        JOptionPane.showMessageDialog(this,
                "Student data saved successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                message,
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Activity8::new);
    }
}