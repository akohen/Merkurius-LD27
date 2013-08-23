package merkurius.ld27.pong;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;

import fr.kohen.alexandre.framework.base.C;
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

public class EntityFactoryPong extends EntityFactory {

public static Map<String, Visual> visuals = new HashMap<String, Visual>();
public static Map<String, Action> actions = new HashMap<String, Action>();
	
	static {
        visuals.put( "background", new BoxVisual(804, 604, Color.BLACK));
        visuals.put( "horizontal_limit", new BoxVisual(804, 6, Color.WHITE));
        visuals.put( "vertical_limit", new BoxVisual(6, 604, Color.WHITE));
        visuals.put( "middle_limit", new BoxVisual(6, 10, Color.WHITE));
		visuals.put( "example_player_visual", new BoxVisual(25, 25, Color.BLUE) );
		visuals.put( "example_box_50", new BoxVisual(50, 50, Color.RED) );
		visuals.put( "example_box_100", new BoxVisual(100, 100, Color.RED) );
		visuals.put( "example_box_green_50", new BoxVisual(50, 50, Color.GREEN) );
		visuals.put( "example_box_green_100", new BoxVisual(100, 100, Color.GREEN) );
	}

    public static Entity newBottomBar(World world, int mapId){
        Entity e = world.createEntity();
        e.addComponent(new Transform(mapId, 0, -297));
        e.addComponent(new VisualComponent("horizontal_limit"));
        e.addComponent( new PhysicsBodyComponent(new BoxBody(1, C.CATEGORY_BACKGROUND, (short) ~C.CATEGORY_PLAYER)) );
        e.addComponent(new DepthComponent(-1));
        return e;
    }

    public static Entity newTopBar(World world, int mapId){
        Entity e = world.createEntity();
        e.addComponent(new Transform(mapId, 0, 297));
        e.addComponent(new VisualComponent("horizontal_limit"));
        e.addComponent( new PhysicsBodyComponent(new BoxBody(1, C.CATEGORY_BACKGROUND, (short) ~C.CATEGORY_PLAYER)) );
        e.addComponent(new DepthComponent(-1));
        return e;
    }

    public static Entity newLeftBar(World world, int mapId){
        Entity e = world.createEntity();
        e.addComponent(new Transform(mapId, -397,0));
        e.addComponent(new VisualComponent("vertical_limit"));
        e.addComponent( new PhysicsBodyComponent(new BoxBody(1, C.CATEGORY_BACKGROUND, (short) ~C.CATEGORY_PLAYER)) );
        e.addComponent(new DepthComponent(-1));
        return e;
    }

    public static Entity newRightBar(World world, int mapId){
        Entity e = world.createEntity();
        e.addComponent(new Transform(mapId, 397,0));
        e.addComponent(new VisualComponent("vertical_limit"));
        e.addComponent( new PhysicsBodyComponent(new BoxBody(1, C.CATEGORY_BACKGROUND, (short) ~C.CATEGORY_PLAYER)) );
        e.addComponent(new DepthComponent(-1));
        return e;
    }

    public static Entity newMiddleFrontierPart(World world, int mapId, float y){
        Entity e = world.createEntity();
        e.addComponent( new Transform(mapId,0,y) );
        e.addComponent( new VisualComponent("middle_limit") );
        e.addComponent( new PhysicsBodyComponent(new BoxBody(1, C.CATEGORY_BACKGROUND, (short) ~C.CATEGORY_PLAYER)) );
        e.addComponent( new DepthComponent(-1));
        return e;
    }

    public static Entity newBackground(World world, int mapId) {
        Entity e = world.createEntity();
        e.addComponent( new Transform(mapId,0,0) );
        e.addComponent( new VisualComponent("background") );
        e.addComponent( new PhysicsBodyComponent(new BoxBody(1, C.CATEGORY_BACKGROUND, (short) ~C.CATEGORY_PLAYER)) );
        e.addComponent( new DepthComponent(-2));
        return e;
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
	
	public static Entity newText(World world, int mapId, float x, float y, String text) {
		Entity e = world.createEntity();
		e.addComponent( new Transform(mapId, x, y) );
		e.addComponent( new TextComponent(text) );
		e.addComponent( new EntityState() );
		e.addComponent( new DepthComponent(10) );
		return e;
	}




}
