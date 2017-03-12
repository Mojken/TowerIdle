package enums;

import net.abysmal.engine.utils.HugeInteger;
import objects.blocks.Block;
import objects.buildings.Building;
import objects.towers.Tower;
import values.researches.Research;

public enum Purchases {
	// Tower
	basicTower(0, 1, new HugeInteger((short) 45), new HugeInteger((short) 10), 0, 0, 130, 2000, 0, "basic", true),

	// Block
	woodenBlock(1, 1, 10, new HugeInteger((short) 5), new HugeInteger((short) 50), 0, 0, "wooden"),

	// Research
	zombie(new HugeInteger((short) 50), 1, 1, null, 0, "zombieUnlock", "Zombies"),
	zombieHealth(new HugeInteger((short) 50), 1.07, .2, null, 1, "health", "Health"),

	// Misc
	researchFacility(0, 1, new HugeInteger((short) 200), new HugeInteger((short) 10), 1, 0, 8, 120, 1, "trap", false);
	//heal towers
	//functional tiles

	// TODO remove for compacting
	final public int id;
	final public String path;
	final public HugeInteger cost;
	final public Tower tower;
	final public Block block;
	final public Building building;
	final public Research research;

	public static final int UPGRADES_PER_MOB = 2;
	
	private Purchases(int type, int strenght, int weight, HugeInteger cost, HugeInteger health, int researchID, int id, String path) {
		tower = null;
		block = new Block(strenght, weight, cost, health, researchID, id, path);
		building = null;
		research = null;
		this.id = id;
		this.path = path;
		this.cost = cost;
		Block.blocks.add(block);
		Building.buildings.add(block);
	}

	private Purchases(int type, int strength, HugeInteger cost, HugeInteger health, int researchID, int weight, int radius, int attackSpeed, int id, String path, boolean requiresBlock) {
		tower = new Tower(strength, weight, cost, health, researchID, radius, attackSpeed, id, path, requiresBlock);
		block = null;
		building = null;
		research = null;
		this.id = id;
		this.path = path;
		this.cost = cost;
		Tower.towers.add(tower);
		Building.buildings.add(tower);
	}

	private Purchases(int type, HugeInteger cost, int weight, HugeInteger health, int researchID, int id, String path) {
		tower = null;
		block = null;
		building = new Building(cost, weight, health, researchID, id, path, 2);
		research = null;
		this.id = id;
		this.path = path;
		this.cost = cost;
		Building.buildings.add(building);
	}

	private Purchases(HugeInteger cost, double multiplier, double increase, int[] prerequisits, int id, String path, String name) {
		research = new Research(cost, multiplier, increase, prerequisits, path, name, id);
		this.cost = cost;
		this.path = path;
		this.id = id;
		tower = null;
		block = null;
		building = null;
	}
}