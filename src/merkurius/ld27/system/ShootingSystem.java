package merkurius.ld27.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import fr.kohen.alexandre.framework.components.Transform;
import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.component.Shooter;

public class ShootingSystem extends EntityProcessingSystem {

    protected ComponentMapper<Shooter>      shooterMapper;
    protected ComponentMapper<Transform> 	transformMapper;

    public ShootingSystem() {
        super(Aspect.getAspectForAll(Shooter.class, Transform.class));
    }

    @Override
    public void initialize(){
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
    }

    @Override
    protected void process(Entity e) {
        shooterMapper.get(e).decrementTimer((int) (world.getDelta()*1000));
        if (shooterMapper.get(e).isWantToShoot() && shooterMapper.get(e).canShoot()){
            EntityFactoryLD27.newCircle(world, 1, playerX(e) + 5, playerY(e) + 5).addToWorld();
            shooterMapper.get(e).setTimer(1000);
        }
        else{
            shooterMapper.get(e).setWantToShoot(false);
        }
    }

    private float playerX(Entity e) {
        return transformMapper.get(e).getPosition2().x;
    }

    private float playerY(Entity e) {
        return transformMapper.get(e).getPosition2().y;
    }
}
