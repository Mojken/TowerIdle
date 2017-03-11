package menues.buttons;

import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;

public class ToggleButton extends Button{

	

	public ToggleButton(Square bounds, String label, int pressedColour, int screen, int id, boolean combineMovement) {
		super(bounds, label, pressedColour, screen, id, combineMovement);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		super.draw(g);
	}
	
}
