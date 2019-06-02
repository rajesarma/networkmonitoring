package com.sakshi.sarma.networkmonitoring.model;

import java.util.List;
import java.util.Objects;

public class Location {

	private String location;

	private List<String> ips;

	public Location() {}

	Location(String locationName, List<String> ipList) {
		this.location = locationName;
		this.ips = ipList;
	}

	public String getLocation() {
		return location;
	}

	public List<String> getIps() {
		return ips;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}

	@Override
	public String toString() {
		return "Location [location=" + location + ", ips=" + ips + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return Objects.equals(this.location, location.location) &&
				Objects.equals(ips, location.ips);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ips == null) ? 0 : ips.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}
}
