package infoprog.twenty.twentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

class sortbytav implements Comparator<Raktar> {
    public int compare(Raktar a, Raktar b) {
        return a.tavlapfoldre - b.tavlapfoldre;
    }
}


public class Main {

    public static String filepath = "Infoprog2022-main/data files/raktar.txt";
    //kilogrammban
    public static int zsaksuly = 20;
    public static int szanteherbiras = 2500;

    public static ArrayList<Raktar> raktarak = new ArrayList<>();
    public static ArrayList<Integer> tav = new ArrayList<>();

    public static void main(String[] args) {
        beolv();
//        tavszamolas();
        System.out.println(mennyiKellMeg());
//        Collections.sort(raktarak, new sortbytav());
//        for (Raktar r :
//                raktarak) {
//            System.out.println(r.tavlapfoldre);
//        }
    }

    public static void beolv() {
        try {
            System.out.println("Beolvasás");
            File f = new File(filepath);
            Scanner sc2 = new Scanner(f);
            boolean yes = true;
            while (sc2.hasNextLine()) {
                String scnextlinestring = sc2.nextLine();
                Scanner sc = new Scanner(scnextlinestring).useDelimiter(",");
                Raktar r = new Raktar();
                if (yes) {
                    r.lat = Integer.parseInt(sc.next());
                    r.lon = Integer.parseInt(sc.next());
                    yes = false;
                } else if (!sc2.hasNextLine()){
                    r.lat = Integer.parseInt(sc.next());
                    r.lon = Integer.parseInt(sc.next());
                } else {
                    r.lat = Integer.parseInt(sc.next());
                    r.lon = Integer.parseInt(sc.next());
                    r.maxcap = Integer.parseInt(sc.next());
                }
                raktarak.add(r);
                sc.close();
            }
            System.out.println("Beolvasva");
            sc2.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void tavszamolas(){
        for (Raktar r : raktarak) {
            r.tavlapfoldre = (mennyiTav(70,23,r.lat,r.lon));
        }
    }

    public static void feladat1() {
        int szanmaxzsakok = szanteherbiras / zsaksuly;
        int szanzsakokszama = szanmaxzsakok;
        int mennyikell = mennyiKellMeg();
        int nemnullaraktarszama = 1;
        int utakszama = 0;
        int megtettKMk = 0;
        while (mennyiKellMeg() != 0) {
        	utakszama++;
            for (int i = 0; i < raktarak.size(); i++) {
				// 1 raktar aminek keszlete nem 0
            	if (raktarak.get(i).keszlet != 0) {
            		nemnullaraktarszama = i;
            		break;
            	}
			}
            while (szanzsakokszama != 0) {
            	if (szanzsakokszama > raktarak.get(nemnullaraktarszama).keszlet) {
            		szanzsakokszama -= raktarak.get(nemnullaraktarszama).keszlet;
            		raktarak.get(nemnullaraktarszama).keszlet = 0;
            		// menjel ma' tovabb
            		if  (nemnullaraktarszama == raktarak.size()) {
            			//FileWriter fw = new FileWriter(filepath);
                 	}
            	} else {
            		raktarak.get(nemnullaraktarszama).keszlet -= szanzsakokszama;
            		szanzsakokszama = 0;
            		//menjel ma' haza
            	}
            }
        }
        System.out.println("lol");
    }
    
    public static int mennyiKellMeg() {
    	int kellendozsak = 0;
    	for (Raktar raktar : raktarak) {
			kellendozsak += raktar.maxcap;
		}
    	return kellendozsak;
    }

    // forras: https://www.movable-type.co.uk/scripts/latlong.html
    // okosabb verzio
    public static int mennyiTav(int lat1, int lon1, int lat2, int lon2) {
        final double r = 6371e3;
        double Phi1 = lat1 * Math.PI/180;
        double Phi2 = lat2 * Math.PI/180;
        double dPhi = (lat2-lat1) * Math.PI/180;
        double dLambda = (lon2-lon1) * Math.PI/180;

        double a = Math.sin(dPhi/2) * Math.sin(dPhi/2) +
                Math.cos(Phi1) * Math.cos(Phi2) *
                        Math.sin(dLambda/2) * Math.sin(dLambda/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return (int)(r * c)/1000; // kilometerben
    }

    // forras: https://www.movable-type.co.uk/scripts/latlong.html
    // pitagorasz tetele szerint
    public static double mennyitav2(int lat1, int lat2, int lon1, int lon2) {
        final double r = 6371e3;
        double Phi1 = lat1 * Math.PI/180;
        double Phi2 = lat2 * Math.PI/180;

        double Lambda1 = lon1 * Math.PI/180;
        double Lambda2 = lon2 * Math.PI/180;

        double x = (Lambda2-Lambda1) * Math.cos((Phi1+Phi2)/2);
        double y = (Phi2-Phi1);
        return Math.sqrt(x*x + y*y) * r;
    }
}