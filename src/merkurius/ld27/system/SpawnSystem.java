package merkurius.ld27.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.SoundPlayer;
import merkurius.ld27.component.NPC;

public class SpawnSystem extends EntityProcessingSystem {

    private int numberOfEnnemiesToAdd = 2;
    private int cooldown = 50;

    @SuppressWarnings("unchecked")
    public SpawnSystem() {
        super( Aspect.getAspectForAll(NPC.class) );
    }

    @Override
    protected void begin(){
    	if( numberOfEnnemiesToAdd > 0 ) {
    		if ( cooldown == 0 ) {
	    		float x = (float) (- 400.0 + Math.random() * 800);
	            float y = (float) (- 300.0 + Math.random() * 600);
	            int timeToLive = (int) (7000 + 6000 * Math.random());
	            EntityFactoryLD27.newEnemy(world,1,x,y,timeToLive).addToWorld();
	            numberOfEnnemiesToAdd--;
	            cooldown = 50;
    		} else {
    			cooldown--;
    		}
    	}
        
    }

    @Override
    protected void removed(Entity e) {
        numberOfEnnemiesToAdd ++;
        SoundPlayer.play("death"+ (Math.random() > 0.5 ? 1 : 2));
    }

    @Override
    protected void process(Entity e) {
    }
}
