package objects.entities;

import java.util.ArrayList;
import main.Main;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.handlers.misc.Movement;
import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import values.researches.Research;
import values.researches.Researches;

public class Mob extends Entity {

	public HugeInteger income, health, armor, damage, currentHealth;
	public int resistanceID, resistanceAmount, id, pathIndex;
	public double speed;
	public Hitbox hitbox;
	public static ArrayList<Mob> mobTypes = new ArrayList<Mob>();

	public Mob(int id, HugeInteger income, HugeInteger health, HugeInteger armor, double speed, HugeInteger damage, int resistanceID, int resistanceAmount, Hitbox hitbox, String texture) {
		textureURL = ClassLoader.getSystemResource(path + texture + ".png");
		this.income = income;
		this.health = health;
		this.armor = armor;
		this.speed = speed;
		this.damage = damage;
		this.resistanceID = resistanceID;
		this.resistanceAmount = resistanceAmount;
		this.hitbox = hitbox;
	}

	public Mob(Vector position, Mob mobType) {
		super(new Vector(position.x, position.y), 0, mobType.hitbox, mobType.textureStr);
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
	}

	@Override
	public boolean move() {
		if (Movement.walkToVector(Main.currentTrack.path.getNodePos(pathIndex), this, speed)) {
			pathIndex++;
			if (Main.currentTrack.path.getLength() == pathIndex) {
				pathIndex = 0;
				teleport(Main.currentTrack.path.getNodePos(pathIndex));
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