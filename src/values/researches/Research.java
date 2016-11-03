package values.researches;

import java.net.URL;
import java.util.ArrayList;

import enums.Purchases;
import net.abysmal.engine.utils.HugeInteger;

public class Research {

	public HugeInteger cost;
	int[] prerequisits;
	public int mobID, trackID, ID, level;
	public URL spritePath;
	public String path = "researchIcon/", name;
	boolean unlocked = false, maxed = false;
	public static ArrayList<Research> research = new ArrayList<Research>();

	public Research(HugeInteger cost, float cooefficient, int[] prerequisits, String spritePath, String name) {
		this.name = name;
		this.cost = cost;
		this.prerequisits = (prerequisits == null) ? new int[] {} : prerequisits;
		this.spritePath = ClassLoader.getSystemResource(path + spritePath + ".png");
		research.add(this);
	}

	public static Research getResearch(int mobId, int id) {
		// TODO Differentiate between different mobs
		return research.get(mobId*Purchases.UPGRADES_PER_MOB+id);
	}
	
	//TODO Optimize?
	public void increaseCost(int level){
		for(int i = 0; i < level; i++){
			cost = cost.mult(2.5f);
		}
	}
}