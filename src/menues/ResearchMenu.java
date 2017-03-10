package menues;

import java.util.ArrayList;
import java.util.Map;

import main.Update;
import menues.buttons.ButtonBase;
import menues.buttons.ResearchButton;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import values.Player;
import values.researches.Research;

public class ResearchMenu {

	public static ButtonBase back, unlockZombie, spawnrate;
	public static ResearchButton zombieUnlock;
	public static Square[] buttons = new Square[18];
	static ArrayList<ResearchButton> researchButton = new ArrayList<ResearchButton>();

	public ResearchMenu() {
		int iterator = 0;
		Partition p = new Partition(new double[] { 0, 1.0 / 4, 2.0 / 4, 3.0 / 4, 1 }, new double[] { 0, .1, 1.3 / 4, 2.2 / 4, 3.1 / 4, 1 }, Window.dimension);
		for (int i = 4; i < p.partitions.length; i++) {
			Square localButton = new Partition(new double[] { 0, .025, .975, 1 }, new double[] { 0, .05, .95, 1 }, p.partitions[i].d).partitions[4];
			buttons[iterator] = new Square(localButton.a.add(p.partitions[i].a), localButton.b.add(p.partitions[i].a));
			iterator++;
		}
		back = new ButtonBase(new Square(new Vector(10, 10), new Vector(80, 40)), "Back", 2, 0);
		researchButton.add(new ResearchButton(Research.research.get(0)));
	}

	public static void draw(Graphics g) {

		g.drawString("Research: " + Player.research.toString(), new Vector(100, 20));

		if (Button.buttons.containsKey(Update.screen))
			for (Map.Entry<Integer, Button> e : Button.buttons.get(Update.screen).entrySet()) {
				e.getValue().draw(g);
			}

		for (ResearchButton b : researchButton) {
			b.draw(g);
		}
	}
}