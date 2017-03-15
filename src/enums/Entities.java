package enums;

import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import objects.entities.Mob;
import objects.entities.Projectile;

public enum Entities {
	canonball(0, 15, new HugeInteger((short) 20), "canonball", new Hitbox(new Vector(-7f, -7f), new Vector(6f, 6f))),

	poison(1, 10, new HugeInteger((short) 5), "canonball", new Hitbox(new Vector(-5f, -5f), new Vector(5f, 5f))),

	lazer(2, 2, new HugeInteger((short) 1), "canonball", new Hitbox(new Vector(-1f, -1f), new Vector(1f, 1f))),

	arrow(3, 5, new HugeInteger((short) 10), "canonball", new Hitbox(new Vector(-3f, -3f), new Vector(3f, 3f))),

	bomb(4, 7, new HugeInteger((short) 100), "canonball", new Hitbox(new Vector(-10f, -10f), new Vector(10f, 10f))),

	// Mobs
	zombie(0, new HugeInteger((short) 20), new HugeInteger((short) 20), new HugeInteger((short) 0), .25, new HugeInteger((short) 1), 0, 0, new Hitbox(new Vector(-7, -7), new Vector(7, 7)), "zombie");

	Entities(int id, HugeInteger income, HugeInteger health, HugeInteger armor, double speed, HugeInteger damage, int resistanceID, int resistanceAmount, Hitbox hitbox, String texture) {
		Mob.mobTypes.add(id, new Mob(id, income, health, armor, speed, damage, resistanceID, resistanceAmount, hitbox, texture));
	}

	private Entities(int id, int speed, HugeInteger damage, String texture, Hitbox hitbox) {
		new Projectile(id, speed, damage, texture, hitbox);
	}
}