/*
 */
package shafin.ml.tfidf.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SHAFIN
 */
public class FileHandler {

	public static List<String> getFileList(String filePath) {
		List<String> fileNames = new ArrayList<>();

		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileNames.add(listOfFiles[i].getName());
			}
		}
		return fileNames;
	}

	public static List<String> readFile(String filePath) {

		List<String> lines = new ArrayList<>();
		BufferedReader br = null;
		String line = null;

		try {
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			return lines;

		} catch (FileNotFoundException e) {
			System.out.println(filePath);
			e.printStackTrace();
		} catch (IOException e) {
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return lines;
	}

	public static boolean writeFile(String filePath, String textData) {
		try {
			File file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF8")) {
				outputStreamWriter.write(textData);
			}

			return true;

		} catch (IOException e) {
			return false;

		}
	}

	public static boolean appendFile(String filePath, String textData) {
		try {
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF8")) {
				textData = textData.replaceAll("\n", System.lineSeparator());
				outputStreamWriter.write(textData);
			}

			return true;

		} catch (IOException e) {
			return false;

		}
	}

	public static boolean writeListToFile(String filePath, List<String> inputList) {
		try {
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			StringBuilder stringBuilder = new StringBuilder();

			for (String t : inputList) {
				stringBuilder.append(t).append("\n");
			}
			String textData = stringBuilder.toString();

			FileOutputStream fileOutputStream = new FileOutputStream(filePath);
			try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF8")) {
				textData = textData.replaceAll("\n", System.lineSeparator());
				outputStreamWriter.write(textData);
			}
			System.out.println("INFO: list has been written to " + filePath);
			return true;

		} catch (IOException e) {
			return false;
		}
	}

	public static boolean validateFileName(String fileName) {
		fileName = fileName.trim();
		if (fileName.length() > 0) {
			return fileName.matches("^[^.\\\\/:*?\"<>|]?[^\\\\/:*?\"<>|]*");
		} else {
			throw new IllegalStateException("File Name " + fileName + " results in a empty fileName!");
		}
	}

	public static String getValidFileName(String fileName) {
		String newFileName = fileName.replaceAll("[.\\\\/:*?\"<>|]?[\\\\/:*?\"<>|]*", "");
		if (newFileName.length() == 0) {
			throw new IllegalStateException("File Name " + fileName + " results in a empty fileName!");
		}
		return newFileName;
	}

	public static String getFileNameFromPathString(String filePath) {
		try {
			int idx = filePath.replaceAll("\\\\", "/").lastIndexOf("/");
			return idx >= 0 ? filePath.substring(idx + 1) : filePath;
		} catch (Exception e) {
			throw e;
		}
	}
}
