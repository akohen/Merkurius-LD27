package merkurius.ld27;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public abstract class SoundPlayer {
	
	private static Map<String,Sound> sounds = new HashMap<String,Sound>();
	
	static{
		sounds.put( "shoot1", Gdx.audio.newSound(Gdx.files.internal("data/examples/shoot1.wav")));
		sounds.put( "shoot2", Gdx.audio.newSound(Gdx.files.internal("data/examples/shoot2.wav")));
		sounds.put( "shoot3", Gdx.audio.newSound(Gdx.files.internal("data/examples/shoot3.wav")));
		sounds.put( "shoot4", Gdx.audio.newSound(Gdx.files.internal("data/examples/shoot4.wav")));
		sounds.put( "shoot5", Gdx.audio.newSound(Gdx.files.internal("data/examples/shoot5.wav")));
		sounds.put( "death1", Gdx.audio.newSound(Gdx.files.internal("data/examples/enemyDeath1.wav")));
		sounds.put( "death2", Gdx.audio.newSound(Gdx.files.internal("data/examples/enemyDeath2.wav")));
	}
	
	
	public static void registerAll(Map<String,Sound> soundsToAdd){
		for (Entry<String,Sound> entry : soundsToAdd.entrySet()){
			sounds.put(entry.getKey(), entry.getValue());
		}
	}
	
	public static void play(String soundName){
		Sound sound = sounds.get(soundName);
		if (sound != null){
			sound.play();
		}
	}

}
