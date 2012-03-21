


import java.io.IOException;

/**
 * renvoie le nombre de bits différents entre le fichier 1 et le fichier 2
 * @author bertrand
 *
 */
public class Diff {
	public Diff(String a, String b) throws IOException{
		FichierEntree fa= new FichierEntree(a);
		FichierEntree fb = new FichierEntree(b);
		int count=0;
		int cur=0;
		while(cur!=-1){
			cur=fa.read();
			count+= (cur==fb.read())?0:1;
		}
		System.out.println(count);
	}
	public static void main(String[] args) throws IOException{
		Diff d = new Diff(args[0], args[1]);
	}
}
