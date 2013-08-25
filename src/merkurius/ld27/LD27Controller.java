package merkurius.ld27;

import merkurius.ld27.screen.ServerlistScreen;
import merkurius.ld27.screen.MainScreen;
import merkurius.ld27.screen.MenuScreen;
import fr.kohen.alexandre.framework.base.GameController;

public class LD27Controller extends GameController {

	@Override
	public void create() {
		this.addScreen(new MainScreen(true), "serverScreen");
		this.addScreen(new MainScreen(false), "clientScreen");
		this.addScreen(new MenuScreen(), "menuScreen");
		this.addScreen(new ServerlistScreen(), "serverlistScreen");
		setScreen("menuScreen");
	}
	
	public void setServerAddress(String address) {
		((MainScreen) screens.get("clientScreen")).setAddress(address);
	}

}
