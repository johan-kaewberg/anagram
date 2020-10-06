package se.pp.forsberg.anagram;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Find all anagrams in set of words
 * @author B3
 *
 */
public class Anagrams {
	
	List<Integer> primes;
	Map<Integer, Integer> letterToIndex;
	
	/**
	 * Find all anagrams in an text resource
	 * @param resourceName
	 * @return Anagrams
	 * @throws IOException
	 */
	public List<List<String>> solve(String resourceName) throws IOException {
		try (InputStream  is = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
			return solve(is);
		}
	}
	/**
	 * Find all anagrams in an input stream
	 * @param is 
	 * @return Anagrams
	 */
	private List<List<String>> solve(InputStream is) {
		try (Scanner scanner = new Scanner(is, "utf-8")) {
			List<String> words = new ArrayList<String>();
			while (scanner.hasNext()) {
				words.add(scanner.next());
			}
			return solve(words);
		}
	}
	/**
	 * Find all anagrams in a list of words
	 * @param words
	 * @return Anagrams
	 */
	private List<List<String>> solve(List<String> words) {
		// This version uses a trick only suitable for small alphabets,
		// where each letter is assigned to a prime number.
		// The prime numbers are multiplied, and the result integer is equal
		// for two words iff they are anagrams.
		// Example fingerprint("aabc" == fingerprint("baca") == 2*2*3*5 
		
		// Collect letters used in the words
		Set<Integer> letters = words.stream()
				.flatMapToInt(s -> s.chars())
				.boxed()
				.collect(Collectors.toSet());
		// Assign each letter an index, to be used into the list of prime numbers
		letterToIndex = new HashMap<>();
		int i = 0;
		for (Integer c: letters) {
			letterToIndex.put(c, i++);
		}
		primes = Primes.generate(300);
		
		Map<Integer, List<String>> allWordsByFingerprint = new HashMap<>();
		for (String word: words) {
			// Generate an integer that is equal for two words iff they are anagrams
			int fingerprint = word.chars()
					.reduce(1, (acc, c) -> acc * primes.get(letterToIndex.get(c)));
			List<String> wordsByFingerprint = allWordsByFingerprint.get(fingerprint);
			if (wordsByFingerprint == null) {
				wordsByFingerprint = new ArrayList<>();
				allWordsByFingerprint.put(fingerprint, wordsByFingerprint);
			}
			wordsByFingerprint.add(word);
		}
		return allWordsByFingerprint.values().stream().
				filter(l -> l.size() > 1).
				collect(Collectors.toList());
	}
}
