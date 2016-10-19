package objects.entities;

import java.util.ArrayList;
import main.Main;
import net.abysmal.engine.handlers.misc.Movement;
import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import objects.towers.Tower;

public class Projectile extends net.abysmal.engine.entities.Projectile<Tower> {

	public static ArrayList<Projectile> projectileTypes = new ArrayList<Projectile>();
	public Vector target;
	public int id = -1;

	public Projectile(int id, int speed, HugeInteger damage, String texture, Hitbox hitbox) {
		super(id, speed, 0, damage, texture, hitbox);
	}

	public Projectile(Vector pos, Projectile type, Tower source, Vector targetPos, int id) {
		super(pos, type, source);
		this.angle = targetPos.sub(pos).calculateAngle();
		this.id = id;
	}

	public boolean shoot(Vector pos, Tower source) {
		if(null == source.getTarget()) return false;
		int id = Main.currentTrack.entities.size();
		Main.currentTrack.entities.add(new Projectile(pos, this, source, source.getTarget().pos, id));
		return true;
	}

	public boolean move() {
		Movement.moveInAngleWithRotation(angle, this, 0, speed);
		Vector p = Main.currentTrack.mapSize.toVector().multiply(Main.currentTrack.tileSize.toVector());
		if (pos.x > p.x || pos.x < -16 || pos.y > p.y || pos.y < -16) {
			return false;
		}
		return true;
	}
	
	public static void destroy(Projectile p) {
		Main.currentTrack.entities.remove(p.id);
	}

}