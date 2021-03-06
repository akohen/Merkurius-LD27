package merkurius.ld27.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import merkurius.ld27.EntityFactoryLD27;
import merkurius.ld27.SoundPlayer;
import merkurius.ld27.component.Shooter;

public class ShootingSystem extends EntityProcessingSystem {

    protected ComponentMapper<Shooter>      shooterMapper;
    protected ComponentMapper<Transform> 	transformMapper;
    protected ComponentMapper<Velocity>     velocityMapper;

    @SuppressWarnings("unchecked")
	public ShootingSystem() {
        super(Aspect.getAspectForAll(Shooter.class, Transform.class, Velocity.class));
    }

    @Override
    public void initialize(){
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
    }

    @Override
    protected void process(Entity e) {
        shooterMapper.get(e).decrementTimer((int) (world.getDelta()*1000));
        if (shooterMapper.get(e).isWantToShoot() && shooterMapper.get(e).canShoot()){
            Vector2 bulletPosition = transformMapper.get(e).getPosition2().cpy().add(shooterMapper.get(e).getShootingVector());
            Vector2 bulletSpeed = new Vector2(0,750);
            bulletSpeed.setAngle(shooterMapper.get(e).getShootingVector().angle());
            Entity bullet = EntityFactoryLD27.newBullet( world, 1, bulletPosition.x, bulletPosition.y, 1000, e.getId() );
            bullet.addToWorld();
            velocityMapper.get(bullet).setSpeed(bulletSpeed);
            shooterMapper.get(e).setTimer(250);
            shooterMapper.get(e).setWantToShoot(false);
            int soundNumber = (int) (1 + 5 * Math.random());
            SoundPlayer.play("shoot"+soundNumber);
        }
    }
}
