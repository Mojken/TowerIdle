package objects.towers;

import java.util.ArrayList;
import net.abysmal.engine.utils.HugeInteger;
import objects.buildings.Building;

public class Tower extends Building {

	public int requiredStrenght;
	public static ArrayList<Tower> towers = new ArrayList<Tower>();

	public Tower(int requiredStrenght, HugeInteger weight, HugeInteger cost, HugeInteger health, int researchID, int ID, String spritePath) {
		super(cost, weight, health, researchID, ID, spritePath, 0);
		this.requiredStrenght = requiredStrenght;
	}
	
	public Tower(Tower t){
		this(t.requiredStrenght, t.weight, t.cost, t.health, t.researchID, t.ID, t.spritePath);
	}

}
