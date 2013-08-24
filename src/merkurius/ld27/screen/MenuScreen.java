package merkurius.ld27.screen;

import merkurius.ld27.EntityFactoryLD27;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.systems.DefaultAnimationSystem;
import fr.kohen.alexandre.framework.systems.DefaultBox2DSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultControlSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultMouseSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;

public class MenuScreen extends GameScreen {

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
	}
	
	@Override
	protected void initialize() {
		//EntityFactoryExamples.newLordLard(world, 1, 0, 125).addToWorld();
		
		//EntityFactoryTest.newBox(world, 1, 100, 0, 50).addToWorld();
		EntityFactoryLD27.newCircle(world, 1, 0, 0).addToWorld();

		EntityFactoryLD27.newCamera(world, 1, 0, 0, 0, 0, 0, 640, 480, 0, "testCamera").addToWorld();
	}

}
