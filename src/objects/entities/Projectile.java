package objects.entities;

import java.util.ArrayList;
import net.abysmal.engine.handlers.misc.Movement;
import net.abysmal.engine.maths.Hitbox;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import objects.towers.Tower;

public class Projectile extends net.abysmal.engine.entities.Projectile<Tower>{

	public static ArrayList<Projectile> projectileTypes = new ArrayList<Projectile>();
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public Vector target;
	
	public Projectile(int id, int speed, HugeInteger damage, String texture, Hitbox hitbox) {
		super(id, speed, 0, damage, texture, hitbox);
	}
	
	public Projectile(Vector pos, Projectile type, Tower source, float angle){
		super(pos, type, source);
		this.angle = angle;
	}
	
	public void shoot(Vector pos, Tower source, Vector target) {
		target = target.sub(pos);
		angle = (float) (java.lang.Math.atan(target.getX() / target.getY()) + rotation);
		if (target.getY() != java.lang.Math.abs(target.getY()))
			angle += net.abysmal.engine.maths.Math.TAU / 2;
		projectiles.add(new Projectile(pos, this, source, angle));
	}
	
	
	
	public void move(){
		Movement.moveInAngleWithRotation(angle, this, 0, speed);
	}
	
}