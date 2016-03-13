import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Counter {
	private static BufferedReader reader;
	private static HashMap<String, Integer> words;
	private static int counter = 0;
	


	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, (o1, o2) -> ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue()));

	    HashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 
	
	
	public static boolean isLowerCase(char c) {
		return (c>='a' && c<='z');
	}
	
	public static boolean isUpperCase(char c) {
		return (c>='A' && c<='Z');
	}
	
	public static String stripChars(String s) {
		StringBuilder str = new StringBuilder(s);
		while (str.length() > 0 && !isLowerCase(str.charAt(0)) && !isUpperCase(str.charAt(0))) str.deleteCharAt(0);
		while (str.length() > 0 && !isLowerCase(str.charAt(str.length()-1)) && !isUpperCase(str.charAt(str.length()-1))) str.deleteCharAt(str.length()-1);
		return str.toString().toLowerCase();
	}
    

	public static HashMap<String, Integer> count(String file) throws IOException {
		words = new HashMap<>();
        try {
        	reader = new BufferedReader(new InputStreamReader(
        		    new FileInputStream(file), "UTF-8"));
        } catch (Exception e) {
        	e.printStackTrace();
        }
        String line;
        while ( (line = reader.readLine()) != null){
        	StringTokenizer tk = new StringTokenizer(line);
        	while (tk.hasMoreTokens()) {
        		String word = tk.nextToken();
        		if (word.length() == 0) continue;
        		counter++;
        		if ( words.get(word) == null) words.put(word, 1);
        		else words.put(word, words.get(word) + 1 );
        	}
        }
        
        words = sortByValue(words);

        reader.close();
        return words;
		
	}
	
	public static HashMap<String, Integer> getWords() {
		return words;
	}


	public static int getCounter() {
		return counter;
	}
}
