import java.util.*; // imported for linked lists.

public class Horn
{
	private LinkedList<String> letters;
	private String arrowToLetter;
	
	// constructs horn clause
	public Horn(String string)
	{
		letters = new LinkedList<String>();
		
		String[] splitAtArrow = string.split("=>"); // split at the arrow in the text file.
		String[] splitAtAmpersand = splitAtArrow[0].split("&"); // split at the & in text file.
		
		// for loop to split at each => or & in the text file, until it reaches the end.
		for (int i = 0; i < splitAtAmpersand.length; i++)
		{
			letters.addLast(splitAtAmpersand[i]);
		}
		arrowToLetter = splitAtArrow[1];
	}
	
	// excluding the arrows to the letter, returns list of string containing each letter the horn clause is referring to.
	public LinkedList<String> getLetters() {
		return letters;
	}
	
	// return a letter at a certain index
	public String getLetterAtIndex(int i)
	{
		try
		{
			if (i >= 0 && i < letters.size())
			{
				return letters.get(i);
			}
			else
			{
				throw new IndexOutOfBoundsException();
			}
		}
		catch (IndexOutOfBoundsException ioobe) // ioobe = index out of bounds exception
		{
			ioobe.printStackTrace();
			System.exit(3);
			// returns null
			// should technically be unreachable, but java's compiler is funny
			return null;
		}
	}
	
	// returns number of letters that the horn clause is referring to
	public int letterCount()
	{
		return letters.size();
	}
	
	// first, checks if the horn refers to a letter.
	// if so, removes the reference.
	public void removeLetter(String letter)
	{
		letters.remove(letter);
	}
	
	// getter for arrows in text file
	public String getArrowToLetter()
	{
		return arrowToLetter;
	}
	
	public String toString()
	{
		String toString = "";
		
		for (int i = 0; i < letters.size(); i++)
		{
			if (i != 0) 
			{
				toString += "^";
			}
			toString += letters.get(i);
		}
		toString += "=>";
		toString += arrowToLetter;
		return toString;
	}
}