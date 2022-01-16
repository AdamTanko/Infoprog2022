package infoprog.twenty.twentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static String filepath = "data files/raktar.txt";
    //kilogrammban
    public static int zsaksuly = 20;
    public static int szanteherbiras = 2500;

    public static class Raktar {
        public int lat;
        public int lon;
        public int zsak;
    }

    public static ArrayList<Raktar> raktarak = new ArrayList<>();
    public static double[] tav = new double[20];

    public static void main(String[] args) {
        beolv();
//        tavszamolas();
//        for (double d:
//             tav){
//            System.out.println(d);
//        }
    }

    public static void beolv()
    {
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
                    r.zsak = Integer.parseInt(sc.next());
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
        for (int i = 1; i < 22; i++) {
            tav[i] = mennyitav(10,20,30,10);
        }
    }

    public static void feladat1() {
        int szanmaxzsakok = szanteherbiras / zsaksuly;

    }

    // forras: https://www.movable-type.co.uk/scripts/latlong.html
    // okosabb verzio
    public static double mennyitav(int lat1, int lat2, int lon1, int lon2) {
        final double r = 6371e3;
        double Phi1 = lat1 * Math.PI/180;
        double Phi2 = lat2 * Math.PI/180;
        double dPhi = (lat2-lat1) * Math.PI/180;
        double dLambda = (lon2-lon1) * Math.PI/180;

        double a = Math.sin(dPhi/2) * Math.sin(dPhi/2) +
                Math.cos(Phi1) * Math.cos(Phi2) *
                        Math.sin(dLambda/2) * Math.sin(dLambda/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return (r * c)/1000; // kilometerben
    }

    // forras: https://www.movable-type.co.uk/scripts/latlong.html
    // Hulyebb verzio
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