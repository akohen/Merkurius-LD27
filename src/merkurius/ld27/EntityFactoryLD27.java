package merkurius.ld27;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.DepthComponent;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.TextComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.components.VisualComponent;
import fr.kohen.alexandre.framework.model.Action;
import fr.kohen.alexandre.framework.model.Visual;
import fr.kohen.alexandre.framework.model.physicsBodies.BoxBody;
import fr.kohen.alexandre.framework.model.visuals.BoxVisual;
import fr.kohen.alexandre.framework.model.visuals.CircleVisual;

public class EntityFactoryLD27 extends EntityFactory {

public static Map<String, Visual> visuals = new HashMap<String, Visual>();
public static Map<String, Action> actions = new HashMap<String, Action>();
	
	static {
		visuals.put( "example_player_visual", new BoxVisual(25, 25, Color.BLUE) );
		visuals.put( "example_box_50", new BoxVisual(50, 50, Color.RED) );
		visuals.put( "example_box_100", new BoxVisual(100, 100, Color.RED) );
		visuals.put( "example_box_green_50", new BoxVisual(50, 50, Color.GREEN) );
		visuals.put( "example_box_green_100", new BoxVisual(100, 100, Color.GREEN) );
		visuals.put( "circle", new CircleVisual(50, Color.RED) );
	}
	
	public static Entity newPlayer(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		world.getManager(TagManager.class).register("player", e);
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new Velocity(200,200) );
		e.addComponent( new VisualComponent("example_player_visual") );
		e.addComponent( new Player() );
		e.addComponent( new EntityState() );
		return e;
	}

	public static Entity newBox(World world, int mapId, float x, float y, int size) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent("example_box_" + size) );
		e.addComponent( new PhysicsBodyComponent(new BoxBody(size)) );
		e.addComponent( new EntityState() );
		return e;		
	}

	public static Entity newCircle(World world, int mapId, float x, float y) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new VisualComponent("circle") );
		e.addComponent( new PhysicsBodyComponent(new BoxBody(100)) );
		e.addComponent( new EntityState() );
		return e;		
	}
	
	public static Entity newText(World world, int mapId, float x, float y, String text) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new TextComponent(text) );
		e.addComponent( new EntityState() );
		e.addComponent( new DepthComponent(10) );
		return e;
	}
	
	
	
	
}
