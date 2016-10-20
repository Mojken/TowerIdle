package values.researches;

import main.Main;
import objects.entities.Mob;
import objects.tracks.Track;

public class Researches {
	
	//trackID, mobID, researchID
	private static int[][][] researchLevels;
	
	public Researches() {
		researchLevels = new int[Track.TRACK_AMOUNT][Mob.mobTypes.size()][100];
		//There needs to be a better way to set everything to the same value...
		for (int track = 0; track < researchLevels.length; track++) {
			for (int mob = 0; mob < researchLevels[track].length; mob++) {
				for (int i = 0; i < researchLevels[track][mob].length; i++){
					researchLevels[track][mob][i] = 0;
				}
			}
		}
	}

	public static boolean unlocked(int i) {
		if (researchLevels[Main.currentTrack.id][i][0] == 1) return true;
		return false;
	}
	
	public static void unlock(int i) {
		researchLevels[Main.currentTrack.id][i][0] = 1;
	}
}