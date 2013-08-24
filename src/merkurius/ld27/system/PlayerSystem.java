package merkurius.ld27.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import fr.kohen.alexandre.framework.components.*;
import merkurius.ld27.EntityFactoryLD27;

import java.text.NumberFormat;

public class PlayerSystem extends EntityProcessingSystem {
    private static final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance();

    static {
        NUMBER_FORMATTER.setMinimumFractionDigits(1);
        NUMBER_FORMATTER.setMaximumFractionDigits(1);
    }

    protected ComponentMapper<Expires> 	expiresMapper;
    private TextComponent timeToLiveDisplayComponent;

    @SuppressWarnings("unchecked")
    public PlayerSystem() {
        super( Aspect.getAspectForAll(Player.class) );
    }

    @Override
    public void initialize(){
        expiresMapper = ComponentMapper.getFor(Expires.class, world);
        Entity timeToLiveDisplay = EntityFactoryLD27.newTimeToLiveDisplay(world, 1, 0, 280, "");
        timeToLiveDisplay.addToWorld();
        timeToLiveDisplayComponent = timeToLiveDisplay.getComponent(TextComponent.class);
    }

    @Override
    protected void process(Entity e) {
        double timeToLive = expiresMapper.get(e).getLifeTime() / 1000.0;
        timeToLiveDisplayComponent.text = new StringBuffer(NUMBER_FORMATTER.format(timeToLive));
    }
}
