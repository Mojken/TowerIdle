package menues;

import java.util.Map;

import main.Update;
import menues.buttons.ButtonBase;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import values.Player;

public class ResearchMenu {

	// What do we need?
	// Shit to render? Lots of buttons...
	// What is this supposed to be?
	// Lot's of objects that are to be rendered by the update class instead of the game.

	public static ButtonBase back, unlockZombie, spawnrate;
//	static Button[] buttons;

	public ResearchMenu() {
		back = new ButtonBase(new Square(new Vector(10, 10), new Vector(50, 20)), "Back", 2, 0);
		unlockZombie = new ButtonBase(new Square(new Vector(100, 30), new Vector(200, 80)), "Zombie", 2, 1);
		spawnrate = new ButtonBase(new Square(new Vector(250, 30), new Vector(350, 80)), "Spawnrate", 2, 2);
//		buttons = new Button[] { back, unlockZombie, spawnrate };
	}

	public static void draw(Graphics g) {
		
		g.drawString("Research: " + Player.research.toString(), new Vector(100, 20));
		
		for (Map.Entry<Integer, Button> e : Button.buttons.get(Update.screen).entrySet()) {
			e.getValue().draw(g);
		}
	}
	
	public static int level = 1;
//	public static void update(Mouse m) {
//		// TODO change to using a Research instead...
//		if (spawnrate.update(m)) {
//			if (Player.research.largerThanOrEqualTo(new HugeInteger((short) (200 * Math.pow(level, 3)))) && Researches.unlocked(0)){
//				Update.sp.changeSpawnrate(0, (int) (Update.sp.getSpawnrate(0) * .5));
//				Player.research.add(new HugeInteger((short) -(200 * Math.pow(level, 3))));
//				level++;
//			}
//		}
//		if (unlockZombie.update(m)) {
//			if (Player.research.largerThanOrEqualTo(new HugeInteger((short) 50)) && !Researches.unlocked(0)) {
//				Researches.unlock(0);
//				Player.research.add(new HugeInteger((short) -50));
//			}
//		}
//	}
}