package values.researches;

import java.util.ArrayList;
import net.abysmal.engine.utils.HugeInteger;

public class Research<Mob> {
	
	HugeInteger cost;
	int[] prerequisits;
	public ArrayList<Research<Mob>> researches = new ArrayList<Research<Mob>>();
	public String spritePath;
	
	public Research(HugeInteger cost, int[] prerequisits, String spritePath) {
		this.cost = cost;
		this.prerequisits = prerequisits;
		this.spritePath = spritePath;
	}
	
	public Research(HugeInteger cost, int prerequisit, String spritePath) {
		this.cost = cost;
		prerequisits = new int[] {prerequisit};
		this.spritePath = spritePath;
	}
	
	public Research(HugeInteger cost, String spritePath) {
		this.cost = cost;
		prerequisits = new int[] {};
		this.spritePath = spritePath;
	}
}
