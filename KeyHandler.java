import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class KeyHandler implements KeyListener
{
	//���� ������� �����
	//private static Color trueGreen = new Color(50, 150, 90);
	
	//�������� "������" ������ � ���� a
	//������� true, ���� ������������ �������� �������������� ������� <50
	//� ������ ������ ����� ���������� �������� ���������������� (�������� ����� 50 �� ������)
	//�� � ���� �� ���������
	private static boolean compare(Color a)
	{
		int dif = abs(a.getRed()-50)+abs(a.getGreen()-150)+abs(a.getBlue()-90);
		return dif<50;
		
	}
	//������� ������
	private static int abs(int x)
	{
		return x>=0?x:-x;
		
	}
	
	//������� ��� ������� ����� �������
	@Override
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		switch(code)
		{
			//ESC - ��������
			case 27: System.exit(0); break; 
			//E - ������ �����
			case 69:
				try
				{
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Robot r = new Robot();
					
					//����
					_w1: while(true)
					{
						System.out.println("Starting cycle");
						//��������� ����� ������� ����� (�� ���� �������)
						int x = screenSize.width/2;
						int y = screenSize.height*47/60;
						
						//������ ��������
						Rectangle area = new Rectangle(0, 0, screenSize.width, screenSize.height);
						BufferedImage screenshot = r.createScreenCapture(area);
						
						System.out.println("Screenshot made");
						
						//����� �� �� ������� ������� ����� � ������� 300 �������� �� ������?
						boolean found = false;
						for(int i = 0; i<60; i++)
						{
							y+=5;
							if(compare(new Color(screenshot.getRGB(x, y))))
							{
								found = true;
								break;
								
							}
							
						}
						//���� �� �����, ��������������� �� ������ ����� ������
						if(!found)
						{
							continue _w1;
							
						} 
						System.out.println("Coords:"+x+", "+y);
						
						//��� ���� ����� ����� �������� ����
						//� ���� ������ ���� ������� � (x,y) ������ �� �������� ��� ������ � compare
						while(compare(r.getPixelColor(x, y))){}
						
						//�������� ������
						r.keyPress(32);
						r.keyRelease(32);
						
						System.out.println("Pressed space");
						//��� 35 ������, ��������� ����
						Thread.sleep(35000);
						
					}
						
				}
				catch(Exception exp)
				{/*�������?*/}
				
			break;
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{}

	@Override
	public void keyTyped(KeyEvent e)
	{}

}
