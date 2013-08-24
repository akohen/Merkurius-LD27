package merkurius.ld27.visuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.kohen.alexandre.framework.base.C;
import fr.kohen.alexandre.framework.model.Visual;

public class BulletVisual extends Visual {
    private static final int        		FRAME_COLS = 8;
    private static final int        		FRAME_ROWS = 1;

    public BulletVisual(){
        Texture.setEnforcePotImages(false);
        Texture sheet = new Texture(Gdx.files.internal("data/examples/bullet.png"));

        TextureRegion[][] tmp = TextureRegion.split(
                sheet,
                sheet.getWidth() / FRAME_COLS,
                sheet.getHeight() / FRAME_ROWS);


        addAnimation(C.WALK_DOWN, 1, Animation.NORMAL, tmp[0][0]);
        addAnimation(C.WALK_DOWN_LEFT, 0.15f, Animation.NORMAL, tmp[0][1]);
        addAnimation(C.WALK_LEFT, 0.15f, Animation.NORMAL, tmp[0][2]);
        addAnimation(C.WALK_UP_LEFT, 0.15f, Animation.NORMAL, tmp[0][3]);
        addAnimation(C.WALK_UP, 0.15f, Animation.NORMAL, tmp[0][4]);
        addAnimation(C.WALK_UP_RIGHT, 0.15f, Animation.NORMAL, tmp[0][5]);
        addAnimation(C.WALK_RIGHT, 0.15f, Animation.NORMAL, tmp[0][6]);
        addAnimation(C.WALK_DOWN_RIGHT, 0.15f, Animation.NORMAL, tmp[0][7]);
    }
}

