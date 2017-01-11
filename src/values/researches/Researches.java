package values.researches;

import enums.Purchases;
import main.Main;
import objects.entities.Mob;
import objects.tracks.Track;
import values.Player;

public class Researches {

	// trackID, mobID, researchID
	private static int[][][] researchLevels;

	public Researches() {
		researchLevels = new int[Track.TRACK_AMOUNT][Mob.mobTypes.size()][100];
		// There needs to be a better way to set everything to the same value...
		for (int track = 0; track < researchLevels.length; track++) {
			for (int mob = 0; mob < researchLevels[track].length; mob++) {
				for (int i = 0; i < researchLevels[track][mob].length; i++) {
					researchLevels[track][mob][i] = 0;
				}
			}
		}
	}

	public static boolean unlocked(int i) {
		if (researchLevels[Main.currentTrack.id][i][0] == 1)
			return true;
		return false;
	}

	public static boolean unlock(int mobId) {
		if (Player.research.largerThanOrEqualTo(Research.research.get(0).cost) && !Researches.unlocked(0)) {
			researchLevels[Main.currentTrack.id][mobId][0] = 1;
			Player.research.sub(Research.research.get(mobId).cost);
			return true;
//			Research.getResearch(mobId, 0).increaseCost(10000);
		}
		return false;
	}

	public static boolean buy(int id) {
		if (id % Purchases.UPGRADES_PER_MOB == 0) {
			return Researches.unlock(0);
		}
		switch (id) {
		}
		return false;
	}

	public static void sell(int i) {
		//TODO Printing
		System.out.println("Sold research with Id " + i);
	}
}
