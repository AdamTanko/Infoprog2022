package infoprog.twenty.twentytwo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static final String filepath = "data files/raktar2.txt";
    //kilogrammban
    private static final int zsaksuly = 20;
    private static final int szanteherbiras = 2500;

    private static final ArrayList<Raktar> raktarak = new ArrayList<>();

    public static void main(String[] args) {
        beolv();
        feladat1_2();
//        feladat3();
//        feladat4();
    }




    //TODO: Megtett kilometerek pontos szamolasa
    public static void feladat1_2(){
        System.out.println("===1. Feladat===");
        try {
            if (raktarak.size() == 0) {
                beolv();
            }
            int szanzsakokszama = szanteherbiras / zsaksuly;
            int mennyikell = mennyiKellMeg();
            int nemnullaraktarszama;
            int utakszama = 0;
            int megtettKMek = 0;

            outer: while (mennyiKellMeg() != 0) {
                utakszama++;
                nemnullaraktarszama = legkozelebbiNemNullaRaktar();
                /*
                  option 1: Line 50
                  option 2: Line 54,80
                  option 3: Line 54,68

                  option 1:+5 diff
                  option 2:-2420 diff
                  option 3:-1552 diff
                 */
                //option 1
                megtettKMek += 2*mennyitav(70,23,raktarak.get(nemnullaraktarszama).getLat(),raktarak.get(nemnullaraktarszama).getLon());
                //option 2
                //option 3
                //megtettKMek += mennyiTav(70,23,raktarak.get(nemnullaraktarszama).getLat(),raktarak.get(nemnullaraktarszama).getLon());
                szanzsakokszama= szanteherbiras / zsaksuly;
                while (szanzsakokszama != 0) {
                    System.out.printf("Erintett raktar: %d, %d; eddig megtett KM-ek: %d \n", raktarak.get(nemnullaraktarszama).getLat(),raktarak.get(nemnullaraktarszama).getLon(), megtettKMek);
                    if (szanzsakokszama > raktarak.get(nemnullaraktarszama).getKeszlet()) {
                        szanzsakokszama -= raktarak.get(nemnullaraktarszama).getKeszlet();
                        raktarak.get(nemnullaraktarszama).setKeszlet(0);
                        nemnullaraktarszama++;
                        // menjel ma' tovabb
                    } else {
                        raktarak.get(nemnullaraktarszama).setKeszlet(raktarak.get(nemnullaraktarszama).getKeszlet() - szanzsakokszama);
                        szanzsakokszama = 0;
                        //option 3
                        //megtettKMek += mennyiTav(raktarak.get(nemnullaraktarszama-1).getLat(),raktarak.get(nemnullaraktarszama-1).getLon(),raktarak.get(nemnullaraktarszama).getLat(),raktarak.get(nemnullaraktarszama).getLon());
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
                System.out.println("Back to base");
                //option 2
                //megtettKMek += mennyiTav(70,23,raktarak.get(nemnullaraktarszama).getLat(),raktarak.get(nemnullaraktarszama).getLon());
                mennyikell=mennyiKellMeg();
            }
            //theoretical distance 24259 for raktar2.txt
            System.out.printf("Utolso raktarba vitt zsakok: %d, megtett kilometerek: %d,elteres az teoretikustol: %d, megtett utakszama: %d \n",szanzsakokszama, megtettKMek,megtettKMek-24259 , utakszama);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("lol");
    }

    public static void feladat3() {

    }

    /**
     *
     *
     * @param option ha igaz akkor a szan kapcitasa vegtelen, ha hamis akkor veges
     */
    public static void feladat4(boolean option) {
        if (option) {
            while (mennyiKellMeg() != 0) {

            }
        } else {
            while (mennyiKellMeg() != 0) {

            }
        }
    }

    public static int melyikALegkozelebbRaktar(int firstidx) {
        int firstlat = raktarak.get(firstidx).getLat();
        int firstlon = raktarak.get(firstidx).getLon();
        int elozotav = 0;
        int legkozelebbidx = 0;

        for (int i = 0; i < raktarak.size(); i++) {
            if (elozotav > mennyitav(firstlat,firstlon,raktarak.get(i).getLat(),raktarak.get(i).getLon())) {
                if(raktarak.get(i).getKeszlet() == 0){
                    continue;
                }
                elozotav = mennyitav(firstlat,firstlon,raktarak.get(i).getLat(),raktarak.get(i).getLon());
                legkozelebbidx = i;
            }
        }

        return legkozelebbidx;
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
    // haversine egyenlet

    //Megjegyzes: ezt az egyenletet hasznalom mert ezek valosagosnak tuno ertekeket ad vissza

    public static int mennyitav(int lat1, int lon1, int lat2, int lon2) {
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
}