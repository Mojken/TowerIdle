package menus.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import enums.Purchases;
import main.Update;
import menus.ResearchMenu;
import net.abysmal.engine.GlobalVariables;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import values.researches.Research;
import values.researches.Researches;

public class ResearchButton {

	Square bBounds;
	Partition layout;
	Research r;
	Font f0, f1;
	RButton buy, sell;
	public ToggleButton toggle;
	Button open;
	Partition p;
	BufferedImage b;
	int screen;

	public ResearchButton(Research research, int screen) {
		r = research;
		this.screen = screen;
		if (screen != 2) bBounds = ResearchMenu.buttons[r.ID - 1];
		else bBounds = ResearchMenu.buttons[r.ID];
		f0 = new Font("Monospaced", Font.BOLD, (int) (bBounds.d.clone().sub(bBounds.a).y / 5));
		f1 = new Font("Monospaced", Font.BOLD, (int) (bBounds.d.clone().sub(bBounds.a).y / 7.4));
		p = new Partition(new double[] { 0, .05, 1.2 / 3, 1.8 / 3, .95, 1 }, new double[] { 0, .1, .3, .4, .65, .775, .9, 1 }, bBounds.dimension);
		if (r.ID % Purchases.UPGRADES_PER_MOB == 0) {
			buy = new RButton(new Square(p.partitions[12].scale(.9f).translate(bBounds.a).a, p.partitions[28].scale(.9f).translate(bBounds.a).d), "Unlock", 0x888888, screen, research.ID * 2 + 1, true, 0xFFAA0C);
			sell = null;
		} else {
			buy = new RButton(new Square(p.partitions[23].scale(.9f).translate(bBounds.a).a, p.partitions[28].scale(.9f).translate(bBounds.a).d), "Buy", 0x888888, screen, research.ID * 2 + 1, true, 0xFFAA0C);
			sell = new RButton(p.partitions[18].scale(.65f).translate(bBounds.a), "Sell", 0x888888, screen, research.ID * 2 + 2, true, 0);
		}
		try {
			b = ImageIO.read(r.spritePath);
		} catch (IOException e) {
			b = null;
		}
	}

	public void draw(Graphics g) {
		Stroke s = g.g.getStroke();

		g.setColour(new Color(0xCCCCCC));
		g.fillRoundRect(bBounds, new Vector(30f, 30f));
		g.setAA(true);
		g.setColour(new Color(0x000000));
		g.g.setStroke(new BasicStroke(4));
		g.drawRoundRect(bBounds, new Vector(30f, 30f));
		g.setFont(f0);
		g.drawString(" " + r.name, p.partitions[7].a.add(bBounds.a).add(new Vector(0, f0.getSize() - f0.getSize() / 5)));
		g.setFont(f1);
		// g.drawString("Price:", p.partitions[21].a.add(bounds.a).add(new
		// Vector(0, 12f)));
		g.drawString("Price:" + Research.research.get(r.ID).cost, p.partitions[31].a.add(bBounds.a));

		if (sell != null) sell.draw(g);
		if (buy != null) buy.draw(g);
		if (toggle != null) toggle.draw(g);
		if (open != null) open.draw(g);

		g.setColour(new Color(0x77000000, true));
		g.fillRoundRect(p.partitions[6].a.add(bBounds.a), p.partitions[21].translate(bBounds.a).d, new Vector(15, 15));
		g.drawImage(b, p.partitions[6].a.add(bBounds.a), p.partitions[21].d.sub(p.partitions[6].a));
		g.setColour(new Color(0x000000));
		g.drawRoundRect(p.partitions[6].a.add(bBounds.a), p.partitions[21].translate(bBounds.a).d, new Vector(15, 15));

		g.g.setStroke(s);
		if (GlobalVariables.debug) for (Square ss:p.partitions) {
			g.drawRect(ss.a.add(bBounds.a), ss.d.add(bBounds.a));
		}

	}

	class RButton extends Button {

		int colour;
		Font f0 = new Font("Monospaced", Font.BOLD, 20);
		Font f1 = new Font("Monospaced", Font.BOLD, 16);

		public RButton(Square bounds, String label, int pressedColour, int screen, int id, boolean combineMovement, int colour) {
			super(bounds, label, pressedColour, screen, id, combineMovement);
			this.colour = colour;
		}

		@Override
		public void draw(Graphics g) {
			Stroke s = g.g.getStroke();
			Color c = pressed ? new Color(fill):new Color(0, true);
			g.setColour(c);
			g.fillRoundRect(bounds, new Vector(10f, 10f));
			g.setAA(true);
			g.setColour(new Color(colour));
			g.g.setStroke(new BasicStroke(3));
			g.fillRoundRect(new Square(bounds.d.sub(bounds.dimension.toVector()), bounds.d), new Vector(10f, 10f), new Color(0x16000000, true));
			g.drawRoundRect(new Square(bounds.d.sub(bounds.dimension.toVector()), bounds.d), new Vector(10f, 10f));
			Font f = id % 2 == 1 ? f0:f1;
			g.setFont(f);
			g.drawString(label, bounds.a.add(new Vector((((bounds.d.x - bounds.a.x) / 2)) - label.length() * f.getSize() / 3.5f, (int) bounds.d.sub(bounds.a).y / 2 + (f.getSize() - f.getSize() / 1.5f))));

			g.setColour(Color.BLACK);
			g.g.setStroke(s);
		}

		@Override
		public void update(boolean pressed) {
			if (pressed) {
				boolean unlocked = false;

				if (id % 2 == 1) unlocked = Researches.buy((id - 1) / 2);
				else Researches.sell((id - 2) / 2);

				if (sell == null && unlocked && toggle == null) {
					buy = null;
					float lenght = p.partitions[8].d.sub(p.partitions[8].a).y;
					Vector dimensions = new Vector(lenght, lenght);
					Square sb = new Square(p.partitions[8].d.sub(dimensions), p.partitions[8].d);
					toggle = new ToggleButton(sb.translate(bBounds.a), "", screen, r.ID/Purchases.UPGRADES_PER_MOB+50, false);
					open = new OButton(bounds, "Open", screen, 11, false);
					new ResearchMenu(r.ID);
					toggle.update(true);
					Update.updateScreen();
				}
			}
		}
	}

	class OButton extends Button {

		public OButton(Square bounds, String label, int screen, int id, boolean combineMovement) {
			super(bounds, label, 0, screen, id, combineMovement);
		}

		@Override
		public void draw(Graphics g) {
			Stroke s = g.g.getStroke();
			Color c = pressed ? new Color(fill):new Color(0, true);
			g.setColour(c);
			g.fillRoundRect(bounds, new Vector(10f, 10f));
			g.setAA(true);
			g.setColour(new Color(0x77000000));
			g.g.setStroke(new BasicStroke(3));
			g.fillRoundRect(new Square(bounds.d.sub(bounds.dimension.toVector()), bounds.d), new Vector(10f, 10f), new Color(0x16000000, true));
			g.drawRoundRect(new Square(bounds.d.sub(bounds.dimension.toVector()), bounds.d), new Vector(10f, 10f));
			Font f = id % 2 == 1 ? f0:f1;
			g.setFont(f);
			g.drawString(label, bounds.a.add(new Vector((((bounds.d.x - bounds.a.x) / 2)) - label.length() * f.getSize() / 3.5f, (int) bounds.d.sub(bounds.a).y / 2 + (f.getSize() - f.getSize() / 1.5f))));

			g.setColour(Color.BLACK);
			g.g.setStroke(s);
		}

		@Override
		public void update(boolean pressed) {
			if (pressed) Update.switchScreen(r.ID + 20);
		}
	}
}