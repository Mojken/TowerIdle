package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import menues.BuildingShop;
import menues.ResearchMenu;
import net.abysmal.engine.GlobalVariables;
import net.abysmal.engine.entities.Entity;
import net.abysmal.engine.entities.AI.Pathfinding;
import net.abysmal.engine.graphics.Graphics;
import net.abysmal.engine.graphics.Window;
import net.abysmal.engine.graphics.geometry.Square;
import net.abysmal.engine.handlers.misc.Button;
import net.abysmal.engine.handlers.misc.Tick;
import net.abysmal.engine.maths.Vector;
import net.abysmal.engine.utils.HugeInteger;
import objects.Spawner;
import objects.blocks.Block;
import objects.buildings.Building;
import objects.entities.Mob;
import objects.entities.Projectile;
import objects.towers.Tower;
import values.Player;

public class Update implements Tick {

	Main main;
	public static boolean lastInput, reading, clearMobs;
	public static int screen = 0;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static Spawner sp;
	float incomeFactor = 1;

	public Update(Main main) {
		this.main = main;
	}

	public static void switchScreen(int screen) {
		Update.screen = screen;
		Button.registerButtons(screen, Main.f);
	}

	public static void updateScreen() {
		Button.registerButtons(screen, Main.f);
	}

	@Override
	public void update() {

	}

	public void updater() {
		if (Main.initialized) {
			if (screen == 0) {

				if (!Main.initialized) return;

// if (main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_1][0] == 1) Main.selectedBuildingID = 0;
// if (main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_2][0] == 1) Main.selectedBuildingID = 1;
//
// if (main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_CONTROL][0] == 1) Main.selectedBuildingType = 0;
// else Main.selectedBuildingType = 1;

				if (main.w.mouseListener.getClickInfo()[1][4] == 1 && (!lastInput || main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_SHIFT][0] == 1)) {
					if (main.track.isWithin(main.w.mouseListener.getMousePosition())) {
						Building.placeBuilding(main.w.mouseListener.getMousePosition().sub(main.track.a));
						Main.currentTrack.path.calculatePath(Main.currentTrack);
					}
				}

				if (main.w.mouseListener.getClickInfo()[3][4] == 1) {
					if (main.track.isWithin(main.w.mouseListener.getMousePosition())) {
						if (main.w.keyboardListener.getCurrentlyPressedKeys()[KeyEvent.VK_CONTROL][0] == 1) {
							Building b0 = Main.currentTrack.buildings[Main.currentTrack.grid.getGridIndex(main.w.mouseListener.getMousePosition().sub(main.track.a))];
							if (b0 != null) {
								for (Building b1:Main.currentTrack.buildings) {
									if (b1 == null) continue;
									if (b0.ID == b1.ID && b0.type == b1.type) {
										Building.sellBuilding(Main.currentTrack.grid.getGridCoordinate(b1.gridIndex));
										Main.currentTrack.path.calculatePath(Main.currentTrack);
									}
								}
							}
						} else {
							Building.sellBuilding(main.w.mouseListener.getMousePosition().sub(main.track.a));
							Main.currentTrack.path.calculatePath(Main.currentTrack);
						}
					}
				}

				for (Building b:Main.currentTrack.buildings) {
					if (b instanceof Block && ((Block) b).tower != null) {
						Tower t = ((Block) b).tower;
						t.update();
					} else if (b instanceof Tower) {
						((Tower) b).update();
					}
				}

				lastInput = main.w.mouseListener.getClickInfo()[1][4] == 1;

				if (Main.currentTrack.lastEscaped > 0) {
					Main.currentTrack.lastEscaped--;
					incomeFactor = .2f;
				} else incomeFactor = 1;

// if (main.buttons[0].update(main.w.mouseListener)) {
// clearMobs = true;
// Main.currentTrack.lastEscaped = Main.currentTrack.cooldown * 10;
// }

				Iterator<Entity> i = Main.currentTrack.entities.iterator();
				while (i.hasNext()) {
					Entity e = i.next();
					e.kill = !e.move();

					if (e instanceof Projectile) {
						for (Entity mob:Main.currentTrack.entities) {
							if (mob instanceof Mob) {
								if (new Square(e.hitbox.getHitboxPoints()[0].add(e.pos), e.hitbox.getHitboxPoints()[1].add(e.pos)).isWithin(mob)) {
									e.kill = true;
									((Mob) mob).currentHealth = ((Mob) mob).currentHealth.mult((float) (((Mob) mob).getResearchMultiplier(1))).sub(((Projectile) e).hugeDamage);
									break;
								}
							}
						}
					}

					if (e instanceof Mob && !((Mob) e).currentHealth.mult((float) (((Mob) e).getResearchMultiplier(1))).largerThanOrEqualTo(new HugeInteger((short) 1))) {
						e.kill = true;
					}
					// TODO implement visual feedback (?)
					if (e.kill) {
						if (e instanceof Mob) {
							Player.money.add(((Mob) e).income.mult((float) (((Mob) e).getIncomeMultiplier())).mult(incomeFactor));
						}
						i.remove();
					}
					if (clearMobs && e instanceof Mob) i.remove();

				}
				clearMobs = false;
				sp.update();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (Main.initialized) {
			updater();
			g.clearRect(Vector.ZERO(), Window.dimension.toVector());

			drawMain(g);
			if (screen == 0) {
				g.drawString("Money: " + Player.money, new Vector(main.money.a.x, main.money.d.y));
				g.drawString("Research: " + Player.research, new Vector(main.research.a.x, main.research.d.y));
// g2.drawString("Clear mobs", (int) (main.save.a.x + ((main.save.b.x - main.save.a.x) / 2)) - 40, (int) (main.save.a.y + ((main.save.b.y - main.save.a.y) / 2)) + 5);

// for (Button b:main.buttons) {
// g.drawRoundRect(b.bounds.a, b.bounds.b, new Vector(10, 10));
// }

				for (Map.Entry<Integer, Button> e:Button.buttons.get(screen).entrySet()) {
					e.getValue().draw(g);
				}

				drawDebug(g);
				g.dispose();
			}
		}
	}

	private void drawMain(Graphics g) {
		switch (screen) {
			case 0:
				drawGame(g);
				BuildingShop.draw(g);
			break;
			case 2:
				ResearchMenu.draw(g);
// ResearchMenu.update(main.w.mouseListener);
			break;
			case 20:
				ResearchMenu.draw(g);
			break;
		}
	}

	private void drawGame(Graphics g) {
		g.drawImage(Main.currentTrack.world.world, main.track.a, main.track.d.sub(main.track.a));
		for (Building b:Main.currentTrack.buildings) {
			if (b == null) continue;
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
		for (Entity e:Main.currentTrack.entities) {
			if (e == null) continue;
			try {
				if (null == e.textureURL || e.pos == null) continue;
				g.drawImage(ImageIO.read(e.textureURL), e.pos.add(e.hitbox.getHitboxPoints()[0]).add(main.track.a), e.hitbox.getHitboxPoints()[1].multiply(2.5f));
			} catch (IOException ex) {
				System.out.println("Projectile image failed to load");
				ex.printStackTrace();
			}
		}
		reading = false;

	}

	public void drawOnHover(Graphics g) {
		if (main.track.isWithin(main.w.mouseListener.getMousePosition())) {
			Vector tile = Main.currentTrack.grid.getGridCoordinate(main.w.mouseListener.getMousePosition().sub(main.track.a)).add(main.track.a);
			if (!(Main.selectedBuildingID < 0) && Building.getBuildingList(Main.selectedBuildingType).size() > Main.selectedBuildingID) {
				int gridIndex = Main.currentTrack.grid.getGridIndex(Main.currentTrack.grid.getGridCoordinate(main.w.mouseListener.getMousePosition().sub(main.track.a)));
				if (Main.currentTrack.world.tiles[1][gridIndex].getTraits()[0] == "buildable") {
					if (Player.money.largerThanOrEqualTo(((Building) Building.getBuildingList(Main.selectedBuildingType).get(Main.selectedBuildingID)).cost)) {
						if (Main.currentTrack.world.tiles[1][gridIndex].getTraits() != null && Main.currentTrack.world.tiles[0][gridIndex].getTraits() != null) {
							if (Main.currentTrack.world.tiles[0][gridIndex].getTraits()[0] != "obstacle") {
								if (Main.selectedBuildingType == 0 && (Main.currentTrack.buildings[gridIndex] instanceof Block && ((Block) Main.currentTrack.buildings[gridIndex]).viableSupport(Main.selectedBuildingID)) && ((Block) Main.currentTrack.buildings[gridIndex]).tower == null) {
									g.setColour(new Color(0x50202040, true));
								} else if ((Main.selectedBuildingType == 1 || (Main.selectedBuildingType == 0 && !Tower.towers.get(Main.selectedBuildingID).requiresBlock)) && Main.currentTrack.buildings[gridIndex] == null) {
									g.setColour(new Color(0x50202040, true));
								} else g.setColour(new Color(0x70FF0000, true));
							} else g.setColour(new Color(0x70FF0000, true));
						} else g.setColour(new Color(0x70FF0000, true));
					} else g.setColour(new Color(0x70FF0000, true));
				} else g.setColour(new Color(0x00FF0000, true));
			} else g.setColour(new Color(0x70FF0000, true));
			g.fillRect(tile, Main.currentTrack.tileSize.toVector().add(tile));
			g.setColour(Color.BLACK);
		}
	}

	public void drawDebug(Graphics g) {
		if (GlobalVariables.debug) {

			for (Square p:Main.partition.partitions) {
				g.drawRect(p.a, p.d);
			}
			for (Square p:Main.towerPartition.partitions) {
				g.drawRect(p.a.add(Main.towerOffset), p.d.add(Main.towerOffset));
			}
			g.setColour(new Color(0xAAAAAA));
			for (Building b:Main.currentTrack.buildings) {
				if (b instanceof Block && ((Block) b).tower != null) {
					Tower t = ((Block) b).tower;
					Vector pos = Main.currentTrack.grid.getGridCoordinate(t.gridIndex).add(main.track.a).add(Main.currentTrack.grid.tileSize.toVector().multiply(.5f));
					g.drawString(t.cooldown + "", pos);
					g.drawOval(pos.add(-t.radius), new Vector(t.radius * 2, t.radius * 2));
					Entity target = t.getTarget();
					if (null != target) g.drawLine(pos, target.pos.add(main.track.a));
				} else if (b instanceof Tower) {
					Tower t = (Tower) b;
					Vector pos = Main.currentTrack.grid.getGridCoordinate(t.gridIndex).add(main.track.a).add(Main.currentTrack.grid.tileSize.toVector().multiply(.5f));
					g.drawString(t.cooldown + "", pos);
					g.drawOval(pos.add(-t.radius), new Vector(t.radius * 2, t.radius * 2));
					Entity target = t.getTarget();
					if (null != target) g.drawLine(pos, target.pos.add(main.track.a));
				}
			}
			g.setColour(new Color(0));
			for (int nodeIndex = 0; nodeIndex < Main.currentTrack.path.getLength(); nodeIndex++) {
				g.fillRect(Main.currentTrack.path.getNodePos(nodeIndex).add(-3f).add(main.track.a), Main.currentTrack.path.getNodePos(nodeIndex).add(3f).add(main.track.a));
				if (Pathfinding.costs != null) g.drawString("" + Pathfinding.costs[nodeIndex][2], Main.currentTrack.path.getNodePos(nodeIndex));
				if (nodeIndex == Main.currentTrack.path.getLength() - 1) continue;
				g.drawLine(Main.currentTrack.path.getNodePos(nodeIndex).add(main.track.a), Main.currentTrack.path.getNodePos(nodeIndex + 1).add(main.track.a));
			}

			for (Entity e:Main.currentTrack.entities) {
				if (GlobalVariables.debug) g.drawRect(e.pos.add(main.track.a).add(e.hitbox.getHitboxPoints()[0]), e.pos.add(main.track.a).add(e.hitbox.getHitboxPoints()[1]));
			}
		}
	}
}