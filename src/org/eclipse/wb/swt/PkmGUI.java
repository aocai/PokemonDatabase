package org.eclipse.wb.swt;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class PkmGUI {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Table table;

	private Connection con = null;
	private String UserID = "ora_w5l8";
	private String passwd = "a36790111";
	private String url = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
	private Table table_1;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_9;
	private Text text_10;
	private Text text_11;
	private Text text_12;
	private Text text_13;
	private Text text_14; 
	private Text text_15;
	private Text text_16;
	private Text txtDdmmyyyy;
	private Text txtDescription;
	private Text text_17;
	private Text text_18;
	private Text text_19;
	private Text text_20;
	private Text text_22;
	private Text text_23;
	private Text text_24;
	private Text text_25;
	private Text txtMf;
	private Text text_26;
	private Text text_27;
	private Text txtTimeOut;
	private Text text_28;
	private Text text_8;
	private Button btnOldestEmployee;
	private Button btnYoungestEmployee;
	private DateTime dateTime;
	private String stringDate = null;
	private java.sql.Date sqlDate = null;
	private int dateTimeEnabled = 0;
	private Text text_21;
	
	public static void main(String[] args) {
		
		try {
			System.out.println("Loading driver...");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded.");
		}
		catch (Exception e) {
			System.out.println("Unable to load driver\n" + e);
			System.exit(-1);
		}
		try {
			PkmGUI window = new PkmGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Connect() {
		try {
			System.out.println("Connecting to OracleDB ...");
			con = DriverManager.getConnection(url, UserID, passwd);
			System.out.println("Connection successful.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection failed\n" + e);
		}
	}
	
	public void Disconnect() {
		try {
			con.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("failed to close connection" + e);
		}
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Connect();
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		Disconnect();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
        shell = new Shell();
        shell.setMinimumSize(new Point(132, 38));
        shell.setSize(778, 669);
        shell.setText("SWT Application");
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));
       
        TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
       
        TabItem tbtmTrainerinterface = new TabItem(tabFolder, SWT.NONE);
        tbtmTrainerinterface.setText("TrainerInterface");
       
        Composite composite = new Composite(tabFolder, SWT.NONE);
        tbtmTrainerinterface.setControl(composite);
       
        Label lblWelcomeTrainers = new Label(composite, SWT.NONE);
        lblWelcomeTrainers.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
        lblWelcomeTrainers.setBounds(240, 10, 192, 31);
        lblWelcomeTrainers.setText("Welcome Trainers!");
       
        ScrolledComposite scrolledComposite = new ScrolledComposite(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite.setBounds(10, 327, 659, 232);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
       
        table = new Table(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
       
        scrolledComposite.setContent(table);
        scrolledComposite.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));
       
        text = new Text(composite, SWT.BORDER);
        text.setBounds(80, 93, 170, 21);
       
        Label lblTrainerid = new Label(composite, SWT.NONE);
        lblTrainerid.setBounds(80, 72, 75, 21);
        lblTrainerid.setText("TrainerID");
       
        Button btnCheckIn = new Button(composite, SWT.NONE);
        btnCheckIn.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table.getColumnCount();
            	while (columnCount > 0) {
            		table.getColumn(columnCount-1).dispose();
            		columnCount--;
            	}
            	table.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    if (text.getText() != "" && text_1.getText() != "" && text_2.getText() != "" && text_3.getText() != "") {
                    	PreparedStatement ps = con.prepareStatement("INSERT into Pokemon Values (?,?,?,?,?,?,?)");
                    	ps.setInt(1, Integer.parseInt(text_1.getText()));
                    	ps.setString(2, text_2.getText());
                    	ps.setString(3, "N/A");
                    	ps.setString(4, text_3.getText());
                    	ps.setInt(5, Integer.parseInt(text.getText()));
                    	ps.setInt(6, 1);
                    	ps.setInt(7, 1);
                    	ps.executeUpdate();
                    	ps.close();
                    	
                    	ResultSet rs = stmt.executeQuery("SELECT * FROM Pokemon p WHERE p.trainerID = " + Integer.parseInt(text.getText()));
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	int count = rsmd.getColumnCount();
                    	for (int i=1; i<=count; i++) {
                        	TableColumn NewColumn = new TableColumn(table, SWT.NONE);
                        	NewColumn.setWidth(100);
                        	String columntitle = rsmd.getColumnName(i);
                        	NewColumn.setText(columntitle);
                    	}
                    	if (!rs.isBeforeFirst()) {
                    		System.out.println("No data");
                    	}
                    	while (rs.next()) {
                    		String[] myarray = new String[count];
                            myarray[0] = Integer.toString(rs.getInt("pokemonID"));
                            myarray[1] = rs.getString("name");
                            myarray[2] = rs.getString("status");
                            myarray[3] = rs.getString("gender");
                            myarray[4] = Integer.toString(rs.getInt("trainerID"));
                            myarray[5] = Integer.toString(rs.getInt("roomNumber"));
                            myarray[6] = Integer.toString(rs.getInt("branchNumber"));
                           
                            TableItem tableItem = new TableItem(table, SWT.NONE);
                            tableItem.setText(myarray);
                    	}
                    } else {
                    	System.out.println("Please fill in all the boxes");
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnCheckIn.setBounds(456, 200, 75, 25);
        btnCheckIn.setText("Check In");
       
        text_1 = new Text(composite, SWT.BORDER);
        text_1.setBounds(456, 93, 122, 21);
       
        Label lblPokemonid = new Label(composite, SWT.NONE);
        lblPokemonid.setBounds(363, 96, 89, 18);
        lblPokemonid.setText("PokemonID");
       
        text_2 = new Text(composite, SWT.BORDER);
        text_2.setBounds(456, 120, 73, 21);
       
        Label lblName = new Label(composite, SWT.NONE);
        lblName.setBounds(363, 123, 57, 18);
        lblName.setText("Name");
       
        text_3 = new Text(composite, SWT.BORDER);
        text_3.setBounds(456, 150, 73, 21);
       
        Label lblGender = new Label(composite, SWT.NONE);
        lblGender.setBounds(363, 153, 69, 21);
        lblGender.setText("Gender");
       
//        Button btnCheckOut = new Button(composite, SWT.NONE);
//        btnCheckOut.addMouseListener(new MouseAdapter() {
//        	@Override
//        	public void mouseDown(MouseEvent e) {int columnCount = table.getColumnCount();
//        	while (columnCount > 0) {
//        		table.getColumn(columnCount-1).dispose();
//        		columnCount--;
//        	}
//            try {
//                Statement stmt = con.createStatement();
//                if (text.getText() != "" && text_1.getText() != "") {
//                	ResultSet rs = stmt.executeQuery("SELECT * FROM Pokemon p WHERE p.trainerID = " + text.getText() + "and p.pokemonID = " + text_1.getText());
//                	ResultSetMetaData rsmd = rs.getMetaData();
//                	int count = rsmd.getColumnCount();
//                	for (int i=1; i<=count; i++) {
//                    	TableColumn NewColumn = new TableColumn(table, SWT.NONE);
//                    	NewColumn.setWidth(100);
//                    	String columntitle = rsmd.getColumnName(i);
//                    	NewColumn.setText(columntitle);
//                	}
//                	if (!rs.isBeforeFirst()) {
//                		System.out.println("No data");
//                	}
//                	if (rs.next()) {
//                		String[] myarray = new String[count];
//                        myarray[0] = Integer.toString(rs.getInt("pokemonID"));
//                        myarray[1] = rs.getString("name");
//                        myarray[2] = rs.getString("status");
//                        myarray[3] = rs.getString("gender");
//                        myarray[4] = Integer.toString(rs.getInt("trainerID"));
//                        myarray[5] = Integer.toString(rs.getInt("roomNumber"));
//                        myarray[6] = Integer.toString(rs.getInt("branchNumber"));
//                       
//                        TableItem tableItem = new TableItem(table, SWT.NONE);
//                        tableItem.setText(myarray);
//                	}
//                } else {
//                	System.out.println("Please fill in all the boxes");
//                }
//               
//            } catch (SQLException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//    	}
//        });
//        btnCheckOut.setBounds(456, 231, 75, 25);
//        btnCheckOut.setText("Check Out");
       
        Button btnHospitalizedPokemons = new Button(composite, SWT.NONE);
        btnHospitalizedPokemons.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
            	int columnCount = table.getColumnCount();
            	while (columnCount > 0) {
            		table.getColumn(columnCount-1).dispose();
            		columnCount--;
            	}
            	table.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    if (text.getText() != "") {
                    	ResultSet rs = stmt.executeQuery("SELECT * FROM Pokemon p WHERE p.trainerID = " + Integer.parseInt(text.getText()));
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	int count = rsmd.getColumnCount();
                    	for (int i=1; i<=count; i++) {
                        	TableColumn NewColumn = new TableColumn(table, SWT.NONE);
                        	NewColumn.setWidth(100);
                        	String columntitle = rsmd.getColumnName(i);
                        	NewColumn.setText(columntitle);
                    	}
                    	if (!rs.isBeforeFirst()) {
                    		System.out.println("No data");
                    	}
                    	while (rs.next()) {
                    		String[] myarray = new String[count];
                            myarray[0] = Integer.toString(rs.getInt("pokemonID"));
                            myarray[1] = rs.getString("name");
                            myarray[2] = rs.getString("status");
                            myarray[3] = rs.getString("gender");
                            myarray[4] = Integer.toString(rs.getInt("trainerID"));
                            myarray[5] = Integer.toString(rs.getInt("roomNumber"));
                            myarray[6] = Integer.toString(rs.getInt("branchNumber"));
                            
                            TableItem tableItem = new TableItem(table, SWT.NONE);
                            tableItem.setText(myarray);
                    	}
                    } else {
                    	System.out.println("no input");
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnHospitalizedPokemons.setBounds(80, 120, 170, 25);
        btnHospitalizedPokemons.setText("Hospitalized Pokemons");
       
        Button btnReturnPokemonid = new Button(composite, SWT.NONE);
        btnReturnPokemonid.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table.getColumnCount();
            	while (columnCount > 0) {
            		table.getColumn(columnCount-1).dispose();
            		columnCount--;
            	}
            	table.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    if (text.getText() != "") {
                    	ResultSet rs = stmt.executeQuery("SELECT pokemonID, name FROM Pokemon p WHERE p.trainerID = " + Integer.parseInt(text.getText()));
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	int count = rsmd.getColumnCount();
                    	for (int i=1; i<=count; i++) {
                        	TableColumn NewColumn = new TableColumn(table, SWT.NONE);
                        	NewColumn.setWidth(100);
                        	String columntitle = rsmd.getColumnName(i);
                        	NewColumn.setText(columntitle);
                    	}
                    	if (!rs.isBeforeFirst()) {
                    		System.out.println("No data");
                    	}
                    	while (rs.next()) {
                    		String[] myarray = new String[count];
                            myarray[0] = Integer.toString(rs.getInt("pokemonID"));
                            myarray[1] = rs.getString("name");
                           
                            TableItem tableItem = new TableItem(table, SWT.NONE);
                            tableItem.setText(myarray);
                    	}
                    } else {
                    	System.out.println("no input");
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnReturnPokemonid.setBounds(80, 147, 170, 25);
        btnReturnPokemonid.setText("Return PokemonID");
       
        Button btnReturnNameStatus = new Button(composite, SWT.NONE);
        btnReturnNameStatus.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table.getColumnCount();
            	while (columnCount > 0) {
            		table.getColumn(columnCount-1).dispose();
            		columnCount--;
            	}
            	table.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    if (text.getText() != "") {
                    	ResultSet rs = stmt.executeQuery("SELECT name, status FROM Pokemon p WHERE p.trainerID = " + Integer.parseInt(text.getText()));
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	int count = rsmd.getColumnCount();
                    	for (int i=1; i<=count; i++) {
                        	TableColumn NewColumn = new TableColumn(table, SWT.NONE);
                        	NewColumn.setWidth(100);
                        	String columntitle = rsmd.getColumnName(i);
                        	NewColumn.setText(columntitle);
                    	}
                    	if (!rs.isBeforeFirst()) {
                    		System.out.println("No data");
                    	}
                    	while (rs.next()) {
                    		String[] myarray = new String[count];
                            myarray[0] = rs.getString("name");
                            myarray[1] = rs.getString("Status");
                           
                            TableItem tableItem = new TableItem(table, SWT.NONE);
                            tableItem.setText(myarray);
                    	}
                    } else {
                    	System.out.println("no input");
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnReturnNameStatus.setBounds(80, 174, 170, 25);
        btnReturnNameStatus.setText("Return Name and Status");
       
        TabItem tbtmNurseinterface = new TabItem(tabFolder, SWT.NONE);
        tbtmNurseinterface.setText("NurseInterface");
       
        Composite composite_1 = new Composite(tabFolder, SWT.NONE);
        tbtmNurseinterface.setControl(composite_1);
       
        ScrolledComposite scrolledComposite_1 = new ScrolledComposite(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite_1.setBounds(10, 352, 732, 229);
        scrolledComposite_1.setExpandHorizontal(true);
        scrolledComposite_1.setExpandVertical(true);
        
        table_1 = new Table(scrolledComposite_1, SWT.BORDER | SWT.FULL_SELECTION);
        table_1.setHeaderVisible(true);
        table_1.setLinesVisible(true);
        scrolledComposite_1.setContent(table_1);
        scrolledComposite_1.setMinSize(table_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
       
        Label lblAuthorizedAccessOnly = new Label(composite_1, SWT.NONE);
        lblAuthorizedAccessOnly.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
        lblAuthorizedAccessOnly.setBounds(227, 10, 211, 28);
        lblAuthorizedAccessOnly.setText("Authorized Access Only");
        
        ScrolledComposite scrolledComposite_2 = new ScrolledComposite(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite_2.setBounds(10, 38, 732, 308);
        formToolkit.adapt(scrolledComposite_2);
        formToolkit.paintBordersFor(scrolledComposite_2);
        scrolledComposite_2.setExpandHorizontal(true);
        scrolledComposite_2.setExpandVertical(true);
        
        TabFolder tabFolder_1 = new TabFolder(scrolledComposite_2, SWT.NONE);
        formToolkit.adapt(tabFolder_1);
        formToolkit.paintBordersFor(tabFolder_1);
        
        TabItem tbtmPatientList = new TabItem(tabFolder_1, SWT.NONE);
        tbtmPatientList.setText("Patient List");
        
        Composite composite_2 = new Composite(tabFolder_1, SWT.NONE);
        tbtmPatientList.setControl(composite_2);
        formToolkit.paintBordersFor(composite_2);
        
        text_4 = new Text(composite_2, SWT.BORDER);
        text_4.setBounds(75, 49, 73, 21);
        formToolkit.adapt(text_4, true, true);
        
        Label lblNewLabel = new Label(composite_2, SWT.NONE);
        lblNewLabel.setBounds(75, 22, 89, 21);
        formToolkit.adapt(lblNewLabel, true, true);
        lblNewLabel.setText("Pokemon ID");
        
        Label lblTrainerId = new Label(composite_2, SWT.NONE);
        lblTrainerId.setBounds(75, 85, 73, 21);
        formToolkit.adapt(lblTrainerId, true, true);
        lblTrainerId.setText("Trainer ID");
        
        text_5 = new Text(composite_2, SWT.BORDER);
        text_5.setBounds(75, 112, 73, 21);
        formToolkit.adapt(text_5, true, true);
        
        Label lblStatus = new Label(composite_2, SWT.NONE);
        lblStatus.setBounds(215, 22, 57, 21);
        formToolkit.adapt(lblStatus, true, true);
        lblStatus.setText("Status");
        
        text_6 = new Text(composite_2, SWT.BORDER);
        text_6.setBounds(215, 49, 73, 21);
        formToolkit.adapt(text_6, true, true);
        
        Label lblNurseAssigned = new Label(composite_2, SWT.NONE);
        lblNurseAssigned.setBounds(369, 22, 149, 21);
        formToolkit.adapt(lblNurseAssigned, true, true);
        lblNurseAssigned.setText("Assigned Employee ID");
        
        text_7 = new Text(composite_2, SWT.BORDER);
        text_7.setBounds(369, 49, 64, 21);
        formToolkit.adapt(text_7, true, true);
        
        Button btnSearch = new Button(composite_2, SWT.NONE);
        btnSearch.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
            	while (columnCount > 0) {
            		table_1.getColumn(columnCount-1).dispose();
            		columnCount--;
            	}
            	table_1.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    if ((text_4.getText() != "" && (text_5.getText() != "" || text_6.getText() != "" || text_7.getText() != "")) ||
                    		(text_5.getText() != "" && (text_4.getText() != "" || text_6.getText() != "" || text_7.getText() != "")) ||
                    		(text_6.getText() != "" && (text_4.getText() != "" || text_5.getText() != "" || text_7.getText() != "")) ||
                    		(text_7.getText() != "" && (text_4.getText() != "" || text_5.getText() != "" || text_6.getText() != ""))) {
                    	System.out.println("Please only search one field at a time");
                    }
                    
                    
                    if (text_4.getText() != "") {
                    	
                    	ResultSet rs = stmt.executeQuery("SELECT * FROM Pokemon WHERE pokemonID = " + Integer.parseInt(text_4.getText()));
                    	
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	int count = rsmd.getColumnCount();
                    	for (int i=1; i<=count; i++) {
                        	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                        	NewColumn.setWidth(100);
                        	String columntitle = rsmd.getColumnName(i);
                        	NewColumn.setText(columntitle);
                    	}
                    	if (!rs.isBeforeFirst()) {
                    		System.out.println("No data");
                    	}
                    	while (rs.next()) {
                    		String[] myarray = new String[count];
                    		myarray[0] = rs.getString("pokemonID");
                            myarray[1] = rs.getString("name");
                            myarray[2] = rs.getString("status");
                            myarray[3] = rs.getString("gender");
                            myarray[4] = Integer.toString(rs.getInt("trainerID"));
                            myarray[5] = Integer.toString(rs.getInt("roomNumber"));
                            myarray[6] = Integer.toString(rs.getInt("branchNumber"));
                           
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                    	}
                    }
                    else if (text_5.getText() != "") {
                    	
                    	ResultSet rs = stmt.executeQuery("SELECT * FROM Pokemon WHERE trainerID = " + Integer.parseInt(text_5.getText()));
                    	
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	int count = rsmd.getColumnCount();
                    	for (int i=1; i<=count; i++) {
                        	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                        	NewColumn.setWidth(100);
                        	String columntitle = rsmd.getColumnName(i);
                        	NewColumn.setText(columntitle);
                    	}
                    	if (!rs.isBeforeFirst()) {
                    		System.out.println("No data");
                    	}
                    	while (rs.next()) {
                    		String[] myarray = new String[count];
                    		myarray[0] = rs.getString("pokemonID");
                            myarray[1] = rs.getString("name");
                            myarray[2] = rs.getString("status");
                            myarray[3] = rs.getString("gender");
                            myarray[4] = Integer.toString(rs.getInt("trainerID"));
                            myarray[5] = Integer.toString(rs.getInt("roomNumber"));
                            myarray[6] = Integer.toString(rs.getInt("branchNumber"));
                           
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                    	}
                    }
                    else if (text_6.getText() != "") {
                    	
                    	ResultSet rs = stmt.executeQuery("SELECT * FROM Pokemon WHERE status LIKE '%" + text_6.getText() + "%'");
                    	
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	int count = rsmd.getColumnCount();
                    	for (int i=1; i<=count; i++) {
                        	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                        	NewColumn.setWidth(100);
                        	String columntitle = rsmd.getColumnName(i);
                        	NewColumn.setText(columntitle);
                    	}
                    	if (!rs.isBeforeFirst()) {
                    		System.out.println("No data");
                    	}
                    	while (rs.next()) {
                    		String[] myarray = new String[count];
                    		myarray[0] = rs.getString("pokemonID");
                            myarray[1] = rs.getString("name");
                            myarray[2] = rs.getString("status");
                            myarray[3] = rs.getString("gender");
                            myarray[4] = Integer.toString(rs.getInt("trainerID"));
                            myarray[5] = Integer.toString(rs.getInt("roomNumber"));
                            myarray[6] = Integer.toString(rs.getInt("branchNumber"));
                           
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                    	}
                    }
                    else if (text_7.getText() != "") {
                    	
                    	ResultSet rs = stmt.executeQuery("SELECT p.pokemonID, p.name, p.status, p.gender, p.trainerID, p.roomNumber, p.branchNumber FROM Pokemon p, Nurses n WHERE p.pokemonID = n.pokemonID and n.employeeID = " + Integer.parseInt(text_7.getText()));
                    	
                    	ResultSetMetaData rsmd = rs.getMetaData();
                    	int count = rsmd.getColumnCount();
                    	for (int i=1; i<=count; i++) {
                        	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                        	NewColumn.setWidth(100);
                        	String columntitle = rsmd.getColumnName(i);
                        	NewColumn.setText(columntitle);
                    	}
                    	if (!rs.isBeforeFirst()) {
                    		System.out.println("No data");
                    	}
                    	while (rs.next()) {
                    		String[] myarray = new String[count];
                    		myarray[0] = rs.getString("pokemonID");
                            myarray[1] = rs.getString("name");
                            myarray[2] = rs.getString("status");
                            myarray[3] = rs.getString("gender");
                            myarray[4] = Integer.toString(rs.getInt("trainerID"));
                            myarray[5] = Integer.toString(rs.getInt("roomNumber"));
                            myarray[6] = Integer.toString(rs.getInt("branchNumber"));
                           
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                    	}
                    }
                    else {
                    	System.out.println("no input");
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnSearch.setBounds(215, 152, 75, 25);
        formToolkit.adapt(btnSearch, true, true);
        btnSearch.setText("Search");
        
        Button btnUpdate = new Button(composite_2, SWT.NONE);
        btnUpdate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                	table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if (text_4.getText() != "" && (text_5.getText() != "" || text_6.getText() != "" || text_7.getText() != "")) {
                        if (text_5.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Pokemon SET trainerID = " + Integer.parseInt(text_5.getText()) + " WHERE pokemonID = " + Integer.parseInt(text_4.getText()));
                        }
                        if (text_6.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Pokemon SET status = '" + text_6.getText() + "' WHERE pokemonID = " + Integer.parseInt(text_4.getText()));
                        }
                        if (text_7.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Nurses SET employeeID = " + Integer.parseInt(text_7.getText()) + " WHERE pokemonID = " + Integer.parseInt(text_4.getText()));
                        }
                    } else {
                        System.out.println("Please enter Pokemon ID and one other box correctly");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT p.pokemonID, p.name, p.status, p.gender, p.trainerID, p.roomNumber, p.branchNumber, n.employeeID FROM Pokemon p, Nurses n WHERE p.pokemonID = " + Integer.parseInt(text_4.getText()) + " AND p.pokemonID = n.pokemonID");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                       	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                       	NewColumn.setWidth(100);
                       	String columntitle = rsmd.getColumnName(i);
                       	NewColumn.setText(columntitle);
                    }
                   	if (!rs.isBeforeFirst()) {
                   		System.out.println("No data");
                   	}
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = rs.getString("pokemonID");
                        myarray[1] = rs.getString("name");
                        myarray[2] = rs.getString("status");
                        myarray[3] = rs.getString("gender");
                        myarray[4] = Integer.toString(rs.getInt("trainerID"));
                        myarray[5] = Integer.toString(rs.getInt("roomNumber"));
                        myarray[6] = Integer.toString(rs.getInt("branchNumber"));
                        myarray[7] = Integer.toString(rs.getInt("employeeID"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnUpdate.setBounds(215, 183, 75, 25);
        formToolkit.adapt(btnUpdate, true, true);
        btnUpdate.setText("Update");
        
        TabItem tbtmUpdatePkmStatus = new TabItem(tabFolder_1, SWT.NONE);
        tbtmUpdatePkmStatus.setText("Update Pkm Status");
        
        Composite composite_7 = new Composite(tabFolder_1, SWT.NONE);
        tbtmUpdatePkmStatus.setControl(composite_7);
        formToolkit.paintBordersFor(composite_7);
        
        Label lblPokemonId = new Label(composite_7, SWT.NONE);
        lblPokemonId.setBounds(75, 22, 92, 21);
        formToolkit.adapt(lblPokemonId, true, true);
        lblPokemonId.setText("Pokemon ID");
        
        text_9 = new Text(composite_7, SWT.BORDER);
        text_9.setBounds(75, 52, 73, 21);
        formToolkit.adapt(text_9, true, true);
        
        Label lblStatus_1 = new Label(composite_7, SWT.NONE);
        lblStatus_1.setBounds(75, 85, 61, 21);
        formToolkit.adapt(lblStatus_1, true, true);
        lblStatus_1.setText("Status");
        
        text_10 = new Text(composite_7, SWT.BORDER);
        text_10.setBounds(75, 112, 73, 21);
        formToolkit.adapt(text_10, true, true);
        
        Label lblRoomNumber = new Label(composite_7, SWT.NONE);
        lblRoomNumber.setBounds(217, 22, 103, 21);
        formToolkit.adapt(lblRoomNumber, true, true);
        lblRoomNumber.setText("Room Number");
        
        text_11 = new Text(composite_7, SWT.BORDER);
        text_11.setBounds(217, 52, 73, 21);
        formToolkit.adapt(text_11, true, true);
        
        Label lblBranchNumber = new Label(composite_7, SWT.NONE);
        lblBranchNumber.setBounds(217, 85, 103, 21);
        formToolkit.adapt(lblBranchNumber, true, true);
        lblBranchNumber.setText("Branch Number");
        
        text_12 = new Text(composite_7, SWT.BORDER);
        text_12.setBounds(217, 112, 73, 21);
        formToolkit.adapt(text_12, true, true);
        
        Button btnUpdate_1 = new Button(composite_7, SWT.NONE);
        btnUpdate_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                	table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if (text_9.getText() != "" && (text_10.getText() != "" || text_11.getText() != "" || text_12.getText() != "")) {
                        if (text_10.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Pokemon SET status = '" + text_10.getText() + "' WHERE pokemonID = " + Integer.parseInt(text_9.getText()));
                        }
                        if (text_11.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Pokemon SET roomNumber = " + Integer.parseInt(text_11.getText()) + " WHERE pokemonID = " + Integer.parseInt(text_9.getText()));
                        }
                        if (text_12.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Pokemon SET branchNumber = " + Integer.parseInt(text_12.getText()) + " WHERE pokemonID = " + Integer.parseInt(text_9.getText()));
                        }
                    } else {
                        System.out.println("Please enter Pokemon ID and one other box correctly");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Pokemon p WHERE p.pokemonID = " + text_9.getText());
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                       	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                       	NewColumn.setWidth(100);
                       	String columntitle = rsmd.getColumnName(i);
                       	NewColumn.setText(columntitle);
                    }
                   	if (!rs.isBeforeFirst()) {
                   		System.out.println("No data");
                   	}
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = rs.getString("pokemonID");
                        myarray[1] = rs.getString("name");
                        myarray[2] = rs.getString("status");
                        myarray[3] = rs.getString("gender");
                        myarray[4] = Integer.toString(rs.getInt("trainerID"));
                        myarray[5] = Integer.toString(rs.getInt("roomNumber"));
                        myarray[6] = Integer.toString(rs.getInt("branchNumber"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnUpdate_1.setBounds(217, 157, 75, 25);
        formToolkit.adapt(btnUpdate_1, true, true);
        btnUpdate_1.setText("Update");
        
        TabItem tbtmMedicalEntry = new TabItem(tabFolder_1, SWT.NONE);
        tbtmMedicalEntry.setText("Medical Entry");
        
        Composite composite_3 = new Composite(tabFolder_1, SWT.NONE);
        tbtmMedicalEntry.setControl(composite_3);
        formToolkit.paintBordersFor(composite_3);
        
        Label lblPokemonId_1 = new Label(composite_3, SWT.NONE);
        lblPokemonId_1.setBounds(75, 26, 89, 21);
        formToolkit.adapt(lblPokemonId_1, true, true);
        lblPokemonId_1.setText("Pokemon ID");
        
        text_13 = new Text(composite_3, SWT.BORDER);
        text_13.setBounds(75, 53, 73, 21);
        formToolkit.adapt(text_13, true, true);
        
        Label lblEntryNumber = new Label(composite_3, SWT.NONE);
        lblEntryNumber.setBounds(73, 95, 101, 21);
        formToolkit.adapt(lblEntryNumber, true, true);
        lblEntryNumber.setText("Entry Number");
        
        text_14 = new Text(composite_3, SWT.BORDER);
        text_14.setBounds(75, 122, 73, 21);
        formToolkit.adapt(text_14, true, true);
        
        Button btnSearch_1 = new Button(composite_3, SWT.NONE);
        btnSearch_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
                int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                    table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    if (text_13.getText() != "" && text_14.getText() != "") {
                        ResultSet rs = stmt.executeQuery("SELECT h.pokemonID, m.fileID, m.entryNumber, m.description, m.timeIn, m.timeOut FROM MedicalHistoryEntryContains m, MedicalHistory h WHERE m.fileID = h.fileID AND h.pokemonID = " + text_13.getText() + " AND m.entryNumber = " + text_14.getText());
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int count = rsmd.getColumnCount();
                        for (int i=1; i<=count; i++) {
                            TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                            NewColumn.setWidth(100);
                            String columntitle = rsmd.getColumnName(i);
                            NewColumn.setText(columntitle);
                        }
                        if (!rs.isBeforeFirst()) {
                            System.out.println("No data");
                        }
                        while (rs.next()) {
                            String[] myarray = new String[count];
                            myarray[0] = Integer.toString(rs.getInt("pokemonID"));
                            myarray[1] = Integer.toString(rs.getInt("fileID"));
                            myarray[2] = Integer.toString(rs.getInt("entryNumber"));
                            myarray[3] = rs.getString("description");
                            myarray[4] = rs.getTimestamp("timeIn").toString();
                            myarray[5] = rs.getTimestamp("timeOut").toString();
                            
                           
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                        }
                    }
                    else if (text_13.getText() != "") {
                    
                        ResultSet rs = stmt.executeQuery("SELECT h.pokemonID, m.fileID, m.entryNumber, m.description, m.timeIn, m.timeOut FROM MedicalHistoryEntryContains m, MedicalHistory h WHERE m.fileID = h.fileID AND h.pokemonID = " + text_13.getText());
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int count = rsmd.getColumnCount();
                        for (int i=1; i<=count; i++) {
                            TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                            NewColumn.setWidth(100);
                            String columntitle = rsmd.getColumnName(i);
                            NewColumn.setText(columntitle);
                        }
                        if (!rs.isBeforeFirst()) {
                            System.out.println("No data");
                        }
                        while (rs.next()) {
                            String[] myarray = new String[count];
                            myarray[0] = Integer.toString(rs.getInt("pokemonID"));
                            myarray[1] = Integer.toString(rs.getInt("fileID"));
                            myarray[2] = Integer.toString(rs.getInt("entryNumber"));
                            myarray[3] = rs.getString("description");
                            myarray[4] = rs.getTimestamp("timeIn").toString();
                            myarray[5] = rs.getTimestamp("timeOut").toString();
                            
                           
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                        }
                    }
                    else {
                        System.out.println("no input");
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnSearch_1.setBounds(238, 130, 75, 25);
        formToolkit.adapt(btnSearch_1, true, true);
        btnSearch_1.setText("Search");
        
        Label lblTimeOut = new Label(composite_3, SWT.NONE);
        lblTimeOut.setBounds(238, 26, 66, 21);
        formToolkit.adapt(lblTimeOut, true, true);
        lblTimeOut.setText("Time Out");
        
        txtTimeOut = new Text(composite_3, SWT.BORDER);
        txtTimeOut.setText("2012/06/20 21:59");
        txtTimeOut.setBounds(238, 53, 118, 21);
        formToolkit.adapt(txtTimeOut, true, true);
        
        Label lblDesciption = new Label(composite_3, SWT.NONE);
        lblDesciption.setBounds(422, 22, 82, 25);
        formToolkit.adapt(lblDesciption, true, true);
        lblDesciption.setText("Desciption");
        
        text_28 = new Text(composite_3, SWT.BORDER);
        text_28.setBounds(423, 53, 202, 127);
        formToolkit.adapt(text_28, true, true);
        
        Button btnUpdate_2 = new Button(composite_3, SWT.NONE);
        btnUpdate_2.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                	table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if (text_13.getText() != "" && text_14.getText() != "" && (text_28.getText() != "" ||
                        txtTimeOut.getText() != "2012/06/20 21:59")) {
                    	int pkmFileID = 0;
                    	Statement stm = con.createStatement();
                    	ResultSet rs = stm.executeQuery("SELECT fileID FROM MedicalHistory m WHERE m.pokemonID = " + Integer.parseInt(text_13.getText()));
                    	if (rs.next()) {
                    		pkmFileID = rs.getInt("fileID");
                    	}
                    	
                    	if (text_28.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE MedicalHistoryEntryContains SET description = '" + text_28.getText() + "' WHERE entryNumber = " + Integer.parseInt(text_14.getText() + " AND fileID = " + pkmFileID));
                        }
                        if (txtTimeOut.getText() != "2012/06/20 21:59") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE MedicalHistoryEntryContains SET timeOut = to_timestamp(" + txtTimeOut.getText() + ", 'YYYY/MM/DD HH24 MI') WHERE entryNumber = " + Integer.parseInt(text_14.getText() + " AND fileID = " + pkmFileID));
                        }
                    } else {
                        System.out.println("Please fill in both Pokemon ID and Entry Number correctly");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM MedicalHistoryEntryContains m WHERE m.pokemonID = " + Integer.parseInt(text_13.getText()));
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                       	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                       	NewColumn.setWidth(100);
                       	String columntitle = rsmd.getColumnName(i);
                       	NewColumn.setText(columntitle);
                    }
                   	if (!rs.isBeforeFirst()) {
                   		System.out.println("No data");
                   	}
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = rs.getTimestamp("timeIn").toString();
                        myarray[1] = rs.getTimestamp("timeOut").toString();
                        myarray[2] = Integer.toString(rs.getInt("entryNumber"));
                        myarray[3] = rs.getString("description");
                        myarray[4] = Integer.toString(rs.getInt("fileID"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                    
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnUpdate_2.setBounds(238, 161, 75, 25);
        formToolkit.adapt(btnUpdate_2, true, true);
        btnUpdate_2.setText("Update");
        
        TabItem tbtmNewEntry = new TabItem(tabFolder_1, SWT.NONE);
        tbtmNewEntry.setText("New Medical Entry");
        
        Composite composite_4 = new Composite(tabFolder_1, SWT.NONE);
        tbtmNewEntry.setControl(composite_4);
        formToolkit.paintBordersFor(composite_4);
        
        Label lblPokemonId_2 = new Label(composite_4, SWT.NONE);
        lblPokemonId_2.setBounds(75, 22, 91, 21);
        formToolkit.adapt(lblPokemonId_2, true, true);
        lblPokemonId_2.setText("Pokemon ID");
        
        text_15 = new Text(composite_4, SWT.BORDER);
        text_15.setBounds(75, 53, 73, 21);
        formToolkit.adapt(text_15, true, true);
        
        Label lblEmployeeId = new Label(composite_4, SWT.NONE);
        lblEmployeeId.setBounds(253, 22, 85, 21);
        formToolkit.adapt(lblEmployeeId, true, true);
        lblEmployeeId.setText("Employee ID");
        
        text_16 = new Text(composite_4, SWT.BORDER);
        text_16.setBounds(253, 53, 73, 21);
        formToolkit.adapt(text_16, true, true);
        
        Label lblTimeIn = new Label(composite_4, SWT.NONE);
        lblTimeIn.setBounds(75, 98, 73, 21);
        formToolkit.adapt(lblTimeIn, true, true);
        lblTimeIn.setText("Time In");
        
        txtDdmmyyyy = new Text(composite_4, SWT.BORDER);
        txtDdmmyyyy.setText("2009-02-24 12:51:00");
        txtDdmmyyyy.setBounds(75, 125, 118, 21);
        formToolkit.adapt(txtDdmmyyyy, true, true);
        
        Label lblDescription = new Label(composite_4, SWT.NONE);
        lblDescription.setBounds(436, 22, 85, 21);
        formToolkit.adapt(lblDescription, true, true);
        lblDescription.setText("Description");
        
        txtDescription = new Text(composite_4, SWT.BORDER);
        txtDescription.setBounds(437, 53, 201, 139);
        formToolkit.adapt(txtDescription, true, true);
        
        Button btnCreate = new Button(composite_4, SWT.NONE);
        btnCreate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
                int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                    table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                	if (text_8.getText() != "" && text_15.getText() != "") {
                		Statement stm = con.createStatement();
                		ResultSet rs = stm.executeQuery("SELECT fileID FROM MedicalHistory");
                		while (rs.next()) {
                			if (rs.getInt("fileID") == Integer.parseInt(text_8.getText())) {
                				break;
                			}
                			PreparedStatement ps = con.prepareStatement("INSERT into MedicalHistory Values (?,?)");
                			ps.setInt(1, Integer.parseInt(text_8.getText()));
                			ps.setInt(2, Integer.parseInt(text_15.getText()));
                			ps.executeUpdate();
                		}
                	}
                	
                    if (text_15.getText() != "" && text_16.getText() != "" && txtDdmmyyyy.getText() != "" &&
                        txtDescription.getText() != "" && text_8.getText() != "") {
                        PreparedStatement ps = con.prepareStatement("INSERT into MedicalHistoryEntryContains Values (?,?,?,?,?)");
                        
                        Statement stm = con.createStatement();
                        ResultSet rs = stm.executeQuery("SELECT MAX(entryNumber) FROM MedicalHistoryEntryContains m WHERE m.fileID = " + Integer.parseInt(text_8.getText()));
                        int result = 0;
                        if (rs.next()) {
                        	result = rs.getInt(1);
                        }
                        Timestamp tstamp = new Timestamp(0);
                        Timestamp.valueOf(txtDdmmyyyy.getText());
                        
                        ps.setTimestamp(1, tstamp);
                        ps.setTimestamp(2, tstamp);
                        if (result == 0) {
                        	ps.setInt(3, 1);
                        } else {
                        	ps.setInt(3, result + 1);
                        }
                        ps.setString(4, txtDescription.getText());
                        ps.setInt(5, Integer.parseInt(text_8.getText()));
                        ps.executeUpdate();
                    } else {
                        System.out.println("Please fill in all the boxes correctly");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM MedicalHistoryEntryContains");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                       	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                       	NewColumn.setWidth(100);
                       	String columntitle = rsmd.getColumnName(i);
                       	NewColumn.setText(columntitle);
                    }
                   	if (!rs.isBeforeFirst()) {
                   		System.out.println("No data");
                   	}
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = rs.getTimestamp("timeIn").toString();
                        myarray[1] = rs.getTimestamp("timeOut").toString();
                        myarray[2] = Integer.toString(rs.getInt("entryNumber"));
                        myarray[3] = rs.getString("description");
                        myarray[4] = Integer.toString(rs.getInt("fileID"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnCreate.setBounds(253, 173, 75, 25);
        formToolkit.adapt(btnCreate, true, true);
        btnCreate.setText("Create");
        
        Label lblFileId = new Label(composite_4, SWT.NONE);
        lblFileId.setBounds(253, 98, 73, 21);
        formToolkit.adapt(lblFileId, true, true);
        lblFileId.setText("File ID");
        
        text_8 = new Text(composite_4, SWT.BORDER);
        text_8.setBounds(253, 125, 73, 21);
        formToolkit.adapt(text_8, true, true);
        
        TabItem tbtmEmployeeList = new TabItem(tabFolder_1, SWT.NONE);
        tbtmEmployeeList.setText("Employee List");
        
        Composite composite_5 = new Composite(tabFolder_1, SWT.NONE);
        tbtmEmployeeList.setControl(composite_5);
        formToolkit.paintBordersFor(composite_5);
        
        Label lblEmployeeId_1 = new Label(composite_5, SWT.NONE);
        lblEmployeeId_1.setBounds(75, 22, 92, 21);
        formToolkit.adapt(lblEmployeeId_1, true, true);
        lblEmployeeId_1.setText("Employee ID");
        
        text_17 = new Text(composite_5, SWT.BORDER);
        text_17.setBounds(75, 43, 73, 21);
        formToolkit.adapt(text_17, true, true);
        
        Label lblEmployeeName = new Label(composite_5, SWT.NONE);
        lblEmployeeName.setBounds(248, 22, 117, 21);
        formToolkit.adapt(lblEmployeeName, true, true);
        lblEmployeeName.setText("Employee Name");
        
        text_18 = new Text(composite_5, SWT.BORDER);
        text_18.setBounds(248, 43, 73, 21);
        formToolkit.adapt(text_18, true, true);
        
        Label lblStartDate = new Label(composite_5, SWT.NONE);
        lblStartDate.setBounds(75, 83, 73, 21);
        formToolkit.adapt(lblStartDate, true, true);
        lblStartDate.setText("Start Date");
        
        dateTime = new DateTime(composite_5, SWT.BORDER);
        dateTime.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		dateTimeEnabled = 1;
        	}
        });
        dateTime.setBounds(75, 104, 102, 24);
        formToolkit.adapt(dateTime);
        formToolkit.paintBordersFor(dateTime);
        
        Label lblAge = new Label(composite_5, SWT.NONE);
        lblAge.setBounds(248, 83, 55, 21);
        formToolkit.adapt(lblAge, true, true);
        lblAge.setText("Age");
        
        text_19 = new Text(composite_5, SWT.BORDER);
        text_19.setBounds(248, 107, 73, 21);
        formToolkit.adapt(text_19, true, true);
        
        Label lblPhoneNumber_1 = new Label(composite_5, SWT.NONE);
        lblPhoneNumber_1.setBounds(75, 150, 102, 21);
        formToolkit.adapt(lblPhoneNumber_1, true, true);
        lblPhoneNumber_1.setText("Phone Number");
        
        text_20 = new Text(composite_5, SWT.BORDER);
        text_20.setBounds(75, 171, 73, 21);
        formToolkit.adapt(text_20, true, true);
        
        Button btnSearch_2 = new Button(composite_5, SWT.NONE);
        btnSearch_2.addMouseListener(new MouseAdapter() {
            @Override        // nurse -> employee -> search
            public void mouseDown(MouseEvent e) {
                int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                    table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    if (text_17.getText() != "") {
                    
                        ResultSet rs = stmt.executeQuery("SELECT * FROM employee e WHERE e.employeeID = " + text_17.getText());
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int count = rsmd.getColumnCount();
                        for (int i=1; i<=count; i++) {
                            TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                            NewColumn.setWidth(100);
                            String columntitle = rsmd.getColumnName(i);
                            NewColumn.setText(columntitle);
                        }
                        if (!rs.isBeforeFirst()) {
                            System.out.println("No data");
                        }
                        while (rs.next()) {
                            String[] myarray = new String[count];
                            myarray[0] = rs.getDate("startDate").toString();
                            myarray[1] = rs.getString("name");
                            myarray[2] = Integer.toString(rs.getInt("employeeID"));
                            myarray[3] = Long.toString(rs.getLong("phoneNumber"));
                            myarray[4] = Integer.toString(rs.getInt("age"));
                            
                           
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                        }
                    } else {
                        System.out.println("no input");
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        btnSearch_2.setBounds(443, 67, 75, 25);
        formToolkit.adapt(btnSearch_2, true, true);
        btnSearch_2.setText("Search");
        
        Button btnCreate_1 = new Button(composite_5, SWT.NONE);
        btnCreate_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                	table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if (text_17.getText() != "" && text_18.getText() != "" && text_19.getText() != "" &&
                        text_20.getText() != "" && dateTimeEnabled == 1) {
                        try {
                        	stringDate = new String(dateTime.getDay() + "/" + dateTime.getMonth()+1 + "/" + (dateTime.getYear() % 100));
                        	SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yy");
                        	java.util.Date utilDate = fm.parse(stringDate);
                        	sqlDate = new java.sql.Date(utilDate.getTime());
                        } catch (ParseException p) {
                        	System.out.println("Date conversion failed, parse exception");
                        }
                    	PreparedStatement ps = con.prepareStatement("INSERT into Employee Values (?,?,?,?,?)");
                        ps.setDate(1, sqlDate);
                        ps.setString(2, text_18.getText());
                        ps.setInt(3, Integer.parseInt(text_17.getText()));
                        ps.setLong(4, Long.parseLong(text_20.getText()));
                        ps.setInt(5, Integer.parseInt(text_19.getText()));
                        ps.executeUpdate();
                        ps.close();
                    } else {
                        System.out.println("Please fill in all the boxes correctly");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                       	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                       	NewColumn.setWidth(100);
                       	String columntitle = rsmd.getColumnName(i);
                       	NewColumn.setText(columntitle);
                    }
                   	if (!rs.isBeforeFirst()) {
                   		System.out.println("No data");
                   	}
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = rs.getDate("startDate").toString();
                        myarray[1] = rs.getString("name");
                        myarray[2] = Integer.toString(rs.getInt("employeeID"));
                        myarray[3] = Long.toString(rs.getLong("phoneNumber"));
                        myarray[4] = Integer.toString(rs.getInt("age"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnCreate_1.setBounds(443, 104, 75, 25);
        formToolkit.adapt(btnCreate_1, true, true);
        btnCreate_1.setText("Create");
        
        Button btnUpdate_3 = new Button(composite_5, SWT.NONE);
        btnUpdate_3.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                	table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if (text_17.getText() != "" && (text_18.getText() != "" || text_19.getText() != "" ||
                        text_20.getText() != "" || dateTimeEnabled == 1)) {
                    	if (text_18.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Employee SET name = '" + text_18.getText() + "' WHERE employeeID = " + Integer.parseInt(text_17.getText()));
                        }
                        if (text_19.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Employee SET age = " + Integer.parseInt(text_19.getText()) + " WHERE employeeID = " + Integer.parseInt(text_17.getText()));
                        }
                        if (text_20.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Employee SET phoneNumber = " + Long.parseLong(text_20.getText()) + " WHERE employeeID = " + Integer.parseInt(text_17.getText()));
                        }
                        if (dateTimeEnabled == 1) {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Employee SET startDate = {d '" + dateTime.getYear() + "-" + dateTime.getMonth()+1 + "-" + dateTime.getDay() + "'} WHERE employeeID = " + Integer.parseInt(text_17.getText()));
                        	dateTimeEnabled = 0;
                        }
                    } else {
                        System.out.println("Please fill in all the boxes correctly");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                       	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                       	NewColumn.setWidth(100);
                       	String columntitle = rsmd.getColumnName(i);
                       	NewColumn.setText(columntitle);
                    }
                   	if (!rs.isBeforeFirst()) {
                   		System.out.println("No data");
                   	}
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = rs.getTimestamp("startDate").toString();
                        myarray[1] = rs.getString("name");
                        myarray[2] = Integer.toString(rs.getInt("employeeID"));
                        myarray[3] = Long.toString(rs.getLong("phoneNumber"));
                        myarray[4] = Integer.toString(rs.getInt("age"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                    
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnUpdate_3.setBounds(443, 140, 75, 25);
        formToolkit.adapt(btnUpdate_3, true, true);
        btnUpdate_3.setText("Update");
        
        Button btnNestedquery = new Button(composite_5, SWT.NONE);
        btnNestedquery.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
                int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                    table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                	if (btnOldestEmployee.getSelection() && !btnYoungestEmployee.getSelection()) {
                		Statement stmt = con.createStatement();
                		ResultSet rs = stmt.executeQuery("SELECT MAX(age) AS MaxAge FROM Employee");

                		if (!rs.isBeforeFirst()) {
                				System.out.println("No data");
                		}
                		if (rs.next()) {
                			int max_age = rs.getInt("MaxAge");
                			try {
                				Statement stm = con.createStatement();
                				ResultSet r_s = stm.executeQuery("SELECT n.employeeID AS employID, n.pokemonID AS pkmID, e.age FROM Nurses n, Employee e WHERE e.employeeID = n.employeeID AND e.age = " + max_age);
                				
                				ResultSetMetaData rsmd = r_s.getMetaData();
                        		int count = rsmd.getColumnCount();
                        		for (int i=1; i<=count; i++) {
                        			TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                        			NewColumn.setWidth(100);
                        			String columntitle = rsmd.getColumnName(i);
                        			NewColumn.setText(columntitle);
                        		}
                        		while (r_s.next()){
                        			String[] myarray = new String[count];
                        			myarray[0] = Integer.toString(r_s.getInt(1));
                        			myarray[1] = Integer.toString(r_s.getInt(2));
                        			myarray[2] = Integer.toString(r_s.getInt(3));
                        			TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        			tableItem.setText(myarray);
                        		}
                        		
                			} catch (SQLException e2){
                				e2.printStackTrace();
                			}
                		}
                	}
                	if (btnYoungestEmployee.getSelection() && !btnOldestEmployee.getSelection()) {
                		Statement stmt = con.createStatement();
                		ResultSet rs = stmt.executeQuery("SELECT MIN(age) AS MinAge FROM Employee");

                		if (!rs.isBeforeFirst()) {
                				System.out.println("No data");
                		}
                		if (rs.next()) {
                			int min_age = rs.getInt("MinAge");
                			try {
                				Statement stm = con.createStatement();
                				ResultSet r_s = stm.executeQuery("SELECT n.employeeID AS employID, n.pokemonID AS pkmID, e.age FROM Nurses n, Employee e WHERE e.employeeID = n.employeeID AND e.age = " + min_age);
                				
                				ResultSetMetaData rsmd = r_s.getMetaData();
                        		int count = rsmd.getColumnCount();
                        		for (int i=1; i<=count; i++) {
                        			TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                        			NewColumn.setWidth(100);
                        			String columntitle = rsmd.getColumnName(i);
                        			NewColumn.setText(columntitle);
                        		}
                        		while (r_s.next()){
                        			String[] myarray = new String[count+1];
                        			myarray[0] = Integer.toString(r_s.getInt(1));
                        			myarray[1] = Integer.toString(r_s.getInt(2));
                        			myarray[2] = Integer.toString(r_s.getInt(3));
                        			TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        			tableItem.setText(myarray);
                        		}
                        		
                			} catch (SQLException e2){
                				e2.printStackTrace();
                			}
                		}
                	}
                	if (btnYoungestEmployee.getSelection() && btnOldestEmployee.getSelection()) {
                		System.out.println("Please only select one button you indecisive human");
                	}
                        
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        btnNestedquery.setBounds(580, 67, 92, 25);
        formToolkit.adapt(btnNestedquery, true, true);
        btnNestedquery.setText("NestedQuery");
        
        btnOldestEmployee = new Button(composite_5, SWT.RADIO);
        btnOldestEmployee.setBounds(582, 104, 117, 16);
        formToolkit.adapt(btnOldestEmployee, true, true);
        btnOldestEmployee.setText("Oldest Employee");
        
       btnYoungestEmployee = new Button(composite_5, SWT.RADIO);
        btnYoungestEmployee.setBounds(582, 126, 128, 16);
        formToolkit.adapt(btnYoungestEmployee, true, true);
        btnYoungestEmployee.setText("Youngest Employee");
        
        TabItem tbtmTrainerProfile = new TabItem(tabFolder_1, SWT.NONE);
        tbtmTrainerProfile.setText("Trainer Profile");
        
        Composite composite_6 = new Composite(tabFolder_1, SWT.NONE);
        tbtmTrainerProfile.setControl(composite_6);
        formToolkit.paintBordersFor(composite_6);
        
        Label lblTrainerName = new Label(composite_6, SWT.NONE);
        lblTrainerName.setBounds(262, 22, 107, 21);
        formToolkit.adapt(lblTrainerName, true, true);
        lblTrainerName.setText("Trainer Name");
        
        text_22 = new Text(composite_6, SWT.BORDER);
        text_22.setBounds(262, 43, 73, 21);
        formToolkit.adapt(text_22, true, true);
        
        Label lblAge_2 = new Label(composite_6, SWT.NONE);
        lblAge_2.setBounds(75, 70, 55, 20);
        formToolkit.adapt(lblAge_2, true, true);
        lblAge_2.setText("Age");
        
        text_23 = new Text(composite_6, SWT.BORDER);
        text_23.setBounds(75, 91, 73, 21);
        formToolkit.adapt(text_23, true, true);
        
        Label lblHomeTown = new Label(composite_6, SWT.NONE);
        lblHomeTown.setBounds(75, 118, 85, 21);
        formToolkit.adapt(lblHomeTown, true, true);
        lblHomeTown.setText("Home Town");
        
        text_24 = new Text(composite_6, SWT.BORDER);
        text_24.setBounds(75, 139, 73, 21);
        formToolkit.adapt(text_24, true, true);
        
        Label lblTrainerId_1 = new Label(composite_6, SWT.NONE);
        lblTrainerId_1.setBounds(75, 22, 73, 21);
        formToolkit.adapt(lblTrainerId_1, true, true);
        lblTrainerId_1.setText("Trainer ID");
        
        text_25 = new Text(composite_6, SWT.BORDER);
        text_25.setBounds(75, 43, 73, 21);
        formToolkit.adapt(text_25, true, true);
        
        Label lblGender_1 = new Label(composite_6, SWT.NONE);
        lblGender_1.setBounds(262, 70, 55, 20);
        formToolkit.adapt(lblGender_1, true, true);
        lblGender_1.setText("Gender");
        
        txtMf = new Text(composite_6, SWT.BORDER);
        txtMf.setText("m/f");
        txtMf.setBounds(262, 91, 34, 21);
        formToolkit.adapt(txtMf, true, true);
        
        Label lblAddress = new Label(composite_6, SWT.NONE);
        lblAddress.setBounds(262, 142, 73, 18);
        formToolkit.adapt(lblAddress, true, true);
        lblAddress.setText("Address");
        
        text_26 = new Text(composite_6, SWT.BORDER);
        text_26.setBounds(262, 166, 158, 44);
        formToolkit.adapt(text_26, true, true);
        
        Label lblPhoneNumber = new Label(composite_6, SWT.NONE);
        lblPhoneNumber.setBounds(75, 166, 107, 17);
        formToolkit.adapt(lblPhoneNumber, true, true);
        lblPhoneNumber.setText("Phone Number");
        
        text_27 = new Text(composite_6, SWT.BORDER);
        text_27.setBounds(75, 187, 107, 21);
        formToolkit.adapt(text_27, true, true);
        
        Button btnSearch_3 = new Button(composite_6, SWT.NONE);
        btnSearch_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                    table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    if (text_25.getText() != "") {
                    
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Trainer WHERE ID = " + Integer.parseInt(text_25.getText()));
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int count = rsmd.getColumnCount();
                        for (int i=1; i<=count; i++) {
                            TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                            NewColumn.setWidth(100);
                            String columntitle = rsmd.getColumnName(i);
                            NewColumn.setText(columntitle);
                        }
                        if (!rs.isBeforeFirst()) {
                            System.out.println("No data");
                        }
                        while (rs.next()) {
                            String[] myarray = new String[count];
                            myarray[0] = Integer.toString(rs.getInt("ID"));
                            myarray[1] = rs.getString("name");
                            myarray[2] = Integer.toString(rs.getInt("age"));
                            myarray[3] = rs.getString("gender");
                            myarray[4] = rs.getString("homeTown");
                            myarray[5] = rs.getString("address");
                            myarray[6] = rs.getString("phoneNumber");
                           
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                        }
                    }
                    if (text_26.getText() != "" || text_22.getText() != "" || text_23.getText() != "" || text_24.getText() != "" || text_27.getText() != "" || txtMf.getText() != "m/f") {
                    	System.out.println("Please only search via Trainer ID as we are too lazy to code for every search possibility. JK we ran out of time");
                    }
                    
                    else {
                        System.out.println("no input");
                    }
                   
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        btnSearch_3.setBounds(477, 65, 75, 25);
        formToolkit.adapt(btnSearch_3, true, true);
        btnSearch_3.setText("Search");
        
        Button btnCreateupdate = new Button(composite_6, SWT.NONE);
        btnCreateupdate.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                	table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if ((text_22.getText() != "") && (text_23.getText() != "") && (text_24.getText() != "") &&
                        (text_25.getText() != "") && (text_26.getText() != "") && (text_27.getText() != "")) {
                        PreparedStatement ps = con.prepareStatement("INSERT into Trainer Values (?,?,?,?,?,?,?)");
                        ps.setInt(1, Integer.parseInt(text_25.getText()));
                        ps.setString(2, text_22.getText());
                        ps.setInt(3, Integer.parseInt(text_23.getText()));
                        ps.setString(4, txtMf.getText());
                        ps.setString(5, text_24.getText());
                        ps.setString(6, text_26.getText());
                        ps.setLong(7, Long.parseLong(text_27.getText()));
                        ps.executeUpdate();
                        ps.close();
                    } else {
                        System.out.println("Please fill in all the boxes correctly");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Trainer");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                       	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                       	NewColumn.setWidth(100);
                       	String columntitle = rsmd.getColumnName(i);
                       	NewColumn.setText(columntitle);
                    }
                   	if (!rs.isBeforeFirst()) {
                   		System.out.println("No data");
                   	}
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = Integer.toString(rs.getInt("ID"));
                        myarray[1] = rs.getString("name");
                        myarray[2] = Integer.toString(rs.getInt("age"));
                        myarray[3] = rs.getString("gender");
                        myarray[4] = rs.getString("homeTown");
                        myarray[5] = rs.getString("address");
                        myarray[6] = Long.toString(rs.getLong("phoneNumber"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnCreateupdate.setBounds(477, 108, 73, 25);
        formToolkit.adapt(btnCreateupdate, true, true);
        btnCreateupdate.setText("Create");
        
        Button btnNewButton = new Button(composite_6, SWT.NONE);
        btnNewButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                	table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if (text_25.getText() != "" && (text_22.getText() != "" || text_23.getText() != "" ||
                        text_24.getText() != "" || text_26.getText() != "" || text_27.getText() != "" || txtMf.getText() != "m/f")) {
                    	if (text_22.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Trainer SET name = '" + text_22.getText() + "' WHERE ID = " + Integer.parseInt(text_25.getText()));
                        }
                        if (text_23.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Trainer SET age = " + Integer.parseInt(text_23.getText()) + " WHERE ID = " + Integer.parseInt(text_25.getText()));
                        }
                        if (text_24.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Trainer SET homeTown = '" + text_24.getText() + "' WHERE ID = " + Integer.parseInt(text_25.getText()));
                        }
                        if (text_26.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Trainer SET address = '" + text_26.getText() + "' WHERE ID = " + Integer.parseInt(text_25.getText()));
                        }
                        if (text_27.getText() != "") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Trainer SET phoneNumber = " + Long.parseLong(text_27.getText()) + " WHERE ID = " + Integer.parseInt(text_25.getText()));
                        }
                        if (txtMf.getText() != "m/f") {
                        	Statement stmt = con.createStatement();
                        	stmt.executeUpdate("UPDATE Trainer SET gender = '" + txtMf.getText().toLowerCase() + "' WHERE ID = " + Integer.parseInt(text_25.getText()));
                        }
                    } else {
                        System.out.println("Please fill in all the boxes correctly");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Trainer");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                       	TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                       	NewColumn.setWidth(100);
                       	String columntitle = rsmd.getColumnName(i);
                       	NewColumn.setText(columntitle);
                    }
                   	if (!rs.isBeforeFirst()) {
                   		System.out.println("No data");
                   	}
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = Integer.toString(rs.getInt("ID"));
                        myarray[1] = rs.getString("name");
                        myarray[2] = Integer.toString(rs.getInt("age"));
                        myarray[3] = rs.getString("gender");
                        myarray[4] = rs.getString("homeTown");
                        myarray[5] = rs.getString("address");
                        myarray[6] = Long.toString(rs.getLong("phoneNumber"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                    
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        	}
        });
        btnNewButton.setBounds(477, 139, 75, 25);
        formToolkit.adapt(btnNewButton, true, true);
        btnNewButton.setText("Update");
        scrolledComposite_2.setContent(tabFolder_1);
        scrolledComposite_2.setMinSize(tabFolder_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        
        
        Button btnNewButton_1 = new Button(composite_2, SWT.NONE);
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                    table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    Statement stmt = con.createStatement();
                    
                    ResultSet rs = stmt.executeQuery("SELECT trainerID, COUNT(pokemonID) AS counts FROM Pokemon WHERE status = 'healthy' GROUP BY trainerID ORDER BY trainerID");

                        ResultSetMetaData rsmd = rs.getMetaData();
                        int count = rsmd.getColumnCount();
                        for (int i=1; i<=count; i++) {
                            TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                            NewColumn.setWidth(100);
                            String columntitle = rsmd.getColumnName(i);
                            NewColumn.setText(columntitle);
                        }
                        
                        if (!rs.isBeforeFirst()) {
                            System.out.println("No data");
                        }
                        while (rs.next()) {
                            String[] myarray = new String[count];
                            myarray[0] = Integer.toString(rs.getInt("trainerID"));
                            myarray[1] = Integer.toString(rs.getInt("counts"));
                            TableItem tableItem = new TableItem(table_1, SWT.NONE);
                            tableItem.setText(myarray);
                        }
                        
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
        btnNewButton_1.setBounds(215, 214, 75, 25);
        formToolkit.adapt(btnNewButton_1, true, true);
        btnNewButton_1.setText("Freeloaders");
        
        Button btnDelete = new Button(composite_5, SWT.NONE);
        btnDelete.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseDown(MouseEvent e) {
          int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                 table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if (text_17.getText() != "" ) {
                    	PreparedStatement ps = con.prepareStatement("DELETE FROM Nurses WHERE EmployeeID = ?");
                        ps.setInt(1, Integer.parseInt(text_17.getText()));
                        ps.executeUpdate();
                        ps.close();
                    	
                    	PreparedStatement ps_1 = con.prepareStatement("DELETE FROM Employs WHERE EmployeeID = ?");
                    	ps_1.setInt(1, Integer.parseInt(text_17.getText()));
                        ps_1.executeUpdate();
                        ps_1.close();
                    	
                        PreparedStatement ps_2 = con.prepareStatement("DELETE FROM Employee WHERE EmployeeID = ?");
                        ps_2.setInt(1, Integer.parseInt(text_17.getText()));
                        ps_2.executeUpdate();
                        ps_2.close();
                    } else { 
                        System.out.println("Please enter the correct ID");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                        TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                        NewColumn.setWidth(100);
                        String columntitle = rsmd.getColumnName(i);
                        NewColumn.setText(columntitle);
                    }
                    if (!rs.isBeforeFirst()) {
                     System.out.println("No data");
                    }
                    while (rs.next()) {
                     String[] myarray = new String[count];
                     myarray[0] = rs.getDate("startDate").toString();
                        myarray[1] = rs.getString("name");
                        myarray[2] = Integer.toString(rs.getInt("employeeID"));
                        myarray[3] = Long.toString(rs.getLong("phoneNumber"));
                        myarray[4] = Integer.toString(rs.getInt("age"));
                           
                        TableItem tableItem = new TableItem(table_1, SWT.NONE);
                        tableItem.setText(myarray);
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
         }
        });
        btnDelete.setBounds(443, 187, 94, 28);
        formToolkit.adapt(btnDelete, true, true);
        btnDelete.setText("Delete Employee");
        
        Label lblBranchNumber_1 = new Label(composite_5, SWT.NONE);
        lblBranchNumber_1.setBounds(580, 171, 92, 15);
        formToolkit.adapt(lblBranchNumber_1, true, true);
        lblBranchNumber_1.setText("Branch Number");
        
        text_21 = new Text(composite_5, SWT.BORDER);
        text_21.setBounds(580, 194, 73, 21);
        formToolkit.adapt(text_21, true, true);
        
        Button btnBankrupt = new Button(composite_5, SWT.NONE);
        btnBankrupt.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		int columnCount = table_1.getColumnCount();
                while (columnCount > 0) {
                 table_1.getColumn(columnCount-1).dispose();
                    columnCount--;
                }
                table_1.removeAll();
                try {
                    if (text_21.getText() != "" && Integer.parseInt(text_21.getText()) > 0) {
                    	PreparedStatement ps = con.prepareStatement("DELETE FROM Employs WHERE branchNumber = ?");
                        ps.setInt(1, Integer.parseInt(text_21.getText()));
                        ps.executeUpdate();
                        ps.close();
                    	
                    	PreparedStatement ps_1 = con.prepareStatement("DELETE FROM PokemonCenter WHERE branchNumber = ?");
                        ps_1.setInt(1, Integer.parseInt(text_21.getText()));
                        ps_1.executeUpdate();
                        ps_1.close();
                    } else {
                        System.out.println("Please enter the correct Branch Number");
                    }
                    
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PokemonCenter");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int count = rsmd.getColumnCount();
                    for (int i=1; i<=count; i++) {
                        TableColumn NewColumn = new TableColumn(table_1, SWT.NONE);
                        NewColumn.setWidth(100);
                        String columntitle = rsmd.getColumnName(i);
                        NewColumn.setText(columntitle);
                    }
                    if (!rs.isBeforeFirst()) {
                     System.out.println("No data");
                    }
                    while (rs.next()) {
                    	String[] myarray = new String[count];
                    	myarray[0] = rs.getString("city");
                    	myarray[1] = rs.getString("region");
                    	myarray[2] = Integer.toString(rs.getInt("branchNumber"));
                           
                    	TableItem tableItem = new TableItem(table_1, SWT.NONE);
                    	tableItem.setText(myarray);
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                	e1.printStackTrace();
                }
        	}
        });
        btnBankrupt.setBounds(580, 221, 75, 25);
        formToolkit.adapt(btnBankrupt, true, true);
        btnBankrupt.setText("Bankrupt");
    }
}
