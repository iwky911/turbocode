

public class Help {
	static void printTab(int[] t ){
		for(int i: t){
			System.out.print(i+" ");
		}
		System.out.println("");
	}
	static void printTab(int[][] t){
		for(int j=0; j<t[0].length; j++){
			for(int i=0; i<t.length; i++){
				System.out.print(t[i][j]+" ");
			}
			System.out.println("");
		}
	}
}
