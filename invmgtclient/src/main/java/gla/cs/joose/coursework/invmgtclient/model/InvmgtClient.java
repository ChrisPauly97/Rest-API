package gla.cs.joose.coursework.invmgtclient.model;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

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
		//Task 1
		
		return null;	
	}
	
	/**
	 * This method makes a REST API call to invmgtapi service to delete an item from the inventory.
	 * 
	 * @param itemid
	 * @return returns a status code indicating the outcome of deleteRequest
	 */
	public int deleteRequest(long itemid){
		//Task 2
		
		return -1;
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
		
		//Task 3
		
		return null;
		
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
				
		//Task 4
		
		return -1;
	}
}
