package merkurius.ld27.system;

import java.net.DatagramPacket;
import java.net.InetAddress;

import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.LD27GameClient;
import merkurius.ld27.component.Shooter;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public class ClientSystem extends DefaultSyncSystem {
	private ComponentMapper<Transform> 		transformMapper;
	private ComponentMapper<Velocity> 		velocityMapper;
	private ComponentMapper<Shooter> 		shooterMapper;
	private ComponentMapper<Player> 		playerMapper;
	private ComponentMapper<Parent> 		parentMapper;
	private PlayerSystem playerSystem;
	
	public ClientSystem() { super(0); }

	protected void initialize() {
		super.initialize();
		transformMapper = ComponentMapper.getFor(Transform.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        playerMapper   	= ComponentMapper.getFor(Player.class, world);
        parentMapper   	= ComponentMapper.getFor(Parent.class, world);
        
        
        playerSystem	= Systems.get(PlayerSystem.class, world);
        if ( playerSystem == null ) {
			throw new RuntimeException("Required System not loaded");
		} 
	}
	
	@Override
	public GameClient newClient(DatagramPacket packet, int port) { return null; }

	@Override
	protected GameClient addHost(InetAddress inetAddress, int port) {
		return new LD27GameClient(inetAddress, port);
	}
	
	@Override
	protected void newEntity(EntityUpdate update, int id) {
		Entity e = null;
		
		if( update.getType().equalsIgnoreCase("player") ) {
			e = EntityFactoryLD27.newPlayer(world, 1, -500, -500, id).addComponent(new Player(-1));
		} else if( update.getType().equalsIgnoreCase("npc") ) {
			e = EntityFactoryLD27.newEnemy(world, 1, -500, -500, 10000, id);
		} else if( update.getType().equalsIgnoreCase("bullet") ) {
			e = EntityFactoryLD27.newBullet(world, 1, 0, 0, 2000, 0, id);
		}
		
		if( e != null ) {
			e.addToWorld();
			updateEntity(e, update);
		}
	}

	@Override
	protected void connected(int clientId) { playerSystem.setPlayerId(clientId); Gdx.app.log("clientId", "" + clientId);}

	@Override
	protected void updateEntity(Entity e, EntityUpdate update) {
		Transform	transform	= transformMapper	.getSafe(e);
		Velocity	velocity	= velocityMapper	.getSafe(e);
		Shooter		shooter		= shooterMapper		.getSafe(e);
		Player		player		= playerMapper		.getSafe(e);
		Parent		parent		= parentMapper		.getSafe(e);
		
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
			shooter.aim.x			= update.getNextFloat();
			shooter.aim.y			= update.getNextFloat();
			shooter.firing			= update.getNextBoolean();
		}
		
		if( player != null ) {
			player.playerId 		= update.getNextInteger();
		}
		
		if( parent != null ) {
			int parentId = update.getNextInteger();
			
			if( parent.parentId == 0 ) {
				for (int i = 0, s = getActives().size(); s > i; i++) {
					Entity entity = getActives().get(i);
					if( syncMapper.get(entity).getId() == parentId ) {
						parent.parentId = entity.getId();
					}
				}
			}
		}
		
		
		
	}

	


}
