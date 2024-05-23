package library.controller.model;

import library.entity.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {
	
	private Long addressId;
	private String streetAddress;
	private String streetAddress2;
	private String city;
	private String state;
	private int zip;
	
	public AddressDTO(Address address) {
		this.addressId = address.getAddressId();
		this.streetAddress = address.getStreetAddress();
		this.streetAddress2 = address.getStreetAddress2();
		this.city = address.getCity();
		this.state = address.getState();
		this.zip = address.getZip();
	}

	public Address toAddress() {
		Address address = new Address();
		
		address.setAddressId(addressId);
		address.setStreetAddress(streetAddress);
		address.setStreetAddress2(streetAddress2);
		address.setCity(city);
		address.setState(state);
		address.setZip(zip);
		
		return address;
	}

}
