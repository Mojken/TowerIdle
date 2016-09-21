package enums;

import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import objects.entities.Mob;
import objects.entities.Projectile;

public enum Entities {
	canonball(0, 5, new HugeInteger((short) 20), "canonball", new Hitbox(new Vector(-7f, -7f), new Vector(6f, 6f))),

	zombie(0, new HugeInteger((short) 1), new HugeInteger((short) 20), new HugeInteger((short) 0), 5, new HugeInteger((short) 1), 0, 0, new Hitbox(new Vector(-7, -7), new Vector(7, 7)), "zombie");

	Entities(int id, HugeInteger income, HugeInteger health, HugeInteger armor, int speed, HugeInteger damage, int resistanceID, int resistanceAmount, Hitbox hitbox, String texture) {
		Mob.mobTypes.add(id, new Mob(id, income, health, armor, speed, damage, resistanceID, resistanceAmount, hitbox, texture));
	}

	private Entities(int id, int speed, HugeInteger damage, String texture, Hitbox hitbox) {
		Projectile.projectileTypes.add(new Projectile(id, speed, damage, texture, hitbox));
	}
}