package objects.entities;

import java.util.ArrayList;
import main.Main;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.handlers.misc.Movement;
import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;

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
}