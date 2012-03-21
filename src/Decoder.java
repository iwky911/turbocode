


import java.io.IOException;
/**
 * decode un fichier.
 * @author bertrand
 *
 */
public class Decoder {
	FichierSortie sortie;
	FichierEntree entree;
	Permutation p;
	int longueur;
	CodeurConv c1, c2;
	
	public Decoder(String input, String output, int[] code1, int[] code2, int retroaction1, int retroaction2, Permutation p) throws IOException{
		sortie= new FichierSortie(output);
		entree = new FichierEntree(input);
		this.p=p;
		longueur=Cst.k;
		int k = 1; // une entrée
		int n = 2; // deux sorties
		int m = Cst.memsize;
		c1= new CodeurConv(k,n,m, code1,longueur, retroaction1);
		c2= new CodeurConv(k,n,m, code2,longueur, retroaction2);
		int[] buf = new int[longueur];
		int[] x1 = new int[longueur];
		int[] x2 = new int[longueur];
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
			for(int i=0; i<buf.length; i++){
				x1[i]=entree.read();
				if(x1[i]==-1){
					if(i==0){
						process=false;
					}
					x1[i]=0;
					isEmpty=true;
				}
			}
			for(int i=0; i<buf.length; i++){
				x2[i]=entree.read();
				if(x2[i]==-1){
					if(i==0){
						process=false;
					}
					x2[i]=0;
					isEmpty=true;
				}
			}
			if(process){
				decode(buf,x1,x2);
			}
		}
	}
	
	public int dirac(int a, int b){
		return (a==b)?1:0;
	}
	
	public void decode(int[] u, int[] x1, int[] x2) throws IOException{
		int[][] e1= new int[longueur][2];
		int[][] e2= new int[longueur][2];
		for(int i=0; i<longueur; i++){
			e1[i][0]=0;
			e1[i][1]=0;
			e2[i][0]=0;
			e2[i][1]=0;
		}
		int N=20;
		for(int i=0; i<N; i++){
			e2=c1.SOVA(u, p.permuteInv(e1), x1);
			e1=c2.SOVA(p.permute(u), p.permute(e2), x2);
		}
		int[] res = new int[longueur];
		int inv=0;
		e1 = p.permuteInv(e1);
		for(int i=0; i<longueur; i++){
			inv=p.inv(i);
			if(dirac(u[i],1)+e1[i][0]+ e2[i][0]<=dirac(u[i],0)+e1[i][1]+e2[i][1]){
				res[i]=0;
			}
			else{
				res[i]=1;
			}
			sortie.write(res[i], 1);
		}
	}
	
    public static void main(String [] args) throws IOException {
    	Decoder d = new Decoder(args[0], args[1],
    			Cst.getGenerateur(Cst.g1),
    			Cst.getGenerateur(Cst.g2),
    			Cst.getRec(Cst.b1),
    			Cst.getRec(Cst.b2),
    			new Permutation(Cst.k));
    }
}