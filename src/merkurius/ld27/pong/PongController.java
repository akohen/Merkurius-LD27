package merkurius.ld27.pong;

import fr.kohen.alexandre.framework.base.GameController;

public class PongController extends GameController {

	@Override
	public void create() {
		this.addScreen(new MainScreen(), "serverScreen");
		this.addScreen(new MainScreen(), "clientScreen");
		this.addScreen(new MenuScreen(), "menuScreen");
		setScreen("menuScreen");
	}

}
