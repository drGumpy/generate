package certyficate.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ReaderCreator {
	final private static String CODING = "UTF-8";
	final private static String CODING_ERROR = "File coding error /n"; 
	
	public static BufferedReader getReader(File file) throws FileNotFoundException {
		BufferedReader reader = newReader(file);
		return reader;
	}
	
	public static BufferedReader getReader(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		BufferedReader reader = newReader(file);
		return reader;
	}
	
	private static BufferedReader newReader(File file) throws FileNotFoundException {
		InputStreamReader streamReader = getStreamReader(file);
		return new BufferedReader(streamReader);
	}
	
	private static InputStreamReader getStreamReader(File file) throws FileNotFoundException {
		InputStreamReader reader;
		try {
			reader = new InputStreamReader(getStream(file), CODING);
		} catch (UnsupportedEncodingException e) {
			throw new FileNotFoundException(CODING_ERROR);
		}
		return reader;
	}

	private static InputStream getStream(File file) throws FileNotFoundException {
		InputStream stream = new FileInputStream(file);
		return stream;
	}
}
