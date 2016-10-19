package objects.towers;

import java.util.ArrayList;
import java.util.Iterator;

import enums.Purchases;
import main.Main;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.utils.HugeInteger;
import objects.buildings.Building;
import objects.entities.Mob;
import objects.entities.Projectile;
import values.Player;

public class Tower extends Building {

	public int requiredStrenght, radius, cooldown, attackSpeed;
	public boolean requiresBlock;
	public HugeInteger money;
	public static ArrayList<Tower> towers = new ArrayList<Tower>();

	public Tower(int requiredStrenght, int weight, HugeInteger cost, HugeInteger health, int researchID, int radius, int attackSpeed, int ID, String spritePath, boolean requiresBlock) {
		super(cost, weight, health, researchID, ID, spritePath, 0);
		this.requiredStrenght = requiredStrenght;
		this.radius = radius;
		this.attackSpeed = attackSpeed;
		this.requiresBlock = requiresBlock;
	}

	public Tower(Tower t, int gridIndex) {
		this(t.requiredStrenght, t.weight, t.cost, t.health, t.researchID, t.radius, t.attackSpeed, t.ID, t.spritePath, t.requiresBlock);
		this.gridIndex = gridIndex;
		cooldown = attackSpeed;
	}

	public Mob getTarget() {
		double closest = radius + 1;
		// Vector closestEPos = null;
		Mob target = null;
		for (Entity e : Main.currentTrack.entities) {
			if (e instanceof Mob) {
				float a = e.pos.checkProximity(Main.currentTrack.grid.getGridCoordinate(this.gridIndex).add(Main.currentTrack.tileSize.toVector().multiply(.5f)));
				if (a < closest) {
					closest = a;
					target = (Mob) e;
				}
			}
		}
		// if (closestEPos != null) angle =
		// closestEPos.sub(Main.currentTrack.grid.getGridCoordinate(this.gridIndex)).calculateAngle();
		// else angle = 13.37;

		return target;
	}

	public void update() {
		if (cooldown == 0) {
			if (this.ID == Purchases.trap.id) {
				Mob target = getTarget();
				if(null == target) return;
				int in = target.currentHealth.number.get(0);
				target.currentHealth = target.currentHealth.sub(new HugeInteger((short) 100));
				cooldown = in*500;
				if (!target.currentHealth.largerThanOrEqualTo(HugeInteger.ZERO)){
					
					money = target.health.mult(.5f);
					Iterator<Entity> i = Main.currentTrack.entities.iterator();
					while (i.hasNext()){
						if (target.equals(i.next())){
							i.remove();
							break;
						}
					}
				}
			} else {
				Projectile p = Projectile.projectileTypes.get(0);
				if (p.shoot(Main.currentTrack.grid.getGridCoordinate(this.gridIndex).add(Main.currentTrack.tileSize.toVector().multiply(.5f)), this))
					cooldown = attackSpeed;
			}
		} else{
			cooldown--;
			
			if (money != null && cooldown%12 == 0){
				if(money.largerThanOrEqualTo(new HugeInteger((short)1))){
					Player.money.add(new HugeInteger((short) 1));
					money.sub(new HugeInteger((short) 1));
				}else{
					money = null;
				}
			}
		}
	}
}