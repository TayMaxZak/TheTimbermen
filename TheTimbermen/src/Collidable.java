
public interface Collidable {
	double[] getBoundsX();
	double[] getBoundsY();
	boolean check(Collidable c);
	String getTag();
}
