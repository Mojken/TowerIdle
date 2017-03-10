package objects.tracks;

import java.net.URL;
import net.abysmal.engine.handlers.misc.World;
import net.abysmal.engine.maths.Dimension;
import net.abysmal.engine.utils.HugeInteger;

public class First extends Track {

	public static HugeInteger price = new HugeInteger();
	public static int towersAllowed = 0, id = 0;
	URL map;

	public First(Dimension partitionSize) {
		super(price, towersAllowed, id);
		map = ClassLoader.getSystemResource("tracks/first.png");

		setWorld(new World(map, 16, false), partitionSize);
	}

}
