import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import com.github.kwhat.jnativehook.keyboard.*;

public class KeyHandler implements NativeKeyListener
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
	public void nativeKeyTyped(NativeKeyEvent e)
	{}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e)
	{
		int code = e.getKeyCode();
		
		switch(code)
		{
			//ESC - ��������
			case NativeKeyEvent.VC_ESCAPE: System.exit(0); break; 
			//E - ������ �����
			case NativeKeyEvent.VC_E:
				try
				{
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Robot r = new Robot();
					
					int i = 0;
					
					//����
					while(true)
					{
						System.out.println("Starting cycle");
						//��������� ����� ������� ����� (�� ���� �������)
						int x = screenSize.width/2;
						int y = screenSize.height;
						
						//������ ��������
						Rectangle area = new Rectangle(0, 0, screenSize.width, screenSize.height);
						BufferedImage screenshot = r.createScreenCapture(area);
						
						System.out.println("Screenshot made");
						
						//����� �� �� ������� ������� ����� � ������� 300 �������� �� ������?
						boolean found = false;
						for(int j = 0; j<60; j++)
						{
							y-=5;
							if(compare(new Color(screenshot.getRGB(x, y))))
							{
								found = true;
								break;
								
							}
							
						}
						
						//���� �� �����, ��������������� �� ������ ����� ������
						
						if(!found)
							continue;
						
						int dy = 0;
						while(compare(new Color(screenshot.getRGB(x, y-dy))))
							dy++;
						
						System.out.println("Coords:"+x+", "+y);
						
						//��� ���� ����� ����� �������� ����
						//� ���� ������ ���� ������� � (x,y) ������ �� �������� ��� ������ � compare
						_w: while(true)
							for(int j = 0; j<dy; j++)
								if(!compare(r.getPixelColor(x, y)))
									break _w;
						
						//�������� ������
						r.keyPress(32);
						r.keyRelease(32);
						
						System.out.println("Pressed space");
						
						Thread.sleep(750);
						
						if(i<9)
							continue;
						else
							i = 0;
						
						//��� 35 ������, ��������� ����
						Thread.sleep(35000);
						
						r.keyPress(69);
						r.keyRelease(69);
						break;
						
					}
						
				}
				catch(Exception exp)
				{/*�������?*/}
				
			break;
		
		}
		
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e)
	{}

}
