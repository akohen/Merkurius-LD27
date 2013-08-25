package merkurius.ld27.system;

import java.net.DatagramPacket;
import java.net.InetAddress;

import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.LD27GameClient;
import merkurius.ld27.component.Shooter;

import com.artemis.ComponentMapper;
import com.artemis.Entity;

import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public class ServerSystem extends DefaultSyncSystem {
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<Shooter> 		shooterMapper;
	private ComponentMapper<Player> 		playerMapper;
	private ComponentMapper<Parent> 		parentMapper;
	
	
	public ServerSystem(float delta, int port) {
		super(delta, port);
	}
	
	protected void initialize() {
		super.initialize();
		transformMapper = ComponentMapper.getFor(Transform.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        playerMapper   	= ComponentMapper.getFor(Player.class, world);
        parentMapper   	= ComponentMapper.getFor(Parent.class, world);
	}
	
	
	@Override
	protected void begin() { 
		super.begin();
	}

	public GameClient newClient(DatagramPacket packet, int port) {
		int playerId = newPlayerId();
		Entity player = EntityFactoryLD27.newPlayer( world, 1, -50, -50, 0 ).addComponent( new Player(playerId) );
		player.addToWorld();
		return new LD27GameClient(packet.getAddress(), port, packet.getPort(), playerId, player);
	}


	@Override
	protected void process(Entity e) {
		Synchronize	sync		= syncMapper		.get(e);
		Transform	transform	= transformMapper	.getSafe(e);
		Velocity	velocity	= velocityMapper	.getSafe(e);
		Shooter		shooter		= shooterMapper		.getSafe(e);
		Player		player		= playerMapper		.getSafe(e);
		Parent		parent		= parentMapper		.getSafe(e);
		
		// Creating the message
		String message = "update " + sync.getType() + " " + e.getId();
		
		if( transform != null )
			message += " " + transform.position.x + " " + transform.position.y + " " + transform.rotation;
		
		if( velocity != null )
			message += " " + velocity.getX() + " " + velocity.getY();
		
		if( shooter != null )
			message += " " + shooter.getShootingVector().x + " " + shooter.getShootingVector().y + " " + shooter.isWantToShoot();
		
		if( player != null )
			message += " " + player.playerId;
		
		if( parent != null )
				message += " " + parent.parentId;
		
		send(message);
	}

	
	
	@Override
	protected void connected(int clientId) { }
	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) { return null; }

	@Override
	protected void newEntity(EntityUpdate entityUpdate, int id) {}

	@Override
	protected void updateEntity(Entity e, EntityUpdate update) {}

}
