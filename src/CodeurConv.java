

import java.io.IOException;
import java.util.Stack;




public class CodeurConv{
	Arete [][] diagramme;
	int k; // nombre d'entrées
	int n; // nombre de sorties
	int m; // ordre du codeur
	int longueur;
	int nbEtats;// TODO Auto-generated constructor stubs;       // 1 << (k * m)
	int nbTransitions; // 1 << k
	int[][] incoming, outgoing;


	public CodeurConv(int k, int n, int m, int[] g, int longueur, int b) {
		System.out.println("memrec: "+b+" "+longueur);
		Help.printTab(g);
		this.k=k;
		this.n=n;
		this.m=m;
		this.longueur=longueur;
		nbEtats=1 << (k*m);
		nbTransitions= 1<<k;
		createAreteTab(g, b);
		incoming = new int[nbEtats][2];
		outgoing = new int[nbEtats][2];
		for(int i=0; i<nbEtats;i++){
			incoming[i][0]=-1;
			incoming[i][1]=-1;
		}
		
		for(int i=0;i<nbEtats; i++){
			for(int x=0; x<2; x++){
				outgoing[i][x]=diagramme[i][x].fin;
				int arrivee=diagramme[i][x].fin;
				if(incoming[arrivee][0]!=-1){
					incoming[arrivee][1]=i;
				}
				else{
					incoming[arrivee][0]=i;
				}
			}
		}
	}

	private void createAreteTab(int[] g, int b) {
		diagramme=new Arete[1<<(k*m)][];
		for(int i=0; i<diagramme.length; i++){
			diagramme[i]=new Arete[1<<k];
			for(int j=0; j<1<<k; j++){
			//	System.out.println("debut"+i+" "+j);
				diagramme[i][j]=new Arete(i, (i>>k)+(getFirstSum(j, i, b)<<(k*(m-1))),j, mul(g,i,j)); //TODO a revoir....
				//System.out.println(i+" "+j);
			}
		}
	}
	
	private int getFirstSum(int j, int i, int b){
		return (j+nbBits(i&b))%2;
	}
	
	public int nbBits(int k){
		int sortie=0;
		while(k>0){
			sortie=sortie+k&1;
			k=k>>1;
		}
		return sortie;
	}
		
	public int mul(int[] g, int i, int j){
		int sortie=0;
		i=i+j*nbEtats;
		for(int index=0; index<g.length; index++){
			sortie^=(i>>(k*(m+1)-1-index))%2==1? g[index] : 0;
		}
		return sortie;
	}
	/**
	 * fonction effectuant l'algorithme SOVA (algorithme 3)
	 * @param u
	 * @param e
	 * @param r
	 * @return
	 */
	public int[][] SOVA(int[] u, int[][] e, int[] r){
		int[][] ep= new int[e.length][2];
		int[][] f= new int[longueur+1][nbEtats];
		int[][] b= new int[longueur+1][nbEtats];
		for(int i=0; i<longueur; i++){
			for(int a=0; a<nbEtats;a++){
				f[i][a]=1<<16;
				b[i][a]=1<<16;
			}
		}
		f[0][0]=0;
		for(int a=0; a<nbEtats; a++){
			b[longueur][a]=0;
		}
		for(int i=1; i<longueur+1;i++){
			for(int a=0; a<nbEtats; a++){
				f[i][a]= minf(f[i-1],e[i-1], a, u[i-1], r[i-1]);
				b[longueur-i][a] = minb(b[longueur-i+1], e[longueur-i], u[longueur-i], r[longueur-i], a);
			}
		}
		for(int i=1; i<longueur+1;i++){
			ep[i-1][0]=mine(0, f[i-1], b[i], r[i-1]);
			ep[i-1][1]=mine(1, f[i-1], b[i], r[i-1]);
		}
		return ep;
	}
	/*
	 * fonctions auxilliaire utilisé dans SOVA
	 */
	public int mine(int val, int[] f, int[] b, int r){
		int sortie=Integer.MAX_VALUE;
		for(int i=0; i<nbEtats; i++){
			for(int j=0; j<nbEtats; j++){
				if(fonctionu(i,j)==val){
					sortie=Math.min(sortie, f[i]+b[j]+dirac(r, 1-fonctionr(i,j)));
				}
			}
		}
		return sortie;
	}
	
	public int minf(int[] f, int[] e, int j, int u, int r){
		int sortie = Integer.MAX_VALUE;
		for(int a: incoming[j]){
			int fctu=fonctionu(a,j);
			int fctr=fonctionr(a,j);
			if(fctu!=Integer.MAX_VALUE){
				sortie=Math.min(sortie, f[a]+e[fctu]+dirac(u,1-fctu)+dirac(r, 1-fctr));
			}
		}
		return sortie;
	}
	
	public int dirac(int a, int b){
		return (a==b)?1:0;
	}
	
	public int minb(int[] b, int[] e, int u, int r, int j){
		int sortie = Integer.MAX_VALUE;
		for(int a: outgoing[j]){
			int fctu=fonctionu(j,a);
			int fctr=fonctionr(j,a);
			if(fctu!=Integer.MAX_VALUE){
				sortie=Math.min(sortie, b[a]+e[fctu]+dirac(u,1-fctu)+dirac(r, 1-fctr));
			}
		}
		return sortie;
	}
	
	public int fonctionu(int a,int b){
		for(int x=0; x<2;x++){
			if(diagramme[a][x].fin==b){
				return x;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	public int fonctionr(int a, int b){
		int u= fonctionu(a,b);
		if(u==Integer.MAX_VALUE){
			return u;
		}
		else{
			return diagramme[a][u].sortie/2;
		}
	}
	/**
	 * fonction retournant la deuxième sortie (x2) du code convotutif pour l'entrée entree.
	 * @param entree
	 * @return
	 */
	public int[] coder(int[] entree){
		int[] sortie = new int[entree.length];
		int etat=0;
		for(int i=0; i<entree.length; i++){
			Arete changement = diagramme[etat][entree[i]];
			etat=changement.fin;
			sortie[i] = changement.sortie/2; //on divise par 2 pour obtenir la seuxième sortie
		}
		return sortie;
	}
}
