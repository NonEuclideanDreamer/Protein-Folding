//*****************************************************************
// Project: ProteinFolding
// Class: Protein
// Author: Non-Euclidean Dreamer
// Models a protein as a sequence of Amino Acids
//*****************************************************************

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Protein 
{
	public ArrayList<Amino>amino;
	
	
	public Protein()
	{
		amino=new ArrayList<>();
	}
	public void add(Amino acid)
	{
		amino.add(0, acid);
	}
	public void move()
	{
		Amino[] acid=new Amino[2];
		int l=amino.size(), k=0;
		double[]a= {0,0,0};
		//double[][]b= {{0,0,0},{0,0,0}};
		acid[0]=amino.get(0);
		for(int i=0;i<l-1;i++)
		{	
			acid[1-k]=amino.get(i+1);
			//dampening
			acid[k].dampen();
			
			//coil force trying to straighten things out
			acid[k].acc(a,1);
		//	acid[k].acc(b[k], 1);
			a=acid[1-k].coil(acid[k],0);
		/*	if(i<l-2)
			{
			b[k]=amino.get(i+2).coil(acid[k], acid[1-k].end+acid[1-k].start);
			acid[k].acc(b[k],-1);
			}*/
			//System.out.println("a=("+a[0]+","+a[1]);
			acid[k].acc(a, -1);
			k=1-k;
		}
			acid[k].dampen();
			acid[k].acc(a, 1);
			//acid[k].acc(b[k], 1);
			
		for(int i=0;i<l-1;i++)
		{
			acid[0]=amino.get(i);
			for(int j=i+1;j<l;j++)
			{
				a=acid[0].force(amino.get(j));
				acid[0].acc(a, 1);
				amino.get(j).acc(a, -1);
			}
		}
			
			for(int i=0;i<l;i++)
			{
				amino.get(i).moveOn();
			}
	}
	public void draw(BufferedImage canvas, double[][] zBuffer) 
	{
		for(Amino acid: amino)
		{
			//System.out.print("a");
			acid.draw(canvas,zBuffer);
		}
	}
}
