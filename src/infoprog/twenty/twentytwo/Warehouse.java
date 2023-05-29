package infoprog.twenty.twentytwo;

public class Warehouse {
	private int lat;
	private int lon;
	private int maxcap;
	private int stock; // a.k.a. how much space left

	public Warehouse() {

	}

	public Warehouse(int lat, int lon, int maxcap) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.maxcap = maxcap;
		this.stock = maxcap; // we assume that the warehouse is filled to the max at the start
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

	public void setMaxcap(int maxcap)  {
		this.maxcap = maxcap ;
	}

	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

}
