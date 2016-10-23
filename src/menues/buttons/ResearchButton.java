package menues.buttons;

import menues.ResearchMenu;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.maths.Vector;
import values.researches.Research;

public class ResearchButton {
	
	Square bounds;
	Partition layout;
	Research r;
	
	public ResearchButton(Research research) {
		r = research;
		bounds = ResearchMenu.buttons[r.ID];
//		layout = new Partition(new double[] {}, new double[] {}, bounds);
	}
	
	public void draw(Graphics g) {
		g.drawRoundRect(bounds, new Vector(5f, 5f));
	}
}