import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import com.github.kwhat.jnativehook.keyboard.*;

public class KeyHandler implements NativeKeyListener
{
	//Цвет зелёного круга
	//private static Color trueGreen = new Color(50, 150, 90);
	
	//Сравнить "нужный" зелёный и цвет a
	//Выводит true, если суммамодулей разности соответсвующий каналов <50
	//В случае ошибок можно подкрутить параметр чувствительности (заменить число 50 на другое)
	//Но у меня всё сработало
	private static boolean compare(Color a)
	{
		int dif = abs(a.getRed()-50)+abs(a.getGreen()-150)+abs(a.getBlue()-90);
		return dif<50;
		
	}
	//Функция модуля
	private static int abs(int x)
	{
		return x>=0?x:-x;
		
	}
	
	//Функция при нажатии любой клавиши
	@Override
	public void nativeKeyTyped(NativeKeyEvent e)
	{}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e)
	{
		int code = e.getKeyCode();
		
		switch(code)
		{
			//ESC - закрытие
			case NativeKeyEvent.VC_ESCAPE: System.exit(0); break; 
			//E - запуск цикла
			case NativeKeyEvent.VC_E:
				try
				{
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Robot r = new Robot();
					
					int i = 0;
					
					//Цикл
					while(true)
					{
						System.out.println("Starting cycle");
						//Примерный центр зелёного круга (по моим замерам)
						int x = screenSize.width/2;
						int y = screenSize.height;
						
						//Делаем скриншот
						Rectangle area = new Rectangle(0, 0, screenSize.width, screenSize.height);
						BufferedImage screenshot = r.createScreenCapture(area);
						
						System.out.println("Screenshot made");
						
						//Нашли ли мы границу зелёного круга в радиусе 300 пикселей от центра?
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
						
						//Если не нашли, перебрасываемся на начало цикла поиска
						
						if(!found)
							continue;
						
						int dy = 0;
						while(compare(new Color(screenshot.getRGB(x, y-dy))))
							dy++;
						
						System.out.println("Coords:"+x+", "+y);
						
						//Ждём пока белая штука переечёт круг
						//В этом случае цвет пикселя в (x,y) больше не проходит как зелёный в compare
						_w: while(true)
							for(int j = 0; j<dy; j++)
								if(!compare(r.getPixelColor(x, y)))
									break _w;
						
						//Нажимаем пробел
						r.keyPress(32);
						r.keyRelease(32);
						
						System.out.println("Pressed space");
						
						Thread.sleep(750);
						
						if(i<9)
							continue;
						else
							i = 0;
						
						//Ждём 35 секунд, повторяем цикл
						Thread.sleep(35000);
						
						r.keyPress(69);
						r.keyRelease(69);
						break;
						
					}
						
				}
				catch(Exception exp)
				{/*Плевать?*/}
				
			break;
		
		}
		
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e)
	{}

}
