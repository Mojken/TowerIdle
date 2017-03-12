package values.researches;

import enums.Purchases;
import main.Main;
import objects.entities.Mob;
import objects.tracks.Track;
import values.Player;

public class Researches {

	// trackID, mobID, researchID, current/max
	private static int[][][][] researchLevels;

	public Researches() {
		researchLevels = new int[Track.TRACK_AMOUNT][Mob.mobTypes.size()][100][2];
		// There needs to be a better way to set everything to the same value...
		for (int track = 0; track < researchLevels.length; track++)
			for (int mob = 0; mob < researchLevels[track].length; mob++)
				for (int i = 0; i < researchLevels[track][mob].length; i++)
					for (int a = 0; a < researchLevels[track][mob][i].length; a++)
						researchLevels[track][mob][i][a] = 0;
	}

	public static boolean unlocked(int i) {
		if (researchLevels[Main.currentTrack.id][i][0][1] == 1) return true;
		return false;
	}

	public static boolean unlock(int mobId) {
		if (Player.research.largerThanOrEqualTo(Research.research.get(mobId).cost) && !Researches.unlocked(mobId)) {
			researchLevels[Main.currentTrack.id][mobId][0] = new int[] { 1, 1 };
			Player.research.sub(Research.research.get(mobId).cost);
			return true;
// Research.getResearch(mobId, 0).increaseCost(10000);
		}
		return false;
	}

	public static boolean buy(int id) {
		if (id % Purchases.UPGRADES_PER_MOB == 0) return Researches.unlock(id / Purchases.UPGRADES_PER_MOB);
		int[] aunt = researchLevels[Main.currentTrack.id][id / Purchases.UPGRADES_PER_MOB][id % Purchases.UPGRADES_PER_MOB];
		if (aunt[0] == aunt[1]) {
			if (Player.research.largerThanOrEqualTo(Research.research.get(id).cost)) {
				researchLevels[Main.currentTrack.id][id / Purchases.UPGRADES_PER_MOB][id % Purchases.UPGRADES_PER_MOB][0]++;
				researchLevels[Main.currentTrack.id][id / Purchases.UPGRADES_PER_MOB][id % Purchases.UPGRADES_PER_MOB][1]++;
				Player.research.sub(Research.research.get(id).cost);
				Research.research.get(id).increaseCost(1);
				return true;
			} else return false;
		} else researchLevels[Main.currentTrack.id][id / Purchases.UPGRADES_PER_MOB][id % Purchases.UPGRADES_PER_MOB][0]++;
		return true;
	}

	public static void sell(int id) {
		researchLevels[Main.currentTrack.id][id / Purchases.UPGRADES_PER_MOB][id % Purchases.UPGRADES_PER_MOB][0]--;
	}
	
	public static int[][][][] getResearchLevels() {
		return researchLevels;
	}
}
