package local.pixy.cheateroo.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jetbrains.annotations.Nullable;

import local.pixy.cheateroo.Cheateroo;

/**
 * @author pixy
 *
 */
public class NetworkUtils {
	/**
	 * Schedule the task for later,
	 * 
	 * @param task the task to execute
	 * 
	 * @implNote currently just calls it!
	 */
	public static void doTask(Runnable task) {
		task.run();
	}

	/**
	 * @param targetUrl   the url to do the action on
	 * @param data        the string to send as content to the server
	 * @param contentType the value of the {@code Content-Type} http header
	 * @param userAgent   the user agent to use in the {@code User-Agent} http
	 *                    header
	 * @param action      the action {@code "POST"} or {@code "GET"}
	 * @return the answer from the server
	 */
	public static String sendData(URL targetUrl, String data, @Nullable String contentType, String userAgent,
			String action) {
		try {
			HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
			connection.setRequestMethod(action);
			if (contentType != null)
				connection.setRequestProperty("Content-Type", contentType);
			connection.setRequestProperty("Content-Length", "" + data.getBytes().length);
			connection.setRequestProperty("User-Agent", userAgent);
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);

			DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
			outStream.writeBytes(data);
			outStream.flush();
			outStream.close();

			BufferedReader inReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer buffer = new StringBuffer();

			String line;
			while ((line = inReader.readLine()) != null) {
				buffer.append(line);
				buffer.append('\n');
			}

			line = buffer.toString();

			return line;
		} catch (Exception e) {
			Cheateroo.LOGGER.error(e.toString());
			return "";
		}
	}

	/**
	 * @param modId
	 * @return
	 */
	public static String getUserAgent(String modId) {
		return "minecraft-" + modId;
	}
}
