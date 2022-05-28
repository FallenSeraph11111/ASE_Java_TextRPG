package JSON;


import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyJsonParser {
	public JSONObject getJsonFromFile(String fileName) {

		try {
			Class cls = getClass();
			ClassLoader cLoader = cls.getClassLoader();
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(cLoader.getResourceAsStream(fileName)));
			StringBuilder jsonStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				jsonStrBuilder.append(inputStr);
			JSONObject jsonObject = new JSONObject(jsonStrBuilder.toString());
			streamReader.close();
			return jsonObject;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
