package com.ukefu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

@Service
public class HazelcastService {
	@Autowired
	public HazelcastInstance hazelcastInstance;	
}
