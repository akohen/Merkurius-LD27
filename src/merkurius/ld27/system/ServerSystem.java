package merkurius.ld27.system;

import java.net.DatagramPacket;
import java.net.InetAddress;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.LD27GameClient;
import merkurius.ld27.component.Shooter;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public class ServerSystem extends DefaultSyncSystem {
	private ComponentMapper<Synchronize> 	syncMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<Shooter> 		shooterMapper;
	
	
	public ServerSystem(float delta, int port) {
		super(delta, port);
	}
	
	protected void initialize() {
		super.initialize();
        syncMapper  	= ComponentMapper.getFor(Synchronize.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
	}
	
	
	@Override
	protected void begin() { 
		super.begin();
	}

	public GameClient newClient(DatagramPacket packet, int port) {
		Entity player = EntityFactoryLD27.newActor(world, 1, 0, 0, "lord_lard", 10000);
		player.addToWorld();
		return new LD27GameClient(packet.getAddress(), port, packet.getPort(), getPlayerId(), player);
	}


	@Override
	protected void process(Entity e) {
		Synchronize	sync		= syncMapper		.get(e);
		Transform	transform	= transformMapper	.getSafe(e);
		Velocity	velocity	= velocityMapper	.getSafe(e);
		Shooter		shooter		= shooterMapper		.getSafe(e);
		
		// Creating the message
		String message = "update " + sync.getType() + " " + e.getId();
		
		if( transform != null )
			message += " " + transform.position.x + " " + transform.position.y + " " + transform.rotation;
		
		if( velocity != null )
			message += " " + velocity.getX() + " " + velocity.getY();
		
		if( shooter != null )
			message += " " + shooter.getShootingVector().x + " " + shooter.getShootingVector().y + " " + shooter.isWantToShoot();
	
		// Sending the message for each client
		send(message);
	}

	
	
	@Override
	protected void connected(int clientId) { }
	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) { return null; }

}
