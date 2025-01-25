import javax.swing.JFrame;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.*;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		KeyHandler kh = new KeyHandler();
		
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(kh);
		
	}

}
