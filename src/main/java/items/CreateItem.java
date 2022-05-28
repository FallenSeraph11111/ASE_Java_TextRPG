package items;

import Entitys.*;

import java.util.ArrayList;

public class CreateItem {

	private String name;
	private double value;
	private ItemType type=ItemType.TRASH;
	private int stackSize=1;
	private double baseDamage;
	private double damageScaling;
	private ArrayList<Action> actions;
	private DamageType damageType;
	private double protection=0;
	private int levelRequirement=0;
	private EquipmentSlot equipmentSlot;

	private CreateItem(String name  ) {
		this.name = name;


	}
	public static CreateItem named(String name){
		return new CreateItem(name);
	}

	public CreateableItem valued(double value) {
		this.value=value;
		this.type=ItemType.VALUABLE;
		return new items.CreateItem.CreateableItem();
	}

	public class CreateableItem {
		private CreateableItem() {

		}
		public CreateableItem stackable(int stackSize){
			CreateItem.this.stackSize = stackSize;
			return this;
		}
		public CreateableItem as(ItemType type){
			CreateItem.this.type=type;
			return this;
		}
		public CreateableItem equipable(int levelRequirement, ArrayList<Action> actions, EquipmentSlot equipmentSlot){
			CreateItem.this.levelRequirement=levelRequirement;
			CreateItem.this.actions=actions;
			CreateItem.this.equipmentSlot=equipmentSlot;
			CreateItem.this.type=ItemType.EQUIPMENT;
			return this;
		}
		public CreateableItem ofTypeWeapon(double baseDamage,double damageScaling,DamageType damageType) {
			CreateItem.this.baseDamage = baseDamage;
			CreateItem.this.damageScaling = damageScaling;
			CreateItem.this.damageType = damageType;
			return this;
		}
		public CreateableItem protects(double protection){
			CreateItem.this.protection=protection;
			return this;
		}
		public EquipmentItem buildEquipment(){
			return CreateItem.this.buildEquipment();
		}public Item buildItem(){
			return CreateItem.this.build();
		}
	}

	private EquipmentItem buildEquipment() {
		return new EquipmentItem(this.name,this.levelRequirement, this.actions, this.equipmentSlot, this.value,this.type,this.protection,this.baseDamage,this.damageScaling,this.damageType);
	}
	private Item build(){
		return new Item(this.name,this.value,this.stackSize, this.type);
	}
}

