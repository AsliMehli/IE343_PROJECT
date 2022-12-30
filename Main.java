import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		List<List<String>> list = readValues();
		List<Integer> valueList = new ArrayList<Integer>();
		List<Integer> weightList = new ArrayList<Integer>();
		ArrayList<Track> tracks = new ArrayList<Track>();

		for (int i = 1; i < list.size(); i++) {
			valueList.add(Integer.parseInt(list.get(i).get(4)));
			weightList.add(Integer.parseInt(list.get(i).get(5)));
		}
		List<List<String>> list1 = readSequential();
		List<ArrayList<Double>> sequential_data = new ArrayList<ArrayList<Double>>();
		for (int i = 1; i < list1.size(); i++) {
			ArrayList<Double> row = new ArrayList<>();
			for (int j = 1; j < list1.get(0).size(); j++) {
				row.add(Double.parseDouble(list1.get(i).get(j)));
			}
			sequential_data.add(row);
		}

		// Create Track Objects
		for (int i = 0; i < valueList.size(); i++) {

			int id = i;
			int duration = weightList.get(i);
			int indValue = valueList.get(i);
			double[] seqValue = new double[50];

			for (int j = 0; j < valueList.size(); j++) {

				seqValue[j] = sequential_data.get(i).get(j);
			}

			Track newTrack = new Track(id, duration, indValue, seqValue);
			tracks.add(newTrack);

//			System.out.println(newTrack);

		}

		System.out.println(prepareAlbum(tracks, 1800000));

	}

	public static List<List<String>> readValues() throws IOException {
		try {
			List<List<String>> data = new ArrayList<>();// list of lists to store data
			String file = "term_project_value_data.csv";// file path
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			// Reading until we run out of lines
			String line = br.readLine();
			while (line != null) {
				List<String> lineData = Arrays.asList(line.split(","));// splitting lines
				data.add(lineData);
				line = br.readLine();
			}

			br.close();
			return data;
		} catch (Exception e) {
			System.out.print(e);
			List<List<String>> data = new ArrayList<>();// list of lists to store data
			return data;
		}

	}

	public static List<List<String>> readSequential() throws IOException {
		try {
			List<List<String>> data = new ArrayList<>();// list of lists to store data
			String file = "term_project_sequential_data.csv";// file path
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			// Reading until we run out of lines
			String line = br.readLine();
			while (line != null) {
				List<String> lineData = Arrays.asList(line.split(","));// splitting lines
				data.add(lineData);
				line = br.readLine();
			}

			br.close();
			return data;
		} catch (Exception e) {
			System.out.print(e);
			List<List<String>> data = new ArrayList<>();// list of lists to store data
			return data;
		}

	}

	// this method selects possible tracks starting from greatest value/duration
	// rate
	public static ArrayList<Track> prepareAlbum(ArrayList<Track> tracks, int maxDuration) {

		// total duration
		int totalDuration = 0;
		int totalValue = 0;

		// initialize album arraylist
		ArrayList<Track> album = new ArrayList<Track>();

		// keep rate and track in the hashmap because it automatically sorts
		HashMap<Double, Track> rateAndTrack = new HashMap<Double, Track>();
		ArrayList<Double> rates = new ArrayList<Double>();

		// calculate value/duration rates
		for (int i = 0; i < tracks.size(); i++) {

			// get current rate
			Track currentTrack = tracks.get(i);

			// calculate rate
			double rate = (double) currentTrack.getIndividualValue() / (double) currentTrack.getDuration();

			// keep which rate is related to track
			rateAndTrack.put(rate, currentTrack);

			// add rate to the arraylist
			rates.add(rate);
		}

		// sort the rates to get greater ones at first
		rates.sort(Comparator.reverseOrder());

		// iterate rates
		for (double rate : rates) {
			// get next track with highest rate
			Track nextTrack = rateAndTrack.get(rate);

			// check duration condition
			if (totalDuration + nextTrack.getDuration() <= maxDuration) {

				// add this album
				album.add(nextTrack);

				// add duration
				totalDuration += nextTrack.getDuration();
				totalValue += nextTrack.getIndividualValue();
			}

		}

		System.out.println("SOLUTION ");
		System.out.println("Total Value: " + totalValue);
		System.out.println("Total DURATION: " + totalDuration);

		// return album
		return album;

	}
}
