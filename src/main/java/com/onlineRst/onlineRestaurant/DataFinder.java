package com.onlineRst.onlineRestaurant;

import java.util.List;

public class DataFinder {

	boolean isPresent(List<Item> list,String itemName){
		for (Item item : list) {
			if(item.getName().equals(itemName)) {
				return true;
			}
		}
		return false;
	}
}
