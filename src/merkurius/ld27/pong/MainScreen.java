package merkurius.ld27.pong;

import com.badlogic.gdx.Gdx;

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

public class MainScreen extends GameScreen {

	@Override
	protected void setSystems() {	
		world.setSystem( new DefaultCameraSystem() );
		world.setSystem( new DefaultAnimationSystem() );
		world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem() );
		world.setSystem( new DefaultVisualSystem(EntityFactoryPong.visuals) );
		world.setSystem( new DefaultBox2DSystem() );
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultDebugSystem() );	
		world.setSystem( new DefaultMouseSystem() );	
	}
	
	@Override
	protected void initialize() {
		EntityFactoryPong.newBackground(world, 1).addToWorld();
        EntityFactoryPong.newBottomBar(world, 1).addToWorld();
        EntityFactoryPong.newTopBar(world, 1).addToWorld();
        EntityFactoryPong.newRightBar(world, 1).addToWorld();
        EntityFactoryPong.newLeftBar(world, 1).addToWorld();
        for (int y=-300; y<=300; y+=20){
            EntityFactoryPong.newMiddleFrontierPart(world,1,y).addToWorld();
        }
		EntityFactoryPong.newCamera(world, 1, 0, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, "cameraFollowPlayer").addToWorld();
	}

}
