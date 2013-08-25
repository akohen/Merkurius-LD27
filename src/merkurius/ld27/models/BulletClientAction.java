package merkurius.ld27.models;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.kohen.alexandre.framework.components.Expires;
import fr.kohen.alexandre.framework.model.Action;

public class BulletClientAction implements Action {

	protected ComponentMapper<Expires> expiresMapper;
	protected World world;
	
	public void initialize(World world) {
		expiresMapper = ComponentMapper.getFor(Expires.class, world);
		
		this.world 	= world;
	}

	@Override
	public void beginContact(Entity e, Entity other, Contact contact) {
		if( world.getManager(GroupManager.class).inInGroup(other,"solid") ) {
			e.deleteFromWorld();
			contact.setEnabled(false);
        }
	}
	
	
	@Override
	public void process(Entity e) {	
	}
	
	@Override
	public void endContact(Entity e, Entity other, Contact contact) {
	}

	@Override
	public void preSolve(Entity e, Entity other, Contact contact) {
		e.deleteFromWorld();
	}

	@Override
	public void postSolve(Entity e, Entity other, Contact contact) {
	}

}
