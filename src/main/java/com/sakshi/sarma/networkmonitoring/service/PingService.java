package com.sakshi.sarma.networkmonitoring.service;

import com.sakshi.sarma.networkmonitoring.model.IpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class PingService {

	private static final int PING_TTL = 60;
	private static final int PING_COUNT = 8;
	private static final int MINIMUM_PACKETS = (int) (PING_COUNT * 0.8);
	private static final String PING_COMMAND;
	private static final String LINUX_PING_COMMAND_FORMAT = "ping -c %d -t %d %s";
	private static final String WINDOWS_PING_COMMAND_FORMAT = "ping -n %d -i %d %s";
	private static final String RECEIVED_MESSAGE_WINDOWS = "Received = ";
	private static final String RECEIVED_MESSAGE_LINUX = "received";
	private final ExecutorService pool = Executors.newFixedThreadPool(10);
	private PingStatusService pingStatusService;

	@Autowired
	PingService(final PingStatusService pingService){
		this.pingStatusService = pingService;
	}

	static {
		boolean isHostOsWindows = System.getProperty("os.name")
				.toLowerCase(Locale.US)
				.startsWith("windows");

		PING_COMMAND = isHostOsWindows ? WINDOWS_PING_COMMAND_FORMAT : LINUX_PING_COMMAND_FORMAT;
	}

	void checkNetworkStatus(String locationName, List<String> ipList){

		pool.submit(() -> {
			for(String ip : ipList) {
				IpStatus ipStatus = pingStats(String.format(PING_COMMAND, PING_COUNT, PING_TTL, ip));
				pingStatusService.saveStatus(locationName, ip, ipStatus);
			}
		});
	}

	private IpStatus pingStats(String command){

		StringBuilder pingStats = new StringBuilder();
		try {
			Process p = Runtime.getRuntime().exec(command);
			try (BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {

				String s;
				// reading output stream of the command
				while ((s = inputStream.readLine()) != null) {
					pingStats.append(s).append(",");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pingStatus(pingStats.toString());
	}

	/**
	 *
	 * @param pingStatistics
	 *   Take the full IP pingStatistics as a input parameter.
	 *   String handling on the pingStatistics.
	 *   Reading the No. of packets from "received" in pingStatistics.
	 *
	 *
	 * @return
	 *   return Good,Packet Loss,Network Down depending on the packets received.
	 */
	private IpStatus pingStatus(String pingStatistics){

		int packetsReceived = 0;
		int ttlExpireCount = 0;

		String[] lines = pingStatistics.split(",");
		for(String line : lines){
			if (line.contains(RECEIVED_MESSAGE_WINDOWS)){
				String s = line.replaceAll(RECEIVED_MESSAGE_WINDOWS, "").trim();
				packetsReceived = Integer.parseInt(s);
			} else if (line.contains(RECEIVED_MESSAGE_LINUX)) {
				String s = line.replaceAll(RECEIVED_MESSAGE_LINUX, "").trim();
				packetsReceived = Integer.parseInt(s);
			}

			if(line.contains("TTL expired in transit")){
				ttlExpireCount++;
			}//else the ttlExpireCount defaults to 0
		}

		int effectivePacketReceived = packetsReceived - ttlExpireCount;

		if(effectivePacketReceived == 0){
			return IpStatus.DOWN;
		} else if(effectivePacketReceived < MINIMUM_PACKETS){
			return IpStatus.PACKET_LOSS;
		} else{
			return IpStatus.UP;
		}
	}
}
