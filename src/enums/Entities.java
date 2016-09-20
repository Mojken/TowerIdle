package enums;

import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.physics.misc.ForceArray;
import net.abysmal.engine.utils.HugeInteger;
import objects.entities.Projectile;

public enum Entities {
	temp(0, 0, new Hitbox(Vector.ZERO(), Vector.ZERO()), ""),
	player(1, 70, ForceArray.generateGenericForceArray(90), new Hitbox(new Vector(-16, -16, -16), new Vector(16, 16, 16)), "player"),
	
	canonball(0, 5, new HugeInteger(), "canonball", new Hitbox(new Vector(-7f, -7f), new Vector(6f, 6f)));
	
	;

	final int id;
	final float mass;
	final Hitbox hitbox;
	final ForceArray forceArray;
	final String texture;

	Entities(int id, float mass, Hitbox hitbox, String texture) {
		this.id = id;
		this.mass = mass;
		this.hitbox = hitbox;
		forceArray = null;
		this.texture = texture;
		Entity.entityTypes.add(id, new Entity(id, mass, hitbox, texture));
	}

	Entities(int id, float mass, ForceArray forceArray, Hitbox hitbox, String texture) {
		this.id = id;
		this.mass = mass;
		this.hitbox = hitbox;
		this.forceArray = forceArray;
		this.texture = texture;
		Entity.entityTypes.add(id, new Entity(id, mass, hitbox, texture));
	}
	
	
	private Entities(int id, int speed, HugeInteger damage, String texture, Hitbox hitbox) {
		this.id = id;
		this.mass = 0;
		this.hitbox = hitbox;
		this.forceArray = null;
		this.texture = texture;
		Projectile.projectileTypes.add(new Projectile(id, speed, damage, texture, hitbox));
	}
}