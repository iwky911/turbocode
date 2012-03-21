


import java.io.*;

class Canal {
    static int randomChar(double p) {
	int r = 0;
	for (int i = 0; i < 8; ++i)
	    // Math.random() est uniformement r�parti entre 0 et 1
	    if (Math.random() < p)
		r ^= (1 << i);
	return r;
    }

    static void bruiter(FileInputStream entree, FileOutputStream sortie, double p) throws IOException {
	int c = entree.read();
	while (c != -1) {
	    sortie.write(c ^ randomChar(p));
	    c = entree.read();
	}
    }

    public static void main(String [] args) throws IOException {
	bruiter(new FileInputStream(args[0]),
		new FileOutputStream(args[1]),
		Double.valueOf(args[2]).doubleValue());
    }
}
