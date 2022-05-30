package Battle;

import Entitys.Entity;
import items.Action;
import items.EquipmentItem;


public class DamageCalculation {
	public static double calculateDamage(Action chosenAction, Entity choosenTarget, Entity player, EquipmentItem equipment) {
		double damage = 0.0d;
		double resistance;
		resistance = choosenTarget.resistanceTo(chosenAction.damageType);
		damage += chosenAction.baseDamage + chosenAction.damageScaling * (player.stats.getStat(chosenAction.damageType.corrospondingStat())-10);

		damage += player.equipment.getBase_damage(); // equipment.damageScaling * player.stats.getStat(equipment.damageType.corrospondingStat());
		//damage *= resistance/100;

		if (choosenTarget.blocking) {
			damage /= 0.50;
		}
		return damage;
	}
}
