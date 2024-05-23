package library.controller.model;

import library.entity.ItemRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemRecordDTO {
	
	private Long itemId;
	private String itemName;
	private String location;
	private boolean available;
	private int checkouts;
	private int checkoutPeriod;
	
	public ItemRecordDTO(ItemRecord item) {
		this.itemId = item.getItemId();
		this.itemName = item.getItemName();
		this.location = item.getLocation();
		this.available = item.isAvailable();
		this.checkouts = item.getCheckouts();
		this.checkoutPeriod = item.getCheckoutPeriod();
	}
	
	public ItemRecordDTO(Long itemId, String itemName, String location, boolean available, int checkoutPeriod) {
		this.setItemId(itemId);
		this.setItemName(itemName);
		this.setLocation(location);
		this.setAvailable(available);
		this.setCheckoutPeriod(checkoutPeriod);
	}
	
	public ItemRecord toItemRecord() {
		
		ItemRecord item = new ItemRecord();
		
		item.setItemId(itemId);
		item.setItemName(itemName);
		item.setLocation(location);
		item.setAvailable(available);
		item.setCheckouts(checkouts);
		item.setCheckoutPeriod(checkoutPeriod);
		
		return item;
	}

}
