package hr.fer.nenr.projekt.fje;

import java.util.Random;

public class LinearOut {

	private double[] p,q,r;
	
	public LinearOut(int nRules){
		p = new double[nRules];
		q = new double[nRules];
		r = new double[nRules];
		init();
	}
	
	private void init() {
		Random gen = new Random();
		
		for(int i = 0; i < p.length; i++){
			p[i] = gen.nextDouble() - 0.5;
			q[i] = gen.nextDouble() - 0.5;
			r[i] = gen.nextDouble() - 0.5;
		}
	}
	
	public void update(double constant, double stopa, int x, int y, int index){
		p[index] -= stopa * constant*x;
		q[index] -= stopa * constant*y;
		r[index] -= stopa * constant;
	}

	public double z(int x, int y, int index){
		return x*p[index] + y*q[index] + r[index];
	}

	public double[] getP() {
		return p;
	}

	public double[] getQ() {
		return q;
	}

	public double[] getR() {
		return r;
	}
	
}
