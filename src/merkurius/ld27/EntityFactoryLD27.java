package merkurius.ld27;

import java.util.HashMap;
import java.util.Map;

import merkurius.ld27.component.Actor;
import merkurius.ld27.component.NPC;
import merkurius.ld27.component.Shooter;
import merkurius.ld27.models.BulletAction;
import merkurius.ld27.models.BulletBody;
import merkurius.ld27.models.PlayerBody;
import merkurius.ld27.visuals.LordLardVisual;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.base.EntityFactory;
import fr.kohen.alexandre.framework.components.ActionsComponent;
import fr.kohen.alexandre.framework.components.EntityState;
import fr.kohen.alexandre.framework.components.Expires;
import fr.kohen.alexandre.framework.components.Parent;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.Player;
import fr.kohen.alexandre.framework.components.TextComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import fr.kohen.alexandre.framework.components.VisualComponent;
import fr.kohen.alexandre.framework.model.Action;
import fr.kohen.alexandre.framework.model.Visual;
import fr.kohen.alexandre.framework.model.visuals.CircleVisual;

public class EntityFactoryLD27 extends EntityFactory {

public static Map<String, Visual> visuals = new HashMap<String, Visual>();
public static Map<String, Action> actions = new HashMap<String, Action>();
	
	static {
        visuals.put( "lord_lard", new LordLardVisual("lord_lard_sheet.png") );
        visuals.put( "herr_von_speck", new LordLardVisual("herr_von_speck_sheet.png") );
        visuals.put( "circle", new CircleVisual(50, Color.RED) );
        visuals.put( "bullet", new CircleVisual(5, Color.GREEN));
        
        actions.put( "bullet_action", new BulletAction() );
	}

    public static Entity newTimeToLiveDisplay(World world, int mapId, float x, float y, String text) {
        Entity e = world.createEntity();
        e.addComponent( new Transform(mapId, x, y, -1) );
        e.addComponent( new TextComponent(text,Color.BLACK) );
        e.addComponent( new EntityState() );
        return e;
    }

    public static Entity newActor(World world, int mapId, float x, float y, String visual, int timeToLive){
        Entity e = world.createEntity();
        e.addComponent( new Transform(mapId, x, y,1) );
        e.addComponent( new Velocity() );
        e.addComponent( new VisualComponent(visual) );
        e.addComponent( new PhysicsBodyComponent(new PlayerBody()) );
        e.addComponent( new Actor() );
        e.addComponent( new EntityState() );
        e.addComponent( new Expires(timeToLive) );
        e.addComponent( new Shooter());
        return e;
    }

    public static Entity newPlayer(World world, int mapId, float x, float y) {
        Entity e = newActor(world, mapId, x, y, "lord_lard",100000)
                .addComponent(new Player());
        world.getManager(TagManager.class).register("player", e);
        world.getManager(GroupManager.class).add(e,"actors");
        return e;
    }

    public static Entity newEnnemy(World world, int mapId, float x, float y, int timeToLive) {
        Entity e = newActor(world, mapId, x, y, "herr_von_speck",timeToLive)
                .addComponent(new NPC());
        world.getManager(GroupManager.class).add(e,"actors");
        return e;
    }

    public static Entity newBullet(World world, int mapId, Vector2 position, int timeToLive, int parentId){
        Entity e = world.createEntity();
        e.addComponent( new Transform(mapId,position.x,position.y,1) );
        e.addComponent( new VisualComponent("bullet") );
        e.addComponent( new PhysicsBodyComponent(new BulletBody(0.5f)) );
        e.addComponent( new Expires(timeToLive) );
        e.addComponent( new Velocity() );
        e.addComponent( new ActionsComponent("bullet_action") );
        e.addComponent( new Parent(parentId) );
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
