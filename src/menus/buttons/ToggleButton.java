package menus.buttons;

import java.awt.BasicStroke;
import java.awt.Stroke;
import main.Main;
import main.Update;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;

public class ToggleButton extends Button {

	private boolean toggled;

	public ToggleButton(Square bounds, String label, int screen, int id, boolean combineMovement) {
		super(bounds, label, 0, screen, id, combineMovement);
		toggled = false;
	}

	int indent = 6;

	@Override
	public void draw(Graphics g) {
		Stroke s = g.g.getStroke();
		g.g.setStroke(new BasicStroke(2));
		g.drawRoundRect(bounds, new Vector(3, 3));
		g.g.setStroke(s);
		if (toggled) {
			g.drawLine(bounds.a.add(indent), bounds.d.sub(indent));
			g.drawLine(bounds.b.add(new Vector(-indent, indent)), bounds.c.sub(new Vector(-indent, indent)));
		}
	}

	public boolean isToggled() {
		return toggled;
	}

	@Override
	public void update(boolean p) {
		if (p) {
			toggled = !toggled;
			Main.currentTrack.enabledMobs[id - 50] = toggled;
			Update.updateScreen();
		}
	}
}