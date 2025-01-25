import javax.swing.JFrame;

public class Main
{
	public static void main(String[] args) throws InterruptedException
	{
		JFrame frame = new JFrame();
		KeyHandler kh = new KeyHandler();
		frame.addKeyListener(kh);
		frame.setTitle("Whatever");
		frame.setAlwaysOnTop(true);
		frame.setFocusable(true);
		frame.setBounds(25, 25, 100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}

}
