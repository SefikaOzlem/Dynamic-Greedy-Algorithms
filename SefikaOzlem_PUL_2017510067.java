
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class SefikaOzlem_PUL_2017510067 {
	static int x=5;   //‘x’ months
	static int c=6;   //number of investment companies
	static int p=6;  //employees to produce ‘p’ cars for each month
	static int B=100; // initial main money 
	static int t=2; //a taxes at a rate ‘t’ 
	static int d=6; // interns, paying ‘d’ TL per car 
	static Integer[] c1=new Integer[x+1];
	static Integer c2[]=new Integer[x+1];
	static Integer c3[]=new Integer[x+1];
	static Integer c4[]=new Integer[x+1];
	static Integer c5[]=new Integer[x+1];
	static Integer c6[]=new Integer[x+1];
	static Hashtable<Integer,Integer[]> cs=new Hashtable<Integer,Integer[]>();//It keeps the values ​​of the investment company and every investment company.
	static int m_demand[]=new int[x+1]; // Keeps the number of demands by month.
	static int sum_demand=0; 
	static int garage[]=new int[33+1]; // garage cost  
	static int array1[][]=new int[x+1][garage.length]; // Two dimensional array for Dp part1 
	static int arr[]=new int[x+1]; // one dimensional array for Greedy part1 
	static int array[][]=new int[x][c]; // // Two dimensional array for Dp part2 
	static int array2[]=new int[x]; // one dimensional array for Greedy part2
	static BufferedReader reader;
	public static void main(String[] args) throws IOException
	{
		readFileInvest();
		readFileMonthDemand();
		readFileGarage();
		//System.out.println("DP Results-Cost : "+ DP_PART1(p,d, x));
		//System.out.println("Greedy Result-Cost : "+GREEDY_PART1(p,d,x));
		//System.out.println("DP Results-Profit : "+	DP_PART2(B,t,x,c));
		//System.out.println("Greedy Results-Profit : "+GreedyPart2(B,t,x,c));
		System.out.println("DP Results : "+(DP_PART2(B,t,x,c)- DP_PART1(p,d, x)));
		System.out.println("Greedy Results : "+(GreedyPart2(B,t,x,c)-GREEDY_PART1(p,d,x)));
	}
	static void readFileInvest() throws IOException
	{
		reader=new BufferedReader(new FileReader("investment.txt"));
		String line;
		int a=0;
		while ((line = reader.readLine()) != null  && a!=x+1) 
		{
			if (line.isEmpty())
			{
				line=line.trim();
				continue;
			}
			String[] parts = line.split("	");
			try {
				for(int j=0;j<parts.length;j++)
				{
					if(parts[j].isEmpty())
					{
						parts[j]=parts[j].trim();
						continue;
					}
					if(a!=0)
					{
						c1[a]=Integer.parseInt(parts[1]);
						c2[a]=Integer.parseInt(parts[2]);
						c3[a]=Integer.parseInt(parts[3]);
						c4[a]=Integer.parseInt(parts[4]);
						c5[a]=Integer.parseInt(parts[5]);
						c6[a]=Integer.parseInt(parts[6]);	      
					}	
				}
			}catch(ArrayIndexOutOfBoundsException e)
			{
			}
			a++;
		}
		cs.put(1,c1);
		cs.put(2,c2);
		cs.put(3,c3);
		cs.put(4,c4);
		cs.put(5,c5);
		cs.put(6,c6);
		reader.close();	
	}
	static void readFileMonthDemand() throws IOException
	{
		reader=new BufferedReader(new FileReader("month_demand.txt"));
		String line;
		int a=0;
		while ((line = reader.readLine()) != null  && a!=x+1) 
		{
			if (line.isEmpty())
			{
				line=line.trim();
				continue;
			}
			String[] parts = line.split("\t");
			try {
				for(int j=0;j<parts.length;j++)
				{
					if(parts[j].isEmpty())
					{
						parts[j]=parts[j].trim();
						continue;
					}
					if(a!=0)
					{
						m_demand[a]=Integer.parseInt(parts[1]); 
					}	
				}
			}catch(ArrayIndexOutOfBoundsException e)
			{
			}
			a++;
		}	
		reader.close();
	}
	static void readFileGarage() throws IOException
	{
		for(int i=0;i<x+1;i++)
		{
			sum_demand=sum_demand+m_demand[i];
		}
		reader=new BufferedReader(new FileReader("garage_cost.txt"));
		String line;
		int a=0;
		while ((line = reader.readLine()) != null  && a!=sum_demand+1) 
		{
			if (line.isEmpty())
			{
				line=line.trim();
				continue;
			}
			String[] parts = line.split("\t");
			try {
				for(int j=0;j<parts.length;j++)
				{
					if(parts[j].isEmpty())
					{
						parts[j]=parts[j].trim();
						continue;
					}
					if(a!=0)
					{
						garage[a]=Integer.parseInt(parts[1]);    
					}	
				}
			}catch(ArrayIndexOutOfBoundsException e)
			{
			}
			a++;
		}	
		reader.close();
	}
	static int DP_PART1(int p, int d, int x)
	{
		for(int i=0;i<array1.length;i++)
		{
			for(int j=0;j<array1[0].length;j++)
			{ 
				if(i==0)
				{
					array1[i][j]=garage[j];//Garage costs were written on the first line of the two-dimensional array for the possibility of a car in the garage.
				}
				else if(i>0)
				{
					if(m_demand[i]>p) 
					{
						if(array1[i-1][m_demand[i]-p]!=0) // array[previous month][demand-production] 
						{
							if(((m_demand[i]-p)*d)>array1[i-1][m_demand[i]-p]) //(m_demand[i]-p)*d) pay for intern for requested car 
							{
								array1[i][j]=array1[i-1][m_demand[i]-p]+array1[i-1][j];
							}
							else if(((m_demand[i]-p)*d)<array1[i-1][m_demand[i]-p])
							{
								array1[i][j]=((m_demand[i]-p)*d)+array1[i-1][j];
							}
							else
							{
								array1[i][j]=array1[i-1][m_demand[i]-p]+array1[i-1][j];
							}
							break;
						}
						else if(array1[i-1][m_demand[i]-p]==0)
						{
							array1[i][j]=((m_demand[i]-p)*d)+array1[i-1][0];
							break;
						}
					}
					else if(p>m_demand[i])
					{   // possibilities to put the car in the garage                      
						for(int k=0;k<=p-m_demand[i];k++)
						{
							if(k==0)
							{
								array1[i][k]=array1[i-1][k];
							}
							else if(k>0) 
							{
								array1[i][k]=garage[k];
							}
						}
					}
					else if(p==m_demand[i])
					{
						array1[i][0]=array1[i-1][0];
					}
					break;
				}
			}
		}
		return array1[m_demand.length-1][0];
	}
	static int GREEDY_PART1(int p,int d,int x)
	{
		int temp=0; // keep to  garage cost for possibilities
		for(int i=0;i<arr.length;i++)
		{
			if(i>0)
			{
				if(m_demand[i]>p)
				{
					if(arr[i-1]==0)
					{
						if(garage[m_demand[i]-p]<(m_demand[i]-p)*d) // (m_demand[i]-p) difference of demand from production
						{
							arr[i]=garage[m_demand[i]-p];
						}
						else if(garage[m_demand[i]-p]>(m_demand[i]-p)*d)
						{
							arr[i]=(m_demand[i]-p)*d;
						}
					}
					else if(temp==0 && arr[i-1]!=0 && m_demand[i-1]<p && (p-m_demand[i-1])<(m_demand[i]-p))
					{
						arr[i]=arr[i-1]+(m_demand[i]-p)*d;
					}
					else if(temp==0 &&arr[i-1]!=0 && m_demand[i-1]<p)
					{
						arr[i]=arr[i-1];
					}
					else if(temp==0 && arr[i-1]!=0 && m_demand[i-1]>=p )
					{
						arr[i]=arr[i-1]+(m_demand[i]-p)*d;
					}
					else if(temp>0 &&m_demand[i]>=m_demand[i-1] &&m_demand[i]-p==p-m_demand[i-1]-(m_demand[i]-p)) 
					{/*If the difference between the production and the demand from the previous month and the difference between the demand from this month 
					and the production from this month is equal to the difference from the production from this month.*/
						arr[i]=arr[i-1]; temp=0;
					}
					else if(temp>0 &&m_demand[i]>=m_demand[i-1] &&(p-m_demand[i-1]-(m_demand[i]-p))==0)
					{
						/*If the result of the difference between the production and the demand from the previous month and the difference from the demand 
						  from this month from production is equal to zero.*/ 
						arr[i]=arr[i-1]; temp=0;
					}
					else if(temp>0 &&m_demand[i]>=m_demand[i-1] &&m_demand[i-1]-p==0)
					{
						arr[i]=arr[i-1]+(m_demand[i]-p)*d; temp=0;
					}
					else if(temp>0 && m_demand[i]<m_demand[i-1])
					{
						arr[i]=arr[i-1];
					}				
				}
				else if(p>m_demand[i])
				{
					if(i+1!=m_demand.length) {
						if(m_demand[i+1]>=p && (p-m_demand[i])>(m_demand[i+1]-p))
						{
							temp=garage[p-m_demand[i]-(m_demand[i+1]-p)]; /* garage[ p-m_demand[i]-(m_demand[i+1]-p)] If the demand from the next month is more 
							than production and the number of cars demanded this month is less than production, the garage cost according to the number of cars we can put into the garage.*/
							arr[i]=arr[i-1]+garage[p-m_demand[i]-(m_demand[i+1]-p)];
						}
						else if(m_demand[i+1]>=p && p-m_demand[i]==(m_demand[i+1]-p)) /* if the demand from the next month is more than the production and less than
							the production demanded this month, and the differences resulting from these two conditions are equal.*/
						{
							temp=garage[p-m_demand[i]];
							arr[i]=arr[i-1]+garage[p-m_demand[i]];
						}
						else if(m_demand[i+1]>=p && (p-m_demand[i])<(m_demand[i+1]-p)) /* if the demand from the next month is more than production and less than 
							the production demanded this month, and the difference between the next month and production is greater as a result of these two situations.*/
						{
							arr[i]=arr[i-1];
						}
						else if(m_demand[i+1]<p)
						{
							arr[i]=arr[i-1];
						}
					}
					if(i+1==m_demand.length)
					{
						arr[i]=arr[i-1];
					}
				}
				else if(p==m_demand[i])
				{
					arr[i]=arr[i-1];
				}
			}
		}
		return arr[m_demand.length-1];
	}
	static int DP_PART2(int B,int t,int x,int c)
	{ 
		int max=0;
		int res=0;
		for(int i=1;i<m_demand.length;i++)
		{
			res+=(B*m_demand[i])/2;// Half of the money we have is expected to be earned with the demand expected from that month.
			if(i==1)
			{
				for(int j=0;j<c;j++)
				{
					array[i-1][j]=res+(res*cs.get(j+1)[i])/100;//money from investment companies as a result of the first month
				}
			}
			else if(i>1)
			{
				for(int j=1;j<=c;j++) // j is temp to investment company
				{
					int temp=array[i-2][j-1]; //Money temp by the investment company where the previous month 
					for(int k=1;k<=c;k++) // k is temp to investment company
					{
						if(j==k) //if it is the same investment company
						{
							if(array[i-1][k-1]!=0 && array[i-1][k-1]<(res+temp)+(((res+temp)*cs.get(k)[i])/100))
							{
								array[i-1][k-1]=(res+temp)+(((res+temp)*cs.get(k)[i])/100);
							}
							else if (array[i-1][k-1]==0)
							{
								array[i-1][k-1]=(res+temp)+(((res+temp)*cs.get(k)[i])/100);
							}
						}
						else if (j!=k) // if it is the different investment company
						{
							temp=temp-(temp*t)/100;
							if(array[i-1][k-1]!=0 && array[i-1][k-1]<(res+temp)+(((res+temp)*cs.get(k)[i])/100))
							{
								array[i-1][k-1]=(res+temp)+(((res+temp)*cs.get(k)[i])/100);
							}
							else if (array[i-1][k-1]==0)
							{
								array[i-1][k-1]=(res+temp)+(((res+temp)*cs.get(k)[i])/100);
							}
							temp=array[i-2][j-1];
						}
					}
				}
			}
			res=(B*m_demand[i])/2;
			if(i==m_demand.length-1)
			{	
				for(int k=0;k<c;k++)
				{
					if(array[array.length-1][k]>max)
					{
						max=array[array.length-1][k];
					}
				}
			}

		}
		return (max+res);	
	}
	static int GreedyPart2(int B,int t,int x,int c)
	{
		int res=0;
		int max=0;
		int temp1=0; // it keeps the investment company selected as the maximum
		int temp2=0; // The variable used to compare the currently invested investment firm with the investment firm that is kept to a maximum.
		for(int i=1;i<m_demand.length;i++)
		{
			res+=(B*m_demand[i])/2; // Half of the money we have is expected to be earned with the demand expected from that month.
			for(int j=0;j<c;j++) // j is temp to investment company 
			{
				if(i==1)
				{
					if(max<(res+(res*cs.get(j+1)[i])/100))
					{
						max=(res+(res*cs.get(j+1)[i])/100);
						temp1=j+1;
					}
				}
				else if(i>1)
				{
					if(temp2!=j+1) //If the investment company currently looked at is not the same as the investment company of the previous month selected as maximum.
					{
						if(max< ((((res+(array2[i-2]-(array2[i-2]*t)/100))*cs.get(j+1)[i]))/100)+(res+(array2[i-2]-(array2[i-2]*t)/100))  )
						{
							max=((((res+(array2[i-2]-(array2[i-2]*t)/100))*cs.get(j+1)[i]))/100)+(res+(array2[i-2]-(array2[i-2]*t)/100));
							temp1=j+1;
						}
					}
					else if(temp2==j+1) //If the investment company currently looked at is the same as the investment company of the previous month selected as maximum.
					{
						if(max< (((res+array2[i-2])*cs.get(j+1)[i]))/100+(res+array2[i-2]))
						{
							max=((res+array2[i-2])*cs.get(j+1)[i])/100+(res+array2[i-2]);
							temp1=j+1;			
						}
					}	
				}	
			}
			array2[i-1]=max;
			temp2=temp1;
			res=(B*m_demand[i])/2;
			max=0;
		}
		return array2[x-1]+res;
	}

}
