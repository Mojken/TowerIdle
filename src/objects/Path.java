package objects;

import main.Main;
import net.abysmal.engine.entities.AI.Pathfinding;
import net.abysmal.engine.handlers.misc.Tile;
import net.abysmal.engine.maths.Vector;
import objects.tracks.Track;

public class Path {

	public Vector[] pathNodes;

	int startIndex, endIndex;
	int[] weights;
	boolean[] traversable;

	public Path(Track track) {
		weights = new int[track.mapSize.getArea()];
		traversable = new boolean[track.mapSize.getArea()];
		calculatePath(track);
	}

	public void calculatePath(Track track) {
		for (int w = 0; w < weights.length; w++) {
			if (track.buildings[w] == null) weights[w] = -1;
			else{
				weights[w] = track.buildings[w].weight;
			}
		}
		for (int w = 0; w < weights.length; w++) {
			if (weights[w] == -1) {
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

// pathNodes = new Vector[] { new Vector(15, -1), new Vector(15, 2), new Vector(11, 2), new Vector(11, 7), new Vector(9, 7), new Vector(9, 12)};
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