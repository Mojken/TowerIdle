package menus;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import enums.Purchases;
import main.Update;
import menus.buttons.ButtonBase;
import menus.buttons.ResearchButton;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import values.Player;
import values.researches.Research;

public class ResearchMenu {

	public static ButtonBase back1, back2;
	public static Square[] buttons = new Square[18];
	public static Map<Integer, ArrayList<ResearchButton>> researchButton = new HashMap<Integer, ArrayList<ResearchButton>>();
	public int screen;

	public ResearchMenu() {
		int iterator = 0;
		researchButton.put(2, new ArrayList<ResearchButton>());
		Partition p = new Partition(new double[] { 0, 1.0 / 4, 2.0 / 4, 3.0 / 4, 1 }, new double[] { 0, .1, 1.3 / 4, 2.2 / 4, 3.1 / 4, 1 }, Window.dimension);
		for (int i = 4; i < p.partitions.length; i++) {
			Square localButton = new Partition(new double[] { 0, .025, .975, 1 }, new double[] { 0, .05, .95, 1 }, p.partitions[i].dimension).partitions[4];
			buttons[iterator] = new Square(localButton.a.add(p.partitions[i].a), localButton.d.add(p.partitions[i].a));
			iterator++;
		}
		back1 = new ButtonBase(new Square(new Vector(10, 10), new Vector(80, 40)), "Back", 2, 0);
		researchButton.get(2).add(new ResearchButton(Research.research.get(0), 2));
	}

	public ResearchMenu(int id) {
		screen = id + 20;
		researchButton.put(screen, new ArrayList<ResearchButton>());
		int iterator = 0;
		Partition p = new Partition(new double[] { 0, 1.0 / 4, 2.0 / 4, 3.0 / 4, 1 }, new double[] { 0, .1, 1.3 / 4, 2.2 / 4, 3.1 / 4, 1 }, Window.dimension);
		for (int i = 4; i < p.partitions.length; i++) {
			Square localButton = new Partition(new double[] { 0, .025, .975, 1 }, new double[] { 0, .05, .95, 1 }, p.partitions[i].dimension).partitions[4];
			buttons[iterator] = new Square(localButton.a.add(p.partitions[i].a), localButton.d.add(p.partitions[i].a));
			iterator++;
		}
		back2 = new ButtonBase(new Square(new Vector(10, 10), new Vector(80, 40)), "Back", screen, 0);
		for (int i = id + 1; i < id + Purchases.UPGRADES_PER_MOB; i++) {
			researchButton.get(screen).add(new ResearchButton(Research.research.get(i), screen));
		}
	}

	public static void draw(Graphics g) {

		g.clearRect(Vector.ZERO(), new Vector(Window.width, Window.height));

		g.drawString("Research: " + Player.research.toString(), new Vector(100, 20));

		Font f = new Font("Monospaced", Font.BOLD, 40);
		Font f0 = g.getFont();
		g.setFont(f);
		String label = "Research";
		if (Update.screen != 2) label = Research.research.get(Update.screen - 20).name + " " + label;
		g.drawString(label, new Vector(Window.width / 2 - label.length() * f.getSize() / 3.5f, f.getSize() * .8f));

		g.setFont(f0);

		if (Button.buttons.containsKey(Update.screen)) for (Map.Entry<Integer, Button> e:Button.buttons.get(Update.screen).entrySet()) {
			e.getValue().draw(g);
		}
		for (Map.Entry<Integer, ArrayList<ResearchButton>> b:researchButton.entrySet())
			if (b.getKey() == Update.screen) for (ResearchButton rb:b.getValue())
				rb.draw(g);
	}
}