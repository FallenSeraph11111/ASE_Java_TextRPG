package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemManager {
	private ArrayList<EquipmentItem> equipableItems;
	private ArrayList<Item> items;
	private int itemIndex=0;
	private int equipIndex=0;
	private Map<String,Integer> itemNameIndexMap =new HashMap<>();
	private Map<String,Integer> equipNameIndexMap =new HashMap<>();
	private Map<String,ItemType> nameTypeIndex =new HashMap<>();

	public Item getItem(String name){
		ItemType type=getItemType(name);
		if(type==ItemType.EQUIPMENT){
			return getEquipment(name);
		}
		int index=itemNameIndexMap.get(name);
		Item item=items.get(index);
		return item;
	}
	public EquipmentItem getEquipment(String name){
		int index=equipNameIndexMap.get(name);
		EquipmentItem item=equipableItems.get(index);
		return item;
	}
	public ItemType getItemType(String name){
		ItemType type=nameTypeIndex.get(name);
		return type;
	}

	public void addItem(Item item){
		if(item.type == ItemType.EQUIPMENT){
			addEquipment((EquipmentItem) item);
			return;
		}
		items.add(itemIndex,item);
		itemNameIndexMap.put(item.name,itemIndex);
		itemIndex++;
		nameTypeIndex.put(item.name,item.type);
		return;
	}
	private void addEquipment(EquipmentItem item){
		equipableItems.add(equipIndex,item);
		equipNameIndexMap.put(item.name,equipIndex);
		equipIndex++;
		nameTypeIndex.put(item.name,item.type);
		return;
	}
}
