package objects.buildings;

import net.abysmal.engine.utils.HugeInteger;

public class Empty extends Building {

	public Empty() {
		super(new HugeInteger((short) -1), new HugeInteger((short) -1), new HugeInteger((short) -1), -1, -1, "void");
	}
}
