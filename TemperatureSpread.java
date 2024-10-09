import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TemperatureSpread {
	public File getFile(String fileName) {
		return new File(fileName);
	}

	public List<String> readFile(String fileName) {
		try {
			return readEntireFile(fileName);
		} catch (Exception exception) {
			logError(exception);
			return new ArrayList<>();
		}
	}

	private List<String> readEntireFile(String fileName) throws Exception {
		List<String> allRows = Files.readAllLines(Paths.get(fileName));
		List<String> allDataRows = new ArrayList<>();
		for (int index = 1; index < allRows.size(); index++) {
			String currentRow = allRows.get(index).trim();
			if (checksIfRowIsNotEmpty(currentRow)) {
				allDataRows.add(currentRow);
			}
		}
		return allDataRows;
	}

	private boolean checksIfRowIsNotEmpty(String row) {
		return !row.isEmpty();
	}

	private void logError(Exception exception) {
		System.err.println(exception.getMessage());
	}

	public int findMaximumTemperature(String row) {
		String[] columns = row.trim().split("\\s+");
		return Integer.parseInt(columns[1].replace("*", ""));
	}

	public int findMinimumTemperature(String row) {
		String[] columns = row.trim().split("\\s+");
		return Integer.parseInt(columns[2].replace("*", ""));
	}

	public int calculateTemperatureSpread(String row) {
		int maximumTemperature = findMaximumTemperature(row);
		int minimumTemperature = findMinimumTemperature(row);
		return maximumTemperature - minimumTemperature;
	}

	public boolean isValidDataRow(String row) {
		return Character.isDigit(row.charAt(0));
	}

	public String findDayWithSmallestSpread(List<String> rows) {
		List<String> daysWithSmallestSpread = new ArrayList<>();
		int smallestSpread = 100;
		for (String row : rows) {
			if (isValidDataRow(row)) {
				int currentSpread = calculateTemperatureSpread(row);
				String[] columns = row.trim().split("\\s+");
				String day = columns[0];
				if (currentSpreadIsSmallerThanSmallestSpread(currentSpread, smallestSpread)) {
					smallestSpread = currentSpread;
					daysWithSmallestSpread.clear();
					daysWithSmallestSpread.add(day);
				} else if (currentSpread == smallestSpread) {
					daysWithSmallestSpread.add(day);
				}
			}
		}
		return String.join(" ", daysWithSmallestSpread);
	}

	private boolean currentSpreadIsSmallerThanSmallestSpread(int currentSpread, int smallestSpread) {
		return currentSpread < smallestSpread;
	}

	public String getDayWithSmallestSpreadMessage(List<String> rows) {
		return findDayWithSmallestSpread(rows);
	}

	public void printDayWithSmallestSpread(List<String> rows) {
		System.out.println(getDayWithSmallestSpreadMessage(rows));
	}
}
