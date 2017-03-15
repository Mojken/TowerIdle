package enums;

import objects.entities.Projectile;
import net.abysmal.engine.utils.HugeInteger;
import objects.blocks.Block;
import objects.buildings.Building;
import objects.towers.Tower;
import values.researches.Research;

public enum Purchases {
	// Tower
	basicTower(0, 1, new HugeInteger((short) 45), new HugeInteger((short) 10), 0, 0, 130, 1600, 0, "basic", true, 0, Projectile.projectileTypes.get(0)),

	aoeTower(0, 1, new HugeInteger((short) 300), new HugeInteger((short) 10), 0, 0, 55, 0, 1, "aoe", true, 0, Projectile.projectileTypes.get(0)),

	dotTower(0, 1, new HugeInteger((short) 75), new HugeInteger((short) 10), 0, 0, 70, 200, 2, "dot", true, 0, Projectile.projectileTypes.get(1)),

	beamTower(0, 1, new HugeInteger((short) 200), new HugeInteger((short) 10), 0, 0, 50, 0, 3, "beam", true, 0, Projectile.projectileTypes.get(2)),

	fastTower(0, 1, new HugeInteger((short) 100), new HugeInteger((short) 10), 0, 0, 70, 50, 4, "fast", true, 0, Projectile.projectileTypes.get(3)),

	heavyTower(0, 1, new HugeInteger((short) 500), new HugeInteger((short) 10), 0, 20, 100, 2500, 5, "heavy", true, 0, Projectile.projectileTypes.get(4)),

	// Block
	woodenBlock(1, 1, 10, new HugeInteger((short) 5), new HugeInteger((short) 50), 0, 0, "wooden"),

	wooden2Block(1, 1, 15, new HugeInteger((short) 10), new HugeInteger((short) 50), 0, 1, "wooden2"),

	wooden3Block(1, 1, 20, new HugeInteger((short) 20), new HugeInteger((short) 50), 0, 2, "wooden3"),

	wooden4Block(1, 1, 25, new HugeInteger((short) 30), new HugeInteger((short) 50), 0, 3, "wooden4"),

	wooden5Block(1, 1, 30, new HugeInteger((short) 40), new HugeInteger((short) 50), 0, 4, "wooden5"),

	wooden6Block(1, 1, 35, new HugeInteger((short) 50), new HugeInteger((short) 50), 0, 5, "wooden6"),

	// Research
	zombie(new HugeInteger((short) 50), 1, 1, null, 0, "zombieUnlock", "Zombies"),

	health(new HugeInteger((short) 50), 1.07, .2, null, 1, "health", "Health");

	// Misc
//	researchFacility(0, 1, new HugeInteger((short) 200), new HugeInteger((short) 10), 1, 2, 8, 120, 6, "trap", false, 2);
	// heal towers
	// functional tiles

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

	private Purchases(int type, int strength, HugeInteger cost, HugeInteger health, int researchID, int weight, int radius, int attackSpeed, int id, String path, boolean requiresBlock, int category, Projectile projectile) {
		tower = new Tower(strength, weight, cost, health, researchID, radius, attackSpeed, id, path, requiresBlock, category, projectile);
		block = null;
		building = null;
		research = null;
		this.id = id;
		this.path = path;
		this.cost = cost;
		Tower.towers.add(tower);
		Building.buildings.add(tower);
	}

	private Purchases(int type, HugeInteger cost, int weight, HugeInteger health, int researchID, int id, String path, int category) {
		tower = null;
		block = null;
		building = new Building(cost, weight, health, researchID, id, path, 2, category);
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