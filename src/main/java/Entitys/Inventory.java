package Entitys;

import items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
	ArrayList<Item> items;
	Map<String,Integer> amount;
    //maybe using an arraylsit isn't a good idea...but the possibility to increase inventory size could be usefull...have to decide optimal data type


	public Inventory() {
		this.items = new ArrayList<>();
		this.amount = new HashMap<>();
	}

	public Inventory(List<Item> items, Map<String, Integer> amount) {
		this.items=new ArrayList<>(items);
		this.amount = amount;
	}
	public void addItem(Item item){
		if (amount.containsKey(item.name)){
			int itemAmount=amount.get(item.name);
			if(item.stacksize <= itemAmount+1) return;
			amount.put(item.name,itemAmount+1);
			return;
		}
		items.add(item);
		amount.put(item.name,1);
	}
	public void addItem(Item item,int inAmount){
		if (amount.containsKey(item.name)){
			int itemAmount=amount.get(item.name);
			if(item.stacksize <= itemAmount+inAmount) return;
			amount.put(item.name,itemAmount+inAmount);
			return;
		}
		items.add(item);
		amount.put(item.name,1);
	}

	public int removeItem(Item item){
		if (amount.containsKey(item.name)){
			int itemAmount=amount.get(item.name);
			if (itemAmount > 1){
				amount.put(item.name,itemAmount-1);
				return 1;
			}
			amount.remove(item.name);
			items.remove(item);
			return 1;
		}
		return 0;
	}
	public int removeItem(Item item,int outAmount){
		if (amount.containsKey(item.name)){
			int itemAmount=amount.get(item.name);
			if (itemAmount > outAmount){
				amount.put(item.name,itemAmount-outAmount);
				return outAmount;
			}
			amount.remove(item.name);
			items.remove(item);
			return itemAmount;
		}
		return 0;
	}

}
