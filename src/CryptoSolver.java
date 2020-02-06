import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CryptoSolver {

	public static void main(String[] args) throws IOException{
		File f = new File("puzzle.txt");
		Scanner k = new Scanner(f);
		String puzzle = k.nextLine();
		puzzle = puzzle.toLowerCase();
		String[] words = puzzle.split(" ");
		HashMap<String, ArrayList<String>> poss = getPossibilities(words);
		String translated = puzzle;
		System.out.println(poss.get("yupd").size());
	}
	
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
			poss.put(w, thisPoss);
		}
		return poss;
	}
	
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
