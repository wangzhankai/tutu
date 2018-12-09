package com.superpeer.tutuyoudian.bean;

public class LocationBean {

    private String title;
    private LocationData location;
    private AddressData address_components;

    public AddressData getAddress_components() {
        return address_components;
    }

    public void setAddress_components(AddressData address_components) {
        this.address_components = address_components;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocationData getLocation() {
        return location;
    }

    public void setLocation(LocationData location) {
        this.location = location;
    }
}
