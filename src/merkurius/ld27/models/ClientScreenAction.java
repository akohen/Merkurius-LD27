package merkurius.ld27.models;

import merkurius.ld27.LD27Controller;
import merkurius.ld27.component.Server;

import com.artemis.Entity;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.model.actions.MouseAction;
import fr.kohen.alexandre.framework.systems.interfaces.ScreenSystem;

public class ClientScreenAction extends MouseAction {

	@Override
	protected void mouseClick(Entity e, Entity mouse) {
		((LD27Controller) Systems.get(ScreenSystem.class, world).getController()).setServerAddress( e.getComponent(Server.class).address );
		Systems.get(ScreenSystem.class, world).setScreen("clientScreen");
	}

	@Override
	protected void mouseRelease(Entity e, Entity mouse) {
	}

	@Override
	protected void mouseOver(Entity e, Entity mouse) {
	}

	@Override
	protected void mouseOff(Entity e, Entity mouse) {
	}
	
}
