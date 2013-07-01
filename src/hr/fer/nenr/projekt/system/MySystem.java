package hr.fer.nenr.projekt.system;

import hr.fer.nenr.projekt.fje.LinearOut;
import hr.fer.nenr.projekt.fje.NeizrazitiSkup;
import hr.fer.nenr.projekt.fje.TNorma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MySystem {
	
	private Stanje s;
	private NeizrazitiSkup A;
	private NeizrazitiSkup B;
	private LinearOut out;
	private ArrayList<Uzorak> primjeri;
	
	private double[] w, normalw, outVals;
	
	public MySystem(int nRules){
		s = new Stanje();
		s.stopa = 0.005;
		s.nPravila = nRules;

		A = new NeizrazitiSkup(nRules);
		B = new NeizrazitiSkup(nRules);
		out = new LinearOut(nRules);
		
		primjeri = new ArrayList<Uzorak>();
		
		w = new double[nRules];
		normalw = new double[nRules];
		outVals = new double[nRules];
		
		pokupiPrimjere();
		
		work();
		
	}
	
	private void work() {
		
		for (int i = 0; i < 10000; i++){
			
			double error = 0;
			for (Uzorak u:primjeri){
				s.x = u.getX();
				s.y = u.getY();
				s.f = u.getF();
				
				forward();
				updateParams();
				
				error += Math.pow(s.f-s.o, 2);
				//System.out.println(error + " sf:" + s.f + " so:" + s.o);
			}
			if (i % 100 == 0) System.out.println(error);
		}
		
		storeParameters();
		
	}

	private void storeParameters() {
		Logger log = new Logger("matlab/params.txt");
		
		double[] a = A.getA();
		double[] b = A.getB();
		double[] c = A.getC();
		
		double[] a1 = B.getA();
		double[] b1 = B.getB();
		double[] c1 = B.getC();
		
		for (int i = 0; i < a.length; i++){
			log.write(a[1] + " " + a1[i] + " " + b[i] + " " + b1[i] + " " + c[i] + " " + c1[i] + "\n");
		}
		
		log.close();
		
	}

	private void pokupiPrimjere(){
		Scanner scan;
		try {
			
			scan = new Scanner(new File("matlab/uzorci.txt"));
			while (scan.hasNextLine()){
				String[] nums = scan.nextLine().split(" ");

				int x = Integer.parseInt(nums[0]);
				int y = Integer.parseInt(nums[1]);
				double f = Double.parseDouble(nums[2]);
				
				primjeri.add(new Uzorak(x, y, f));
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void updateParams() {
		
		for (int i = 0; i < w.length; i++){
			
			double sum1 = 0.0, sum2 = 0.0;
			for (int j = 0; j < w.length; j++){
				sum1 += w[j] * (outVals[i] - outVals[j]);
				sum2 += w[j];
			}
			
			double constant = -(s.f - s.o)*(sum1/(sum2*sum2));
			
			double miA = A.pripadnost(s.x, i);
			double miB = B.pripadnost(s.y, i);
			double derSkupA = -miB*(miB-2)/(Math.pow(2+miA*(miB-1)-miB,2));
			double derSkupB = -miA*(miA-2)/(Math.pow(2+miA*(miB-1)-miB,2));
			
			A.update(s.x, s.stopa, i, constant*derSkupA);
			B.update(s.y, s.stopa, i, constant*derSkupB);
			
			constant = -(s.f - s.o)*normalw[i];
			out.update(constant, s.stopa, s.x, s.y, i);
		}
		
	}

	private void forward(){
		
		double Wuk = 0.0; 
		for (int i = 0; i < w.length; i++){
			w[i] = TNorma.einstein(A.pripadnost(s.x, i), B.pripadnost(s.y, i));
			Wuk += w[i];
		}
		
		for (int i = 0; i < normalw.length; i++){
			normalw[i] = w[i]/Wuk;
		}
		
		double tempO = 0.0;
		for (int i = 0; i < w.length; i++){
			outVals[i] = out.z(s.x, s.y, i);
			tempO += normalw[i] * outVals[i];
		}
		
		s.o = tempO;
	}

	public static void main(String[]args){
		new MySystem(6);
	}
}
