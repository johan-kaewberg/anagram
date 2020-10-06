package se.pp.forsberg.anagram;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			new Anagrams().solve("ordbok-utf8.txt").stream()
				.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
