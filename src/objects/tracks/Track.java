package objects.tracks;

import java.util.ArrayList;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.graphics.geometry.Grid;
import net.abysmal.engine.handlers.misc.World;
import net.abysmal.engine.maths.Dimension;
import net.abysmal.engine.utils.HugeInteger;
import objects.buildings.Building;

public class Track {

	HugeInteger price;
	int towersAllowed; // Zero for no limit
	public World world;
	public Grid grid;
	public ArrayList<Entity> Entities = new ArrayList<Entity>();
	public Building[] buildings;
	public Dimension tileSize, mapSize;

	public Track(HugeInteger price, int towersAllowed) {
		this.price = price;
		this.towersAllowed = towersAllowed;
	}

	protected void setWorld(World world, Dimension partitionSize) {
		this.world = world;
		mapSize = world.mapSize;
//		for (int i = 0; i < mapSize.getArea(); i++)
//			buildings.add(i, new Empty());
		tileSize = new Dimension(partitionSize.getWidth() / mapSize.getWidth(), partitionSize.getHeight() / mapSize.getHeight());
		grid = new Grid(tileSize, mapSize);
		buildings = new Building[mapSize.getArea()];
	}
}
