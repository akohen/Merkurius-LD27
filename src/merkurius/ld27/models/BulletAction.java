package merkurius.ld27.models;

import merkurius.ld27.component.Actor;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.physics.box2d.Contact;

import fr.kohen.alexandre.framework.components.Expires;
import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.model.Action;

public abstract class BulletAction implements Action {

	protected ComponentMapper<Expires> expiresMapper;
	protected World world;
	private ComponentMapper<Parent> parentMapper;
	private ComponentMapper<Actor> actorMapper;
	
	public void initialize(World world) {
		expiresMapper = ComponentMapper.getFor(Expires.class, world);
		parentMapper = ComponentMapper.getFor(Parent.class, world);
		actorMapper = ComponentMapper.getFor(Actor.class, world);
		
		this.world 	= world;
	}

	@Override
	public void beginContact(Entity e, Entity other, Contact contact) {
		if ( actorMapper.has(other) ) {
			expiresMapper.get(other).reduceLifeTime(2000);
			Entity parent = world.getEntity( parentMapper.get(e).getParentId() );
			if( parent != null && parent.isActive() ) {
				expiresMapper.get(parent).increaseLifeTime(1000);
			}
		}
		e.deleteFromWorld();
	}
	
	
	@Override
	public void process(Entity e) {	
	}
	
	@Override
	public void endContact(Entity e, Entity other, Contact contact) {
	}

	protected abstract void mouseClick(Entity e, Entity mouse);
	
	protected abstract void mouseRelease(Entity e, Entity mouse);
	
	protected abstract void mouseOver(Entity e, Entity mouse);
	
	protected abstract void mouseOff(Entity e, Entity mouse);

	

	

}
