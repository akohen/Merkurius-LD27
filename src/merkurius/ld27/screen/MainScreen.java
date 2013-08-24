package merkurius.ld27.screen;

import fr.kohen.alexandre.framework.systems.*;
import merkurius.ld27.EntityFactoryLD27;
import fr.kohen.alexandre.framework.base.GameScreen;
import merkurius.ld27.system.*;

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new LD27AnimationSystem() );
		world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryLD27.visuals) );
		world.setSystem( new ScaledBox2DSystem() );
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultDebugSystem() );	
		world.setSystem( new DefaultMouseSystem() );	
		world.setSystem( new DefaultActionSystem(EntityFactoryLD27.actions) );
        world.setSystem( new PlayerSystem() );
        world.setSystem( new DefaultTextSystem() );
        world.setSystem( new SpawnSystem() );
        world.setSystem( new ShootingSystem() );
        world.setSystem( new NpcSystem() );
	}
	
	@Override
	protected void initialize() {
		EntityFactoryLD27.newPlayer(world, 1, 0, 125).addToWorld();
		EntityFactoryLD27.newCamera(world, 1, 0, 0, 0, 0, 0, 800, 600, 0, "cameraFollowPlayer").addToWorld();
		EntityFactoryLD27.newCamera(world, 2, 0, 0, 0, 0, 0, 800, 600, 0, "cameraInterface").addToWorld();
	}

}
