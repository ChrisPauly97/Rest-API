package gla.cs.joose.coursework.invmgtapi.invmgt.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author inah Omoronyia
 */

import gla.cs.joose.coursework.invmgtapi.invmgt.util.SearchBy;
import gla.cs.joose.coursework.invmgtapi.invmgt.util.Util;

public class ItemFactory {
		
	public static boolean addItem(Item item){
		boolean done = false;
		if(ItemType.getTypeDesc(item.getItemType())!=null){
			if(item.getItemName() !=null && item.getItemName().length()>0){
				if(!containsSpecialCharacter(item.getItemName())){
					if(item.getSupplier() !=null && item.getSupplier().length()>0){
						if(!containsSpecialCharacter(item.getSupplier())){
							if(!itemSupplierExist(item.getItemName(), item.getSupplier())){
								if(item.getBarcode() >99 && item.getBarcode()  < 9999){
									if(item.getQuantity() >=0 && item.getQuantity() <=100){										
										done = Util.storeItem(item);
									}									
								}								
							}
						}						
					}					
				}					
			}			
		}
				
		return done;
	}
	
	public static boolean addItem(long barcode, String itemName, String itemType_s, int qty, String supplier, String desc){
		boolean done = false;
		if(itemType_s !=null){
			if(itemName !=null && itemName.length()>0){
				if(!containsSpecialCharacter(itemName)){
					if(supplier !=null && supplier.length()>0){
						if(!containsSpecialCharacter(supplier)){
							if(!itemSupplierExist(itemName, supplier)){
								if(barcode >99 && barcode < 9999){
									if(qty >=0 && qty <=100){
										ItemType itemType = ItemType.getItemType(itemType_s);
										Item item = new Item(barcode, itemName, itemType, qty, supplier, desc);
										
										done = Util.storeItem(item);
									}									
								}								
							}
						}						
					}					
				}					
			}			
		}
				
		return done;
	}
	
	public static Item[] search(String searchbydesc, String pattern, int limit){
		SearchBy searchby = SearchBy.getSearchBy(searchbydesc);	
		if(pattern == null){
			pattern = "";
		}
		Item [] results = Util.getMatchingItems(pattern, searchby, limit);
		
		return results;
	}
	
	public static boolean delete(Item item){
		return Util.deleteItem(item);
	}
	
	public static boolean delete(long itemid){
		return Util.deleteItem(itemid);
	}
	
	
	private static boolean itemSupplierExist(String itemName, String supplierName){
		Item item = Util.getSupplierItem(itemName, supplierName);
		
		if(item != null){
			return true;
		}
		
		return false;
	}
	
	private static boolean containsSpecialCharacter(String text){
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(text);
		return m.find();
	}
	

}
