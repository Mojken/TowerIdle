package menues;

import java.util.Map;
import main.Update;
import menues.buttons.ButtonBase;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import values.Player;

public class ResearchMenu {

	public static ButtonBase back, unlockZombie, spawnrate;
	public static Square[] buttons = new Square[15];

	public ResearchMenu() {
		int iterator = 0;
		Partition p = new Partition(new double[] { 0, .1, (.8) / 3, .53, .9, 1 }, new double[] { 0, .1, .2, .4, .6, .8, 1 }, Window.dimension);
		for (int i = 6; i < p.partitions.length - 1; i++) {
			if(i % 5 != 0 && i % 5 != 4) {
				Square localButton = new Partition(new double[] {.1, .9}, new double[] {.1, .9}, p.partitions[i].d).partitions[5];
				buttons[iterator] = new Square(localButton.a.add(p.partitions[i].a), localButton.b.add(p.partitions[i].a));
			}
		}
		back = new ButtonBase(new Square(new Vector(10, 10), new Vector(80, 40)), "Back", 2, 0);
		unlockZombie = new ButtonBase(new Square(new Vector(100, 30), new Vector(200, 80)), "Zombie", 2, 1);
		spawnrate = new ButtonBase(new Square(new Vector(250, 30), new Vector(350, 80)), "Spawnrate", 2, 2);
	}

	public static void draw(Graphics g) {

		g.drawString("Research: " + Player.research.toString(), new Vector(100, 20));

		for (Map.Entry<Integer, Button> e:Button.buttons.get(Update.screen).entrySet()) {
			e.getValue().draw(g);
		}
	}
}