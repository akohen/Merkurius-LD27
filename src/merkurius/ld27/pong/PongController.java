package merkurius.ld27.pong;

import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public class PongController extends GameController {

	@Override
	public void create() {
		GameScreen screen = new MainScreen();
		setScreen(screen);
	}

}
