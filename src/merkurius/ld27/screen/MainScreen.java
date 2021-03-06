package merkurius.ld27.screen;

import java.net.UnknownHostException;

import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.system.ClientSystem;
import merkurius.ld27.system.LD27AnimationSystem;
import merkurius.ld27.system.LD27InputSystem;
import merkurius.ld27.system.LD27MapSystem;
import merkurius.ld27.system.LD27PlayerSystem;
import merkurius.ld27.system.NpcSystem;
import merkurius.ld27.system.ScaledBox2DSystem;
import merkurius.ld27.system.ServerSystem;
import merkurius.ld27.system.ShootingSystem;
import merkurius.ld27.system.SpawnSystem;
import fr.kohen.alexandre.framework.base.GameScreen;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.systems.DefaultActionSystem;
import fr.kohen.alexandre.framework.systems.DefaultCameraSystem;
import fr.kohen.alexandre.framework.systems.DefaultDebugSystem;
import fr.kohen.alexandre.framework.systems.DefaultExpirationSystem;
import fr.kohen.alexandre.framework.systems.DefaultMouseSystem;
import fr.kohen.alexandre.framework.systems.DefaultRenderSystem;
import fr.kohen.alexandre.framework.systems.DefaultTextSystem;
import fr.kohen.alexandre.framework.systems.DefaultVisualSystem;

public class MainScreen extends GameScreen {
	private boolean isServer;
	protected ClientSystem syncSystem;
	private String address = "127.0.0.1";
	
	public MainScreen(boolean isServer) {
		this.isServer = isServer;
	}
	
	public void setAddress(String address) {
		this.isServer = false;
		this.address  = address;
	}

	@Override
	protected void setSystems() {	
		
		if( isServer ) {
			world.setSystem( new ServerSystem(0.03f, 4445) );
		} else {
			syncSystem = world.setSystem( new ClientSystem() );
		}
			
		world.setSystem( new DefaultCameraSystem() );
		//world.setSystem( new DefaultControlSystem(50) );
		world.setSystem( new DefaultRenderSystem(0,0,0,0) );
		world.setSystem( new DefaultVisualSystem(EntityFactoryLD27.visuals) );
		world.setSystem( new ScaledBox2DSystem() );
		world.setSystem( new DefaultExpirationSystem() );
		world.setSystem( new DefaultDebugSystem() );	
		world.setSystem( new DefaultMouseSystem() );	
		world.setSystem( new DefaultActionSystem(EntityFactoryLD27.actions) );
        world.setSystem( new DefaultTextSystem() );
		world.setSystem( new LD27MapSystem() );
		world.setSystem( new LD27AnimationSystem() );	
		world.setSystem( new LD27InputSystem() );	
        if( isServer ) {
			world.setSystem( new LD27PlayerSystem(0) );
	        world.setSystem( new ShootingSystem() );
	        world.setSystem( new SpawnSystem() );
	        world.setSystem( new NpcSystem() );
		} else {
			world.setSystem( new LD27PlayerSystem() );
		}
		
	}
	
	@Override
	protected void initialize() {
		EntityFactoryLD27.newCamera(world, 1, -200, 0, 0, 0, 0, 800, 600, 0, "cameraFollowPlayer").addToWorld();
		EntityFactoryLD27.newCamera(world, 2, 0, 0, 0, 0, 0, 800, 600, 0, "cameraInterface").addToWorld();
		EntityFactoryLD27.newMap(world, 1, "data/examples/map1.tmx", -500, -400).addToWorld();  
		
		if( isServer ) {
			EntityFactoryLD27.newPlayer(world, 1, 0, 125, 0).addComponent(new Player(0)).addToWorld();
		} else {
			try { syncSystem.connect(address, 4445); }
			catch (UnknownHostException e) { e.printStackTrace(); }
		}
	}

}
