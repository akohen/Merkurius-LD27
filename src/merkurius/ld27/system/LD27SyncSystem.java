package merkurius.ld27.system;

import java.net.DatagramPacket;
import java.net.InetAddress;

import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.LD27GameClient;
import merkurius.ld27.component.Input;
import merkurius.ld27.component.Shooter;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;

import fr.kohen.alexandre.framework.base.Systems;
import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Synchronize;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.network.GameClient;
import fr.kohen.alexandre.framework.systems.DefaultSyncSystem;

public abstract class LD27SyncSystem extends DefaultSyncSystem {
	protected ComponentMapper<Transform> 		transformMapper;
	protected ComponentMapper<Velocity> 		velocityMapper;
	protected ComponentMapper<Shooter> 		shooterMapper;
	protected ComponentMapper<Player> 		playerMapper;
	protected ComponentMapper<Parent> 		parentMapper;
	protected ComponentMapper<Input>      	inputMapper;
	protected PlayerSystem playerSystem;
	protected int playerId = -1;
	protected Entity playerEntity;
	
	public LD27SyncSystem(float delta, int port) { super(delta, port); }
	
	public LD27SyncSystem() { super(0); }

	protected void initialize() {
		super.initialize();
		transformMapper = ComponentMapper.getFor(Transform.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        playerMapper   	= ComponentMapper.getFor(Player.class, world);
        parentMapper   	= ComponentMapper.getFor(Parent.class, world);
		inputMapper 	= ComponentMapper.getFor(Input.class, world);
        
        
        playerSystem	= Systems.get(PlayerSystem.class, world);
        if ( playerSystem == null ) {
			throw new RuntimeException("Required System not loaded");
		} 
	}
	
	
	@Override
	public GameClient newClient(DatagramPacket packet, int port) {
		int playerId = newPlayerId();
		Entity player = EntityFactoryLD27.newPlayer( world, 1, -50, -50, 0 ).addComponent( new Player(playerId) );
		player.addToWorld();
		return new LD27GameClient(packet.getAddress(), port, packet.getPort(), playerId, player);
	}
	
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
			e = EntityFactoryLD27.newBulletClient(world, 1, 0, 0, 2000, 0, id);
		}
		
		if( e != null ) {
			e.addToWorld();
			updateEntity(e, update);
		}
	}

	@Override
	protected void connected(int clientId) { 
		this.playerId = clientId;
		playerSystem.setPlayerId(clientId); 
		Gdx.app.log("clientId", "" + clientId);
	}

	protected String entitySendUpdate(Entity e) {
		Synchronize	sync		= syncMapper		.get(e);
		Transform	transform	= transformMapper	.getSafe(e);
		Velocity	velocity	= velocityMapper	.getSafe(e);
		Shooter		shooter		= shooterMapper		.getSafe(e);
		Player		player		= playerMapper		.getSafe(e);
		Parent		parent		= parentMapper		.getSafe(e);
		Input		input		= inputMapper		.getSafe(e);
		
		if( syncMapper.get(e).getId() == 0 ) {
			syncMapper.get(e).setId( e.getId() );
		}
		
		// Creating the message
		String message = "update " + sync.getType() + " " + sync.getId();
		
		if( transform != null )
			message += " " + transform.position.x + " " + transform.position.y + " " + transform.rotation;
		
		if( velocity != null )
			message += " " + velocity.getX() + " " + velocity.getY();
		
		if( player != null )
			message += " " + player.playerId;
		
		if( shooter != null )
			message += " " + shooter.getShootingVector().x + " " + shooter.getShootingVector().y + " " + shooter.isWantToShoot();
		
		if( parent != null )
				message += " " + parent.parentId;
		
		if( input != null )
				message += " " + input.input;
		
		send(message);
		return message;
	}
	
	protected void entityApplyUpdate(Entity e, EntityUpdate update) {
		Transform	transform	= transformMapper	.getSafe(e);
		Velocity	velocity	= velocityMapper	.getSafe(e);
		Shooter		shooter		= shooterMapper		.getSafe(e);
		Player		player		= playerMapper		.getSafe(e);
		Parent		parent		= parentMapper		.getSafe(e);
		Input		input		= inputMapper		.getSafe(e);
		
		if( transform != null ) {
			transform.position.x 	= update.getNextFloat();
			transform.position.y 	= update.getNextFloat();
			transform.rotation 		= update.getNextFloat();
		}
		
		if( velocity != null ) {
			velocity.speed.x		= update.getNextFloat();
			velocity.speed.y 		= update.getNextFloat();
		}
		
		if( player != null ) {
			player.playerId 		= update.getNextInteger();
		}
		
		if( shooter != null ) {
			shooter.aim.x			= update.getNextFloat();
			shooter.aim.y			= update.getNextFloat();
			shooter.firing			= update.getNextBoolean();
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
		
		if( input != null ) {
			input.input 		= update.getNextInteger();
		}		
	}
	
	

}
