package enums;

import net.abysmal.engine.utils.HugeInteger;
import objects.blocks.Block;
import objects.buildings.Building;
import objects.entities.Mob;
import objects.towers.Tower;
import values.researches.Research;

@SuppressWarnings("null")
public enum Purchases {
	/* Types: 0: Towers 1: Blocks 2: Building */
// new HugeInteger((short))

	basicTower(0, 1, new HugeInteger((short) 45), new HugeInteger((short) 10), 0, 0, 130, 2000, 0, "basic", true),
	woodenBlock(1, 1, 10, new HugeInteger((short) 5), new HugeInteger((short) 50), 0, 0, "wooden"),
	zombie(true, new HugeInteger((short) 50), 0, "zombieUnlock"),
	//TODO Balance
	trap(0, 1, new HugeInteger((short) 1), new HugeInteger((short) 10), 1, -50000, 8, 120, 1, "trap", false);

	final public int type, id, researchID, strenght, requiredStrenght, weight;
	final public boolean isResearch;
	final public String path;
	final public HugeInteger cost;
	final public Tower tower;
	final public Block block;
	final public Building building;
	final public Research<Mob> research;

	private Purchases(int type, int strenght, int weight, HugeInteger cost, HugeInteger health, int researchID, int id, String path) {
		tower = null;
		block = new Block(strenght, weight, cost, health, researchID, id, path);
		building = null;
		research = null;
		this.type = type;
		this.id = id;
		this.researchID = researchID;
		isResearch = false;
		this.path = path;
		this.cost = cost;
		this.strenght = strenght;
		this.requiredStrenght = -1;
		this.weight = weight;
		Block.blocks.add(block);
		Building.buildings.add(block);
	}

	private Purchases(int type, int strength, HugeInteger cost, HugeInteger health, int researchID, int weight, int radius, int attackSpeed, int id, String path, boolean requiresBlock) {
		tower = new Tower(strength, weight, cost, health, researchID, radius, attackSpeed, id, path, requiresBlock);
		block = null;
		building = null;
		research = null;
		this.weight = weight;
		this.type = type;
		this.id = id;
		this.researchID = researchID;
		isResearch = false;
		this.path = path;
		this.cost = cost;
		this.strenght = -1;
		this.requiredStrenght = strenght;
		Tower.towers.add(tower);
		Building.buildings.add(tower);
	}

	private Purchases(int type, HugeInteger cost, int weight, HugeInteger health, int researchID, int id, String path) {
		if (type != 2) System.err.println("INCORRECT SYNTAX ON " + name());
		tower = null;
		block = null;
		building = new Building(cost, weight, health, researchID, id, path);
		research = null;
		this.type = type;
		this.id = id;
		this.researchID = researchID;
		isResearch = false;
		this.path = path;
		this.cost = cost;
		this.strenght = -1;
		this.requiredStrenght = -1;
		this.weight = weight;
		Building.buildings.add(building);

	}

	private Purchases(boolean isResearch, HugeInteger cost, int[] prerequisits, int id, String path) {
		if (isResearch) {
			research = new Research<Mob>(cost, prerequisits, path);
			this.cost = cost;
			this.path = path;
			this.id = id;
			this.isResearch = isResearch;
			tower = null;
			block = null;
			building = null;
			this.type = -1;
			this.researchID = -1;
			this.strenght = -1;
			this.requiredStrenght = -1;
			this.weight = 0;
//			Research.researches.add(research);
		} else {
			System.err.println("Why do you have a 'false' at the start of your research?");
			tower = null;
			block = null;
			building = null;
			research = null;
			this.type = -1;
			this.id = -1;
			this.researchID = -1;
			this.isResearch = (Boolean) null;
			this.path = null;
			this.cost = null;
			this.strenght = -1;
			this.requiredStrenght = -1;
			this.weight = 0;
		}
	}

	private Purchases(boolean isResearch, HugeInteger cost, int prerequisits, int id, String path) {
		if (isResearch) {
			research = new Research<Mob>(cost, prerequisits, path);
			this.cost = cost;
			this.path = path;
			this.id = id;
			this.isResearch = isResearch;
			tower = null;
			block = null;
			building = null;
			this.type = -1;
			this.researchID = -1;
			this.strenght = -1;
			this.requiredStrenght = -1;
			this.weight = 0;
//			Research.researches.add(research);
		} else {
			System.err.println("Why do you have a 'false' at the start of your research?");
			tower = null;
			block = null;
			building = null;
			research = null;
			this.type = -1;
			this.id = -1;
			this.researchID = -1;
			this.isResearch = (Boolean) null;
			this.path = null;
			this.cost = null;
			this.strenght = -1;
			this.requiredStrenght = -1;
			this.weight = 0;
		}
	}

	private Purchases(boolean isResearch, HugeInteger cost, int id, String path) {
		if (isResearch) {
			research = new Research<Mob>(cost, path);
			this.cost = cost;
			this.path = path;
			this.id = id;
			this.isResearch = isResearch;
			tower = null;
			block = null;
			building = null;
			this.type = -1;
			this.researchID = -1;
			this.strenght = -1;
			this.requiredStrenght = -1;
			this.weight = 0;
//			Research.researches.add(research);
		} else {
			System.err.println("Why do you have a 'false' at the start of your research?");
			tower = null;
			block = null;
			building = null;
			research = null;
			this.type = -1;
			this.id = -1;
			this.researchID = -1;
			this.isResearch = (Boolean) null;
			this.path = null;
			this.cost = null;
			this.strenght = -1;
			this.requiredStrenght = -1;
			this.weight = 0;
		}
	}

}
