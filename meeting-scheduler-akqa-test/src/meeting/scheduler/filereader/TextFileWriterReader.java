package meeting.scheduler.filereader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public final class TextFileWriterReader {
	/**
	 * Reads the file from the resource folder and populate each line into List
	 * <String>
	 * 
	 * @param classLoader
	 * @param path
	 * @return
	 */
	public static List<String> readFile(ClassLoader classLoader, String path) {
		try {
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			List<String> lineList = new ArrayList<String>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				lineList.add(StringUtils.trim(line));
			}
			fileReader.close();
			return lineList;
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * This method writes the output to the new file
	 * 
	 * @param classLoader
	 * @param path
	 * @param meetingSchedule
	 */
	public static void write(ClassLoader classLoader, String path, String meetingSchedule) {
		BufferedWriter output = null;
		try {
			File file = new File(path);
			output = new BufferedWriter(new FileWriter(file));
			output.write(meetingSchedule);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
