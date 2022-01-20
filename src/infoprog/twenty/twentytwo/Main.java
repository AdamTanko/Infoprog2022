package infoprog.twenty.twentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

class sortbytav implements Comparator<Raktar> {
    public int compare(Raktar a, Raktar b) {
        return a.getTavlapfoldre() - b.getTavlapfoldre();
    }
}


public class Main {

    private static final String filepath = "data files/raktar2.txt";
    //kilogrammban
    private static final int zsaksuly = 20;
    private static final int szanteherbiras = 2500;
    private static boolean voltMarIrva;

    private static final ArrayList<Raktar> raktarak = new ArrayList<>();

    public static void main(String[] args) {
        beolv();
        tavszamolas();
        System.out.println(mennyiKellMeg());
        feladat1_2();
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
                    yes = false;
                    r.setLat(Integer.parseInt(sc.next()));
                    r.setLon(Integer.parseInt(sc.next()));
                } else if (!sc2.hasNextLine()){
                    r.setLat(Integer.parseInt(sc.next()));
                    r.setLon(Integer.parseInt(sc.next()));
                } else {
                    r.setLat(Integer.parseInt(sc.next()));
                    r.setLon(Integer.parseInt(sc.next()));
                    int maxcapkeszlet = Integer.parseInt(sc.next());
                    r.setMaxcap(maxcapkeszlet);
                    r.setKeszlet(maxcapkeszlet);
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
            r.setTavlapfoldre(mennyiTav(70,23,r.getLat(),r.getLon()));
        }
    }

    public static void feladat1_2(){
        try {
            if (raktarak.size() == 0) {
                beolv();
            }
            int szanzsakokszama;
            int mennyikell = mennyiKellMeg();
            int nemnullaraktarszama;
            int utakszama = 0;
            int megtettKMek = 0;

            outer: while (mennyiKellMeg() != 0) {
                utakszama++;
                nemnullaraktarszama = legkozelebbiNemNullaRaktar();

                szanzsakokszama= szanteherbiras / zsaksuly;
                while (szanzsakokszama != 0) {

                    if (szanzsakokszama > raktarak.get(nemnullaraktarszama).getKeszlet()) {
                        szanzsakokszama -= raktarak.get(nemnullaraktarszama).getKeszlet();
                        raktarak.get(nemnullaraktarszama).setKeszlet(0);
                        nemnullaraktarszama++;
                        // menjel ma' tovabb
                    } else {
                        raktarak.get(nemnullaraktarszama).setKeszlet(raktarak.get(nemnullaraktarszama).getKeszlet() - szanzsakokszama);
                        szanzsakokszama = 0;
                        //menjel ma' haza
                    }
                    if (nemnullaraktarszama == raktarak.size()) {
                        String s = "," + szanzsakokszama;
                        Files.write(Paths.get(filepath), s.getBytes(), StandardOpenOption.APPEND);
                        raktarak.get(nemnullaraktarszama-1).setKeszlet(szanzsakokszama);
                        System.out.println("Maradek zsakok szama hozza adva a raktar.txt-hez");
                        break outer;
                    }
                }
                mennyikell=mennyiKellMeg();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("lol");
    }

    public static int mennyiKellMeg() {
    	int kellendozsak = 0;
    	for (Raktar raktar : raktarak) {
			kellendozsak += raktar.getKeszlet();
		}
    	return kellendozsak;
    }

    public static int legkozelebbiNemNullaRaktar() {
        int nemnullaraktarszama = 1;
        for (int i = 0; i < raktarak.size(); i++) {
            // 1 raktar aminek keszlete nem 0
            if (raktarak.get(i).getKeszlet() != 0) {
                nemnullaraktarszama = i;
                break;
            }
        }
        return nemnullaraktarszama;
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