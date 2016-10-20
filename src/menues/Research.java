package menues;

import main.Update;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.HID.Mouse;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import values.Player;
import values.researches.Researches;

public class Research {

	// What do we need?
	// Shit to render? Lots of buttons...
	// What is this supposed to be?
	// Lot's of objects that are to be rendered by the update class instead of the game.

	public static Button back = new Button(new Square(new Vector(10, 10), new Vector(50, 20)));
	static Button unlockZombie = new Button(new Square(new Vector(100, 30), new Vector(200, 80)));
	static Button spawnrate = new Button(new Square(new Vector(250, 30), new Vector(350, 80)));
	static Button[] buttons;

	public Research() {
		buttons = new Button[] { back, unlockZombie, spawnrate };
	}

	public static void draw(Graphics g) {
		
		g.drawString("Research: " + Player.research.toString(), new Vector(100, 20));
		
		for (Button b:buttons) {
			g.drawRoundRect(b.bounds.a, b.bounds.b, new Vector(10, 10));
		}
	}
	
	static int level = 1;
	public static void update(Mouse m) {
		// TODO change to using a Research instead...
		if (spawnrate.update(m)) {
			if (Player.research.largerThanOrEqualTo(new HugeInteger((short) (200 * Math.pow(level, 3)))) && Researches.unlocked(0)){
				Update.sp.changeSpawnrate(0, (int) (Update.sp.getSpawnrate(0) * .5));
				Player.research.add(new HugeInteger((short) -(200 * Math.pow(level, 3))));
				level++;
			}
		}
		if (unlockZombie.update(m)) {
			if (Player.research.largerThanOrEqualTo(new HugeInteger((short) 50)) && !Researches.unlocked(0)) {
				Researches.unlock(0);
				Player.research.add(new HugeInteger((short) -50));
			}
		}
	}
}