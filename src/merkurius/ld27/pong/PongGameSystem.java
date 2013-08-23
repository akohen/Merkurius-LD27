package merkurius.ld27.pong;

import com.artemis.Entity;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

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

        ball = EntityFactoryPong.newBall(world,1,0,0);
        ball.addToWorld();
        ballTransform = ball.getComponent(Transform.class);
        ballVelocity = ball.getComponent(Velocity.class);
    }

    @Override
    protected void processSystem() {

        if (!gameOn){
            setBallOnMiddleAndApplyStrength();
        }
        if (ballTransform.getPosition2().x < -380) {
            scoreRight = new StringBuffer(Integer.parseInt(scoreRight.toString()) + 1);
            setBallOnMiddleAndApplyStrength();
        }
        else if (ballTransform.getPosition2().x > 380) {
            scoreLeft = new StringBuffer(Integer.parseInt(scoreLeft.toString()) + 1);
            setBallOnMiddleAndApplyStrength();
        }
    }

    private void setBallOnMiddleAndApplyStrength(){
        ballTransform.setPosition(new Vector2(0,0));
        ballVelocity.setSpeed(250, 0);
        Gdx.app.log("GameSystem", "start");
        gameOn = true;
//        float angle = Math.random()
//        ballBody.applyForceToCenter( new Vector2(0,1).setAngle(45));

    }
}
