import java.util.*;
import java.io.*;
public class main
{
	class account
	{
		int id,pin,type,enable;
        double rest,pend,lim;
        account(int a,int b,int c,int d,double e,double f,double g)
        {
        	id=a;pin=b;type=c;enable=d;rest=e;pend=f;lim=g;
        }
	}
	Calendar c=Calendar.getInstance();
	int Year=c.get(Calendar.YEAR),Month=c.get(Calendar.MONTH)+1,Date=c.get(Calendar.DATE),next=0,num; 
	int day[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
	HashMap <String,account> list=new HashMap <String,account>();
	Scanner cin=new Scanner(System.in);
	File f=new File("data");
	void Init()
	{
		try
		{	
        	Scanner fin=new Scanner(new FileReader(f));
        	num=fin.nextInt();
        	System.out.printf("%d\n",num);
        	for (int i=1;i<=num;i++)
        	{
        		int id=fin.nextInt(),pin=fin.nextInt(),type=fin.nextInt(),enable=fin.nextInt();
        		double rest=fin.nextDouble(),pend=fin.nextDouble(),lim=fin.nextDouble();
        		list.put(String.valueOf(id),new account(id,pin,type,enable,rest,pend,lim));
        		if (id>i&&next==0)
        			next=i;
        	}
        	fin.close();
        	if (next==0)
        		next=num+1;
        	System.out.printf("%d\n",next);
        	Iterator it=list.keySet().iterator();
        	while (it.hasNext())
        	{
        		String key=it.next().toString();
        		account a=list.get(key);
        		System.out.println(key+" "+a.id+" "+a.pin+" "+a.type+" "+a.enable+" "+a.rest+" "+a.pend+" "+a.lim);
        	}//list.remove("key");
    	}
    	catch(Exception e){}
	}
	void Save()
	{
		try
        {
        	PrintWriter out=new PrintWriter(new FileWriter(f));
        	out.println(num);
        	Iterator it=list.keySet().iterator();
        	while (it.hasNext())
        	{
        		String key=it.next().toString();
        		account a=list.get(key);
        		out.println(a.id+" "+a.pin+" "+a.type+" "+a.enable+" "+a.rest+" "+a.pend+" "+a.lim);
        	}
        	out.close();
        }
        catch(Exception e){}
	}
	void SetMenu()
	{
		System.out.println("Welcome to our bank!"+"\n"+
						   "Please choose the survice you need:"+"\n"+
						   "1.Open Account"+"\n"+
						   "2.Deposit Funds"+"\n"+
						   "3.Clear Funds"+"\n"+
						   "4.Withdraw Funds"+"\n"+
						   "5.Suspend Account"+"\n"+
						   "6.Close Account"+"\n"+
						   "Please input an integer to choose.");
	}
	int ReadInt(int l,int r)
	{
		String s=cin.nextLine();
		if (s.length()!=1)
			return -1;
	    if (s.charAt(0)>='0'+l&&s.charAt(0)<='0'+r)
	    	return s.charAt(0)-'0';
	    return -1;
	}
	void Wrong()
	{
		System.out.println("Invalid input!");
	}
	void GetYear()
	{
		System.out.println("Please input your the year of your birth:");
	}
	int ReadYear()
	{
		int ans=0;
		String s=cin.nextLine();
		if (s.length()!=4)
			return -1;
		for (int i=0;i<=3;i++)
	    {
	    	if (s.charAt(i)<'0'||s.charAt(i)>'9')
	    		return -1;
	    	ans*=10;
	    	ans+=s.charAt(i)-'0';
	    }
	    return ans<=Year?ans:-1;
	}
	void GetMonth()
	{
		System.out.println("Please input your the month of your birth:");
	}
	int Read2(int lim)
	{
		int ans=0;
		String s=cin.nextLine();
		if (s.length()!=1&&s.length()!=2)
			return -1;
		for (int i=0;i<s.length();i++)
	    {
	    	if (s.charAt(i)<'0'||s.charAt(i)>'9')
	    		return -1;
	    	ans*=10;
	    	ans+=s.charAt(i)-'0';
	    }
	    return ans<=lim?ans:-1;
	}
	void GetDate()
	{
		System.out.println("Please input your the date of your birth:");
	}
	int run(int year)
	{
		if (year%400==0||(year%100!=0&&year%4==0))
			return 0;
		return 1;
	}
	void GetType()
	{
		System.out.println("Please input your the type of your account:"+"\n"+
						   "1.Saver account"+"\n"+
						   "2.Junior account"+"\n"+
						   "3.Current account");
	}
	int judge(String a,String b)
	{
		int sum=0;
		for (int i=0;i<a.length();i++)
			sum+=a.charAt(i);
		for (int i=0;i<b.length();i++)
			sum+=b.charAt(i);
		return sum;
	}
	void Open()
	{
		System.out.println("Please input your name:");
		String name=cin.nextLine();
        System.out.println("Please input your address:");
		String address=cin.nextLine();
		GetYear();
        int year=-1,month=-1,date=-1,t=-1;
		for (;;Wrong(),GetYear())
        {
        	year=ReadYear();
        	if (year!=-1)
        		break;
        }
        GetMonth();
        for (;;Wrong(),GetMonth())
        {
        	month=Read2(12);
        	if (year==Year&&month>Month)
        		month=-1;
        	if (month!=-1)
        		break;
        }
        GetDate();
        for (;;Wrong(),GetDate())
        {
        	date=Read2(31);
        	if (year==Year&&month==Month&&date>Date)
        		date=-1;
        	day[2]=run(year)==1?28:29;
        	if (date>day[month])
        		date=-1;
        	if (date!=-1)
        		break;
        }
        int age=Year-year;
        System.out.println(Year+" "+Month+" "+Date);
        System.out.println(year+" "+month+" "+date);
        GetType();
        for (;;Wrong(),GetType())
		{
			t=ReadInt(1,3);
			if (age<16&&t!=2)
				t=-1;
			if (t!=-1)
				break;
		}
		if (!(judge(name,address)%2==0&&t==3))
		{
			int id=next,pin=(int)(Math.random()*(1<<30)),type=t,enable=1;
        	double rest=0,pend=0,lim=0;
        	if (type==3)
        		lim=judge(name,address)%5000;
        	num++;
        	list.put(String.valueOf(id),new account(id,pin,type,enable,rest,pend,lim));
        	System.out.println("You have successfully opened an account!"+"\n"+
        					   "Your account number is "+id+"."+"\n"+
        					   "Your personal identification number id is "+pin+".");
        	Save();
		}
		else
		{
			System.out.println("Operation Failed! According to the credit searth, you are not allowed to open a new account!");
		}
	}
	void GetAccount()
	{
		System.out.println("Please input the account number:");
	}
	int ReadAccount()
	{
		int ans=0;
		String s=cin.nextLine();
		if (s.length()>9)
			return -1;
		for (int i=0;i<s.length();i++)
	    {
	    	if (s.charAt(i)<'0'||s.charAt(i)>'9')
	    		return -1;
	    	ans*=10;
	    	ans+=s.charAt(i)-'0';
	    }
	    return ans;
	}
	account IsAccount()
	{
		int id=-1;
		GetAccount();
		for (;;Wrong(),GetAccount())
		{
			id=ReadAccount();
			if (id!=-1)
				break;
		}
		account a=list.get(String.valueOf(id));
		if (a==null)
		{
			System.out.println("Operation Failed! The account number doses not exist!");
			return null;
		}
		if (a.enable==0)
		{
			System.out.println("Operation Failed! The account is suspended!");
			return null;
		}
		return a;
	}
	void GetKind()
	{
		System.out.println("Please input your the kind of your deposit:"+"\n"+
						   "1.Cleared"+"\n"+
						   "2.Un-cleared");
	}
	void GetMoney()
	{
		System.out.println("Please input the amount of money:");
	}
	double Readdouble()
	{
		double ans=0,t=1;
		char[] s=cin.nextLine().toCharArray();
		int l=s.length;
		if (l>=3)
		{	
			if (s[l-2]=='.')
			{
				t=10;
				s[l-2]=s[l-1];
				l--;
			}
			else if (s[l-3]=='.')
			{
				t=100;
				s[l-3]=s[l-2];
				s[l-2]=s[l-1];
				l--;
			}
		}
		for (int i=0;i<l;i++)
	    {
	    	if (s[i]<'0'||s[i]>'9')
	    		return -1;
	    	ans*=10;
	    	ans+=s[i]-'0';
	    }
	    return ans/t;
	}
	double IsMoney()
	{
		double money=-1;
		GetMoney();
        for (;;Wrong(),GetMoney())
		{
			money=Readdouble();
			if (money!=-1)
				break;
		}
		return money;
	}
	void Deposit()
	{
		account a=IsAccount();
		if (a==null)
			return;
		int type=-1;
		GetKind();
        for (;;Wrong(),GetKind())
		{
			type=ReadInt(1,2);
			if (type!=-1)
				break;
		}
		double money=IsMoney();
		if (type==1)
			list.put(String.valueOf(a.id),new account(a.id,a.pin,a.type,a.enable,a.rest+money,a.pend,a.lim));
		else
			list.put(String.valueOf(a.id),new account(a.id,a.pin,a.type,a.enable,a.rest,a.pend+money,a.lim));
		Save();
		System.out.println(money);
	}
	void Clear()
	{
		account a=IsAccount();
		if (a==null)
			return;
		list.put(String.valueOf(a.id),new account(a.id,a.pin,a.type,a.enable,a.rest+a.pend,0,a.lim));
		Save();
	}
	void GetPin()
	{
		System.out.println("Please input your personal identification number:");
	}
	void Withdraw()
	{
		account a=IsAccount();
		if (a==null)
			return;
		GetPin();
		int i,id;
		for (i=1;i<=3;i++)
		{
			if (ReadAccount()==a.pin)
				break;
			Wrong();
			GetPin();
		}
		if (i==4)
		{
			System.out.println("More than 3 PIN attempts! Your account is suspended!");
			list.put(String.valueOf(a.id),new account(a.id,a.pin,a.type,0,a.rest,a.pend,a.lim));
			Save();
			return;
		}
		double money=IsMoney();
		if (((a.type==1||a.type==2)&&a.rest<money)||(a.type==3&&(a.rest+a.lim)<money))
		{
			System.out.println("Sorry, your credit is running low! Please deposit funds or Clear funds!");
			return;
		}
		list.put(String.valueOf(a.id),new account(a.id,a.pin,a.type,a.enable,a.rest-money,a.pend,a.lim));
		Save();
	}
	void GetSur()
	{
		System.out.println("Please choose the survice:"+"\n"+
						   "1.Suspend account"+"\n"+
						   "2.Enable account");
	}
	void Suspend()
	{
		account a=IsAccount();
		if (a==null)
			return;
		GetPin();
		for (;;Wrong(),GetPin())
		{
			if (ReadAccount()==a.pin)
				break;
		}
        GetSur();
        int type=-1;
        for (;;Wrong(),GetSur())
		{
			type=ReadInt(1,2);
			if (type!=-1)
				break;
		}
		list.put(String.valueOf(a.id),new account(a.id,a.pin,a.type,type-1,a.rest,a.pend,a.lim));
		Save();
	}
	void Close()
	{
		account a=IsAccount();
		if (a==null)
			return;
		GetPin();
		for (;;Wrong(),GetPin())
		{
			if (ReadAccount()==a.pin)
				break;
		}
		list.remove(String.valueOf(a.id));
		Save();
	}
	main()
	{
		Init();
		SetMenu();
		int t=-1;
		for (;;Wrong(),SetMenu())
		{
			t=ReadInt(1,6);
			if (t!=-1)
				break;
		}
		if (t==1)
			Open();
		else if (t==2)
			Deposit();
		else if (t==3)
			Clear();
		else if (t==4)
			Withdraw();
		else if (t==5)
			Suspend();
		else
			Close();
	}
	public static void main(String[] args)
	{
		main t=new main();
	}
}