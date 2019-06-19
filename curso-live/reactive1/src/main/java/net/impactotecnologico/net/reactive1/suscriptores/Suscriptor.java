package net.impactotecnologico.net.reactive1.suscriptores;

public class Suscriptor {

	public static void multiplicar(Integer n) {
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Subscriber 2" + n * n);
	}
}
