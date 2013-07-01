package hr.fer.nenr.projekt.fje;

public class TNorma {

	public static double einstein(double m1, double m2){
		double rez = (m1*m2)/(2- (m1+m2-m1*m2));
		return rez;
	}
}
