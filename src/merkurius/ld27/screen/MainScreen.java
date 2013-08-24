package merkurius.ld27.screen;

import fr.kohen.alexandre.framework.systems.*;
import merkurius.ld27.EntityFactoryLD27;
import fr.kohen.alexandre.framework.base.GameScreen;
import merkurius.ld27.system.PlayerSystem;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultAnimationSystem() );
		world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryLD27.visuals) );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultDebugSystem() );	
		world.setSystem( new DefaultMouseSystem() );
        world.setSystem( new PlayerSystem() );
        world.setSystem( new DefaultTextSystem() );
	}
	
	@Override
	protected void initialize() {
		EntityFactoryLD27.newPlayer(world, 1, 0, 125).addToWorld();
		
		EntityFactoryLD27.newCircle(world, 1, 0, 0).addToWorld();

		EntityFactoryLD27.newCamera(world, 1, 0, 0, 0, 0, 0, 800, 600, 0, "testCamera").addToWorld();
	}

}
