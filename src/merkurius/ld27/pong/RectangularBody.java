package merkurius.ld27.pong;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import fr.kohen.alexandre.framework.model.PhysicsBody;

public class RectangularBody extends PhysicsBody {

    private float width;
    private float length;
    private short categoryBits = 0x0001;
    private short maskBits = -1;
    private BodyType bodyType;

    public RectangularBody(float width, float length, BodyType bodyType) {
        this.width = width;
        this.length = length;
        this.bodyType = bodyType;
    }

    public RectangularBody(float width, float length, BodyType bodyType, short categoryBits, short maskBits) {
        this.width = width;
        this.length = length;
        this.categoryBits = categoryBits;
        this.maskBits = maskBits;
        this.bodyType = bodyType;
    }

    @Override
    public void initialize(World box2dworld) {
        // Create our body definition
        BodyDef bodyDef =new BodyDef();
        // Set its world position
        bodyDef.position.set(new Vector2(0, 10));
        bodyDef.type = bodyType;

        // Create a body from the defintion and add it to the world
        body = box2dworld.createBody(bodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(width/2, length/2);
        // Create a fixture from our polygon shape and add it to our ground body
        Fixture fixture = body.createFixture(groundBox, 0.0f);
        fixture.setFriction(0.5f);

        Filter filter = new Filter();
        filter.categoryBits = this.categoryBits;
        filter.maskBits = this.maskBits;

        fixture.setFilterData(filter);

        // Clean up after ourselves
        groundBox.dispose();
    }
}
