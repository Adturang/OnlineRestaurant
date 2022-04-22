package com.onlineRst.onlineRestaurant;

import java.util.List;

import com.onlineRst.onlineRestaurant.model.Item;
import com.onlineRst.onlineRestaurant.model.OwnRecipe;

public class DataFinder {

	boolean isPresent(List<Item> list,String itemName){
		for (Item item : list) {
			if(item.getName().equals(itemName)) {
				return true;
			}
		}
		return false;
	}
	
	boolean isExist(List<OwnRecipe> recList,String itemName){
		for (OwnRecipe recipe : recList) {
			if(recipe.getName().equals(itemName)) {
				return true;
			}
		}
		return false;
	}
}
