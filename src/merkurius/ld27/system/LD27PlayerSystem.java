package merkurius.ld27.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.base.KeyBindings;
import fr.kohen.alexandre.framework.components.*;
import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.component.Input;
import merkurius.ld27.component.Shooter;

import java.text.NumberFormat;

public class LD27PlayerSystem extends EntityProcessingSystem implements PlayerSystem {
    private static final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance();

    static {
        NUMBER_FORMATTER.setMinimumFractionDigits(1);
        NUMBER_FORMATTER.setMaximumFractionDigits(1);
    }

    protected ComponentMapper<Expires> 	    expiresMapper;
    protected ComponentMapper<Transform> 	transformMapper;
    protected ComponentMapper<Shooter>      shooterMapper;
    protected ComponentMapper<Player>      	playerMapper;
    protected ComponentMapper<Input>      	inputMapper;

    private TextComponent timeToLiveDisplayComponent;
    private Entity mouse;
    private Mouse mouseComponent;
    private Transform mouseTransform;
    private int playerId = -1;

    @SuppressWarnings("unchecked")
    public LD27PlayerSystem() {
        super( Aspect.getAspectForAll(Player.class, Expires.class, Transform.class, Shooter.class) );
    }
    
    public LD27PlayerSystem(int playerId) {
    	this();
    	this.playerId = playerId;
    }

    @Override
    public void initialize(){
        expiresMapper   = ComponentMapper.getFor(Expires.class, world);
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        playerMapper   	= ComponentMapper.getFor(Player.class, world);
        inputMapper   	= ComponentMapper.getFor(Input.class, world);

        Entity timeToLiveDisplay = EntityFactoryLD27.newTimeToLiveDisplay(world, 2, 0, 280, "");
        timeToLiveDisplay.addToWorld();
        timeToLiveDisplayComponent = timeToLiveDisplay.getComponent(TextComponent.class);
        KeyBindings.addKey(Keys.LEFT, "move_left");
		KeyBindings.addKey(Keys.RIGHT, "move_right");
		KeyBindings.addKey(Keys.UP, "move_up");
		KeyBindings.addKey(Keys.DOWN, "move_down");
		
		KeyBindings.addKey(Keys.Q, "move_left");
		KeyBindings.addKey(Keys.D, "move_right");
		KeyBindings.addKey(Keys.Z, "move_up");
		KeyBindings.addKey(Keys.S, "move_down");
    }

    @Override
    protected void begin(){
        if (mouse == null){
            mouse = world.getManager(TagManager.class).getEntity("cameraFollowPlayer").getComponent(CameraComponent.class).mouse;
            mouseComponent = mouse.getComponent(Mouse.class);
            mouseTransform = mouse.getComponent(Transform.class);
        }
    }

    @Override
    protected void process(Entity e) {
    	if( playerMapper.get(e).playerId == playerId ) {
			if (mouseComponent.clicked){
				shooterMapper.get(e).setWantToShoot(true);
			} else {
				shooterMapper.get(e).setWantToShoot(false);
			}
			Vector2 direction = mouseTransform.getPosition2().cpy().sub(transformMapper.get(e).getPosition2()).nor().mul(20);
			shooterMapper.get(e).aim(direction);
			double timeToLive = expiresMapper.get(e).getLifeTime() / 1000.0;
			if (timeToLive <0) timeToLive = 0;
	        timeToLiveDisplayComponent.text = new StringBuffer(NUMBER_FORMATTER.format(timeToLive));
	        world.getManager(TagManager.class).register("player", e);
	        
	        inputMapper.get(e).input = 0;
	        if ( KeyBindings.isKeyPressed("move_left") ) {
	        	inputMapper.get(e).input += 1;
			}
			
			if ( KeyBindings.isKeyPressed("move_right") ) {
				inputMapper.get(e).input += 2;
			} 
			
			if ( KeyBindings.isKeyPressed("move_up") ) {
				inputMapper.get(e).input += 4;
			}
			
			if ( KeyBindings.isKeyPressed("move_down") ) {
				inputMapper.get(e).input += 8;
			}
    	}        
    }
    
    public void setPlayerId(int playerId) {
    	this.playerId = playerId;
    }
}
