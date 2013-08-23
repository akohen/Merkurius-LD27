package merkurius.ld27.pong;

import com.artemis.Entity;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.math.Vector2;

import fr.kohen.alexandre.framework.components.TextComponent;
import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.components.Velocity;

public class PongGameSystem extends VoidEntitySystem {

    private int scoreLeft = 0;
    private int scoreRight = 0;
    private TextComponent componentScoreLeft;
    private TextComponent componentScoreRight;

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
        componentScoreLeft = leftDisplay.getComponent(TextComponent.class);
        componentScoreRight = rightDisplay.getComponent(TextComponent.class);

        ball = EntityFactoryPong.newBall(world,1,0,0);
        ball.addToWorld();
        ballTransform = ball.getComponent(Transform.class);
        ballVelocity = ball.getComponent(Velocity.class);
    }

    @Override
    protected void processSystem() {
        if (!gameOn){
            setBallOnMiddleAndApplyStrength();
            gameOn = true;
        }
        if (ballTransform.getPosition2().x < -380){
            componentScoreRight.text = new StringBuffer(""+(++scoreRight));
            setBallOnMiddleAndApplyStrength();
        }
        else if (ballTransform.getPosition2().x > 380){
            componentScoreLeft.text = new StringBuffer(""+(++scoreLeft));
            setBallOnMiddleAndApplyStrength();
        }
    }

    private void setBallOnMiddleAndApplyStrength(){
        ballTransform.setPosition(new Vector2(0, 0));
        float angle = (float) (-30.0 + Math.random() * 60);
        Vector2 force = new Vector2(0,250);
        force.setAngle(angle);
        boolean goRight = Math.random() > 0.5;
        ballVelocity.setSpeed(goRight? force.x : -force.x, force.y);
    }
}
