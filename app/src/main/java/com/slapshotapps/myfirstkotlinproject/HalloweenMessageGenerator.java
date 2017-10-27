package com.slapshotapps.myfirstkotlinproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HalloweenMessageGenerator {

	private List<String> messageList;
	private Random random;

	public HalloweenMessageGenerator() {
		messageList = new ArrayList<>(5);
		messageList.add("Boo!!!");
		messageList.add("Trick or Treat!");
		messageList.add("Beware! Keep Out! Danger! Caution!");
		messageList.add("Franken-tastic");
		messageList.add("Happy Halloween");

		random = new Random(5);
	}

	public String getMessage() {
		return messageList.get(random.nextInt(4));
	}
}
