package merkurius.ld27.pong;

import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;
import fr.kohen.alexandre.framework.components.TextComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class PongGameSystem extends VoidEntitySystem {

    private StringBuffer scoreLeft;
    private StringBuffer scoreRight;

    private Entity ball;
    private Transform ballTransform;
    private Velocity ballVelocity;

    private boolean gameOn = false;


    @Override
    public void initialize(){
        Entity leftDisplay = EntityFactoryPong.newScoreDisplay(world,1,50,270,"Right");
        Entity rightDisplay = EntityFactoryPong.newScoreDisplay(world,1,-50,270,"Left");
        leftDisplay.addToWorld();
        rightDisplay.addToWorld();
        scoreLeft = leftDisplay.getComponent(TextComponent.class).text;
        scoreRight = rightDisplay.getComponent(TextComponent.class).text;
//
//        ball = world.getManager(TagManager.class).getEntity("ball");
//        ballTransform = ball.getComponent(Transform.class);
//        ballVelocity = ball.getComponent(Velocity.class);
    }

    @Override
    protected void processSystem() {

        if (!gameOn){

        }


    }
}
