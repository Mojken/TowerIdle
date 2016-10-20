package objects.tracks;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.abysmal.engine.handlers.misc.World;
import net.abysmal.engine.maths.Dimension;
import net.abysmal.engine.utils.HugeInteger;

public class First extends Track {
	
	public static HugeInteger price = new HugeInteger();
	public static int towersAllowed = 0, id = 0;
	BufferedImage map;

	public First(Dimension partitionSize) {
		super(price, towersAllowed, id);
		try {
			map = ImageIO.read(ClassLoader.getSystemResource("tracks/first.png"));
		} catch (IOException e2) {
			map = null;
		}
		setWorld(new World(map, 16, false), partitionSize);
	}

}
