package main;

import enums.Entities;
import enums.Purchases;
import enums.Tiles;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Settings;
import net.abysmal.engine.maths.Dimension;
import objects.entities.Mob;
import objects.tracks.First;
import objects.tracks.Track;

public class Main {

	Window w;
	Partition partition, moneyPartition;
	public Square money, research, menu, save, towers, track;
	public static Track currentTrack;
	int trackWidth, trackHeight, counter;
	double widthPartition = .7, heightPartition = .8;
	public static boolean initialized = false;
	public static int selectedBuildingID = 0, selectedBuildingType = 1;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		w = new Window();
		w.createWindow("Abysmal Tower", 1000);
		w.start(new Update(this));
		init();
	}

	public void init() { // innit mate?
		for (Tiles s:Tiles.values())
			s.name();
		for (Entities s:Entities.values())
			s.name();
		for (Purchases s:Purchases.values())
			s.name();

		Settings.setDvorak();
		currentTrack = new First(new Dimension((int) (Window.width * widthPartition), (int) (Window.height * heightPartition)));
		setupPartitions();
		currentTrack.entities.add(new Mob(currentTrack.grid.getGridCoordinate(76).add(track.a), Mob.mobTypes.get(0)));
		initialized = true;
	}

	private void setupPartitions() {
		Dimension d = new Dimension(Window.width, Window.height);
		partition = new Partition(new double[] { 0, 0.2, heightPartition, 1 }, new double[] { 0, 0.1, widthPartition, 1 }, d);
		moneyPartition = new Partition(new double[] { 0.05, 1 }, new double[] { 0, 0.4, 0.8, 1 }, partition.partitions[0].d);
		assignPartitions(partition.partitions, moneyPartition.partitions);
	}

	int lastInput;

	private void assignPartitions(Square[] p, Square[] pm) {
		money = pm[0];
		menu = p[1];
		save = p[2];
		towers = p[3];
		track = new Square(p[4].a, currentTrack.grid.getSize().add(p[4].a));
		research = pm[1];
	}
}