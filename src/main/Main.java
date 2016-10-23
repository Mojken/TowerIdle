package main;

import javax.swing.JFrame;

import enums.Entities;
import enums.Purchases;
import enums.Tiles;
import menues.ResearchMenu;
import menues.buttons.ButtonBase;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.handlers.misc.Settings;
import net.abysmal.engine.maths.Dimension;
import objects.Spawner;
import objects.tracks.First;
import objects.tracks.Track;
import values.researches.Researches;

public class Main {

	public static JFrame f;
	Window w;
	Partition partition, moneyPartition, tabPartition;
	public Square money, research, tabs, save, towers, track, islandTab, researchTab, upgradeTab;
	public static Track currentTrack;
	int trackWidth, trackHeight, counter;
	double widthPartition = .7, heightPartition = .8;
	public static boolean initialized = false;
	public static int selectedBuildingID = 0, selectedBuildingType = 1;
	public Button[] buttons;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		w = new Window();
		f = w.createWindow("Abysmal Tower", 1000);
		w.start(new Update(this));
		init();
		Update.sp = new Spawner(Main.currentTrack);
		initialized = true;
	}

	public void init() { // innit mate?
		for (Tiles s:Tiles.values())
			s.name();
		for (Entities s:Entities.values())
			s.name();
		for (Purchases s:Purchases.values())
			s.name();

		Settings.setDvorak();
//		GlobalVariables.debug = true;
		currentTrack = new First(new Dimension((int) (Window.width * widthPartition), (int) (Window.height * heightPartition)));
		setupPartitions();
		
		new ButtonBase(save, "Clear Mobs", 0, 0);
		new ButtonBase(islandTab, "Islands", 0, 1);
		new ButtonBase(researchTab, "Research", 0, 2);
		new ButtonBase(upgradeTab, "Upgrades", 0, 3);
		
		Button.registerButtons(Update.screen, f);
		
		new Researches();
		new ResearchMenu();
	}

	private void setupPartitions() {
		Dimension d = new Dimension(Window.width, Window.height);
		partition = new Partition(new double[] { 0, 0.2, heightPartition, 1 }, new double[] { 0, 0.1, widthPartition, 1 }, d);
		moneyPartition = new Partition(new double[] { 0.05, 1 }, new double[] { 0, 0.4, 0.8, 1 }, partition.partitions[0].d);
		tabPartition = new Partition(new double[] { 0, 1 / 3.0, 2 / 3.0, 1 }, new double[] { 0, 1 }, partition.partitions[1].d);
		assignPartitions(partition.partitions, moneyPartition.partitions, tabPartition.partitions);
	}

	int lastInput;

	private void assignPartitions(Square[] p, Square[] pm, Square[] pt) {
		tabs = p[1];
		save = p[2];
		towers = p[3];
		money = pm[0];
		research = pm[1];
		track = new Square(p[4].a, currentTrack.grid.getSize().add(p[4].a));
		islandTab = new Square (pt[0].a.add(tabs.a), pt[0].b.add(tabs.a));
		researchTab = new Square (pt[1].a.add(tabs.a), pt[1].b.add(tabs.a));
		upgradeTab = new Square (pt[2].a.add(tabs.a), pt[2].b.add(tabs.a));
	}
}