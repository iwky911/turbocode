

import java.io.IOException;
import java.io.OutputStream;

public class Coder {
	FichierSortie sortie;
	FichierEntree entree;
	public Coder(String input, String output, int[] code1, int[] code2, int retroaction1, int retroaction2, Permutation p) throws IOException{
		sortie= new FichierSortie(output);
		entree = new FichierEntree(input);
		int longueur=Cst.k;
		int k = 1; // une entrée
		int n = 2; // deux sorties
		int m = Cst.memsize;
		CodeurConv c1= new CodeurConv(k,n,m, code1,longueur, retroaction1);
		CodeurConv c2= new CodeurConv(k,n,m, code2,longueur, retroaction2);
		int[] buf = new int[longueur];
		boolean isEmpty=false, process=true;
		while(!isEmpty){
			for(int i=0; i<buf.length; i++){
				buf[i]=entree.read();
				if(buf[i]==-1){
					if(i==0){
						process=false;
					}
					buf[i]=0;
					isEmpty=true;
				}
			}
			if(process){
				print(buf, c1.coder(buf), c2.coder(p.permute(buf)));
			}
		}
	}
	
	public void print(int[] normal, int[] x1, int[]x2){
		if(normal.length!= x1.length || normal.length!=x2.length){
			throw new Error("les taille ne sont pas compatible");
		}
		try{
			sortie.writeTab(normal,1);
			sortie.writeTab(x1, 1);
			sortie.writeTab(x2, 1);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public static void main(String [] args) throws IOException {
		Coder c = new Coder(args[0], args[1],
				Cst.getGenerateur(Cst.g1),
				Cst.getGenerateur(Cst.g2),
				Cst.getRec(Cst.b1),
				Cst.getRec(Cst.b2),
				new Permutation(Cst.k));
		
	}

}
