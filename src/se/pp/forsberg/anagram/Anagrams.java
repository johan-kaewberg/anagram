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
		Map<Map<Integer, Integer>, List<String>> allWordsByLetters = new HashMap<>();
		for (String word: words) {
			// Count letters in word
			Map<Integer, Integer> letters = new HashMap<Integer, Integer>();
			word.chars().forEach(c -> letters.compute(c, (k, v) -> v == null? 1 : v + 1));
			
			List<String> wordsByLetters = allWordsByLetters.computeIfAbsent(letters, k -> new ArrayList<String>());
			wordsByLetters.add(word);
		}
		return allWordsByLetters.values().stream().
				filter(l -> l.size() > 1).
				collect(Collectors.toList());
	}
}
