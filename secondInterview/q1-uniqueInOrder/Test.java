package nz.co.mahara.wms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args) {
		try {
			String[] result = Test.uniqueInOrder("DDDAAAAAAABBBBBBBBBCCCCCCCCDDDDDAAAAABBBBBDDDDD");

			System.out.print("size: " + result.length + " content:");
			for (int i = 0; i< result.length; i++) {
				System.out.print(result[i]);
			}
			System.out.println();
			
			result = Test.uniqueInOrder(null);
			System.out.print("size: " + result.length + " content:");
			for (int i = 0; i< result.length; i++) {
				System.out.print(result[i]);
			}
			System.out.println();
			
			result = Test.uniqueInOrder("");
			System.out.print("size: " + result.length + " content:");
			for (int i = 0; i< result.length; i++) {
				System.out.print(result[i]);
			}
			System.out.println();
			
			result = Test.uniqueInOrder(" ");
			System.out.print("size: " + result.length + " content:");
			for (int i = 0; i< result.length; i++) {
				System.out.print(result[i]);
			}
			System.out.println();

			result = Test.uniqueInOrder("AAAAAAABB C  DDDDDAAAAADD");
			System.out.print("size: " + result.length + " content:");
			for (int i = 0; i< result.length; i++) {
				System.out.print(result[i]);
			}
			System.out.println();
			
			result = Test.uniqueInOrder("AAAAAAABBBBBBBaaa112BBBDDDDD");
			System.out.print("size: " + result.length + " content:");
			for (int i = 0; i< result.length; i++) {
				System.out.print(result[i]);
			}
			System.out.println();
			
			
			
		} catch(IllegalArgumentException e) {
			System.out.println("Illegal arguments.");
		}
	}
	
	public static String[] uniqueInOrder(String src) throws IllegalArgumentException {
		String[] retValue = null;

		
		if (src == null) {
			return new String[0];
		}
		
		String trimSrc = src.trim();
		if ("".equals(trimSrc)) {
			return new String[0];
		}
		
		Pattern parttern = Pattern.compile("^([A-Z]|\\s)*$");
		Matcher matcher = parttern.matcher(src);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException();
		}
		
		char[] splitSrc = trimSrc.toCharArray();
		
		Map<String,String> retMap = new HashMap<String,String>();
		
		for (int i = 0; i < splitSrc.length; i++) {
			String tmp = String.valueOf(splitSrc[i]);
			if (!" ".equals(tmp)) {
				retMap.put(tmp, tmp);
			}
		}
		
		Set<String> keySet = retMap.keySet();
		retValue = new String[keySet.size()];
		keySet.toArray(retValue);
		
		Arrays.sort(retValue, new DescComprator());
		
		return retValue;
	}
	
	static class DescComprator implements Comparator<String>{

		@Override
		public int compare(String o1, String o2) {
			int retValue = 0;
			
			int ascResult = o1.compareTo(o2);
			retValue = 0 - ascResult;
			
			return retValue;
		}
		
	}
}
