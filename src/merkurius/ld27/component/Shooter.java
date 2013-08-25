package merkurius.ld27.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Shooter extends Component {

    public int timer = 0;
    public boolean firing = false;
    public Vector2 aim = new Vector2(0,0);

    public void aim(Vector2 direction) { aim = direction; }
    
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
        return firing;
    }

    public void setWantToShoot(boolean wantToShoot) {
        this.firing = wantToShoot;
    }

    public Vector2 getShootingVector() {
        return aim;
    }
}
