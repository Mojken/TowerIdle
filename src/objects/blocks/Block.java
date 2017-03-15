package objects.blocks;

import java.util.ArrayList;
import net.abysmal.engine.utils.HugeInteger;
import objects.buildings.Building;
import objects.towers.Tower;

public class Block extends Building {
	
	int strength;
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	public Tower tower;
	
	public Block(int strength, int weight, HugeInteger cost, HugeInteger health, int researchID, int ID, String spritePath) {
		super(cost, weight, health, researchID, ID, spritePath, 1, 1);
		this.strength = strength;
	}

	public Block(Block b){
		this(b.strength, b.weight, b.cost, b.health, b.researchID, b.ID, b.spritePath);
	}
	
	public boolean viableSupport(int selectedBuildingID) {
			
		return strength >= Tower.towers.get(selectedBuildingID).requiredStrenght;
	}
	
	
}