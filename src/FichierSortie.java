

import java.io.*;

class FichierSortie {
    int c; // carat�re courant
    int n; // nombre de bits �crits
    FileOutputStream fichier;

    FichierSortie(String s) throws IOException {
	fichier = new FileOutputStream(s);
	n = 0;
	c = 0;
    }
    void writeTab(int[] tab, int i) throws IOException{
    	System.out.println("wtab "+tab.length);
    	for(int k=0; k<tab.length; k++){
    		write(tab[k], i);
    	}
    }

    // �crit les i bits les moins significatifs de l'�criture de c
    void write(int c, int i) throws IOException {
	for (; i > 0; --i) {
	    write(c & 1);
	    c >>= 1;
	}
    }

    // �crit le bit b
    void write(int b) throws IOException {
	c ^= (b << n);
	++n;
	if (n == 8)
	    flush();
    }

    void close() throws IOException {
	flush();
	fichier.close();
    }

    boolean bufferVide() {
	return (n == 0);
    }

    void flush() throws IOException {
	fichier.write(c);
	n = 0;
	c = 0;
    }
}
