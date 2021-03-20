package edu.escuelaing.arep;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.secure;

public class App 
{
    public static void main( String[] args )
    {
    	secure("llaves/ecikeystore.p12", "123456", "llaves/myTrustStore", "654321");
    	port(getPort());
        get("/acceso", (req, res) -> "Hola, desde un servicio seguro");
    }
    
    public static int getPort()
    {
    	int puerto = 0;
    	
    	if(System.getenv("PORT") != null) puerto = Integer.parseInt(System.getenv("PORT"));
    	else puerto = 5001;
    	
    	return puerto;
    }
}