package objects.entities;

import java.util.ArrayList;
import main.Main;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.entities.AI.Pathfinding;
import net.abysmal.engine.handlers.misc.Movement;
import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import objects.Path;
import values.researches.Research;
import values.researches.Researches;

public class Mob extends Entity {

	public HugeInteger income, health, armor, damage, currentHealth;
	public int resistanceID, resistanceAmount, id, pathIndex;
	public Path path;
	public double speed;
	public Hitbox hitbox;
	public static ArrayList<Mob> mobTypes = new ArrayList<Mob>();

	public Mob(int id, HugeInteger income, HugeInteger health, HugeInteger armor, double speed, HugeInteger damage, int resistanceID, int resistanceAmount, Hitbox hitbox, String texture) {
		textureURL = ClassLoader.getSystemResource(super.path + texture + ".png");
		this.income = income;
		this.health = health;
		this.armor = armor;
		this.speed = speed;
		this.damage = damage;
		this.resistanceID = resistanceID;
		this.resistanceAmount = resistanceAmount;
		this.hitbox = hitbox;
	}

	public Mob(Vector position, Mob mobType, Path path) {
		super(new Vector(position.x, position.y), 0, mobType.hitbox, mobType.textureStr);
		textureURL = mobType.textureURL;
		hitbox = new Hitbox(mobType);
		this.textureURL = mobType.textureURL;
		this.income = mobType.income.clone();
		this.health = mobType.health.clone();
		currentHealth = mobType.health.clone();
		this.armor = mobType.armor.clone();
		this.speed = mobType.speed;
		this.damage = mobType.damage.clone();
		this.resistanceID = mobType.resistanceID;
		this.resistanceAmount = mobType.resistanceAmount;
		this.path = path;
	}
	
	//TODO add the other stats along with the researches
	public void updateStats() {
		currentHealth.mult((float) getResearchMultiplier(1));
	}
	
	public boolean onPath() {
		for (Vector n:path.pathNodes)
			if (Main.currentTrack.grid.getGridIndex(n) == Main.currentTrack.grid.getGridIndex(pos)) return true;
		return false;
	}

	public void updatePath() {
		if (!onPath()) {
			path = new Path(path, Pathfinding.findPath(Main.currentTrack.grid.getGridIndex(pos), Main.currentTrack.grid.getGridIndex(path.getNodePos(path.getLength() - 1)), Main.currentTrack.path.weights, Main.currentTrack.path.traversable, Main.currentTrack.mapSize.getWidth()));
			pathIndex = 1;
		} else for (int i = 0; i < Main.currentTrack.path.pathNodes.length; i++)
			if (Main.currentTrack.grid.getGridIndex(Main.currentTrack.path.pathNodes[i]) == Main.currentTrack.grid.getGridIndex(pos)) {
			pathIndex = i + 1;
			path = Main.currentTrack.path;
		}
	}

	@Override
	public boolean move() {
// if (Main.currentTrack.path != path && onPath()) path = Main.currentTrack.path;

		if (Movement.walkToVector(path.getNodePos(pathIndex), this, speed)) {
			pathIndex++;
			if (path.getLength() == pathIndex) {
				path = Main.currentTrack.path;
				pathIndex = 0;
				teleport(path.getNodePos(0));
				if (Main.currentTrack.lastEscaped < Main.currentTrack.cooldown) Main.currentTrack.lastEscaped = Main.currentTrack.cooldown;
			}
		}
		return super.move();
	}

	public double getResearchMultiplier(int id) {
		return 1 + (Research.research.get(id).increase * Researches.getResearchLevels()[Main.currentTrack.id][this.id][id][0]);
	}

	public double getIncomeMultiplier() {
		double multi = 1;
		for (int i = 0; i < Researches.getResearchLevels()[Main.currentTrack.id][id][i].length; i++)
			multi += (Research.research.get(i).multiplier * Researches.getResearchLevels()[Main.currentTrack.id][id][i][0]);

		return multi;
	}
}