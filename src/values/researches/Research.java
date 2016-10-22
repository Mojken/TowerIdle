package values.researches;

import net.abysmal.engine.utils.HugeInteger;

public class Research {
	
	HugeInteger cost;
	int[] prerequisits;
	int mobID, trackID, ID;
	public String spritePath;
	boolean unlocked = false, maxed = false;
	
	public Research(HugeInteger cost, int[] prerequisits, String spritePath) {
		this.cost = cost;
		this.prerequisits = prerequisits;
		this.spritePath = spritePath;
	}
	
	public Research(HugeInteger cost, int prerequisite, String spritePath) {
		this.cost = cost;
		prerequisits = new int[] {prerequisite};
		this.spritePath = spritePath;
	}
	
	public Research(HugeInteger cost, String spritePath) {
		this.cost = cost;
		prerequisits = new int[] {};
		this.spritePath = spritePath;
	}

	
	
}