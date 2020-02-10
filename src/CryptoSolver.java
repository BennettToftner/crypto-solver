import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class CryptoSolver {

	public static void main(String[] args) throws IOException{
		File f = new File("puzzle.txt");
		Scanner k = new Scanner(f);
		String puzzle = k.nextLine();
		puzzle = puzzle.toLowerCase();
		String[] words = puzzle.split(" ");
		HashMap<String, ArrayList<String>> poss = getPossibilities(words);
		String translated = puzzle;
		System.out.println(poss.get("jad").size());
		System.out.println(poss.get("jad"));
		ArrayList<String> sorted = sortByPoss(poss);
		System.out.println(sorted);
		int loops = 1;
		for (int a = 0; a < poss.get(sorted.get(0)).size(); a++)
		{
			loops *= poss.get(sorted.get(0)).size();
			System.out.println(loops);
		}
	}
	
	public static ArrayList<String> sortByPoss(HashMap<String, ArrayList<String>> original)
	{
		Set<String> keys = original.keySet();
		int size = keys.size();
		ArrayList<String> sorted = new ArrayList<String>();
		for (int i = 0; i < size; i++)
		{
			String small = minimum(keys, original);
			sorted.add(small);
			keys.remove(small);
		}
		return sorted;
	}
	
	public static String minimum(Set<String> words, HashMap<String, ArrayList<String>> original)
	{
		String smallest = "";
		for (String w: words)
		{
			if (smallest.equals(""))
			{
				smallest = w;
			}
			if (original.get(w).size() < original.get(smallest).size())
			{
				smallest = w;
			}
		}
		return smallest;
	}
	
	/**
	 * Removes possibilites where a letter is the same, as in an aristocratic cipher no letter can be encrypted as itself.
	 * 
	 * @param word The encrypted word
	 * @param poss The list of possibilities to check
	 */
	public static void removePossibilities(String word, ArrayList<String> poss)
	{
		ArrayList<String> toRemove = new ArrayList<String>();
		for (int i = 0; i < poss.size(); i++)
		{
			for (int j = 0; j < word.length(); j++)
			{
				if (word.charAt(j) == poss.get(i).charAt(j))
				{
					toRemove.add(poss.get(i));
					break;
				}
			}
		}
		removeList(poss, toRemove);
	}
	
	public static void removeList(ArrayList<String> base, ArrayList<String> toRemove)
	{
		for (String w: toRemove)
		{
			base.remove(w);
		}
	}
	
	/**
	 * Finds possibilities for the encrypted words based on their pattern and letters.
	 * 
	 * @param words The encrypted words
	 * @return A HashMap in which each encrypted word is a key for an ArrayList of possibilites for that word.
	 * @throws IOException Gets thrown if the files with the words by length is not found.
	 */
	public static HashMap<String, ArrayList<String>> getPossibilities(String[] words) throws IOException
	{
		HashMap<String, ArrayList<String>> poss = new HashMap<String, ArrayList<String>>();
		for (String w: words)
		{
			ArrayList<String> thisPoss = new ArrayList<String>();
			File lengthF = new File("length" + w.length() + "words.txt");
			Scanner l = new Scanner(lengthF);
			while (l.hasNextLine())
			{
				String possibility = l.nextLine();
				if (matchesPattern(w, possibility))
				{
					thisPoss.add(possibility);
				}
			}
			removePossibilities(w, thisPoss);
			poss.put(w, thisPoss);
		}
		return poss;
	}
	
	/**
	 * Checks if two words equal each others' pattern.
	 * For example, food and beet will match each others' pattern of one letter, then a different letter twice, and then a third different letter.
	 * 
	 * @param a The first word to compare
	 * @param b The second word to compare
	 * @return Whether they match patterns
	 */
	public static boolean matchesPattern(String a, String b)
	{
		int diffA = distinctChars(a);
		int diffB = distinctChars(b);
		if (diffA != diffB || a.length() != b.length())
		{
			return false;
		}
		char count = 'A';
		for (int i = 0; i < a.length(); i++)
		{
			if (Character.isLowerCase(a.charAt(i)))
			{
				if (Character.isUpperCase(b.charAt(i)))
				{
					return false;
				}
				a = a.replace(a.charAt(i), count);
				b = b.replace(b.charAt(i), count);
				count++;
			}
		}
		return a.equals(b);
	}
	
	public static int distinctChars(String a)
	{
		int count = 0;
		String used = "";
		for (int i = 0; i < a.length(); i++)
		{
			if (used.indexOf(a.charAt(i)) > -1)
			{
				count++;
				used+= a.charAt(i);
			}
		}
		return count;
	}

}
