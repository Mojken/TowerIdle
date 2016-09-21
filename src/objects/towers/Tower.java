package objects.towers;

import java.util.ArrayList;
import main.Main;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import objects.buildings.Building;
import objects.entities.Mob;

public class Tower extends Building {

	public int requiredStrenght, radius;
	public static ArrayList<Tower> towers = new ArrayList<Tower>();

	public Tower(int requiredStrenght, HugeInteger weight, HugeInteger cost, HugeInteger health, int researchID, int radius, int ID, String spritePath) {
		super(cost, weight, health, researchID, ID, spritePath, 0);
		this.requiredStrenght = requiredStrenght;
		this.radius = radius;
	}

	public Tower(Tower t, int gridIndex) {
		this(t.requiredStrenght, t.weight, t.cost, t.health, t.researchID, t.radius, t.ID, t.spritePath);
		this.gridIndex = gridIndex;
	}

	public double getTarget() {
		double angle = 0, closest = radius++;
		Vector closestEPos = null;

		for (Entity e:Main.currentTrack.entities) {
			if (e instanceof Mob) {
				float a = e.pos.checkProximity(Main.currentTrack.grid.getGridCoordinate(this.gridIndex));
				if (a < closest) {
					closest = a;
					closestEPos = e.pos;
				}
			}
		}
		if (closestEPos != null) angle = closestEPos.sub(Main.currentTrack.grid.getGridCoordinate(this.gridIndex)).calculateAngle();
		else angle = 13.37;

		return angle;
	}

}
