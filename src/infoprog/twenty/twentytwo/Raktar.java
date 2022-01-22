package infoprog.twenty.twentytwo;

public class Raktar {
	private int lat;
	private int lon;
	private int maxcap; //
	private int keszlet; // mennyi fer meg

	public Raktar() {

	}

	public Raktar(int lat, int lon, int maxcap) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.maxcap = maxcap;
		this.keszlet = maxcap;
	}

	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}

	public int getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}

	public int getMaxcap() {
		return maxcap;
	}
	public void setMaxcap(int maxcap)  {
		this.maxcap = maxcap ;
	}

	public int getKeszlet() {
		return keszlet;
	}
	public void setKeszlet(int keszlet) {
		this.keszlet = keszlet;
	}

	public int throw_() {
		throw new RuntimeException("maxcap mar meg volt adva");
	}
}
