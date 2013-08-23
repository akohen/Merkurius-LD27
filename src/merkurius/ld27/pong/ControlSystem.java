package merkurius.ld27.pong;

import com.artemis.ComponentMapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Input.Keys;

import fr.kohen.alexandre.framework.base.KeyBindings;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.Velocity;

public class ControlSystem extends VoidEntitySystem {

	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected float 						force = 1000;
	private ComponentMapper<PhysicsBodyComponent> bodyMapper;

	public ControlSystem() {
		super();
	}

	public void initialize() {
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
		stateMapper 	= ComponentMapper.getFor(EntityState.class, world);
		bodyMapper 		= ComponentMapper.getFor(PhysicsBodyComponent.class, world);
		KeyBindings.addKey(Keys.UP, "player_right_move_up");
		KeyBindings.addKey(Keys.DOWN, "player_right_move_down");

		KeyBindings.addKey(Keys.Z, "player_left_move_up");
		KeyBindings.addKey(Keys.W, "player_left_move_up");
		KeyBindings.addKey(Keys.S, "player_left_move_down");
	}


	@Override
	protected void processSystem() {
		if ( KeyBindings.isKeyPressed("player_left_move_up") ) {
			bodyMapper.get( world.getManager(TagManager.class).getEntity("playerLeft") ).getBody().applyForceToCenter(0, force);
		}
		if ( KeyBindings.isKeyPressed("player_left_move_down") ) {
			bodyMapper.get( world.getManager(TagManager.class).getEntity("playerLeft") ).getBody().applyForceToCenter(0, -force);
		}
		
		if ( KeyBindings.isKeyPressed("player_right_move_up") ) {
			bodyMapper.get( world.getManager(TagManager.class).getEntity("playerRight") ).getBody().applyForceToCenter(0, force);
		}
		if ( KeyBindings.isKeyPressed("player_right_move_down") ) {
			bodyMapper.get( world.getManager(TagManager.class).getEntity("playerRight") ).getBody().applyForceToCenter(0, -force);
		}	
	}

}
