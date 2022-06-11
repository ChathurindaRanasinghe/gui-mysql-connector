import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.lang.*;

class MyFrame
        extends JFrame
        implements ActionListener {

    Connection conn = null;

    private final Container c;
    private final JComboBox tables;
    private final JLabel deptDname;
    private final JTextField deptDnamefield;
    private final JLabel deptDnumber;
    private final JTextField deptDnumberfield;
    private final JLabel deptMgrSsn;
    private final JTextField deptMgrSsnfield;
    private final JLabel deptlocation;
    private final JTextField deptlocationfield;
    private final JLabel deptlnumber;
    private final JTextField deptlnumberfield;
    private final JLabel deptMgrStartDate;
    private final JComboBox day;
    private final JComboBox month;
    private final JComboBox year;
    private final JButton select;
    private final JButton search;
    private final JButton insert;
    private final JButton update;
    private final JButton delete;
    private final JTextArea messages;

    private final String[] months
            = {"Jan", "feb", "Mar", "Apr",
            "May", "Jun", "July", "Aug",
            "Sup", "Oct", "Nov", "Dec"};

    public MyFrame() {

        String[] dates = new String[31];
        for (int i = 1; i <= 31; i++) {
            dates[i - 1] = String.valueOf(i);
        }

        String[] years = new String[60];
        for (int i = 0; i < 60; i++) {
            years[i] = String.valueOf(2022 - i);
        }


        setTitle("MySQL Connector");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        JLabel title = new JLabel("MySQL Connector");

        title.setSize(300, 40);
        title.setLocation(300, 30);
        c.add(title);

        JLabel tableName = new JLabel("Select Table");
        //tableName.setFont(new Fon)
        tableName.setSize(150, 25);
        tableName.setLocation(100, 100);
        c.add(tableName);

        String[] tableNames = {
                "DEPARTMENT", "DEPT_LOCATIONS"
        };
        tables = new JComboBox(tableNames);
        //tables.setFont();

        tables.setSize(200, 25);
        tables.setLocation(250, 100);
        //tables.setBackground(new Color(255,255,255));
        c.add(tables);

        deptDname = new JLabel("Department Name*");
        deptDname.setSize(150, 25);
        deptDname.setLocation(100, 150);
        deptDnamefield = new JTextField();
        deptDnamefield.setSize(200, 25);
        deptDnamefield.setLocation(250, 150);

        deptlnumber = new JLabel("Department Number*");
        deptlnumber.setSize(150, 25);
        deptlnumber.setLocation(100, 150);
        deptlnumberfield = new JTextField();
        deptlnumberfield.setSize(200, 25);
        deptlnumberfield.setLocation(250, 150);

        deptDnumber = new JLabel("Department Number*");
        deptDnumber.setSize(150, 25);
        deptDnumber.setLocation(100, 200);
        deptDnumberfield = new JTextField();
        deptDnumberfield.setSize(200, 25);
        deptDnumberfield.setLocation(250, 200);

        deptlocation = new JLabel("Department Location*");
        deptlocation.setSize(150, 25);
        deptlocation.setLocation(100, 200);
        deptlocationfield = new JTextField();
        deptlocationfield.setSize(200, 25);
        deptlocationfield.setLocation(250, 200);

        deptMgrSsn = new JLabel("Mgr_ssn*");
        deptMgrSsn.setSize(150, 25);
        deptMgrSsn.setLocation(100, 250);
        deptMgrSsnfield = new JTextField();
        deptMgrSsnfield.setSize(200, 25);
        deptMgrSsnfield.setLocation(250, 250);

        deptMgrStartDate = new JLabel("Mgr_start_date*");
        deptMgrStartDate.setSize(150, 25);
        deptMgrStartDate.setLocation(100, 300);
        day = new JComboBox(dates);
        day.setSize(60, 25);
        day.setLocation(250, 300);
        month = new JComboBox(months);
        month.setSize(60, 25);
        month.setLocation(320, 300);
        year = new JComboBox(years);
        year.setSize(60, 25);
        year.setLocation(390, 300);

        messages = new JTextArea();
        messages.setSize(400, 150);
        messages.setLocation(100, 350);
        messages.setBorder(BorderFactory.createCompoundBorder(
                messages.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        messages.setLineWrap(true);
        c.add(messages);


        select = new JButton("Select");
        select.setSize(100, 25);
        select.setLocation(600, 100);
        select.addActionListener(this);
        c.add(select);

        search = new JButton("<html>Search by<br>Dnumber</html>");
        search.setSize(100, 50);
        search.setLocation(600, 150);
        search.addActionListener(this);
        c.add(search);

        insert = new JButton("INSERT");
        insert.setBackground(new Color(255, 255, 255));
        insert.setSize(100, 25);
        insert.setLocation(600, 250);
        insert.addActionListener(this);
        c.add(insert);

        update = new JButton("UPDATE");
        update.setBackground(new Color(255, 255, 255));
        update.setSize(100, 25);
        update.setLocation(600, 300);
        update.addActionListener(this);
        c.add(update);

        delete = new JButton("DELETE");
        delete.setBackground(new Color(255, 255, 255));
        delete.setSize(100, 25);
        delete.setLocation(600, 350);
        delete.addActionListener(this);
        c.add(delete);

        setVisible(true);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dept_db?", "root", "");
            messages.setText("Database connection successful!");
        } catch (Exception e) {
            messages.setText(String.valueOf(e));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == select) {
            String selection = (String) tables.getSelectedItem();
            messages.setText(selection + " table selected.");
            if (Objects.equals(selection, "DEPARTMENT")) {

                if (deptlnumber.getParent() == c) {
                    c.remove(deptlnumber);
                    c.remove(deptlnumberfield);
                    c.remove(deptlocation);
                    c.remove(deptlocationfield);
                }
                c.add(deptDname);
                c.add(deptDnamefield);
                c.add(deptDnumber);
                c.add(deptDnumberfield);
                c.add(deptMgrSsn);
                c.add(deptMgrSsnfield);
                c.add(deptMgrStartDate);
                c.add(day);
                c.add(month);
                c.add(year);
                search.setText("<html>Search by<br>Dnumber</html>");
                c.validate();
                c.repaint();
                //System.out.println(selection);
            } else if (Objects.equals(selection, "DEPT_LOCATIONS")) {
                if (deptDname.getParent() == c) {
                    c.remove(deptDname);
                    c.remove(deptDnamefield);
                    c.remove(deptDnumber);
                    c.remove(deptDnumberfield);
                    c.remove(deptMgrSsn);
                    c.remove(deptMgrSsnfield);
                    c.remove(deptMgrStartDate);
                    c.remove(day);
                    c.remove(month);
                    c.remove(year);
                }
                search.setText("<html>Search by<br>Dlocation</html>");
                c.add(deptlnumber);
                c.add(deptlnumberfield);
                c.add(deptlocation);
                c.add(deptlocationfield);
                c.validate();
                c.repaint();

            }
        } else if (e.getSource() == insert) {

            try {
                Statement stmt = conn.createStatement();
                String selection = (String) tables.getSelectedItem();
                if (Objects.equals(selection, "DEPARTMENT")) {
                    String insertQuery = "INSERT INTO department(Dname,Dnumber,Mgr_ssn,Mgr_start_date) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                    pstmt.setString(1, deptDnamefield.getText());
                    pstmt.setInt(2, Integer.parseInt(deptDnumberfield.getText()));
                    pstmt.setBigDecimal(3, new BigDecimal(deptMgrSsnfield.getText()));
                    String selectedDate = (String) year.getSelectedItem() + "-" + String.valueOf(Arrays.asList(months).indexOf((String) month.getSelectedItem()) + 1) + "-" + (String) day.getSelectedItem();
                    pstmt.setString(4, selectedDate);
                    pstmt.execute();
                    messages.setText("Insert Operation Successful!");
                } else if (Objects.equals(selection, "DEPT_LOCATIONS")) {
                    String insertQuery = "INSERT INTO dept_locations(Dnumber,Dlocation) VALUES (?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                    pstmt.setInt(1, Integer.parseInt(deptlnumberfield.getText()));
                    pstmt.setString(2, deptlocationfield.getText());
                    pstmt.execute();
                    messages.setText("Insert Operation Successful!");
                }

            } catch (Exception error) {
                messages.setText(String.valueOf(error));
            }


        } else if (e.getSource() == search) {
            String selection = (String) tables.getSelectedItem();
            if (Objects.equals(selection, "DEPARTMENT")) {

                //System.out.println(deptDnumberfield.getSelectedText());
                String query = String.format("SELECT * FROM department WHERE Dnumber=%d", Integer.parseInt(deptDnumberfield.getText()));
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    deptDnamefield.setText(rs.getString("Dname"));
                    deptMgrSsnfield.setText(rs.getString("Mgr_ssn"));
                    //day.setSelectedIndex(rs.getInt);
                    Date date = rs.getDate("Mgr_start_date");
                    day.setSelectedIndex(date.getDate() - 1);
                    month.setSelectedIndex(date.getMonth() - 1);
                    year.setSelectedIndex(122 - date.getYear());
                    //messages.setText();
                } catch (Exception error) {
                    messages.setText(String.valueOf(error));

                }

            } else if (Objects.equals(selection, "DEPT_LOCATIONS")) {
                String query = String.format("SELECT * FROM dept_locations WHERE Dlocation='%s'", deptlocationfield.getText());
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    rs.next();
                    deptlnumberfield.setText(rs.getString("Dnumber"));
                    messages.setText("Match found!");
                } catch (Exception error) {
                    messages.setText(String.valueOf(error));

                }
            }
        } else if (e.getSource() == update) {
            String selection = (String) tables.getSelectedItem();

            try {
                Statement stmt = conn.createStatement();

                if (Objects.equals(selection, "DEPARTMENT")) {
                    String updateQuery = "UPDATE department SET Dname=?, Mgr_ssn=?, Mgr_start_date=? WHERE Dnumber=?";
                    PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                    pstmt.setString(1, deptDnamefield.getText());
                    pstmt.setInt(4, Integer.parseInt(deptDnumberfield.getText()));
                    pstmt.setBigDecimal(2, new BigDecimal(deptMgrSsnfield.getText()));
                    String selectedDate = (String) year.getSelectedItem() + "-" + String.valueOf(Arrays.asList(months).indexOf((String) month.getSelectedItem()) + 1) + "-" + (String) day.getSelectedItem();
                    pstmt.setString(3, selectedDate);
                    pstmt.execute();
                } else if (Objects.equals(selection, "DEPT_LOCATIONS")) {
                    String updateQuery = "UPDATE dept_locations SET Dnumber=? WHERE Dlocation=?";
                    PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                    pstmt.setInt(1, Integer.parseInt(deptlnumberfield.getText()));
                    pstmt.setString(2, deptlocationfield.getText());
                    pstmt.execute();
                }

            } catch (Exception error) {
                messages.setText(String.valueOf(error));
            }


        } else if (e.getSource() == delete) {
            String selection = (String) tables.getSelectedItem();

            try {
                Statement stmt = conn.createStatement();

                if (Objects.equals(selection, "DEPARTMENT")) {
                    String deleteQuery = "DELETE FROM department WHERE Dnumber=?";
                    PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
                    pstmt.setInt(1, Integer.parseInt(deptDnumberfield.getText()));
                    pstmt.execute();
                } else if (Objects.equals(selection, "DEPT_LOCATIONS")){
                    String deleteQuery = "DELETE FROM dept_locations WHERE Dlocation=?";
                    PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
                    pstmt.setString(1,deptlocationfield.getText());
                    pstmt.execute();
                }

            } catch (Exception error) {
                messages.setText(String.valueOf(error));
            }

        }


    }
}