package com.sakshi.sarma.networkmonitoring.model;

public enum IpStatus {
	DOWN,
	UP,
	PACKET_LOSS;

	public String getIpStatus() {
		return this.toString();
	}
}
