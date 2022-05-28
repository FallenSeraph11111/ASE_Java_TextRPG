package Battle;

import Entitys.Entity;
import items.Action;
import items.EquipmentItem;


public class DamageCalculation {
	public static double calculateDamage(Action chosenAction, Entity choosenTarget, Entity player, EquipmentItem equipment) {
		double damage = 0.0d;
		double resistance;
		resistance = choosenTarget.resistanceTo(chosenAction.damageType);
		damage += chosenAction.baseDamage + chosenAction.damageScaling * player.stats.getStat(chosenAction.damageType.corrospondingStat());
		if (equipment instanceof EquipmentItem ? true : false) {
			EquipmentItem weapon = (EquipmentItem) equipment;
			damage += weapon.baseDamage + weapon.damageScaling * player.stats.getStat(weapon.damageType.corrospondingStat());
			resistance = choosenTarget.resistanceTo(weapon.damageType);

		} else {

		}
		if (choosenTarget.blocking) {
			damage /= 0.50;
		}
		return damage;
	}
}
