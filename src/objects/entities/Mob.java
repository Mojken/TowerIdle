package objects.entities;

import java.util.ArrayList;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;

public class Mob extends Entity {

	HugeInteger income, health, armor, damage;
	int resistanceID, resistanceAmount, id, speed;
	Hitbox hitbox;
	public static ArrayList<Mob> mobTypes = new ArrayList<Mob>();

	public Mob(int id, HugeInteger income, HugeInteger health, HugeInteger armor, int speed, HugeInteger damage, int resistanceID, int resistanceAmount, Hitbox hitbox, String texture) {
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
		super(position, 0, mobType.hitbox, mobType.textureStr);
		hitbox = new Hitbox(mobType);
		this.textureURL = mobType.textureURL;
		System.out.println(textureURL);
		this.income = mobType.income;
		this.health = mobType.health;
		this.armor = mobType.armor;
		this.speed = mobType.speed;
		this.damage = mobType.damage;
		this.resistanceID = mobType.resistanceID;
		this.resistanceAmount = mobType.resistanceAmount;
	}
	
	@Override
	public void move(){
	}
}