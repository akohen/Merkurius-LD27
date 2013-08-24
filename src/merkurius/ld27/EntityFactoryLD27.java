package merkurius.ld27;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.*;
import fr.kohen.alexandre.framework.model.Action;
import fr.kohen.alexandre.framework.model.Visual;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;
import fr.kohen.alexandre.framework.model.visuals.CircleVisual;
import merkurius.ld27.models.PlayerBody;
import merkurius.ld27.visuals.LordLardVisual;

public class EntityFactoryLD27 extends EntityFactory {

public static Map<String, Visual> visuals = new HashMap<String, Visual>();
public static Map<String, Action> actions = new HashMap<String, Action>();
	
	static {
        visuals.put( "lord_lard", new LordLardVisual() );
        visuals.put( "circle", new CircleVisual(50, Color.RED) );
	}

    public static Entity newTimeToLiveDisplay(World world, int mapId, float x, float y, String text) {
        Entity e = world.createEntity();
        e.addComponent( new Transform(mapId, x, y, -1) );
        e.addComponent( new TextComponent(text,Color.BLACK) );
        e.addComponent( new EntityState() );
        return e;
    }
	
	public static Entity newPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		e.addComponent( new Transform(mapId, x, y,1) );
		e.addComponent( new Velocity() );
		e.addComponent( new VisualComponent("lord_lard") );
        e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
        e.addComponent( new Player() );
		e.addComponent( new EntityState() );
        e.addComponent( new Expires(10000) );
		return e;
	}

	public static Entity newCircle(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y,-1) );
		e.addComponent( new VisualComponent("circle") );
		e.addComponent( new PhysicsBodyComponent(new BoxBody(100)) );
		e.addComponent( new EntityState() );
		return e;		
	}
	
	public static Entity newText(World world, int mapId, float x, float y, String text) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y, -1) );
		e.addComponent( new TextComponent(text) );
		e.addComponent( new EntityState() );
		return e;
	}
	
	
	
	
}
