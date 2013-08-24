package merkurius.ld27.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import fr.kohen.alexandre.framework.model.PhysicsBody;

public class PlayerBody extends PhysicsBody {

    public PlayerBody() {
    }

    @Override
    public void initialize(World box2dworld) {
        // Create our body definition
        BodyDef groundBodyDef =new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(50, 100));
        groundBodyDef.type = BodyType.DynamicBody;
        groundBodyDef.fixedRotation = true;
        
        // Create a body from the defintion and add it to the world
        body = box2dworld.createBody(groundBodyDef);
        body.setLinearDamping(10f);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(0.8f, 1.4f);
        // Create a fixture from our polygon shape and add it to our ground body
        
        body.createFixture(groundBox, 1.0f);
        
		// Clean up after ourselves
        groundBox.dispose();

    }

}
