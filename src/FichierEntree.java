

import java.io.*;

class FichierEntree {
    FileInputStream fichier;
    int c; // carat�re courant
    int n; // nombre de bits lus

    FichierEntree(String s) throws IOException {
	fichier = new FileInputStream(s);
	n = 0;
	c = 0;
    }

    // retourne k bits sous forme d'un entier entre 0 et 2^k-1, sauf
    // si le fichier est �puis� -> -1
    int read(int k) throws IOException {
	int b = 0, t = 0, i;
	for (i = 0; i < k; ++i) {
	    b = read();
	    if (b == -1)
		break;
	    t ^= b << i;
	}
	if (i == 0)
	    return -1;
	else
	    return t;
    }	

    // retourne 0 ou 1, sauf si le fichier est �puis� -> -1
    int read() throws IOException {
	if (n == 0) {
	    c = fichier.read();
	    if (c == -1)
		return -1;
	    n = 8;
	}
	--n;
	int b = c & 1;
	c >>= 1;
	return b;
    }

    void close() throws IOException {
	fichier.close();
    }
}
