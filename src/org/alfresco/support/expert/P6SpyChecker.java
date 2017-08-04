/**
 * Utility App to try and isolate long running queries from a p6spy output file.
 * 
 * @author astrachan
 */

package org.alfresco.support.expert;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Scanner;

public class P6SpyChecker {

	private static int BATCH = 4; // out of all the sorted (ascending) query times isolated, how many to go back
									// from the top of the list.

	public static void main(String[] args) throws IOException {

		System.out.println("P6Spy checker");
		System.out.println("-------------");

		String p6spyfile = "C:\\Users\\astrachan\\Desktop\\alfresco_p6spy\\spy.log";
		File f = new File(p6spyfile);
		BufferedReader p6spyin1 = new BufferedReader(new FileReader(p6spyfile));
		BufferedReader p6spyin2 = new BufferedReader(new FileReader(p6spyfile));

		List<Integer> lList = new ArrayList<Integer>(); // get querytimes and order them

		try {

			// phase one, get the querytimes and order them

			String line;
			String line2;

			while ((line = p6spyin1.readLine()) != null) {
				if (line.startsWith("15")) { // <--- this is the first char from the epoch time
					String[] queryTime = line.split("\\|");
					lList.add(Integer.parseInt(queryTime[1]));
				}
			}
			Collections.sort(lList);

			System.out.println(BATCH + " of the longest query times:");
			for (int i = 1; i <= BATCH; i++) {
				System.out.println("QueryTime (ms): " + lList.get(lList.size() - i));
			}
			System.out.println("--------------------------------");

			// phase two, once we've got the query times, read in again via scanner and
			// (badly) isolate the query information itself for each one.

			while ((line2 = p6spyin2.readLine()) != null) {
				for (int i = 1; i <= BATCH; i++) {
					if (line2.contains(lList.get(lList.size() - i).toString())) {
						String[] dets = line2.split("\\|");

						Scanner fileScanner = new Scanner(f);
						BufferedReader p6spyin3 = new BufferedReader(new FileReader(p6spyfile));
						String text;

						Date date = new Date(Long.parseLong(dets[0]));
						DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
						String formatted = format.format(date);

						// output the details for each isolated query...
						System.out.println("Date/Time: " + formatted + " | Query Time (ms): " + dets[1]
								+ " | Connection ID: " + dets[2] + " | Category: " + dets[3]);
						System.out.println("Query: ");

						while (fileScanner.hasNextLine()) {
							String scan = fileScanner.nextLine();
							if (scan.startsWith(line2)) {
								while (!(text = fileScanner.nextLine()).startsWith("15")) {
									System.out.println(text);
								}
							}
						}
						System.out.println("--------------------------------\r\n");
						fileScanner.close();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			p6spyin1.close();
			p6spyin2.close();
		}
	}

}
