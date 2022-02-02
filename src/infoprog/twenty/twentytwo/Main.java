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

    private static final String filepath = "C:\\Users\\Tanulo\\Documents\\Adam Tanko 1.G\\eclipse\\Infoprog2022-main(1).zip_expanded\\Infoprog2022-main\\data files\\raktar.txt";
    //kilogrammban
    private static final int zsaksuly = 20;
    private static final int szanteherbiras = 2500;

    public static final ArrayList<Raktar> raktarak = new ArrayList<>();

    public static void main(String[] args) {
        beolv();
  //    feladat1_2();
        //feladat3();
        feladat4();
        int[][] tav =raktarakkoztitav();
        System.out.println();
        for (int i = 0; i < tav.length; i++) {
			for (int j = 0; j < tav.length; j++) {
				System.out.print(tav[i][j] + " ");
			}
			System.out.println();
		}
    }




    public static void feladat1_2(){
        System.out.println("===1. Feladat===");
        try {
            if (raktarak.size() == 0) {
                beolv();
            }
            raktarak.get(raktarak.size()-1).setKeszlet(0);
            raktarak.get(raktarak.size()-1).setMaxcap(0);
            int szanzsakokszama = szanteherbiras / zsaksuly;
            int nemnullaraktarszama;
            int utakszama = 0;
            int megtettKMek = 0;

            outer: while (mennyiKellMeg() != 0) {
                utakszama++;
                nemnullaraktarszama = legkozelebbiNemNullaRaktar();
                megtettKMek += 2*mennyitav(70,23,raktarak.get(nemnullaraktarszama).getLat(),raktarak.get(nemnullaraktarszama).getLon());
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
            }
            //theoretical distance 24254 for raktar2.txt
            System.out.printf("Utolso raktarba vitt zsakok: %d, megtett kilometerek: %d, megtett utakszama: %d \n",szanzsakokszama, megtettKMek, utakszama);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("lol");
    }

    // feltetel: egyik raktarban sincs tobb zsak mint a szan egyszerre tud vinni
    /*
    Hány kilométert tesz meg a szán összesen, ha az 1. feladatban megírt szállítási algoritmust úgy
    módosítjuk, hogy a szán egy adott raktár feltöltése után visszatér Lappföldre, ha a következő raktár
    feltöltéséhez nincs elegendő zsákja?
     */
    public static void feladat3() {
    	System.out.println("===3. Feladat===");
    	if (raktarak.size() == 0) {
            beolv();
        }
        int megtettKMek = 0;
        int szanzsakokszama = szanteherbiras / zsaksuly;
        int aktualisraktar = 0;
        
    	for (int i = 1; i < raktarak.size()-1; i++) {
    		if (raktarak.get(i).getKeszlet() == 0) {
    			continue;
    		}
            	
    		megtettKMek += mennyitav(raktarak.get(aktualisraktar).getLat(),raktarak.get(aktualisraktar).getLon(),raktarak.get(i).getLat(),raktarak.get(i).getLon());
    		System.out.printf("Erintett raktar: %d, %d; eddig megtett KM-ek: %d \n", raktarak.get(i).getLat(),raktarak.get(i).getLon(), megtettKMek);
    		//  		raktarak.get(i).setKeszlet(0);
    		szanzsakokszama -= raktarak.get(i).getKeszlet();
    		if (szanzsakokszama >= raktarak.get(i+1).getKeszlet()) {
    			aktualisraktar = i;
    		} else {
    			aktualisraktar = 0;
    			megtettKMek += mennyitav(70,23,raktarak.get(i).getLat(),raktarak.get(i).getLon());
    			System.out.println("back to base");
    		}
    		
    		
		} 	
    	
    	
    }

    /*
    Határozzátok meg a raktárak bejárásának azt a sorrendjét, ahol az első feltöltött raktár a lappföldi
    raktárhoz legközelebbi, és minden i+1-edik raktárhely az i-edikhez legközelebb eső, eddig még fel nem
    töltött raktár. Előfordulhat, hogy a legközelebbi raktár meghatározása többértékű hozzárendelés. Az
    összes esetet figyelembe véve, adjátok meg a lehetséges bejárási sorrendeket! Most tételezzük fel,
    hogy a szán kapacitása végtelen (csak egyszer töltik fel induláskor) és nem kell visszafordulni
    Lappföldre.
     */
	public static void feladat4() {
		System.out.println("===4. Feladat===");
//		int[][] tavok = raktarakkoztitav();
		int[][] tavok = {{0,120,100,500,100},
						 {120, 0, 200, 25, 45},
						 {100, 200, 0, 40, 200},
						 {500,25, 40, 0, 420},
						 {100,45,200, 420, 0}
		};
		ArrayList<ArrayList<Integer>> utvonalak = new ArrayList<>();
		utvonalak.add(new ArrayList<Integer>());
		int aktualisUtvonal = 0;
		int ittVanAMinTav = 0;

		utvonalak.get(0).add(0);
		while (aktualisUtvonal < utvonalak.size()) {

			for (int j = 0; j < tavok.length - 1; j++) {
				int aktsor = utvonalak.get(0).get(utvonalak.get(0).size() - 1);
				int mintav = Integer.MAX_VALUE;
				for (int i = 0; i < tavok.length; i++) {

					if ((tavok[aktsor][i] < mintav) && !(utvonalak.get(0).contains(i)) && (tavok[aktsor][i] != 0)) {
						mintav = tavok[aktsor][i];
//						ittVanAMinTav = i;
					}
				}
				int elso = 0;
				boolean volteMarTalalva = false;
				for (int i = 0; i < tavok.length; i++) {
					if ((tavok[aktsor][i] == mintav) && !(utvonalak.get(aktualisUtvonal).contains(i)) && !volteMarTalalva ) {
					elso = i;
						volteMarTalalva = true;
						continue;
					}
					if ((tavok[aktsor][i] == mintav) && !(utvonalak.get(aktualisUtvonal).contains(i)) ) {
						ArrayList<Integer> list = new ArrayList<>(utvonalak.get(aktualisUtvonal));
						list.add(i);
						utvonalak.add(list);
					}
				}
				utvonalak.get(aktualisUtvonal).add(elso);
				
			}
			aktualisUtvonal++;
		}

		System.out.println("lol");
		for (int i = 0; i < utvonalak.size(); i++) {
			System.out.println(utvonalak.get(i).toString());
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
    
    public static int[][] raktarakkoztitav() {
        int[][] tavok= new int[raktarak.size()][raktarak.size()];
        for (int i = 0; i < raktarak.size(); i++) {
            for (int j = 0; j < raktarak.size(); j++) {
                int tav = mennyitav(raktarak.get(i).getLat(),raktarak.get(i).getLon(),raktarak.get(j).getLat(),raktarak.get(j).getLon());
                tavok[i][j] = tav;
               // System.out.println(tav);
            }
        }
        return tavok;
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
                    if(sc.hasNextInt()) {
                    	int maxcapkeszlet = sc.nextInt();
                        r.setMaxcap(maxcapkeszlet);
                        r.setKeszlet(maxcapkeszlet);
                    }
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