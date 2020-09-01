import javax.swing.UIManager;

public class main{
	public static void main(String[] args)
	{
		TrueGuiTemplate gui = new TrueGuiTemplate();
		//Histogram h = new histogram();

	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
	}


}
