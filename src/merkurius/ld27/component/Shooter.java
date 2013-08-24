package merkurius.ld27.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Shooter extends Component {

    private int timer = 0;
    private boolean wantToShoot = false;

    /**
     * X and Y are used to determine the starting point of the shoot relative to the entity position. The shoot should
     * then procede in the same direction.
     */
    private Vector2 shootingVector = new Vector2(0,0);

    public void trigger( Vector2 direction){
        wantToShoot = true;
        shootingVector = direction;
    }

    public boolean canShoot() {
        return timer <= 0;
    }

    public void decrementTimer(int duration){
        timer = timer - duration;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public boolean isWantToShoot() {
        return wantToShoot;
    }

    public void setWantToShoot(boolean wantToShoot) {
        this.wantToShoot = wantToShoot;
    }

    public Vector2 getShootingVector() {
        return shootingVector;
    }
}
