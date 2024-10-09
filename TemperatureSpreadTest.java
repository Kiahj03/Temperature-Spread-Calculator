import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TemperatureSpreadTest {
	TemperatureSpread temperatureSpread;
	String file = "weather.dat";
	List<String> rows;
	String rowOne;
	String rowTwo;

	@BeforeEach
	public void setUp() {
		temperatureSpread = new TemperatureSpread();
		rows = temperatureSpread.readFile(file);
		rowOne = rows.get(0);
		rowTwo = rows.get(1);
	}

	@Test
	public void check_if_file_exists() {
		File fileName = temperatureSpread.getFile(file);
		boolean actual = fileName.exists();
		assertEquals(true, actual);
	}

	@Test
	public void read_one_row_of_data() {
		String actual = rowOne.trim();
		assertEquals("1  88    59    74          53.8       0.00 F       280  9.6 270  17  1.6  93 23 1004.5", actual);
	}

	@Test
	public void read_two_rows_of_data() {
		String actual = rowOne.trim() + "\n" + rowTwo.trim();
		assertEquals(
				"1  88    59    74          53.8       0.00 F       280  9.6 270  17  1.6  93 23 1004.5\n"
						+ "2  79    63    71          46.5       0.00         330  8.7 340  23  3.3  70 28 1004.5",
				actual);
	}

	@Test
	public void read_all_rows_of_data() {
		int actual = rows.size();
		assertEquals(31, actual);
	}

	@Test
	public void find_maximum_for_one_row_of_data() {
		int actual = temperatureSpread.findMaximumTemperature(rowOne);
		assertEquals(88, actual);
	}

	@Test
	public void find_minimum_for_one_row_of_data() {
		int actual = temperatureSpread.findMinimumTemperature(rowOne);
		assertEquals(59, actual);
	}

	@Test
	public void calculate_temperature_spread_for_one_row_of_data() {
		int actual = temperatureSpread.calculateTemperatureSpread(rowOne);
		assertEquals(29, actual);
	}

	@Test
	public void find_maximum_for_two_rows_of_data() {
		String actual = temperatureSpread.findMaximumTemperature(rowOne) + " "
				+ temperatureSpread.findMaximumTemperature(rowTwo);
		assertEquals("88 79", actual);
	}

	@Test
	public void find_minimum_for_two_rows_of_data() {
		String actual = temperatureSpread.findMinimumTemperature(rowOne) + " "
				+ temperatureSpread.findMinimumTemperature(rowTwo);
		assertEquals("59 63", actual);
	}

	@Test
	public void calculate_temperature_spread_for_two_rows_of_data() {
		String actual = temperatureSpread.calculateTemperatureSpread(rowOne) + " "
				+ temperatureSpread.calculateTemperatureSpread(rowTwo);
		assertEquals("29 16", actual);
	}

	@Test
	public void find_maximum_for_all_rows_of_data() {
		String actual = "";
		for (String currentRow : rows) {
			if (Character.isDigit(currentRow.charAt(0))) {
				int currentMaximum = temperatureSpread.findMaximumTemperature(currentRow);
				actual = actual + currentMaximum + " ";
			}
		}
		actual = actual.trim();
		assertEquals("88 79 77 77 90 81 73 75 86 84 91 88 70 61 64 79 81 82 81 84 86 90 90 90 90 97 91 84 88 90",
				actual);
	}

	@Test
	public void find_minimum_for_all_rows_of_data() {
		String actual = "";
		for (String currentRow : rows) {
			if (temperatureSpread.isValidDataRow(currentRow)) {
				int currentMinimum = temperatureSpread.findMinimumTemperature(currentRow);
				actual = actual + currentMinimum + " ";
			}
		}
		actual = actual.trim();
		assertEquals("59 63 55 59 66 61 57 54 32 64 59 73 59 59 55 59 57 52 61 57 59 64 68 77 72 64 72 68 66 45",
				actual);
	}

	@Test
	public void calculate_spread_for_all_rows_of_data() {
		String actual = "";
		for (String currentRow : rows) {
			if (temperatureSpread.isValidDataRow(currentRow)) {
				int currentSpread = temperatureSpread.calculateTemperatureSpread(currentRow);
				actual = actual + currentSpread + " ";
			}
		}
		actual = actual.trim();
		assertEquals("29 16 22 18 24 20 16 21 54 20 32 15 11 2 9 20 24 30 20 27 27 26 22 13 18 33 19 16 22 45", actual);
	}

	@Test
	public void find_maximum_for_extremely_high_temperature() {
		String exampleOfExtremeRow = "10 1000 400";
		int actual = temperatureSpread.findMaximumTemperature(exampleOfExtremeRow);
		assertEquals(1000, actual);
	}

	@Test
	public void find_minimum_for_extremely_low_temperature() {
		String exampleOfExtremeRow = "10 -400 -1000";
		int actual = temperatureSpread.findMinimumTemperature(exampleOfExtremeRow);
		assertEquals(-1000, actual);
	}

	@Test
	public void calculate_zero_temperature_spread() {
		String exampleOfZeroRow = "10 100 100";
		int actual = temperatureSpread.calculateTemperatureSpread(exampleOfZeroRow);
		assertEquals(0, actual);
	}

	@Test
	public void find_day_with_smallest_spread() {
		String actual = temperatureSpread.findDayWithSmallestSpread(rows);
		assertEquals("14", actual);
	}

	@Test
	public void output_day_with_smallest_spread() {
		String actual = temperatureSpread.getDayWithSmallestSpreadMessage(rows);
		assertEquals("14", actual);
	}

	@Test
	public void find_two_days_with_smallest_spread() {
		String rowOneWithSameSpread = "1 25 15";
		String rowTwoWithSameSpread = "2 20 10";
		List<String> testRowsWithSameSpread = new ArrayList<String>();
		testRowsWithSameSpread.add(rowOneWithSameSpread);
		testRowsWithSameSpread.add(rowTwoWithSameSpread);
		String actual = temperatureSpread.getDayWithSmallestSpreadMessage(testRowsWithSameSpread);
		assertEquals("1 2", actual);
	}

	@Test
	public void find_many_days_with_smallest_spread() {
		String rowOneWithSameSpread = "1 25 15";
		String rowTwoWithSameSpread = "2 20 10";
		String rowThreeWithSameSpread = "3 30 20";
		String rowFourWithSameSpread = "4 35 25";
		List<String> testRowsWithSameSpread = new ArrayList<String>();
		testRowsWithSameSpread.add(rowOneWithSameSpread);
		testRowsWithSameSpread.add(rowTwoWithSameSpread);
		testRowsWithSameSpread.add(rowThreeWithSameSpread);
		testRowsWithSameSpread.add(rowFourWithSameSpread);
		String actual = temperatureSpread.getDayWithSmallestSpreadMessage(testRowsWithSameSpread);
		assertEquals("1 2 3 4", actual);
	}
}
