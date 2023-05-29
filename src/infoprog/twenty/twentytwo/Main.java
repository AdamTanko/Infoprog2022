package infoprog.twenty.twentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

	private static final String filepath = "datafiles/raktar2.txt";
	// in kilograms
	private static final int SACK_WEIGHT = 20;
	private static final int SLEIGH_CARRYING_CAPACITY = 2500;

	public static ArrayList<Warehouse> warehouses = new ArrayList<>();



	public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
		parse();
        while (true) {

            System.out.print("╒=======================╕\n" +
                             "|     Infoprog 2022     |\n" +
                             "|                       |\n" +
                             "| By entering a number  |\n" +
                             "| you can choose        |\n" +
                             "| which exercise should |\n" +
                             "| be runned             |\n" +
                             "╘=======================╛\n" +
                             " 0 - exit, 1 - 1. & 2.exercise, 3 - 3.exercise, 4 - 4.exercise, 5 - 5.exercise \n" +
                             "Here: ");
            String input = sc.next();
            int x;
            try  {
				x = Integer.parseInt(input);
			} catch (NumberFormatException e) {
            	System.out.println("\"" + input+ "\"" + " bad input");
				TimeUnit.SECONDS.sleep(2);
				for (int i = 0; i < 20; i++) {
					System.out.println();
				}
            	continue;
			}

			switch (x) {
			case 0:
				System.out.println("Exiting...");
				sc.close();
				return;
			case 1:
				warehouses = new ArrayList<>();
				parse();
				System.out.println("===1/2. exercise===");
				ex1_2();
				break;
			case 3:
				warehouses = new ArrayList<>();
				parse();
				System.out.println("===3. exercise===");
				ex3();
				break;
			case 4:
				warehouses = new ArrayList<>();
				parse();
				System.out.println("===4. exercise===");
				ex4();
				break;
			case 5:
				warehouses = new ArrayList<>();
				parse();
				System.out.println("===5. exercise===");
				ex5(ex4());
				break;

			}
            
            while (true) {
                try {
                    System.out.println("Press \"ENTER\" to continue");
                    if (System.in.read() == 10) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 20; i++) {
                System.out.println();
            }


        }
       
    }

	public static void ex1_2() {
		try {
			int sacksOnSleigh = SLEIGH_CARRYING_CAPACITY / SACK_WEIGHT;
			int firstNonZeroWarehouseIndex;
			int tripcount = 0;
			int kilometersTravelled = 0;

			outer: while (howManyAreNeeded(warehouses) != 0) {
				tripcount++;
				firstNonZeroWarehouseIndex = closestNonZeroWarehouse(warehouses);
				kilometersTravelled += howFar(warehouses.get(0).getLat(), warehouses.get(0).getLon(), warehouses.get(firstNonZeroWarehouseIndex).getLat(), warehouses.get(firstNonZeroWarehouseIndex).getLon());
				sacksOnSleigh = SLEIGH_CARRYING_CAPACITY / SACK_WEIGHT;
				while (sacksOnSleigh != 0) {
					System.out.printf("Current warehouse: %d, %d; kilometers travelled so far: %d \n", warehouses.get(firstNonZeroWarehouseIndex).getLat(), warehouses.get(firstNonZeroWarehouseIndex).getLon(), kilometersTravelled);
					if (sacksOnSleigh > warehouses.get(firstNonZeroWarehouseIndex).getStock()) {
						sacksOnSleigh -= warehouses.get(firstNonZeroWarehouseIndex).getStock();
						warehouses.get(firstNonZeroWarehouseIndex).setStock(0);
						int curWarehouse = firstNonZeroWarehouseIndex;
						firstNonZeroWarehouseIndex = closestNonZeroWarehouse(warehouses);
						kilometersTravelled += howFar(warehouses.get(curWarehouse).getLat(), warehouses.get(curWarehouse).getLon(), warehouses.get(firstNonZeroWarehouseIndex).getLat(), warehouses.get(firstNonZeroWarehouseIndex).getLon());
					} else {
						warehouses.get(firstNonZeroWarehouseIndex).
								setStock(warehouses.get(firstNonZeroWarehouseIndex).
										getStock() - sacksOnSleigh);
						sacksOnSleigh = 0;
					}
					if (firstNonZeroWarehouseIndex == warehouses.size()) {
						String s = "," + sacksOnSleigh;
						Files.write(Paths.get(filepath), s.getBytes(), StandardOpenOption.APPEND);
						warehouses.get(firstNonZeroWarehouseIndex - 1).setStock(sacksOnSleigh);
						System.out.println("The number of remaining sacks has been added to raktarok.txt");
						break outer;
					}
				}
				kilometersTravelled += howFar(warehouses.get(firstNonZeroWarehouseIndex).getLat(), warehouses.get(firstNonZeroWarehouseIndex).getLon(), warehouses.get(0).getLat(), warehouses.get(0).getLon());
				System.out.println("Back to Lapland");
			}
			// theoretical distance 24254 for raktar2.txt
			System.out.printf("Sacks taken to the last warehouse: %d, kilometers travelled: %d, trips made: %d \n", sacksOnSleigh, kilometersTravelled, tripcount);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ex3() {
		int sacksOnSleigh;
		int firstNonzeroWarehouseIndex;
		int tripcount = 0;
		int kilometersTravelled = 0;

		outer:while (howManyAreNeeded(warehouses) != 0) {
			tripcount++;
			firstNonzeroWarehouseIndex = closestNonZeroWarehouse(warehouses);
			kilometersTravelled += howFar(warehouses.get(0).getLat(), warehouses.get(0).getLon(), warehouses.get(firstNonzeroWarehouseIndex).getLat(), warehouses.get(firstNonzeroWarehouseIndex).getLon());
			sacksOnSleigh = SLEIGH_CARRYING_CAPACITY / SACK_WEIGHT;
			while (sacksOnSleigh != 0) {
				if(firstNonzeroWarehouseIndex == 0) {
					break outer;
				}
				System.out.printf("Current warehouse: %d, %d; kilometers travelled so far: %d \n", warehouses.get(firstNonzeroWarehouseIndex).getLat(), warehouses.get(firstNonzeroWarehouseIndex).getLon(), kilometersTravelled);
				if (sacksOnSleigh > warehouses.get(firstNonzeroWarehouseIndex).getStock()) {
					sacksOnSleigh -= warehouses.get(firstNonzeroWarehouseIndex).getStock();
					warehouses.get(firstNonzeroWarehouseIndex).setStock(0);
					int aktraktar = firstNonzeroWarehouseIndex;
					firstNonzeroWarehouseIndex = closestNonZeroWarehouse(warehouses);
					if (sacksOnSleigh < warehouses.get(firstNonzeroWarehouseIndex).getStock()) {
						break;
					}
					kilometersTravelled += howFar(warehouses.get(aktraktar).getLat(), warehouses.get(aktraktar).getLon(), warehouses.get(firstNonzeroWarehouseIndex).getLat(), warehouses.get(firstNonzeroWarehouseIndex).getLon());
				} else {
					warehouses.get(firstNonzeroWarehouseIndex).setStock(warehouses.get(firstNonzeroWarehouseIndex).getStock() - sacksOnSleigh);
					sacksOnSleigh = 0;
				}
				
			}
			kilometersTravelled += howFar(warehouses.get(firstNonzeroWarehouseIndex).getLat(), warehouses.get(firstNonzeroWarehouseIndex).getLon(), warehouses.get(0).getLat(), warehouses.get(0).getLon());
			System.out.println("Back to Lapland");
		}
		System.out.printf("Kilometers travelled: %d, trips made: %d \n", kilometersTravelled, tripcount);
	}

	public static ArrayList<ArrayList<Integer>> ex4() {

		int[][] distances = distancesBetweenWarehouses();
		ArrayList<ArrayList<Integer>> routes = new ArrayList<>();
		routes.add(new ArrayList<>());
		int currentRoute = 0;
		routes.get(0).add(0);

		while (currentRoute < routes.size()) {
			for (int j = 0; j < distances.length - 1; j++) {
				int curLine = routes.get(currentRoute).get(routes.get(currentRoute).size() - 1);
				int minDist = Integer.MAX_VALUE;
				for (int i = 0; i < distances.length; i++) {
					if ((distances[curLine][i] < minDist) && !(routes.get(currentRoute).contains(i))
							&& (distances[curLine][i] != 0)) {
						minDist = distances[curLine][i];
					}
				}
				int first = 0;
				boolean found = false;
				for (int i = 0; i < distances.length; i++) {
					if ((distances[curLine][i] == minDist) && !(routes.get(currentRoute).contains(i))
							&& !found) {
						first = i;
						found = true;
						continue;
					}
					if ((distances[curLine][i] == minDist) && !(routes.get(currentRoute).contains(i))) {
						ArrayList<Integer> list = new ArrayList<>(routes.get(currentRoute));
						list.add(i);
						routes.add(list);
					}
				}
				if (found) {
					routes.get(currentRoute).add(first);
				}
			}
			currentRoute++;
			System.out.println("Number of routes found:" + routes.size());
		}
		for (ArrayList<Integer> integers : routes) {
			integers.forEach(System.out::println);
		}
		return routes;
	}

	public static void ex5(ArrayList<ArrayList<Integer>> utvonalak) {
        for (ArrayList<Integer> integers : utvonalak) {
            ArrayList<Warehouse> tempWare = new ArrayList<>();
            for (Integer integer : integers) {
                tempWare.add(warehouses.get(integer));
            }

            int sacksOnSleigh = SLEIGH_CARRYING_CAPACITY / SACK_WEIGHT;
            int firstNonzeroWarehouseIndex;
            int tripcount = 0;
            int kilometersTravelled = 0;

            while (howManyAreNeeded(tempWare) != 0) {

                tripcount++;
                firstNonzeroWarehouseIndex = closestNonZeroWarehouse(tempWare);
                kilometersTravelled += 2 * howFar(70, 23, tempWare.get(firstNonzeroWarehouseIndex).getLat(), tempWare.get(firstNonzeroWarehouseIndex).getLon());
                sacksOnSleigh = SLEIGH_CARRYING_CAPACITY / SACK_WEIGHT;

                while (sacksOnSleigh != 0) {
                    if (firstNonzeroWarehouseIndex >= tempWare.size()) {
                        return;
                    }
                    System.out.printf("Current warehouse: %d, %d; kilometers travelled so far: %d \n", tempWare.get(firstNonzeroWarehouseIndex).getLat(), tempWare.get(firstNonzeroWarehouseIndex).getLon(), kilometersTravelled);
                    if (sacksOnSleigh > tempWare.get(firstNonzeroWarehouseIndex).getStock()) {
                        sacksOnSleigh -= tempWare.get(firstNonzeroWarehouseIndex).getStock();
                        tempWare.get(firstNonzeroWarehouseIndex).setStock(0);
                        firstNonzeroWarehouseIndex++;
                    } else {
                        tempWare.get(firstNonzeroWarehouseIndex).setStock(tempWare.get(firstNonzeroWarehouseIndex).getStock() - sacksOnSleigh);
                        sacksOnSleigh = 0;
                    }

                }
                System.out.println("Back to Lapland");
            }
            System.out.printf("Sacks taken to the last warehouse: %d, kilometers travelled: %d, trips made: %d \n", sacksOnSleigh, kilometersTravelled, tripcount);
        }
	}

	public static int howManyAreNeeded(ArrayList<Warehouse> warehouseTemp) {
		int howManySacksAreNeeded = 0;
		for (Warehouse warehouse : warehouseTemp) {
			howManySacksAreNeeded += warehouse.getStock();
		}
		return howManySacksAreNeeded;
	}

	public static int[][] distancesBetweenWarehouses() {
		int[][] distances = new int[warehouses.size()][warehouses.size()];
		for (int i = 0; i < warehouses.size(); i++) {
			for (int j = 0; j < warehouses.size(); j++) {
				int distance = howFar(warehouses.get(i).getLat(),
						warehouses.get(i).getLon(),
						warehouses.get(j).getLat(),
						warehouses.get(j).getLon());
				distances[i][j] = distance;
			}
		}
		return distances;
	}

	public static int closestNonZeroWarehouse(ArrayList<Warehouse> warehouseTemp) {
		int nonZeroWarehouseIndex = 0;
		for (int i = 0; i < warehouseTemp.size(); i++) {
			// 1. raktar aminek keszlete nem 0
			if (warehouseTemp.get(i).getStock() != 0) {
				nonZeroWarehouseIndex = i;
				break;
			}
		}
		return nonZeroWarehouseIndex;
	}
	
	// source: https://www.movable-type.co.uk/scripts/latlong.html
	// haversine formula
	public static int howFar(int lat1, int lon1, int lat2, int lon2) {
		final double r = 6371e3;
		double Phi1 = lat1 * Math.PI / 180;
		double Phi2 = lat2 * Math.PI / 180;
		double dPhi = (lat2 - lat1) * Math.PI / 180;
		double dLambda = (lon2 - lon1) * Math.PI / 180;

		double a = Math.sin(dPhi / 2) * Math.sin(dPhi / 2)
				+ Math.cos(Phi1) * Math.cos(Phi2) * Math.sin(dLambda / 2) * Math.sin(dLambda / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (int) (r * c) / 1000; // in kilometers
	}

	

	public static void parse() {
		try {
			System.out.println("Parsing");
			File f = new File(filepath);
			Scanner sc2 = new Scanner(f);
			boolean yes = true;
			while (sc2.hasNextLine()) {
				String scnextlinestring = sc2.nextLine();
				Scanner sc = new Scanner(scnextlinestring).useDelimiter(",");
				Warehouse r = new Warehouse();
				if (yes) {
					yes = false;
					r.setLat(Integer.parseInt(sc.next()));
					r.setLon(Integer.parseInt(sc.next()));
				} else if (!sc2.hasNextLine()) {
					r.setLat(Integer.parseInt(sc.next()));
					r.setLon(Integer.parseInt(sc.next()));
					if (sc.hasNextInt()) {
						int maxstockcap = sc.nextInt();
						r.setMaxcap(maxstockcap);
						r.setStock(maxstockcap);
					}
				} else {
					r.setLat(Integer.parseInt(sc.next()));
					r.setLon(Integer.parseInt(sc.next()));
					int maxstockcap = Integer.parseInt(sc.next());
					r.setMaxcap(maxstockcap);
					r.setStock(maxstockcap);
				}
				
				warehouses.add(r);
				sc.close();
			}
			System.out.println("Reading complete");
			sc2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
