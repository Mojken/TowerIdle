package values.researches;

import java.net.URL;
import net.abysmal.engine.utils.HugeInteger;

public class Research {
	
	HugeInteger cost;
	int[] prerequisits;
	public int mobID, trackID, ID, level;
	public URL spritePath;
	String path = "researchIcon/";
	boolean unlocked = false, maxed = false;
	
	public Research(HugeInteger cost, int[] prerequisits, String spritePath) {
		this.cost = cost;
		this.prerequisits = prerequisits;
		this.spritePath = ClassLoader.getSystemResource(path + spritePath + ".png");
	}
	
	public Research(HugeInteger cost, int prerequisite, String spritePath) {
		this.cost = cost;
		prerequisits = new int[] {prerequisite};
		this.spritePath = ClassLoader.getSystemResource(path + spritePath + ".png");
	}
	
	public Research(HugeInteger cost, String spritePath) {
		this.cost = cost;
		prerequisits = new int[] {};
		this.spritePath = ClassLoader.getSystemResource(path + spritePath + ".png");
	}
	
	
}