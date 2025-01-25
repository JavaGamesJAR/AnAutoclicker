import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class KeyHandler implements KeyListener
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
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		switch(code)
		{
			//ESC - закрытие
			case 27: System.exit(0); break; 
			//E - запуск цикла
			case 69:
				try
				{
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					Robot r = new Robot();
					
					//Цикл
					_w1: while(true)
					{
						System.out.println("Starting cycle");
						//Примерный центр зелёного круга (по моим замерам)
						int x = screenSize.width/2;
						int y = screenSize.height*47/60;
						
						//Делаем скриншот
						Rectangle area = new Rectangle(0, 0, screenSize.width, screenSize.height);
						BufferedImage screenshot = r.createScreenCapture(area);
						
						System.out.println("Screenshot made");
						
						//Нашли ли мы границу зелёного круга в радиусе 300 пикселей от центра?
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
						//Если не нашли, перебрасываемся на начало цикла поиска
						if(!found)
						{
							continue _w1;
							
						} 
						System.out.println("Coords:"+x+", "+y);
						
						//Ждём пока белая штука переечёт круг
						//В этом случае цвет пикселя в (x,y) больше не проходит как зелёный в compare
						while(compare(r.getPixelColor(x, y))){}
						
						//Нажимаем пробел
						r.keyPress(32);
						r.keyRelease(32);
						
						System.out.println("Pressed space");
						//Ждём 35 секунд, повторяем цикл
						Thread.sleep(35000);
						
					}
						
				}
				catch(Exception exp)
				{/*Плевать?*/}
				
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
