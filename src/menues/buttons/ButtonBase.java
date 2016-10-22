package menues.buttons;

import java.awt.Color;

import main.Main;
import main.Update;
import menues.ResearchMenu;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import values.Player;
import values.researches.Researches;

public class ButtonBase extends Button {

	public ButtonBase(Square bounds, String label, int screen, int id) {
		super(bounds, label, 0, screen, id, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics g) {
		g.drawRoundRect(bounds.a, bounds.b, new Vector(10, 10));

		g.drawString(label, new Vector((bounds.a.x + ((bounds.b.x - bounds.a.x) / 2)) - label.length()*3.5f, (int) (bounds.a.y + ((bounds.b.y - bounds.a.y) / 2)) + 5));
		System.out.println((bounds.b.x - bounds.a.x)*.2);
		Color c = g.getColour();
		if (within && !pressed) {
			g.setColour(new Color(0x30000000, true));
			g.fillRect(bounds.a.add(1), bounds.b);
		} else if (within) {
			g.setColour(new Color(0x60000000, true));
			g.fillRect(bounds.a.add(1), bounds.b);
		}
		g.setColour(c);
	}

	@Override
	public void update(boolean pressed) {
		if (pressed) {
			switch (screen) {
			case 0:
				switch (id) {
				case 0:
					Update.clearMobs = true;
					Main.currentTrack.lastEscaped = Main.currentTrack.cooldown * 10;
					break;
				case 1:

					break;
				case 2:
					Update.switchScreen(2);
					break;
				}
			case 1:
				break;
			case 2:
				switch (id) {
				case 0:
					Update.switchScreen(0);
					break;
				case 1:
					if (Player.research.largerThanOrEqualTo(new HugeInteger((short) 50)) && !Researches.unlocked(0)) {
						Researches.unlock(0);
						Player.research.add(new HugeInteger((short) -50));
					}
					break;
				case 2:
					if (Player.research.largerThanOrEqualTo(new HugeInteger((short) (200 * Math.pow(ResearchMenu.level, 3)))) && Researches.unlocked(0)) {
						Update.sp.changeSpawnrate(0, (int) (Update.sp.getSpawnrate(0) * .5));
						Player.research.add(new HugeInteger((short) -(200 * Math.pow(ResearchMenu.level, 3))));
						ResearchMenu.level++;
					}
					break;
				}
			}

			System.out.println("Pressed button " + id + " on screen " + screen + ". Button pos are: " + bounds.a + " And button size is: " + bounds.b.sub(bounds.a));
		}
	}
	
	@Override
	public void update() { }
}
