package merkurius.ld27.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import fr.kohen.alexandre.framework.components.*;
import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.component.Shooter;

import java.text.NumberFormat;

public class PlayerSystem extends EntityProcessingSystem {
    private static final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance();

    static {
        NUMBER_FORMATTER.setMinimumFractionDigits(1);
        NUMBER_FORMATTER.setMaximumFractionDigits(1);
    }

    protected ComponentMapper<Expires> 	    expiresMapper;
    protected ComponentMapper<Transform> 	transformMapper;
    protected ComponentMapper<Shooter>      shooterMapper;

    private TextComponent timeToLiveDisplayComponent;
    private Entity mouse;
    private Mouse mouseComponent;
    private Transform mouseTransform;

    @SuppressWarnings("unchecked")
    public PlayerSystem() {
        super( Aspect.getAspectForAll(Player.class, Expires.class, Transform.class, Shooter.class) );
    }

    @Override
    public void initialize(){
        expiresMapper   = ComponentMapper.getFor(Expires.class, world);
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);

        Entity timeToLiveDisplay = EntityFactoryLD27.newTimeToLiveDisplay(world, 1, 0, 280, "");
        timeToLiveDisplay.addToWorld();
        timeToLiveDisplayComponent = timeToLiveDisplay.getComponent(TextComponent.class);
    }

    @Override
    protected void begin(){
        if (mouse == null){
            mouse = world.getManager(TagManager.class).getEntity("testCamera").getComponent(CameraComponent.class).mouse;
            mouseComponent = mouse.getComponent(Mouse.class);
            mouseTransform = mouse.getComponent(Transform.class);
        }
    }

    @Override
    protected void process(Entity e) {
        double timeToLive = expiresMapper.get(e).getLifeTime() / 1000.0;
        timeToLiveDisplayComponent.text = new StringBuffer(NUMBER_FORMATTER.format(timeToLive));
        if (mouseComponent.clicked){
            Vector2 direction = mouseTransform.getPosition2().cpy().sub(transformMapper.get(e).getPosition2()).limit(10);
            shooterMapper.get(e).trigger(direction);
        }
    }
}
