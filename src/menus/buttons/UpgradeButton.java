package menus.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import menus.UpgradeMenu;
import net.abysmal.engine.GlobalVariables;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import objects.buildings.Building;

public class UpgradeButton {

	Square bBounds;
	Partition layout;
	Building b;
	Font f0, f1;
	BButton speed, range, damage;
	Partition p;
	BufferedImage i;
	int screen = 3;
	boolean hidden;

	public UpgradeButton(Building b, int id) {
		this.b = b;
		bBounds = UpgradeMenu.buttons[id];
		f0 = new Font("Monospaced", Font.BOLD, (int) (bBounds.d.clone().sub(bBounds.a).y / 2));
		f1 = new Font("Monospaced", Font.PLAIN, (int) (bBounds.d.clone().sub(bBounds.a).y / 3.1));
		p = new Partition(new double[] { 0, .08, .3, .38, .533, .613, .766, .846, 1 }, new double[] { 0, .6, .97, 1 }, bBounds.dimension);
		speed = new BButton(new Square(p.partitions[2].translate(bBounds.a).a, p.partitions[11].translate(bBounds.a).d), "+", 0x888888, id * 4 + 1);
		range = new BButton(new Square(p.partitions[4].translate(bBounds.a).a, p.partitions[13].translate(bBounds.a).d), "+", 0x888888, id * 4 + 2);
		damage = new BButton(new Square(p.partitions[6].translate(bBounds.a).a, p.partitions[15].translate(bBounds.a).d), "+", 0x888888, id * 4 + 3);
		try {
			i = ImageIO.read(b.getFile());
		} catch (IOException e) {
			i = null;
		}
	}

	public void draw(Graphics g) {
		Stroke s = g.g.getStroke();
		g.setColour(new Color(0xCCCCCC));
		g.setAA(true);
		g.g.setStroke(new BasicStroke(2));
		g.drawLine(bBounds.c, bBounds.d);
		g.g.setStroke(s);
		g.setColour(new Color(0x000000));

		g.setFont(f0);
		g.drawString("Null", p.partitions[1].c.add(bBounds.a));
		g.setFont(f1);
		if (speed != null) {
			speed.draw(g);
			if (b.type == 0) {
				if (b.category == 0) g.drawString("Fire rate", p.partitions[3].c.add(bBounds.a));
				if (b.category == 2) g.drawString("Speed", p.partitions[3].c.add(bBounds.a));
			} else if (b.type == 1) g.drawString("Weight", p.partitions[3].c.add(bBounds.a));

		}
		if (range != null) {
			range.draw(g);
			if (b.type == 0) {
				if (b.category == 0) g.drawString("Range", p.partitions[5].c.add(bBounds.a));
				if (b.category == 2) g.drawString("Efficiency", p.partitions[5].c.add(bBounds.a));
			} else if (b.type == 1) g.drawString("Strength", p.partitions[5].c.add(bBounds.a));
		}
		if (damage != null) {
			damage.draw(g);
			if (b.type == 0) {
				if (b.category == 0) g.drawString("Damage", p.partitions[7].c.add(bBounds.a));
				if (b.category == 2) g.drawString("Capacity", p.partitions[7].c.add(bBounds.a));
			} else if (b.type == 1) g.drawString("Capaticy", p.partitions[7].c.add(bBounds.a));
			
		}

		g.drawImage(i, p.partitions[0].a.add(bBounds.a), new Vector(p.partitions[9].d.y - p.partitions[0].a.x, p.partitions[9].d.y - p.partitions[0].a.y));

		if (GlobalVariables.debug) for (Square ss:p.partitions) {
			g.drawRect(ss.a.add(bBounds.a), ss.d.add(bBounds.a));
		}
	}

	class BButton extends Button {

		Square bounds;
		String label;
		boolean pressed;
		int counter = 0;

		public BButton(Square bounds, String label, int pressedColour, int id) {
			super(bounds, label, pressedColour, 3, id, false);
			this.bounds = bounds;
			this.label = label;
		}

		public BButton(Square bounds, String label, URL imagePath, int id) {
			super(bounds, label, imagePath, 3, id, false);
			this.bounds = bounds;
			this.label = label;
		}

		public void draw(Graphics g) {
			Square bound = new Square(bounds.a, bounds.a.add(new Vector(bounds.dimension.toVector().y, bounds.dimension.toVector().y)));
			g.setColour(Color.LIGHT_GRAY);
			if (pressed) {
				g.fillRect(bound.a, bound.d);
				counter++;
			}
			g.setColour(Color.BLACK);
			g.drawRect(bound);
			g.setFont(f0);
			float x = (float) (bound.a.x + (bound.dimension.toVector().x * .38));
			float y = (float) (bound.a.y + (bound.dimension.toVector().x * .7));
			Vector v = new Vector(x, y);
			g.drawString(label, v);
			g.setFont(f1);
			if (counter == 4) {
				pressed = false;
				counter = 0;
			}
		}

		@Override
		public void update() {
			this.pressed = true;
		}
	}
}