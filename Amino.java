
//*****************************************************************
// Project: ProteinFolding
// Class: Amino
// Author: Non-Euclidean Dreamer
// Objects model amino acids as the building block of the protein
//*****************************************************************
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;
public class Amino 
{
	public static Color[]colors;
	public static double[][]zoo;
	public static int dim=3;
	public static double slowdown=0.003,
			dt=0.1,
			f=0.3;//coil constant(maybe not constant?)
	public static double[] fc= {10,10};
	public double start, end, r,s;//s:radius for force
	public double[]loc,or ,v,charge;//charge 0:opposing attract. charge1: opposing repell
	public Color color;

	public Amino(double[]lo,int n)
	{
		loc=lo;
		v=new double[dim];	
		r=zoo[n][0];
		start=zoo[n][1];
		end=zoo[n][2];
		
		s=zoo[n][3];
			
		color=colors[n];
		charge=new double[] {zoo[n][4],zoo[n][5]};
		
	}
	
	public Amino(int n, double[]lo)
	{
		loc=lo;
		v=new double[dim];
		if(n==0)
		{
			start=15;
			end=13;
			r=13;
			s=14;
			
			color=Color.white;
			charge=new double[] {0,0};
		}
		else if(n==1)
		{
			s=20;
			start=20;
			end=20;
			r=20;
			
			color=Color.red;
			charge=new double[] {10,0};
		}
		else if(n==2)
		{
			s=8;
			start=20;
			end=19;
			r=8;
		
			color=Color.blue;
			charge=new double[] {0,10};
		}
		else if(n==3)
		{
			s=18;
			start=7;;
			end=9;
			r=2;
			color=Color.green;
			charge=new double[] {-10,0};
		}
		else if(n==4)
		{
			s=19;
			start=18;
			end=20;
			r=18;
			color=Color.yellow;
			charge=new double[] {0,-10};
		}
	}
	
	public double[] coil(Amino acid, double between)
	{
		double[] a=new double[dim];
		double d=distance(acid),
				d0=start+acid.end+between;
		
		for(int i=0;i<dim;i++)
		{
			a[i]=(acid.loc[i]-loc[i])*f*(d-d0)/d;
		}
		//System.out.println("coil a=("+a[0]+","+a[1]+","+a[2]+")");
		return a;
	}
	
	public double distance(Amino acid)
	{
		double out=0;
		
		for(int i=0;i<dim;i++)
		{
			out+=Math.pow(loc[i]-acid.loc[i], 2);
		}
		
		return Math.sqrt(out);
	}

	public void dampen() 
	{
		double speed=norm(v);
		for(int i=0;i<dim;i++)
		{
			v[i]*=(1-slowdown*speed);
		}
	}
	
	private double norm(double[] v2) 
	{
		double out=0;
		for(int i=0;i<dim;i++)
		{
			out+=Math.pow(v2[i],2);
		}
		return Math.sqrt(out);
	}

	//apply acceleration to velocity
	public void acc(double[] a, double sign)
	{
		for(int i=0;i<dim;i++)
		{
			v[i]+=a[i]*dt*sign;
		//	System.out.println("v"+i+"="+v[i]);
		}
	}
	
	//apply velocity to location

public static void aminoZoo(int n)
{
	double x;
	zoo=new double[n][6];
	colors=new Color[n];
	Random rand=new Random();
	int[]c=new int[3];
	for(int i=0;i<n;i++)
	{
		
		int j=rand.nextInt(3);
		c[j]=255*rand.nextInt(1);
		c[(j+1)%3]=rand.nextInt(256);
		c[(j+2)%3]=rand.nextInt(256);
		colors[i]=new Color(c[0],c[1],c[2]);
		
		x=rand.nextDouble(20);
		zoo[i][0]=x;
		zoo[i][1]=x+rand.nextDouble(20-x);
		zoo[i][2]=x+rand.nextDouble(20-x);
		zoo[i][3]=x+rand.nextDouble(20-x);
		zoo[i][4]=rand.nextGaussian(0, 5);
		zoo[i][5]=rand.nextGaussian(2,5);
	}
}
public static void loadAminoZoo()
{
	
}

public void moveOn()
	{
		for(int i=0;i<dim;i++)
		{
			loc[i]+=v[i]*dt;
		}
	}

public void draw(BufferedImage canvas, double[][] zBuffer) 
{
//	System.out.print("r="+r);
	for(int i=(int) (loc[0]-r);i<loc[0]+r;i++)
	{
	//	System.out.print(i+",");
		double s=Math.sqrt(r*r-Math.pow(loc[0]-i, 2));
		//System.out.println("s="+s+", loc=("+loc[0]+","+loc[1]+")");
		for(int j=(int)(loc[1]-s);j<loc[1]+s;j++)
		try {	
			if(dim<3)canvas.setRGB(i, j, color.getRGB());
			else 
			{
				double x=loc[2]-Math.sqrt(r*r-Math.pow(loc[0]-i, 2)-Math.pow(loc[1]-j,2));
	
				if(x>0&&x<zBuffer[i][j])
				{
					canvas.setRGB(i, j, Landscape.blur(Landscape.background,color,x/Landscape.width[2]));
					zBuffer[i][j]=x;
				}
			}
				}
		catch(ArrayIndexOutOfBoundsException e) {}
	}
	//System.out.print("-");
}

public double[] force(Amino amino) 
{
	double d=distance(amino),d0=s+amino.s,factor=0;if(d>d0)factor=(charge[0]*amino.charge[0]*fc[0]-charge[1]*amino.charge[1]*fc[1])/(Math.pow(d, 3));
	if(d<r+amino.r)factor=-20/d/d*(r+amino.r);
	//	System.out.println(d+","+d0+","+factor);
	double[]out=new double[dim];
	for(int i=0;i<dim;i++)
	{
		out[i]=(amino.loc[i]-loc[i])*factor;

//		System.out.println(out[i]);
	}
	//if(out[1]!=0)System.out.println("force a=("+out[0]+","+out[1]+","+out[2]+")");

	return out;
}

public static double[][] zoo1= {{10,11,11,20,1,1},
								{10,11,11,20,1,10},};

}
