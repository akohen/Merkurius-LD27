package merkurius.ld27.pong;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import fr.kohen.alexandre.framework.model.PhysicsBody;

public class CircularBody extends PhysicsBody {

    private float radius;
    private BodyType bodyType;

    public CircularBody(float radius, BodyType bodyType){
        this.bodyType = bodyType;
        this.radius = radius;
    }

    @Override
    public void initialize(World box2dworld) {
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesnt move we would set it to StaticBody
        bodyDef.type = bodyType;
        // Set our body's starting position in the world
        bodyDef.position.set(100, 300);

        // Create our body in the world using our body definition
        body = box2dworld.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        //Fixture fixture = body.createFixture(fixtureDef);
        body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();

    }

}
