package gla.cs.joose.workshop.invmgtapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.net.URI;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import gla.cs.joose.coursework.invmgtapi.invmgt.model.Item;
import gla.cs.joose.coursework.invmgtapi.invmgt.model.ItemFactory;
import gla.cs.joose.coursework.invmgtapi.invmgt.model.ItemType;

public class InvAPI {
	
	/**
	 * This function receives request from rest client to delete an item from the inventory
	 * @param itemid
	 * @param uriinfo
	 * @return Return a Response object containing the status code after delete operation
	 */
	@DELETE
	@Path("/items/{itemid}")
	public Response deleteItem(@PathParam("itemid") long itemid ,@Context UriInfo uriinfo) {		
		boolean deleted = ItemFactory.delete(itemid);
		if(!deleted){
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Status.OK).build();
	

	}
	
	/**
	 * This function receives request from rest client to retrieve a set of items that matches 
	 * a search pattern from the inventory
	 * @param searchbydesc
	 * @param pattern
	 * @param limit
	 * @param uriinfo
	 * @return return a Response object containing a list of items that matches a search pattern and the status code	 * 		  
	 */
	@GET
	@Path("/items")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@QueryParam("searchbydesc")String searchbydesc, 
			@QueryParam("pattern")String pattern, 
			@QueryParam("limit")int limit, @Context UriInfo uriinfo) {        
       
		Item[] results = ItemFactory.search(searchbydesc, pattern, limit);
        
		URI uri = uriinfo.getAbsolutePath();
		return Response.created(uri).entity(results).status(Status.OK).build();
		
	}
	
	/**
	 * This function receives request from rest client to update an item in the inventory
	 * @param updateitemid
	 * @param newBarcode
	 * @param newItemName
	 * @param newItemType_s
	 * @param newQty
	 * @param newSupplier
	 * @param newDesc
	 * @param uriinfo
	 * @return return a Response object containing  the updated item and the status code
	 */
	@PUT
	@Path("/items/{itemid}")
	public Response updateItem(@PathParam("itemid")long updateitemid,
							   long newBarcode,
							   String newItemName, 
							   String newItemType_s, 
							   int newQty,
							   String newSupplier,
							   String newDesc,
							   @Context UriInfo uriinfo){	
				        
		boolean updated = false;
		
		boolean deleted = ItemFactory.delete(updateitemid);
		Item uitem = null;
		
		if(deleted){
			ItemType itemType = ItemType.getItemType(newItemType_s);
			uitem = new Item(newBarcode, newItemName, itemType, newQty, newSupplier, newDesc);
			uitem.setId(updateitemid);
			boolean done = ItemFactory.addItem(uitem);
			if(done){
				updated = true;
			}
		}	
		
		String u_id = String.valueOf(updateitemid);
		URI uri = uriinfo.getAbsolutePathBuilder().path(u_id).build();
		if(updated){
			return Response.created(uri).entity(uitem).status(Status.OK).build();
		}
		return Response.created(uri).entity(uitem).status(Status.INTERNAL_SERVER_ERROR).build();
					
	}
	
	/**
	 * This function receives request from rest client to add an item to the inventory
	 * @param barcode
	 * @param itemName
	 * @param itemType_s
	 * @param qty
	 * @param supplier
	 * @param desc
	 * @param uriinfo
	 * @return return a Response object containing  the status code of the operation
	 */
	@POST
	@Path("/items") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addItem(long barcode,
							String itemName, 
					        String itemType_s, 
					        int qty,
					        String supplier,
					        String desc,
					        @Context UriInfo uriinfo){			
		        
		ItemType itemType = ItemType.getItemType(itemType_s);
		Item item = new Item(barcode, itemName, itemType, qty, supplier, desc);
				
		boolean done = ItemFactory.addItem(item);
		URI uri = uriinfo.getAbsolutePath();
		
		if(done){
			return Response.created(uri).status(Status.CREATED).build();
		}
		return Response.created(uri).status(Status.NOT_ACCEPTABLE).build();
		
	}
	
}
