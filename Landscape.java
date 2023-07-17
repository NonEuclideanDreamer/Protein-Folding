//*****************************************************************
// Project: ProteinFolding
// Class: Amino
// Author: Non-Euclidean Dreamer
// Sets the scene
//*****************************************************************

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Landscape 
{
	static String name="tline",
			type="png",
			mode="line";
	static int l=120, maxit=3000,speed=12, zoosize=10,period=20;
	static double blur=0.0;
	static int[]width= {1080,1080,1080};
	static double[]loc= {540,540,200},v= {0,0,0};
	static Color background=Color.black;
	static DecimalFormat df=new DecimalFormat("0000");
	
	public static void main(String[] args)
	{
		Protein protein=new Protein();
		Random rand=new Random();
		BufferedImage canvas=setUpCanvas();
		double[][] zBuffer=zBuffer();
	int r,k=0;
		Amino.aminoZoo(zoosize);
		print(Amino.zoo);
		int[]order=randomorder();
		
/*	for(int i=0;i<l;i++)
		{
			double[]loc=new double[Amino.dim];
			for(int j=0;j<Amino.dim;j++)
			loc[j]=rand.nextInt(width[j]);
			
			protein.add(new Amino(r,loc));
		}*/
		if(mode.equals("line")||mode.equals("wave")) {loc=new double[] {20,width[1]/2,width[2]/4};v[0]=(width[0]-40)/(l-1);}
		int t1=0;
	r=0;
		for(int t=0;t<maxit;t++)
		{
			System.out.println(t);
			if(t%speed==0)
			if(t<speed*l) 
			{
			//loc=circle(t);
			//r=0;if(/*rand.nextDouble()<0.1*/(t/speed)%10==0)r=2;
			//if((t/speed)%30==0)r=1;
			//	r=rand.nextInt(5);
				System.out.print(".");
		if(mode.equals("random")){		for(int j=0;j<Amino.dim;j++)
					loc[j]=rand.nextInt(width[j]);}
		System.out.print(".");
			protein.add(new Amino(random(loc,rand),order[t1%period]));//random(loc,rand)));
			if(mode.equals("circle"))
			{
				v[1]=30*Math.cos(t1/10.0);v[2]=30*Math.sin(t1/10.0);v[0]=v[2];
			}
			else if(mode.equals("wave"))
			{
				v[1]=20*Math.cos(t1/10.0);
			}
			if(!mode.equals("random"))	move();
		System.out.print(".");
			t1++;
			//if(t1%36==0) {loc[0]+=100;v[1]*=-1;}
			System.out.print(".");
			}
			System.out.print(",");
			protein.draw(canvas,zBuffer);
			System.out.print(".");
			print(canvas,t);
			System.out.print(".");
			blur(canvas,zBuffer);
			System.out.print(".");
			protein.move();
			System.out.print(".");
		}
			
	}
	private static int[] randomorder() 
	{
		Random rand=new Random();
		int done=0,k=0;
		int[]out=new int[period];
		for(int i=0;i<period;i++)
		{
			if(period-i==zoosize-done||rand.nextInt(zoosize)>=done)
			{	
				out[i]=done;
				done++;
			}
			else out[i]=rand.nextInt(done);
		}
		print(out);
		return out;
	}
	private static double[] random(double[] loc2,Random rand )
	{
		double[] out=loc2.clone();
		for(int i=0;i<loc2.length;i++)
			out[i]+=rand.nextDouble()-0.5;
		return out;
	}
	private static double[][] zBuffer() 
	{
		double[][]out=new double[width[0]][width[1]];
		for(int i=0;i<width[0];i++)
			for(int j=0;j<width[1];j++)
				out[i][j]=width[2];
		return out;
	}
	private static void move() 
	{
		for(int i=0;i<Amino.dim;i++)
			loc[i]+=v[i];
	}
	private static void print(BufferedImage canvas, int t) 
	{
		File file=new File(name+df.format(t)+"."+type);
		try
		{
			ImageIO.write(canvas, type, file);
		}
		catch(IOException e)
		{
			System.out.println("Couldn't print File "+t);
		}
	}
	private static BufferedImage setUpCanvas() 
	{
		BufferedImage canvas=new BufferedImage(width[0],width[1],BufferedImage.TYPE_4BYTE_ABGR);
		for(int i=0;i<width[0];i++)
			for(int j=0;j<width[1];j++)
		{
			canvas.setRGB(i, j, background.getRGB());
		}
		return canvas;
	}
	private static void blur(BufferedImage canvas,double[][]zBuffer)
	{
		for(int i=0;i<width[0];i++)
			for(int j=0;j<width[1];j++)
		{
			canvas.setRGB(i, j, blur(new Color(canvas.getRGB(i, j)),background,blur));
			zBuffer[i][j]=width[2];
		}
	}
	
	static int blur(Color c1,Color c2, double factor)
	{
		return new Color((int)(c1.getRed()*factor+c2.getRed()*(1-factor)),
				(int)(c1.getGreen()*factor+c2.getGreen()*(1-factor)),
				(int)(c1.getBlue()*factor+c2.getBlue()*(1-factor))).getRGB();
	}
	static double[] circle(int t)
	{
		double[]out =new double[Amino.dim];
		out[0]=(0.9*Math.sin(t*2*Math.PI/l)+1)*width[0]/2;
		out[1]=(0.9*Math.cos(t*2*Math.PI/l)+1)*width[1]/2;
		out[2]=width[2]/2;
		return out;
	}
	static void print(double[]d)
	{
		System.out.print("{");
		for(int i=0;i<d.length;i++)
		{
			System.out.print(d[i]+", ");
		}
		System.out.println("}");
	}
	static void print(int[]d)
	{
		System.out.print("{");
		for(int i=0;i<d.length;i++)
		{
			System.out.print(d[i]+", ");
		}
		System.out.println("}");
	}
	static void print(double[][]d)
	{
		System.out.print("{");
		for(int i=0;i<d.length;i++)
		{
			print(d[i]); System.out.print(", ");
		}
		System.out.println("}");	
	}
}
