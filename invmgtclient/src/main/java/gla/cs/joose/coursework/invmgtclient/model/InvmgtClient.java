package gla.cs.joose.coursework.invmgtclient.model;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.WebTarget;
import gla.cs.joose.coursework.invmgtclient.util.*;

import gla.cs.joose.coursework.invmgtclient.util.Util;

public class InvmgtClient {
	private static Client client;
	private static WebTarget baseTarget;
	private static WebTarget itemsTarget;
	private static WebTarget itemTarget;
	
	/**
	 * Constructor to initialise a REST client and web targets (resource URIs)
	 * DO NOT MODIFY
	 */
	public InvmgtClient(){
		client = ClientBuilder.newClient();
		
		baseTarget = client.target("http://localhost:8080/invmgtapi/webapi/invapi");
		itemsTarget = baseTarget.path("items");
		itemTarget = baseTarget.path("items").path("{itemid}");
	}
	
	/**
	 * This method makes a REST API call to invmgtapi service for an update to an item in the inventory.
	 * 
	 * @param updateitemid
	 * @param newBarcode
	 * @param newItemName
	 * @param newItemType_s
	 * @param newQty
	 * @param newSupplier
	 * @param newDesc
	 * @return returns the updated item if updateRequest is successful or the error status code if unsuccessful
	 */
	public Object updateRequest(long updateitemid,  long newBarcode, 
							 String newItemName, String newItemType_s,
							 int newQty, String newSupplier, String newDesc){
		Builder oldItem = itemTarget.resolveTemplate("itemid", updateitemid).request();
		
		Item itemToUpdate = new Item(newBarcode, newItemName , ItemType.getItemType(newItemType_s) , newQty , newSupplier, newDesc);
		Response putResponse = oldItem.put(Entity.json(itemToUpdate));
		
		if (putResponse.getStatus() != 200) {
            return (Object) putResponse.getStatus();
        } else {
        	return itemToUpdate;
        }
	
	}
	
	/**
	 * This method makes a REST API call to invmgtapi service to delete an item from the inventory.
	 * 
	 * @param itemid
	 * @return returns a status code indicating the outcome of deleteRequest
	 */
	public int deleteRequest(long itemid){
		Builder itemDel = itemTarget.resolveTemplate("itemid", itemid).request();
		Response getResponse = itemDel.delete();
		
		return getResponse.getStatus();
	}
	
	/**
	 * This method makes a REST API call to invmgtapi service to retrieve items that matches a 
	 * specific search pattern from the inventory.
	 * 
	 * @param searchbydesc
	 * @param pattern
	 * @param limit
	 * @return returns a list of items that matches the searchRequest parameters
	 */
	public Item[] searchRequest(String searchbydesc, String pattern, int limit){
		Builder items = itemsTarget.queryParam("searchbydesc", searchbydesc)
				.queryParam("pattern", pattern)
				.queryParam("limit", limit).request(MediaType.APPLICATION_JSON);
		Response getResponse = items.get();
		return getResponse.readEntity(Item[].class);
		
	}
	
	/**
	 * This method makes a REST API call to invmgtapi service to add an item to the inventory
	 *  
	 * @param barcode
	 * @param itemName
	 * @param itemType_s
	 * @param qty
	 * @param supplier
	 * @param desc
	 * @return - returns a REST response status code indicating the outcome of addItemRequest
	 */
	public int addItemRequest(long barcode, String itemName, String itemType_s, int qty, String supplier, String desc){
		Item newItem = new Item(barcode, itemName, ItemType.getItemType(itemType_s), qty, supplier, desc);
		Builder itemToAdd = itemsTarget.request();
		Response postResponse = itemToAdd.post(Entity.json(newItem));
		
		return postResponse.getStatus();
	}
}
