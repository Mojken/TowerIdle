package objects;

import java.util.ArrayList;
import main.Main;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.entities.AI.Pathfinding;
import net.abysmal.engine.handlers.misc.Tile;
import net.abysmal.engine.maths.Vector;
import objects.entities.Mob;
import objects.tracks.Track;

public class Path {

	public Vector[] pathNodes;
	int endIndex, startIndex;
	public int[] weights;
	public boolean[] traversable;

	public Path(Track track) {
		weights = new int[track.mapSize.getArea()];
		traversable = new boolean[track.mapSize.getArea()];
		calculatePath(track, null);
	}

	public Path(Path path, Pathfinding pathfinding) {
		endIndex = path.endIndex;
		startIndex = path.startIndex;
		weights = path.weights;
		traversable = path.traversable;

		pathNodes = new Vector[pathfinding.pathIndexes.size()];
		for (int i = 0; i < pathfinding.pathIndexes.size(); i++)
			pathNodes[i] = Main.currentTrack.grid.getGridCoordinate(pathfinding.pathIndexes.get(i));
//
// for (int w = 0; w < weights.length; w++) {
// if (Main.currentTrack.buildings[w] == null) weights[w] = 1;
// else{
// weights[w] = Main.currentTrack.buildings[w].weight;
// }
// }
// for (int w = 0; w < weights.length; w++) {
// if (weights[w] == 1) {
// if (Main.currentTrack.world.tiles[1][w].getID() != Tile.tilesReal.get(0).getID()) {
// weights[w] = 1;
// }
// }
// }
	}

	public void calculatePath(Track track, ArrayList<Entity> m) {
		for (int w = 0; w < weights.length; w++) {
			if (track.buildings[w] == null) weights[w] = 1;
			else {
				weights[w] = track.buildings[w].weight;
			}
		}
		for (int w = 0; w < weights.length; w++) {
			if (weights[w] == 1) {
				if (track.world.tiles[1][w].getID() != Tile.tilesReal.get(0).getID()) {
					weights[w] = 1;
				}
			}
		}

		for (int t = 0; t < traversable.length; t++) {
			traversable[t] = true;
			for (int layer = 0; layer < 3; layer++) {
				for (String s:track.world.tiles[layer][t].getTraits()) {
					if (s == "nontraversable" || s == "obstacle") {
						traversable[t] = false;
					}
				}
			}
		}

		Pathfinding path = Pathfinding.findPath(15, 207, weights, traversable, track.mapSize.getWidth());
		pathNodes = new Vector[path.pathIndexes.size()];
		for (int i = 0; i < path.pathIndexes.size(); i++)
			pathNodes[i] = track.grid.getGridCoordinate(path.pathIndexes.get(i));

		if (m != null) for (Entity e:m)
			if (e instanceof Mob) ((Mob) e).updatePath();
	}

	public Vector getPathNode(int pathIndex) {
		return pathNodes[pathIndex];
	}

	public int getLength() {
		return pathNodes.length;
	}

	public Vector getNodePos(int pathIndex) {
		pathIndex %= pathNodes.length;
		return pathNodes[pathIndex].add(Main.currentTrack.grid.tileSize.toVector().multiply(.5f));
	}
}