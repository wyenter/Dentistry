import javax.swing.;
import javax.swing.table.;
import javax.swing.event.;
import java.util.;
import java.awt.;
import java.awt.event.;

public class Calendar extends JApplet{
static JFrame frame;
static Container pane;
static JButton next, prev;
static JLabel month, year;
static JTable calendar;
static DefaultTableModel mdlCalendar;
static JScrollPane scroll;
static JPanel panel;
static int realDay, realMonth, realYear, currentMonth, currentYear;
//year selection
static JComboBox yearSelect;

public static void main(String args[]){
	//theme
	try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
	catch (ClassNotFoundException e) {}
	catch (InstantiationException e) {}
	catch (IllegalAccessException e) {}
	catch (UnsupportedLookAndFeelException e) {}
	
	//make frame
	frame = new JFrame("Appointments");
	frame.setSize(330, 375);
	pane = frame.getContentPane();
	pane.setLayout(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	month = new JLabel("January");
	year = new JLabel("Change year:");
	yearSelect = new JComboBox();
	prev = new JButton("<<");
	next = new JButton(">>");
	mdlCalendar = new DefaultTableModel();
	calendar = new JTable(mdlCalendar);
	scroll = new JScrollPane(calendar);
	
	
	panel = new JPanel(null);
	panel.setBorder(BorderFactory.createTitledBorder("Calendar")); //set border
	
	//add controls to pane
	pane.add(panel);
	panel.add(month);
	panel.add(year);
	panel.add(yearSelect);
	panel.add(prev);
	panel.add(next);
	panel.add(scroll);
	
	//positioning
	panel.setBounds(0, 0, 320, 335);
	month.setBounds(160-month.getPreferredSize().width/2, 25, 100, 25);
	year.setBounds(10, 305, 80, 20);
	yearSelect.setBounds(230, 305, 80, 20);
	prev.setBounds(10, 25, 50, 25);
	next.setBounds(260, 25, 50, 25);
	scroll.setBounds(10, 50, 300, 250);
	
	//make visible
	frame.setResizable(false);
	frame.setVisible(true);
	pane.setVisible(true);
	month.setVisible(true);
	year.setVisible(true);
	yearSelect.setVisible(true);
	prev.setVisible(true);
	next.setVisible(true);
	calendar.setVisible(true);
	scroll.setVisible(true);
	panel.setVisible(true);
	
	//Get the real dates
	GregorianCalendar cal = new GregorianCalendar();
	realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
	realMonth = cal.get(GregorianCalendar.MONTH);
	realYear = cal.get(GregorianCalendar.YEAR);
	currentMonth = realMonth;
	currentYear = realYear;
	
	//Populate ComboBox
	for(int i = realYear - 100; i < realYear + 100; i++){
		yearSelect.addItem(String.valueOf(i));
	}
	
	String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
	for (int i=0; i<7; i++){
	    mdlCalendar.addColumn(headers[i]);
	}
	
	//no reszing or reordering
	calendar.getParent().setBackground(calendar.getBackground());
	calendar.getTableHeader().setResizingAllowed(false);
	calendar.getTableHeader().setReorderingAllowed(false);

	//Single cell selection
	calendar.setColumnSelectionAllowed(true);
	calendar.setRowSelectionAllowed(true);
	calendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	//Set row/column count
	calendar.setRowHeight(38);
	mdlCalendar.setColumnCount(7);
	mdlCalendar.setRowCount(6);
	
	refreshCalendar(realMonth, realYear);

	//set up event listeners
	prev.addActionListener(new prevAction());
	next.addActionListener(new nextAction());
	yearSelect.addActionListener(new selectYear());
}

//Refreshes the calendar
public static void refreshCalendar(int mon, int year){
	String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	int nod, som; //Number of Days, Start of Month
	
	prev.setEnabled(true);
	next.setEnabled(true);
	if (mon == 0 && year <= realYear-10){prev.setEnabled(false);} //Too early
	if (mon == 11 && year >= realYear+100){next.setEnabled(false);} //Too late
	month.setText(months[mon]);
	month.setBounds(160-month.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
	yearSelect.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
	
	//Get first day of month and number of days
	GregorianCalendar cal = new GregorianCalendar(year, mon, 1);
	nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	som = cal.get(GregorianCalendar.DAY_OF_WEEK);
	
	//Clear table
	for (int i=0; i<6; i++){
	    for (int j=0; j<7; j++){
	        mdlCalendar.setValueAt(null, i, j);
	    }
	}
	
	//Draw calendar
	for (int i=1; i<=nod; i++){
	    int row = ((i+som-2)/7);
	    int column  =  (i+som-2)%7;
                Integer j = new Integer(i);
	    mdlCalendar.setValueAt(j, row, column);
	}
}
static class prevAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentMonth == 0){
			currentMonth = 11;
			currentYear -= 1;
		}
		else{
			currentMonth -= 1;
		}
		refreshCalendar(currentMonth, currentYear);
	}
 }

static class nextAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if(currentMonth == 11){
			currentMonth = 0;
			currentYear += 1;
		}
		else{
			currentMonth += 1;
		}
		refreshCalendar(currentMonth, currentYear);
	}
 }

static class selectYear implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e){
		if(yearSelect.getSelectedItem() != null){
			String b = yearSelect.getSelectedItem().toString();
			currentYear = Integer.parseInt(b);
			refreshCalendar(currentMonth, currentYear);
		}
	}
}