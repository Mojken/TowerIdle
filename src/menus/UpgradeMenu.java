package menus;

import java.awt.Font;
import java.util.ArrayList;
import menus.buttons.ButtonBase;
import menus.buttons.UpgradeButton;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.maths.Vector;
import objects.buildings.Building;

public class UpgradeMenu {

	public static ButtonBase back;
	public static Square[] buttons = new Square[100];
	public static ArrayList<UpgradeButton> upgradeButton = new ArrayList<UpgradeButton>();

	public UpgradeMenu() {
		int iterator = 0;
		Partition offset = new Partition(new double[] {0, 1}, new double[] {0, .1, 1}, Window.dimension);
		Partition p = Partition.generateEvenPartitions(2, 12, offset.partitions[1].dimension);
		for (int i = 0; i < p.partitions.length; i++) {
			Square localButton = new Partition(new double[] { 0, .025, .975, 1 }, new double[] { 0, .05, .95, 1 }, p.partitions[i].dimension).partitions[4];
			buttons[iterator] = new Square(localButton.a.add(p.partitions[i].a).add(offset.partitions[1].a), localButton.d.add(p.partitions[i].a).add(offset.partitions[1].a));
			iterator++;
		}
		back = new ButtonBase(new Square(new Vector(10, 10), new Vector(80, 40)), "Back", 3, 0);
		for (int id = 0; id < Building.buildings.size(); id++)
			upgradeButton.add(new UpgradeButton(Building.buildings.get(id), id));
	}

	public static void draw(Graphics g) {

		g.clearRect(Vector.ZERO(), new Vector(Window.width, Window.height));

		Font f = new Font("Monospaced", Font.BOLD, 40);
		Font f0 = g.getFont();
		g.setFont(f);
		String label = "Upgrades";
		g.drawString(label, new Vector(Window.width / 2 - label.length() * f.getSize() / 3.5f, f.getSize() * .8f));

		g.setFont(f0);

		back.draw(g);

		for (UpgradeButton b:upgradeButton)
			b.draw(g);
	}
}