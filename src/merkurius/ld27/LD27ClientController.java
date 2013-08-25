package merkurius.ld27;

import merkurius.ld27.screen.MainScreen;
import fr.kohen.alexandre.framework.base.GameController;
import fr.kohen.alexandre.framework.base.GameScreen;

public class LD27ClientController extends GameController {

	@Override
	public void create() {
		GameScreen screen = new MainScreen(false);
		setScreen(screen);
	}

}
