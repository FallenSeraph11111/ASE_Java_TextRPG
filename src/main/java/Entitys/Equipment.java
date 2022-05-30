package Entitys;

import items.Action;
import items.EquipmentItem;
import items.EquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Equipment {
	public Map<EquipmentSlot,EquipmentItem> equipmentSlotEquipmentItemMap;
	public List<EquipmentItem> items;
	private double protection;
	private double base_damage;
	public List<Action> actions;

	public Equipment(){
		equipmentSlotEquipmentItemMap = new HashMap<>();
		for (EquipmentSlot eqSlot: EquipmentSlot.values()) {
			equipmentSlotEquipmentItemMap.putIfAbsent(eqSlot,null);
		}
		actions=new ArrayList<>();
		items=new ArrayList<>();
		protection=0.0d;
		base_damage=0.0;
	}
	public Equipment(List<EquipmentItem>equipment){
		equipmentSlotEquipmentItemMap = new HashMap<>();
		items=new ArrayList<>();
		actions=new ArrayList<>();
		for (EquipmentItem item:equipment) {
			System.out.println(item.name);
			equipmentSlotEquipmentItemMap.put(item.equipmentSlot,item);
		}
		items.addAll(equipmentSlotEquipmentItemMap.values());
		for (EquipmentSlot eqSlot: EquipmentSlot.values()) {
			equipmentSlotEquipmentItemMap.putIfAbsent(eqSlot,null);
		}
		calculateArmour();
		getAllActions();
		protection=0.0d;
		base_damage=0.0;
	}
    public EquipmentItem getEquipmentWithAction(Action choosenAction) {
	    for (EquipmentItem item:items) {
		    if (item.actions.contains(choosenAction)){
				return item;
		    }
	    }
		return null;
    }
	public void getAllActions(){
		actions.clear();
		for (EquipmentItem item:items) {
			actions.addAll(item.actions);
		}
	}

	public EquipmentItem unequipItem(EquipmentSlot slot){
		EquipmentItem equip=equipmentSlotEquipmentItemMap.getOrDefault(slot,null);
		if(equip == null){
			return null;
		}
		equipmentSlotEquipmentItemMap.put(slot,null);
		items.remove(equip);
		calculateArmour();
		getAllActions();
		return equip;
	}
	public EquipmentItem equipItem(EquipmentItem item){
		EquipmentSlot slot=item.equipmentSlot;
		EquipmentItem equip=equipmentSlotEquipmentItemMap.getOrDefault(slot,null);
		equipmentSlotEquipmentItemMap.put(slot,item);
		items.add(item);
		calculateArmour();
		getAllActions();
		if(equip != null){
			items.remove(equip);
			return equip;
		}

		return null;
	}
	private void calculateArmour(){
		protection=0.0d;
		for (EquipmentItem item:items) {
			protection+=item.protection;
			base_damage+=item.baseDamage;
		}
	}

	public double getProtection() {
		return protection;
	}

	public double getBase_damage() {
		return base_damage;
	}
}
