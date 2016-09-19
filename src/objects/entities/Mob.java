package objects.entities;

import java.util.ArrayList;
import net.abysmal.engine.utils.HugeInteger;

public class Mob {

	HugeInteger income, health, armor, speed, damage, strenght;
	int resistanceID, resistanceAmount;
	public static ArrayList<Mob> mobs = new ArrayList<Mob>();

	public Mob(HugeInteger income, HugeInteger health, HugeInteger armor, HugeInteger speed, HugeInteger damage, HugeInteger strenght, int resistanceID, int resistanceAmount) {
		this.income = income;
		this.health = health;
		this.armor = armor;
		this.speed = speed;
		this.damage = damage;
		this.strenght = strenght;
		this.resistanceID = resistanceID;
		this.resistanceAmount = resistanceAmount;
	}
}