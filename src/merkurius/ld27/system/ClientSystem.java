package merkurius.ld27.system;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Map.Entry;

import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.LD27GameClient;
import merkurius.ld27.component.Shooter;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public class ClientSystem extends DefaultSyncSystem {
	private ComponentMapper<Synchronize> 	syncMapper;
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<Shooter> 		shooterMapper;
	private int clientId = 0;
	
	public ClientSystem() { super(0); }

	protected void initialize() {
		super.initialize();
        syncMapper  	= ComponentMapper.getFor(Synchronize.class, world);
		transformMapper = ComponentMapper.getFor(Transform.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
	}
	
	@Override
	public GameClient newClient(DatagramPacket packet, int port) { return null; }

	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) {
		return new LD27GameClient(inetAddress, port);
	}

	@Override
	protected void process(Entity e) {
		Synchronize	sync		= syncMapper		.get(e);
		Transform	transform	= transformMapper	.getSafe(e);
		Velocity	velocity	= velocityMapper	.getSafe(e);
		Shooter		shooter		= shooterMapper		.getSafe(e);
		
		if( updates.containsKey(sync.getId()) ) {
			EntityUpdate update = updates.get( sync.getId() );
			
			if( transform != null ) {
				transform.position.x 	= update.getNextFloat();
				transform.position.y 	= update.getNextFloat();
				transform.rotation 		= update.getNextFloat();
			}
			
			if( velocity != null ) {
				velocity.speed.x		= update.getNextFloat();
				velocity.speed.y 		= update.getNextFloat();
			}
			
			if( shooter != null ) {
				shooter.aim( new Vector2(update.getNextFloat(), update.getNextFloat()) );
				shooter.setWantToShoot( update.getNextBoolean() );
			}
			
			updates.remove(sync.getId());
		}
	}
	
	protected void end() {
		for (Entry<Integer, EntityUpdate> entry : updates.entrySet()) {
			EntityUpdate update = entry.getValue();
			if( update.getType().equalsIgnoreCase("actor") ) {
				Entity actor = EntityFactoryLD27.newActor(world, 1, -500, -500, "lord_lard", 10000);
				actor.addToWorld();
				syncMapper.get(actor).setId( entry.getKey() );
			}
		}
	}

	@Override
	protected void connected(int clientId) { this.clientId = clientId; }

}
