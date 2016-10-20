package values;

import enums.Purchases;
import net.abysmal.engine.GlobalVariables;
import net.abysmal.engine.utils.HugeInteger;

public class Player {

	public static HugeInteger money = new HugeInteger().add(GlobalVariables.debug ? ((Purchases.basicTower.cost.clone().add(Purchases.woodenBlock.cost)).mult(4)).add(Purchases.researchFacility.cost) : Purchases.basicTower.cost.clone().add(Purchases.woodenBlock.cost));
	public static HugeInteger research = new HugeInteger().add(GlobalVariables.debug ? Purchases.zombie.cost.add(new HugeInteger((short) 200)):Purchases.zombie.cost);
}