public class EjerciciosSerie4 {

    public static void main(String[] args){
		int i;
		int a=0;
		int b=1;
		int c=1;
		int d;
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		for(i=1;i<=20;i++)
		{
			d= a+b+c;
			System.out.println(d);
			a=b;
			b=c;
			c=d;
		}
	}
	
}
