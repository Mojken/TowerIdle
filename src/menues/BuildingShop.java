package menues;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.Main;
import main.Update;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.maths.Vector;
import objects.buildings.Building;

public class BuildingShop {

	public static ArrayList<ShopButton> buttons = new ArrayList<ShopButton>();

	public BuildingShop() {
		int tower = 0;
		int block = 20;
		for (Building b:Building.buildings) {
			switch (b.category) {
				case 0:
					buttons.add(new ShopButton(Main.towerPartition.partitions[tower].translate(Main.towerOffset), b, 100 + tower));
					tower++;
				break;
				case 1:
					buttons.add(new ShopButton(Main.towerPartition.partitions[block].translate(Main.towerOffset), b, 100 + block));
					block++;
				break;
			}
		}
		Update.updateScreen();
	}

	public static void draw(Graphics g) {
		for (ShopButton b:buttons)
			b.draw(g);
	}

	class ShopButton extends Button {

		Building b;

		public ShopButton(Square bounds, Building b, int id) {
			super(bounds, "", b.getFile(), 0, id, false);
			this.b = b;
		}

		@Override
		public void update() {
			super.update();
			Main.selectedBuildingID = b.ID;
			Main.selectedBuildingType = b.type;
		}

		@Override
		public void draw(Graphics g) {
			Square sb = new Square(bounds.translate(new Vector(3, 3)).a.sub(1), bounds.d.sub(5).add(2));

			g.setColour(new Color(0xff606060));
			if (Main.selectedBuildingID == b.ID && Main.selectedBuildingType == b.type) g.setColour(new Color(0xff333333));
			g.fillRoundRect(sb, new Vector(10, 10));
			g.setColour(new Color(0xff303030));
			if (Main.selectedBuildingID == b.ID && Main.selectedBuildingType == b.type) g.setColour(new Color(0xff101010));

			try {
				g.drawImage(ImageIO.read(imagePath), sb.a.add(1), sb.dimension.toVector().sub(2));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Stroke s = g.g.getStroke();
			g.g.setStroke(new BasicStroke(4));
			g.drawRoundRect(sb, new Vector(10, 10));
			g.g.setStroke(s);
			g.setColour(new Color(0xff000000));
		}

	}
}
