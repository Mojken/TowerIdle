package main;

import javax.swing.JFrame;
import enums.Entities;
import enums.Purchases;
import enums.Tiles;
import menues.BuildingShop;
import menues.ResearchMenu;
import menues.buttons.ButtonBase;
import net.abysmal.engine.GlobalVariables;
import net.abysmal.engine.graphics.Partition;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.handlers.misc.Settings;
import net.abysmal.engine.maths.Dimension;
import net.abysmal.engine.maths.Vector;
import objects.Spawner;
import objects.tracks.First;
import objects.tracks.Track;
import values.researches.Researches;

public class Main {

	public static JFrame f;
	Window w;
	public static Partition partition, moneyPartition, tabPartition, towerPartition;
	public Square money, research, tabs, save, towers, track, islandTab, researchTab, upgradeTab;
	public static Vector towerOffset;
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
		System.out.print("Initiating");
		w = new Window();
		f = w.createWindow("Abysmal Tower", 1000);
		System.out.print(".");
		w.start(new Update(this));
		System.out.print(".");
		init();
		System.out.println(".");
		Update.sp = new Spawner(Main.currentTrack);
		System.out.println("Done!");
		Window.frame.setVisible(true);
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
		GlobalVariables.debug = true;
		currentTrack = new First(new Dimension((int) (Window.width * widthPartition), (int) (Window.height * heightPartition)));
		setupPartitions();

		new ButtonBase(save, "Clear Mobs", 0, 0);
		new ButtonBase(islandTab, "Islands", 0, 1);
		new ButtonBase(researchTab, "Research", 0, 2);
		new ButtonBase(upgradeTab, "Upgrades", 0, 3);

		Button.registerButtons(Update.screen, f);
		new ResearchMenu();
		new Researches();
		new BuildingShop();
	}

	private void setupPartitions() {
		Dimension d = new Dimension(Window.width, Window.height);
		partition = new Partition(new double[] { 0, 0.2, heightPartition, 1 }, new double[] { 0, 0.1, widthPartition, 1 }, d);
		moneyPartition = new Partition(new double[] { 0.05, 1 }, new double[] { 0, 0.4, 0.8, 1 }, partition.partitions[0].dimension);
		tabPartition = new Partition(new double[] { 0, 1 / 3.0, 2 / 3.0, 1 }, new double[] { 0, 1 }, partition.partitions[1].dimension);
		assignPartitions(partition.partitions, moneyPartition.partitions, tabPartition.partitions);
		towerPartition = Partition.generateEvenPartitions(5, 9, towers.dimension);
	}

	int lastInput;

	private void assignPartitions(Square[] p, Square[] pm, Square[] pt) {
		tabs = p[1];
		save = p[2];
		towers = new Partition(new double[] { 0, .03, .97, 1 }, new double[] { 0, .015, .985, 1 }, p[3].dimension).partitions[4];
		towerOffset = towers.a.add(p[3].a);
		money = pm[0];
		research = pm[1];
		track = new Square(p[4].a, currentTrack.grid.getSize().add(p[4].a));
		islandTab = new Square(pt[0].a.add(tabs.a), pt[0].d.add(tabs.a));
		researchTab = new Square(pt[1].a.add(tabs.a), pt[1].d.add(tabs.a));
		upgradeTab = new Square(pt[2].a.add(tabs.a), pt[2].d.add(tabs.a));
	}
}