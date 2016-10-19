package objects;

import main.Main;
import net.abysmal.engine.maths.Vector;
import objects.entities.Mob;
import objects.tracks.Track;

public class Spawner {
	
	Track track;
	Mob[] types;
	int[] spawnrate, relativeSpawnrate;
	Vector sp;
	
	//TODO types...
	public Spawner(Track track) {
		this.track = track;
		types = new Mob[] {Mob.mobTypes.get(0)};
		spawnrate = new int[] {500};
		relativeSpawnrate = spawnrate.clone();
		sp = track.path.getPathNode(0);
	}
	
	public void update(){
		for (int i = 0; i < relativeSpawnrate.length; i++){
			relativeSpawnrate[i]--;
			if(relativeSpawnrate[i] <= 0){
				relativeSpawnrate[i] = spawnrate[i];
				Main.currentTrack.entities.add(new Mob(sp.add(Main.currentTrack.tileSize.toVector().multiply(.5f)), types[i]));
			}
		}
	}
}