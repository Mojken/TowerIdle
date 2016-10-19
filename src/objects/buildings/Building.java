package objects.buildings;

import java.net.URL;
import java.util.ArrayList;
import main.Main;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import objects.blocks.Block;
import objects.towers.Tower;
import objects.tracks.Track;
import values.Player;

public class Building {

	public HugeInteger cost, health;
	public int researchID, ID, weight;
	public static ArrayList<Building> buildings = new ArrayList<Building>();
	public String spritePath;
	public int type;
	public int gridIndex;

	public Building(HugeInteger cost, int weight, HugeInteger health, int researchID, int ID, String spritePath) {
		this.weight = weight;
		this.cost = cost;
		this.researchID = researchID;
		this.ID = ID;
		this.health = health;
		this.spritePath = spritePath;
		this.type = 2;
	}

	public Building(HugeInteger cost, int weight, HugeInteger health, int researchID, int ID, String spritePath, int type) {
		this.weight = weight;
		this.cost = cost;
		this.researchID = researchID;
		this.ID = ID;
		this.health = health;
		this.spritePath = spritePath;
		this.type = type;
	}

	public Building(Building b) {
		this.weight = b.weight;
		this.cost = b.cost;
		this.researchID = b.researchID;
		this.ID = b.ID;
		this.health = b.health;
		this.spritePath = b.spritePath;
		this.type = b.type;
	}

	public boolean locationValid(Track t, int gridIndex) {
		if (t.buildings[gridIndex].equals(null) && t.world.tiles[1][gridIndex].getTraits()[0] == "buildable")
			return true;
		else
			return false;
	}

	public URL getFile() {
		switch (type) {
		case 0:
			return ClassLoader.getSystemResource("towers/" + spritePath + ".png");
		case 1:
			return ClassLoader.getSystemResource("blocks/" + spritePath + ".png");
		case 2:
			return ClassLoader.getSystemResource("buildings/" + spritePath + ".png");
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList getBuildingList(int type) {
		switch (type) {
		case 0:
			return Tower.towers;
		case 1:
			return Block.blocks;
		case 2:
			return buildings;
		}
		return null;
	}

	public static void placeBuilding(Vector pos) {
		if (!(Main.selectedBuildingID < 0) && getBuildingList(Main.selectedBuildingType).size() > Main.selectedBuildingID && null != getBuildingList(Main.selectedBuildingType).get(Main.selectedBuildingID)) {
			int gridIndex = Main.currentTrack.grid.getGridIndex(Main.currentTrack.grid.getGridCoordinate(pos));
			// Making sure you have enough money
			if (Player.money.largerThanOrEqualTo(((Building) Building.getBuildingList(Main.selectedBuildingType).get(Main.selectedBuildingID)).cost)) {
				if (Main.currentTrack.world.tiles[1][gridIndex].getTraits() != null && Main.currentTrack.world.tiles[0][gridIndex].getTraits() != null) {
					// Making sure you can place building on current wheel
					if (Main.currentTrack.world.tiles[1][gridIndex].getTraits()[0] == "buildable" && Main.currentTrack.world.tiles[0][gridIndex].getTraits()[0] != "obstacle") {
						Building bClone = null;
						if (Main.selectedBuildingType == 0) {
							if (Tower.towers.get(Main.selectedBuildingID).requiresBlock && (Main.currentTrack.buildings[gridIndex] instanceof Block && ((Block) Main.currentTrack.buildings[gridIndex]).viableSupport(Main.selectedBuildingID)) && ((Block) Main.currentTrack.buildings[gridIndex]).tower == null) {
								((Block) Main.currentTrack.buildings[gridIndex]).tower = new Tower(Tower.towers.get(Main.selectedBuildingID), gridIndex);
								Player.money.sub(Tower.towers.get(Main.selectedBuildingID).cost);
							}else if(!Tower.towers.get(Main.selectedBuildingID).requiresBlock && (Main.currentTrack.buildings[gridIndex] == null)){
//								((Block) Main.currentTrack.buildings[gridIndex]).tower = new Tower(Tower.towers.get(Main.selectedBuildingID), gridIndex);
//								Player.money.sub(Tower.towers.get(Main.selectedBuildingID).cost);
								bClone = new Tower(Tower.towers.get(Main.selectedBuildingID), gridIndex);
							}
						} else if (Main.selectedBuildingType == 1 && Main.currentTrack.buildings[gridIndex] == null) {
							bClone = new Block((Block) Building.getBuildingList(Main.selectedBuildingType).get(Main.selectedBuildingID));
						}
						if (bClone != null) {
							bClone.gridIndex = gridIndex;
							Main.currentTrack.buildings[bClone.gridIndex] = bClone;
							Player.money.sub(bClone.cost);
						}
					}
				}
			}
		}
	}

	public static void sellBuilding(Vector pos) {
		int gridIndex = Main.currentTrack.grid.getGridIndex(Main.currentTrack.grid.getGridCoordinate(pos));
		if (Main.currentTrack.buildings[gridIndex] != null) {
			if (Main.currentTrack.world.tiles[1][gridIndex].getTraits() != null && Main.currentTrack.world.tiles[0][gridIndex].getTraits() != null) {
				if (Main.currentTrack.world.tiles[1][gridIndex].getTraits()[0] == "buildable" && Main.currentTrack.world.tiles[0][gridIndex].getTraits()[0] != "obstacle") {
					if (Main.currentTrack.buildings[gridIndex] instanceof Block && ((Block) Main.currentTrack.buildings[gridIndex]).tower != null)
						Player.money.add(((Block) Main.currentTrack.buildings[gridIndex]).tower.cost);
					Player.money.add(Main.currentTrack.buildings[gridIndex].cost);
					Main.currentTrack.buildings[gridIndex] = null;
				}
			}
		}
	}
}
