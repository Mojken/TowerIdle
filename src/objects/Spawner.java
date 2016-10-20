package objects;

import main.Main;
import net.abysmal.engine.maths.Vector;
import objects.entities.Mob;
import objects.tracks.Track;
import values.researches.Researches;

public class Spawner {

	Track track;
	Mob[] types;
	private int[] spawnrate;
	int[] relativeSpawnrate;
	Vector sp;

	// TODO types...
	public Spawner(Track track) {
		this.track = track;
		types = new Mob[Mob.mobTypes.size()];


		spawnrate = (new int[] { 500 });
		relativeSpawnrate = spawnrate.clone();
		sp = track.path.getPathNode(0);
	}

	public void update() {
		for (int i = 0; i < types.length; i++) {
			if (Researches.unlocked(i)) {
				types[i] = Mob.mobTypes.get(i);
			}
		}
		if (types[0] != null) {
			for (int i = 0; i < relativeSpawnrate.length; i++) {
				relativeSpawnrate[i]--;
				if (relativeSpawnrate[i] <= 0) {
					relativeSpawnrate[i] = getSpawnrate(i);
					Main.currentTrack.entities.add(new Mob(sp.add(Main.currentTrack.tileSize.toVector().multiply(.5f)), types[i]));
				}
			}
		}
	}

	public int getSpawnrate(int mobID) {
		return spawnrate[mobID];
	}

	public void changeSpawnrate(int mobID, int spawnrate) {
		this.spawnrate[mobID] = spawnrate;
	}
}