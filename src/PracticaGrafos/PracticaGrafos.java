package PracticaGrafos;
import graphsDSESIUCLM.Vertex;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.util.Pair;
import Util.EDVertex;
import Util.graph;

 /*********************************************************************
 *
 * Class Name: PracticaGrafos 
 * Author/s name: Laura, Marta y Teresa
 * Release/Creation date: Diciembre 2018 
 * Class version: 1.0 
 * Class description: En esta clase se encuentra el método main en 
 * el cual cargamos el grafo, además de un switch que permite crear un menú 
 * con las diferentes funcionalidades de la práctica. Existen cuatro apartados:
 *
 * Apartado a) Nos mostrará el número de personajes, el número total de
 * relaciones entre personajes, el personaje con más relaciones y la pareja de
 * personajes con más relación entre ellos.
 *
 * Apartado b) Nos mostrará si el grafo es conexo o no.
 *
 * Apartado c) Nos permitirá enviar un mensaje entre dos personajes, en el caso
 * de que estos no tengan relación directa, se enviará a través de
 * intermediarios de confianza. Si no tienen relación, ni intermediarios de
 * confianza no se enviará.
 *
 * Terminar) Termina de ejecutar el programa.
 *
 * Utilizamos la excepcion IOException para evitar errores en la entrada y la
 * salida de datos.
 *
 **********************************************************************/
  
public class PracticaGrafos {
    private static final Scanner teclado = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        graph g = new graph ("stormofswords.csv");
        String opcion = "";
        Vertex<EDVertex<String>> v,u = null;  
       
        do {
            System.out.print("\033[30m1. Apartado a) Mostrar el número de personajes, el número total de relaciones\n" +
                "entre personajes, el personaje que tiene más relaciones con otros, y la pareja \n" +
                "de personajes que mantienen entre sí el mayor grado de relación." +
                "\n2. Apartado b) Comprobar si el grafo es conexo." +
                "\n3. Apartado c) Enviar un mensaje entre personajes." +
                "\n4. Terminar\n\nElige una opción: ");
            
            opcion = teclado.next();
            switch (opcion) {
                case "1":
                    System.out.println("\033[30mNúmero total de personajes: \033[34m" + g.cantidadVertices()); 
                    System.out.println("\033[30mNúmero total de relaciones: \033[34m" + g.cantidadAristas());
                    Pair<String,Integer> personaje = g.mayorNumeroRelaciones();
                    System.out.println("\033[30mMayor número de relaciones \033[34m" + personaje.getKey()
                    + "\033[30m con \033[34m" + personaje.getValue() + " relaciones");
                    personaje = g.mayorGradoenlaRelacion();
                    System.out.println("\033[30mMayor grado de relacion entre: \033[34m" + personaje.getKey() 
                    + "\033[30m con \033[34m" + personaje.getValue() + " de grado\n");        
                    break;
                    
                case "2":
                    g.restablecerGrafo();
                    System.out.println (g.esGrafoConexo()?"\033[32mNo existen conjuntos sin relación, "
                    + "sólo hay una componente conexa.\n":"\033[31mExiste más de una componente conexa.\n");
                    break;
                    
                case "3":
                    g.restablecerGrafo();
                    v = pedirPersonaje(g, "\033[30mIntroduce emisor del mensaje: ");
                    u = pedirPersonaje(g, "\033[30mIntroduce destinatario del mensaje: ");
                    Deque<String> mensajeCamino = new LinkedList<String>(g.mensajeBFS(v, u));
                    if (mensajeCamino.size()!=0) {
                        int i=0;            
                        for (String per: mensajeCamino) {                
                            if (i!=(mensajeCamino.size()-1))
                                System.out.print ("\033[31m"+per + "--> ");
                            else
                                System.out.println ("\033[31m"+per);
                            i++;
                        } 
                    }
                    else
                         System.out.println ("\033[31mNo hay camino para enviar el mensaje debido a "
                                 + "la falta de amigos con confianza.") ;
                    break;
                    
                case "4":
                    System.out.println("\033[31mPrograma cerrado.");
                    break;
                    
                default:
                    System.out.println("Opcion no válida. Sólo números del 1 al 4.");
                    break;
                    
            }           
        } while (!opcion.equals("4"));
    }    
    
    
    /*********************************************************************
     *
     * Method Name: pedirPersonaje
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método nos permite introducir los personajes
     * que serán el emisor y el receptor del mensaje, además controla
     * si el personaje existe o no.
     *
     ***********************************************************************/
  
    public static Vertex<EDVertex<String>> pedirPersonaje(graph g, String texto) {
        Vertex<EDVertex<String>> v = null;        
        do {
            System.out.print(texto);
            v = g.getPersonaje(teclado.next());
            if (v==null)
                System.out.println("Error. El personaje introducido no existe.");
        } while(v==null);
        
        return v;
    }
}


