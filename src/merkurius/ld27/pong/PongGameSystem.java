package merkurius.ld27.pong;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import fr.kohen.alexandre.framework.components.PhysicsBodyComponent;
import fr.kohen.alexandre.framework.components.TextComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class PongGameSystem extends VoidEntitySystem {

    private StringBuffer scoreLeft;
    private StringBuffer scoreRight;

    private Entity ball;
    private Transform ballTransform;
    private Velocity ballVelocity;
    private Body ballBody;

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
       ballBody = ball.getComponent(PhysicsBodyComponent.class).getBody();
    }

    @Override
    protected void processSystem() {
        if (!gameOn){
            setBallOnMiddleAndApplyStrength();
        }
        if (ballTransform.getPosition2().x < -380){
            scoreRight = new StringBuffer(Integer.parseInt(scoreRight.toString()) + 1);
            setBallOnMiddleAndApplyStrength();
        }
        else if (ballTransform.getPosition2().x > 380){
            scoreLeft = new StringBuffer(Integer.parseInt(scoreLeft.toString()) + 1);
            setBallOnMiddleAndApplyStrength();
        }
    }

    private void setBallOnMiddleAndApplyStrength(){
        ballTransform.setPosition(new Vector2(0,0));
//        float angle = Math.random()
//        ballBody.applyForceToCenter( new Vector2(0,1).setAngle(45));

    }
}
