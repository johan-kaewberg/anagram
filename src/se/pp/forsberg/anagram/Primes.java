package se.pp.forsberg.anagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for generating an array of prime numbers
 * @author B3
 *
 */
public class Primes {
	/**
	 * Generate prime numbers
	 * @param n Maximum number
	 * @return Array of primes up to n, not inclusive
	 */
	public static List<Integer> generate(int n) {
		boolean[] prime = new boolean[n];
		Arrays.fill(prime, true);
		
		for (int i = 2; i < n/2; i++) {
			if (!prime[i]) {
				continue;
			}
			for (int j = i*2; j < n; j += i) {
				prime[j] = false;
			}
		}
		
		List<Integer> result = new ArrayList<>();
		for (int i = 2; i < n/2; i++) {
			if (prime[i]) {
				result.add(i);
			}
		}
		return result;
	}
}
