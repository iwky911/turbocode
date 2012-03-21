

import java.util.Random;

public class Cst {
	static int[] G1 = {3, 2, 0, 2, 2};
	static int[] G2={2,0,2,0,2};
	static int g1=5369753;
	static int g2 = 642531;
	static int b1=256985;
	static int b2=12386215;
	static int rinit=123456789;
	static int memsize=5;
	static int k=1024;
	
	static int getRec(int init){
		return (new Random(init)).nextInt(1<<(memsize));
	}
	static int[] getGenerateur(int init){
		Random r = new Random(init);
		int[] sortie = new int[memsize+1];
		for(int i=0; i<sortie.length; i++){
			sortie[i]=r.nextInt(4);
		}
		return sortie;
	}
}
