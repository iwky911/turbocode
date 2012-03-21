


import java.util.Random;
/**
 * classe permettant de stocker une permutation et de l'appliquer à différents cas de figures
 * La permutation est créée grace à une fonction random initialisé avec Cst.rinit afin d'obtenir
 * la même permutation aléatoire au codage et au décodage
 * @author bertrand
 *
 */
public class Permutation {
	int[] p;
	public Permutation(int[] p){
		this.p=p;
	}
	public Permutation(int longueur){
		p=new int[longueur];
		for(int i=0; i<p.length; i++){
			p[i]=i;
		}
		int tmp=0, i=0, j=0;
		Random r = new Random((long) Cst.rinit);
		for(int k=0; k<longueur*3; k++){
			i=r.nextInt(longueur);
			j=i;
			while(j==i){
				j=r.nextInt(longueur);
			}
			tmp=p[i];
			p[i]=p[j];
			p[j]=tmp;
		}
	}
	
	public int getLongueur(){
		return p.length;
	}
	public int[] permute(int[] entree){
		if(entree.length != p.length){
			throw new Error("les taille ne sont pas compatibles");
		}
		int[] sortie= new int[entree.length];
		for(int i=0; i<p.length; i++){
			sortie[p[i]]=entree[i];
		}
		return sortie;
	}
	public int[][] permute(int[][] entree){
		if(entree.length != p.length){
			throw new Error("les taille ne sont pas compatibles");
		}
		int[][] sortie= new int[entree.length][];
		for(int i=0; i<p.length; i++){
			sortie[p[i]]=entree[i];
		}
		return sortie;
	}
	public int[][] permuteInv(int[][] entree){
		if(entree.length != p.length){
			throw new Error("les taille ne sont pas compatibles");
		}
		int[][] sortie= new int[entree.length][];
		for(int i=0; i<p.length; i++){
			sortie[i]=entree[p[i]];
		}
		return sortie;
	}
	
	public int inv(int i){
		for(int k=0; k<p.length; k++){
			if(p[k]==i){
				return k;
			}
		}
		return -1;
	}
	
}
