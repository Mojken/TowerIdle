package values.researches;

import java.net.URL;
import java.util.ArrayList;
import enums.Purchases;
import net.abysmal.engine.utils.HugeInteger;

public class Research {

	public HugeInteger cost;
	int[] prerequisits;
	public int mobID, trackID, ID, level;
	public double multiplier, increase;
	public URL spritePath;
	public String path = "researchIcon/", name;
	boolean unlocked = false, maxed = false;
	public static ArrayList<Research> research = new ArrayList<Research>();

	public Research(HugeInteger cost, double multiplier, double increase, int[] prerequisits, String spritePath, String name, int id) {
		this.name = name;
		this.cost = cost;
		this.prerequisits = (prerequisits == null) ? new int[] {}:prerequisits;
		this.spritePath = ClassLoader.getSystemResource(path + spritePath + ".png");
		this.multiplier = multiplier;
		this.increase = increase;
		research.add(id, this);
		ID = id;
	}

	public static Research getResearch(int mobId, int id) {
		// TODO Differentiate between different mobs
		return research.get(mobId * Purchases.UPGRADES_PER_MOB + id);
	}

	// TODO Optimize?
	public void increaseCost(int level) {
		for (int i = 0; i < level; i++) {
			cost = cost.mult(2.5f);
		}
	}
}