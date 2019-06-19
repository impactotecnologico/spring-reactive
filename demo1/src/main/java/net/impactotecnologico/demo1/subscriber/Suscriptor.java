package net.impactotecnologico.demo1.subscriber;
public class Suscriptor {

	public static void multiplicar(Integer n)  {
	    try {
	        Thread.sleep(1000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	    System.out.println("Subscriber2: "+n*n);
	}
}