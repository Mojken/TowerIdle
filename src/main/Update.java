package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList;

import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Tick;
import net.abysmal.engine.maths.Vector;
import objects.blocks.Block;
import objects.buildings.Building;
import objects.entities.Projectile;
import objects.towers.Tower;
import values.Player;

public class Update implements Tick {

	Main main;
	boolean lastInput, reading, skip, shoot;
	int shot = 0;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public Update(Main main) {
		this.main = main;
	}

	@Override
	public void update() {
		if (Main.initialized) {
			if (main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_CONTROL][0] == 1)
				Main.selectedBuildingType = 0;
			else
				Main.selectedBuildingType = 1;
			if (main.w.mouseListener.getClickInfo()[1][4] == 1 && (!lastInput || main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_SHIFT][0] == 1)) {
				if (main.track.isWithin(main.w.mouseListener.getMousePosition())) {
					// if(buildingSelected){
					Building.placeBuilding(main.w.mouseListener.getMousePosition().sub(main.track.a));
					// }
				}
			}
			if (main.w.mouseListener.getClickInfo()[3][4] == 1) {
				if (main.track.isWithin(main.w.mouseListener.getMousePosition())) {
					// if(buildingSelected){
					if (main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_CONTROL][0] == 1) {
						Building b0 = Main.currentTrack.buildings[Main.currentTrack.grid.getGridIndex(main.w.mouseListener.getMousePosition().sub(main.track.a))];
						if (b0 != null) {
							for (Building b1 : Main.currentTrack.buildings) {
								if (b1 == null)
									continue;
								if (b0.ID == b1.ID && b0.type == b1.type)
									Building.sellBuilding(Main.currentTrack.grid.getGridCoordinate(b1.gridIndex));
							}
						}
					} else
						Building.sellBuilding(main.w.mouseListener.getMousePosition().sub(main.track.a));
					// }
				}
			}
			if (main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_SPACE][0] == 1 && shot == 0) {

				shoot = true;
			}

			if (shoot || skip) {
				if (!reading) {
					for (Building b : Main.currentTrack.buildings) {
						if (b instanceof Block && ((Block) b).tower != null) {
							Tower source = ((Block) b).tower;
							Vector target = main.w.mouseListener.getMousePosition();
							Projectile p = Projectile.projectileTypes.get(0);
							p.shoot(Main.currentTrack.grid.getGridCoordinate(b.gridIndex).add(Main.currentTrack.tileSize.toVector().multiply(.5f)).add(main.track.a), source, target);
						}
					}
					shot = 1;
					shoot = false;
					skip = false;
				} else {
					skip = true;
				}
			}

			lastInput = main.w.mouseListener.getClickInfo()[1][4] == 1;
			if (shot > 0)
				shot++;
			if (shot >= 100)
				shot = 0;

			for (Projectile p : Projectile.projectiles) {
				// System.out.println(p.target);
				p.move();
			}
		}
	}

	@Override
	public void render(java.awt.Graphics g2) {
		if (Main.initialized) {
			Graphics g = new Graphics(g2);
			g2.clearRect(0, 0, Window.width, Window.height);
			// super.paint(g);
			for (Square p : main.partition.partitions) {
				g.drawRect(p.a, p.b);
			}
			g2.drawString("Money: " + Player.money, (int) main.money.a.x, (int) main.money.b.y);
			g2.drawString("Research: " + Player.research, (int) main.research.a.x, (int) main.research.b.y);
			g.drawImage(Main.currentTrack.world.world, main.track.a, main.track.b.sub(main.track.a));
			g.setColor(new Color(0x10303030, true));
			for (Square p : Main.currentTrack.grid.grid) {
				g.drawRect(p.a.add(main.track.a), p.b.add(main.track.a));
			}
			for (Building b : Main.currentTrack.buildings) {
				if (b == null)
					continue;
				try {
					Vector pos = Main.currentTrack.grid.getGridCoordinate(b.gridIndex).add(main.track.a);
					g.drawImage(ImageIO.read(b.getFile()), pos, Main.currentTrack.tileSize.toVector());
					if (b instanceof Block && ((Block) b).tower != null) {
						g.drawImage(ImageIO.read(((Block) b).tower.getFile()), pos, Main.currentTrack.tileSize.toVector());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			drawOnHover(g);
			reading = true;
			for (Projectile p : Projectile.projectiles) {
				if (p == null)
					continue;
				try {
					if (null == p.textureURL || p.pos == null)
						continue;
					g.drawImage(ImageIO.read(p.textureURL), p.pos.add(p.hitbox.getHitboxPoints()[0].multiply(2.5f)));
				} catch (IOException e) {
					System.out.println("Projectile image failed to load");
					e.printStackTrace();
				}
			}
			reading = false;
			g.dispose();
		}
	}

	public void drawOnHover(Graphics g) {
		if (main.track.isWithin(main.w.mouseListener.getMousePosition())) {

			Vector tile = Main.currentTrack.grid.getGridCoordinate(main.w.mouseListener.getMousePosition().sub(main.track.a)).add(main.track.a);
			if (!(Main.selectedBuildingID < 0) && Block.blocks.size() > Main.selectedBuildingID) {
				int gridIndex = Main.currentTrack.grid.getGridIndex(Main.currentTrack.grid.getGridCoordinate(main.w.mouseListener.getMousePosition().sub(main.track.a)));
				if (Main.currentTrack.world.tiles[1][gridIndex].getTraits()[0] == "buildable") {
					if (Player.money.largerThanOrEqualTo(((Building) Building.getBuildingList(Main.selectedBuildingType).get(Main.selectedBuildingID)).cost)) {
						if (Main.currentTrack.world.tiles[1][gridIndex].getTraits() != null && Main.currentTrack.world.tiles[0][gridIndex].getTraits() != null) {
							if (Main.currentTrack.world.tiles[0][gridIndex].getTraits()[0] != "obstacle") {
								if (Main.selectedBuildingType == 0 && (Main.currentTrack.buildings[gridIndex] instanceof Block && ((Block) Main.currentTrack.buildings[gridIndex]).viableSupport(Main.selectedBuildingID)) && ((Block) Main.currentTrack.buildings[gridIndex]).tower == null) {
									g.setColor(new Color(0x50202040, true));
								} else if (Main.selectedBuildingType == 1 && Main.currentTrack.buildings[gridIndex] == null) {
									g.setColor(new Color(0x50202040, true));
								} else
									g.setColor(new Color(0x70FF0000, true));
							} else
								g.setColor(new Color(0x70FF0000, true));
						} else
							g.setColor(new Color(0x70FF0000, true));
					} else
						g.setColor(new Color(0x70FF0000, true));
				} else
					g.setColor(new Color(0x00FF0000, true));
			} else
				g.setColor(new Color(0x70FF0000, true));
			g.fillRect(tile, Main.currentTrack.tileSize.toVector().add(tile));
		}
	}
}
