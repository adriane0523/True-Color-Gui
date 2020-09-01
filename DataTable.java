import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DataTable{
	
	public DataTable() {
		
	}
	
	public JTable getDataTable()
	{
		
		TrueGuiTemplate gui1 = new TrueGuiTemplate();
		
		String[] columns = new String[] {
				"Color", "Mean", "Deviation"
		};
		
		Object[][] data = new Object[][] {
			{"Red", gui1.getAvgR() + "", gui1.getDeviationR() + "" },
			{"Green", gui1.getAvgG() + "", gui1.getDeviationG()  + ""},
			{"Blue", gui1.getAvgB() + "", gui1.getDeviationB() + ""},

			
		};
		JTable table = new JTable(data,columns);
		
		//frame.add(table);
		//frame.add(new JScrollPane(table), BorderLayout.CENTER);
	//	frame.setTitle("Table Example");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
     //   frame.pack();
      //  frame.setSize(500,800);
        //frame.setAlwaysOnTop(true);
      //  frame.setVisible(true);
        return table;
	}
	

}
