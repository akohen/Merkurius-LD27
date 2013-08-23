package merkurius.ld27.pong;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Body;

import fr.kohen.alexandre.framework.base.KeyBindings;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.Velocity;

public class ControlSystem extends EntityProcessingSystem {

	protected ComponentMapper<Velocity> 	velocityMapper;
	protected ComponentMapper<EntityState> 	stateMapper;
	protected float 						force = 1;
	private ComponentMapper<PhysicsBodyComponent> bodyMapper;

	@SuppressWarnings("unchecked")
	public ControlSystem() {
		super( Aspect.getAspectForAll(Player.class, PhysicsBodyComponent.class) );
	}

	@Override
	public void initialize() {
		velocityMapper 	= ComponentMapper.getFor(Velocity.class, world);
		stateMapper 	= ComponentMapper.getFor(EntityState.class, world);
		bodyMapper 		= ComponentMapper.getFor(PhysicsBodyComponent.class, world);
		KeyBindings.addKey(Keys.UP, "move_up");
		KeyBindings.addKey(Keys.DOWN, "move_down");
		KeyBindings.addKey(Keys.Z, "move_up");
		KeyBindings.addKey(Keys.S, "move_down");
	}

	@Override
	protected void process(Entity e) {
		Body body = bodyMapper.get(e).getBody();
	
		if ( KeyBindings.isKeyPressed("move_up") ) {
			body.applyForceToCenter(0, force);
		}
		
		if ( KeyBindings.isKeyPressed("move_down") ) {
			body.applyForceToCenter(0, -force);
		}	
	
	}

}
