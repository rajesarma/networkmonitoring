package com.sakshi.sarma.networkmonitoring.service;

import com.sakshi.sarma.networkmonitoring.model.IpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PingStatusService {

	private Map<String, LinkedHashMap<String, IpStatus>> siteStatus = new LinkedHashMap<>();

	public void saveStatus(String locationName, String ip, IpStatus ipStatus) {

		if(!siteStatus.containsKey(locationName)) {
			siteStatus.put(locationName, new LinkedHashMap<>());
		}
		siteStatus.get(locationName).put(ip, ipStatus);
	}

	public Map<String, LinkedHashMap<String, IpStatus>> getAllLocationsStatus(){
//		Map newMap = siteStatus.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Map newMap = siteStatus.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));

		return newMap;
	}
}
