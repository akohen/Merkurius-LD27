package merkurius.ld27.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;
import merkurius.ld27.component.NPC;
import merkurius.ld27.component.Shooter;

public class NpcSystem extends EntityProcessingSystem {

    private ComponentMapper<Shooter> shooterMapper;
    private ComponentMapper<Transform> transformMapper;
    private ComponentMapper<Velocity> velocityMapper;

    private ImmutableBag<Entity> actors;

    @SuppressWarnings("unchecked")
	public NpcSystem() {
        super(Aspect.getAspectForAll(NPC.class, Shooter.class, Transform.class, Velocity.class));
    }

    @Override
    public void initialize(){
        transformMapper = ComponentMapper.getFor(Transform.class, world);
        shooterMapper   = ComponentMapper.getFor(Shooter.class, world);
        velocityMapper  = ComponentMapper.getFor(Velocity.class, world);
    }

    @Override
    protected void begin(){
        actors = world.getManager(GroupManager.class).getEntities("actors");
    }

    @Override
    protected void process(Entity e) {
        Entity closest = findClosestActor(e);
        Vector2 direction = direction(e,closest);
        goInDirectionOf(e,direction);
        shooterMapper.get(e).aim(direction);
        shooterMapper.get(e).setWantToShoot(true);
    }

    private void goInDirectionOf(Entity e, Vector2 direction){
        Vector2 speed = new Vector2(150,0);
        speed.setAngle(direction.angle());
        velocityMapper.get(e).setSpeed(speed);
    }

    private Entity findClosestActor(Entity e) {
        Entity closest = actors.get(0);
        float distToClosest = dist(e,actors.get(0));
        for (int i=0; i<actors.size(); i++){
            if (dist(e,actors.get(i)) < distToClosest && actors.get(i).getId() != e.getId()){
               distToClosest = dist(e,actors.get(i));
               closest = actors.get(i);
            }
        }
        return closest;
    }

    private float dist(Entity first,Entity other){
        return transformMapper.get(first).getPosition2().dst(transformMapper.get(other).getPosition2());
    }

    private Vector2 direction(Entity first, Entity target){
       return  transformMapper.get(target).getPosition2().cpy().sub(transformMapper.get(first).getPosition2()).nor().mul(20);
    }
}
