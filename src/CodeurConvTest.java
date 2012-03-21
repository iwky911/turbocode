

import junit.framework.Assert;
import junit.framework.TestCase;


public class CodeurConvTest extends TestCase{
	public void testFonction(){
		CodeurConv c = new CodeurConv(1,2,4, Cst.G1, 8, 5);
		int etat=9;
		int ok0=4, ok1=12, ko=7;
		Assert.assertEquals(1, c.fonctionu(etat, ok0));
		Assert.assertEquals(0, c.fonctionu(etat, ok1));
		Assert.assertEquals(Integer.MAX_VALUE, c.fonctionu(etat, ko));
		Assert.assertEquals(1,c.fonctionr(etat, ok0));
		
		int[] f= {0,0,1,2,3,0,4,5};
		int[] b= {0,2,4,9,3,2,4,8,4,6,5,8,9,7,5,8};
		int[] e={0,0};
		int j=etat, u=1, r=0;
		
		int minfRes = Math.min(f[2]+e[c.fonctionu(2,j)]+c.dirac(u, 1-c.fonctionu(2,j))+c.dirac(r, 1-c.fonctionr(2,j)),
				f[3]+e[c.fonctionu(3, j)]+c.dirac(u, 1-c.fonctionu(3, j))+c.dirac(r, 1-c.fonctionr(3, j)));
		
		Assert.assertEquals(minfRes, c.minf(f,e,j,u,r));
		
		int minbRes = Math.min(b[4]+ e[c.fonctionu(j, 4)]+c.dirac(u, 1-c.fonctionu(j, 4))+c.dirac(r, 1-c.fonctionr(j, 4)),
				b[12]+e[c.fonctionu(j, 12)]+c.dirac(u, 1-c.fonctionu(j, 12))+c.dirac(r, 1-c.fonctionr(j, 12)));
		
		Assert.assertEquals(minbRes, c.minb(b, e, u, r, j));
	}
}
