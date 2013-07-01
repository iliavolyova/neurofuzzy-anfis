package hr.fer.nenr.projekt.fje;

import java.util.Random;

public class NeizrazitiSkup {

	private double[] a, b, c;
	private Random generator = new Random();
	
	public NeizrazitiSkup(int nPravila){
		a = new double[nPravila];
		b = new double[nPravila];
		c = new double[nPravila];
		init();
	}

	private void init() {
		for (int i = 0; i < a.length; i++){
			a[i] = generator.nextDouble() - 0.5;
			b[i] = generator.nextDouble() - 0.5;
			c[i] = generator.nextDouble() - 0.5;
		}
	}
	
	public double pripadnost(double x, int i){
		double rez = 1.0 / (1 + Math.pow(((x - c[i])*(x - c[i]))/(a[i]*a[i]), b[i]));
		return rez;
	}
	
	public void update(double x, double stopa, int index, double constant){
		
		double mi = pripadnost(x, index);
		
		double derA = (2*b[index]*mi*(1-mi))/a[index];
		double derB = (-mi*(1-mi)*Math.log(((x - c[index])*(x-c[index]))/(a[index]*a[index])))/b[index];
		double derC = (-2*b[index]*mi*(1-mi))/(c[index]-x);
		
		a[index] -= stopa*constant*derA;
		b[index] -= stopa*constant*derB;
		c[index] -= stopa*constant*derC;
		
	}

	public double[] getA() {
		return a;
	}

	public double[] getB() {
		return b;
	}

	public double[] getC() {
		return c;
	}
	
}