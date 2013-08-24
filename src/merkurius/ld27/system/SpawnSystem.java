package merkurius.ld27.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.component.NPC;

public class SpawnSystem extends EntityProcessingSystem {

    private int numberOfEnnemiesToAdd = 25;

    @SuppressWarnings("unchecked")
    public SpawnSystem() {
        super( Aspect.getAspectForAll(NPC.class) );
    }

    @Override
    protected void begin(){
        for (int i =0; i < numberOfEnnemiesToAdd ;i++){
            float x = (float) (- 400.0 + Math.random() * 800);
            float y = (float) (- 300.0 + Math.random() * 600);
            int timeToLive = (int) (7000 + 6000 * Math.random());
            EntityFactoryLD27.newEnnemy(world,1,x,y,timeToLive).addToWorld();
        }
        numberOfEnnemiesToAdd = 0;
    }

    @Override
    protected void removed(Entity e) {
        numberOfEnnemiesToAdd ++;
    }

    @Override
    protected void process(Entity e) {
    }
}
