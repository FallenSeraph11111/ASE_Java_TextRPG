package items;

import java.util.ArrayList;

public class EquipmentItem extends Item {
    public final int levelRequirement;
    public final ArrayList<Action> actions;
    //todo add stat changin attribute
    public final EquipmentSlot equipmentSlot;
	public final double baseDamage;
	public final double damageScaling;
	public final DamageType damageType;
	public final double protection;
	public final ItemType type;
    public EquipmentItem(String name, int levelRequirement, ArrayList<Action> actions, EquipmentSlot equipmentSlot, double value, ItemType type, double protection, double baseDamage, double damageScaling, DamageType damageType) {
	    super(name, value, 1, type);
	    this.levelRequirement = levelRequirement;
	    this.actions = actions;
	    this.equipmentSlot = equipmentSlot;
		this.protection=protection;
		this.type=type;
	    this.baseDamage = baseDamage;
	    this.damageScaling = damageScaling;
	    this.damageType = damageType;
    }
}
