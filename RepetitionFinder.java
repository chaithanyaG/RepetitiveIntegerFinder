import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RepetitionFinder {

	public static String myURL = "http://hackajob.co/hackjobs/rainbowData";

	public static void main(String[] args) {

		// String inputString = "112233355556667771133";

		/**
		 * The program takes input from console. Uncomment the above line to
		 * provide required input and comment the below 3 lines to provide input
		 * directly in the class"
		 **/
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the Integer to parse: ");
		String inputString = scanner.next();

		if (inputString.isEmpty()) {
			System.out.println("Input is Empty");
		} else {
			try {
				Integer.valueOf(inputString);
			} catch (NumberFormatException ne) {
				System.out.println("Please provide only numbers as input");
				return;
			}

			int longest = 0;
			int length = 1;
			char char1 = ' ';
			for (int i = 0; i < inputString.length() - 1; i++) {
				if (inputString.charAt(i) == inputString.charAt(i + 1)) {
					++length;
					if (length > longest) {
						longest = length;
						char1 = inputString.charAt(i);

					}
				} else {
					length = 1;
				}
			}
			if (length > longest) {
				longest = length;
			}
			String charStr = String.valueOf(char1);
			int index = inputString.indexOf(charStr);
			String subStr = inputString.substring(index, inputString.length());
			String repStr = subStr.substring(0, longest);
			System.out.println("Longest Repetitive Integer value:" + repStr);

			try {
				JSONObject jsonRep = new JSONObject();
				jsonRep.put("hackJob", repStr);
				System.out.println("Json Repetitive String: " + jsonRep);
				String jsonString = readURL();
				System.out.println("URL Data: " + jsonString);
				int arrayIndex = (Integer.parseInt(charStr)) - 1;
				JSONArray jsonArray = new JSONArray(jsonString);
				if (jsonArray != null) {
					if (jsonArray.length() > arrayIndex)
						System.out.println("Value for the repetitive number: "
								+ jsonArray.get(arrayIndex));
					else
						System.out
								.println("The longest Repetitive number in the input should not be greater than "
										+ jsonArray.length());
				}

			} catch (JSONException je) {
				je.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	public static String readURL() throws IOException, JSONException {
		System.out.println("Requested URL:" + myURL);
		StringBuilder strBuiler = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int character;
					while ((character = bufferedReader.read()) != -1) {
						strBuiler.append((char) character);
					}
					bufferedReader.close();
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:" + myURL,
					e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}

		return strBuiler.toString();
	}

}