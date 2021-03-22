package edu.escuelaing.arep;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.secure;
import static spark.Spark.staticFileLocation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import spark.staticfiles.StaticFilesConfiguration;

public class App 
{
    public static void main( String[] args ) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException
    {
    	SecureURLReader.ssl();
    	Gson gson = new Gson();
    	Map<String, String> usuarios = new HashMap<>();
    	usuarios.put("carlos@gmail.com", Hashing.sha256().hashString("hola12", StandardCharsets.UTF_8).toString());
    	staticFileLocation("/static");
    	secure("keystores/ecikeystore.p12", "123456", null, null);
    	port(getPort());
        get("/helloservice", (req, res) -> "Hola, desde un login seguro");
        get("/", (req, res) -> {res.redirect("a.html"); return "";});
        post("/login", (req, res) -> {
        	req.body();
        	req.session(true);
        	Usuario usuario = gson.fromJson(req.body(), Usuario.class);
        	String respuesta = "";
        	if(Hashing.sha256().hashString(usuario.getContrasena(), StandardCharsets.UTF_8).toString().equals(usuarios.get(usuario.getCorreo())))
        	{
        		System.out.println("Llego");
        		System.out.println(usuario.getContrasena());
        		req.session().attribute("User", usuario.getCorreo());
        		req.session().attribute("Loged", true);
        	}
        	else
        	{
        		respuesta = "Los datos ingresados son incorrectos";
        	}
        	return respuesta;
        });
        get("/servicio", (req, res) -> {
        	String respuesta = SecureURLReader.readURL("https://localhost:5001/servicio");
        	System.out.println(respuesta);
        	return respuesta;
        });
    }
    
    public static int getPort()
    {
    	int puerto = 0;
    	
    	if(System.getenv("PORT") != null) puerto = Integer.parseInt(System.getenv("PORT"));
    	else puerto = 5000;
    	System.out.println("Listo para recibir...\nPuerto "+puerto);
    	return puerto;
    }
}
