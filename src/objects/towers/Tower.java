package objects.towers;

import java.util.ArrayList;
import main.Main;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.utils.HugeInteger;
import objects.buildings.Building;
import objects.entities.Mob;
import objects.entities.Projectile;

public class Tower extends Building {

	public int requiredStrenght, radius, cooldown, attackSpeed;
	public static ArrayList<Tower> towers = new ArrayList<Tower>();

	public Tower(int requiredStrenght, int weight, HugeInteger cost, HugeInteger health, int researchID, int radius, int attackSpeed, int ID, String spritePath) {
		super(cost, weight, health, researchID, ID, spritePath, 0);
		this.requiredStrenght = requiredStrenght;
		this.radius = radius;
		this.attackSpeed = attackSpeed;
	}

	public Tower(Tower t, int gridIndex) {
		this(t.requiredStrenght, t.weight, t.cost, t.health, t.researchID, t.radius, t.attackSpeed, t.ID, t.spritePath);
		this.gridIndex = gridIndex;
		cooldown = attackSpeed;
	}

	public Mob getTarget() {
		double closest = radius + 1;
// Vector closestEPos = null;
		Mob target = null;

		for (Entity e:Main.currentTrack.entities) {
			if (e instanceof Mob) {
				float a = e.pos.checkProximity(Main.currentTrack.grid.getGridCoordinate(this.gridIndex));
				if (a < closest) {
					closest = a;
					target = (Mob) e;
				}
			}
		}
// if (closestEPos != null) angle = closestEPos.sub(Main.currentTrack.grid.getGridCoordinate(this.gridIndex)).calculateAngle();
// else angle = 13.37;

		return target;
	}

	public void update() {
		if (cooldown == 0) {
			Projectile p = Projectile.projectileTypes.get(0);
			if (p.shoot(Main.currentTrack.grid.getGridCoordinate(this.gridIndex).add(Main.currentTrack.tileSize.toVector().multiply(.5f)), this)) cooldown = attackSpeed;
		} else cooldown--;
	}
}