package values;

import enums.Purchases;
import net.abysmal.engine.utils.HugeInteger;

public class Player {

	public static HugeInteger money = new HugeInteger().add(Purchases.basicTower.cost).add(Purchases.woodenBlock.cost), research = new HugeInteger().add(Purchases.zombie.cost);
	
}