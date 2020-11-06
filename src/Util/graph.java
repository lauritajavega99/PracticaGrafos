package Util;
import graphsDSESIUCLM.Edge;
import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;
import graphsDSESIUCLM.Vertex;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.util.Pair;


/*********************************************************************
*
* Class Name: graph
* Author/s name: Laura, Marta y Teresa
* Release/Creation date: Diciembre 2018
* Class version: 1.0
* Class description: En esta clase se encuentran los métodos con los que 
* se trabajará para realizar la práctica y se crea un grafo g.
* Utilizamos la excepcion IOException para controlar los errores en la entrada
* y salida.
* 
***********************************************************************/

public class graph {
    private Graph g; 
    
    
    /*********************************************************************
     *
     * Method Name: graph
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método define el grafo g como 
     * TreeMapGraph y llama al método crearGrafo pasándole el archivo .csv 
     * para cargar los datos del fichero en éste.
     *
     **********************************************************************/
    
    public graph(String csvFile) throws IOException {
        g = new TreeMapGraph<>();
        crearGrafo(csvFile);
    }
    
    
    /*********************************************************************
     *
     * Method Name: cantidadVertices
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método permite devolver el número de 
     * vértices de un grafo.
     *
     *
     **********************************************************************/
    
    public int cantidadVertices () {
        return g.getN();
    }
    
    
    /*********************************************************************
     *
     * Method Name: cantidadAristas
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método permite devolver el número de 
     * aristas de un grafo.
     *
     **********************************************************************/
  
     public int cantidadAristas () {
        return g.getM();
    }    
     
     
     /*********************************************************************
     *
     * Method Name: crearGrafo
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: En este método leemos los datos del fichero .csv, 
     * el primer elemento que leemos [0] es un nombre por tanto creamos un
     * vértice de él, con el segundo elemento [1] pasa lo mismo, y el tercer
     * elemento [2] es el grado de relación, de éste creamos una arista.
     * "UUID.randomUUID()" genera keys (ids) únicos para no confundir
     * aristas que tengan el mismo peso (valor o grado de relación).
     *
     **********************************************************************/
  
    public void crearGrafo(String csvFile) throws IOException{        
        String lineatexto, valores[];     
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            bufferedReader.readLine();
            
            while ((lineatexto = bufferedReader.readLine()) != null) {
                valores = lineatexto.split(",");
                g.insertEdge(new EDVertex(valores[0]), new EDVertex(valores[1]),
                        new EDEdge(UUID.randomUUID().toString(),valores[2]));
            }
        }       
    }
    
    
    /*********************************************************************
     *
     * Method Name: mayorNumerorRelaciones
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método va tomando cada vértice (primer while)
     * y contando las aristas incidentes en él (segundo while), luego mediante
     * una comparación (if), se va quedando con el mayor número de relaciones.
     * La función "Pair" nos permite devolver dos valores simultáneamente,
     * entonces devolvemos el personaje con mayor número de relaciones
     * (aristas), y la cantidad de éstas.
     *
     **********************************************************************/
 
    public Pair<String,Integer> mayorNumeroRelaciones () {        
        int edges,relaciones=0;  
        String personaje="";
        Vertex<EDVertex<String>> vertex = null;        
        Iterator<EDEdge<String>> itEdges;
        Iterator<Vertex<EDVertex<String>>> it=  g.getVertices(); 
        
        while(it.hasNext()) {
            vertex=it.next();
            itEdges=g.incidentEdges(vertex); 
            edges=0;
            
            while (itEdges.hasNext()) {
                itEdges.next(); 
                edges++;
            }          
            
            if(edges>relaciones) {
                relaciones=edges;
                personaje=vertex.getElement().getID();
            }
        } 
        return new Pair(personaje,relaciones);         
    }
    
    
    /*********************************************************************
     *
     * Method Name: mayorGradoenlaRelacion
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método encuentra la arista con mayor peso,
     * va recorriendo todas a través del while y establece la comparación con
     * el if. Al final se obtiene la arista con mayor peso y los dos
     * vértices (personajes) que tiene y devolvemos los valores con la 
     * función "Pair" explicada en el método anterior.
     * "Integer.parseInt()" convierte un String a entero.
     *
     **********************************************************************/
  
    public Pair<String,Integer> mayorGradoenlaRelacion () {        
        Vertex<EDVertex<String>> [] personajes=null; 
        Edge<EDEdge<String>> e1,e2=null;
        Iterator <Edge<EDEdge<String>>> it=null;       
        int gradoRelacion=0;
        it=g.getEdges();
        while(it.hasNext()) {
            e1=it.next();            
            if (gradoRelacion<Integer.parseInt(e1.getElement().getElemento())) {
                gradoRelacion=Integer.parseInt(e1.getElement().getElemento());
                e2=e1;
            }        
        }
        personajes = g.endVertices(e2); 
        return new Pair(personajes[0].getID() + " <> " + personajes[1].getID(),gradoRelacion);           
    }
    
    
    /*********************************************************************
     *
     * Method Name: visitarporDFS
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: En este método se programa un recorrido DFS
     * (Depth First Search) que después se utilizará para encontrar componentes
     * conexas del grafo o no.
     *
     **********************************************************************/

  
    public void visitarporDFS(Vertex<EDVertex<String>> vcomienzo) {        
        Vertex<EDVertex<String>> v;
        Iterator<Edge<String>> it;        
        Edge<String> e;
        vcomienzo.getElement().setVisitado(true); // Poner vértice vcomienzo a "visitado".
        it = g.incidentEdges(vcomienzo); // Obtener todas las aristas incidentes en el vértice.
        while (it.hasNext()) { // Obtenemos todas y cada una de las itEdges (aristas incidentes en el vértice).
            e = it.next();
            v = g.opposite(vcomienzo, e); // Vértice a través de la arista.
            if (!v.getElement().isVisitado())// En caso de llegar a un vertice aún no visitado.
                visitarporDFS(v); // Llamar con este nuevo vértice al algoritmo para continuar recorriendo el grafo.
        }        
    }
    
    
    /*********************************************************************
     *
     * Method Name: esGrafoConexo
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método llama al método visitarporDFS, y pone
     * la variable booleana a "true" si todos los vértices se quedan a
     * "visitado", y la pondrá a "false" si queda alguno por visitar, que
     * sería la componente no conexa del grafo.
     *
     **********************************************************************/
     
    public boolean esGrafoConexo() { 
        boolean conexo=true;       
        Iterator<Vertex<EDVertex<String>>> it=g.getVertices();
        if (it.hasNext())
            visitarit.next().getElement().isporDFS(it.next());
        while(it.hasNext() && conexo)        
            conexo=it.next().getElement().isVisitado();       
        return conexo;
    }  
    
    
    /*********************************************************************
     *
     * Method Name: restablecerGrafo
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método pone todos los vértices del grafo
     * a "no visitado", es decir lo reinicia para poder trabajar con él sin
     * que haya errores de sobreescritura o de variables.
     *
     **********************************************************************/
  
    public void restablecerGrafo() {
        Iterator<Vertex<EDVertex<String>>> it;
        Vertex<EDVertex<String>> v;
        it = g.getVertices();
        while (it.hasNext()) {
                v = it.next();                                		
                v.getElement().setPadre(null);
                v.getElement().setVisitado(false);
        }
    }
    
    
    /*********************************************************************
     *
     * Method Name: mensajeBFS
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: En este método realizamos un recorrido BFS 
     * (Breadth First Search) para encontrar el camino más corto entre dos 
     * personajes teniendo en cuenta que el peso de la arista (grado de la
     * relación) tiene que ser mayor o igual a diez para asegurar que el
     * mensaje se envía a través de intermediarios de confianza. 
     *
     **********************************************************************/
  
    public Deque<String> mensajeBFS(Vertex<EDVertex<String>> origen, Vertex<EDVertex<String>> destino) {
        Deque<String> mensajeCamino = new LinkedList<String>();
        boolean encontrado=false;
        Queue<Vertex<EDVertex<String>>> q=new LinkedBlockingQueue();
        Vertex<EDVertex<String>> u,v=null;
        EDVertex<String> z;
        Edge<EDEdge<String>> e;
        Iterator<Edge<EDEdge<String>>> it;
        restablecerGrafo();
        origen.getElement().setVisitado(true); // Vértice origen a "visitado".
        q.offer(origen); // Añadir origen a la cola de vértices.
        while(!q.isEmpty() && !encontrado) { // Se sacan elemntos de la cola.
            u=q.remove();
            it=g.incidentEdges(u); // itEdges aristas incidentes en el vértice.
            while (it.hasNext() && !encontrado){ // Recorremos las aristas.
            e=it.next();
            v=g.opposite(u,e); // Vértice por la arista.
            
            // Se comprueba que no esté visitado y que el peso de la relacién sea mayor o igual a 10.
            if (!(v.getElement().isVisitado()) && Integer.parseInt(e.getElement().getElemento()) >= 10) { 
                v.getElement().setVisitado(true); // Se pone a visitado para no volver a él
                v.getElement().setPadre(u.getElement()); // y se enlaza con su vértice padre.
                q.offer(v); // Se inserta en la cola.
                if(v.getElement().equals(destino.getElement()))
                    encontrado=true;
                }
            }
        }
        if(encontrado) { // En el caso de encontrar el destino
            mensajeCamino.addFirst(v.getID()); // añadimos el destino a la lista del camino
            z=v.getElement().getPadre(); 
            while(z!=null) { // y recorremos los padres de los vértices para añadirlos a la lista del camino.
                mensajeCamino.addFirst(z.getID());
                z=z.getPadre();
            }                       
        }
        return mensajeCamino;
    }   
    
    
    /*********************************************************************
     *
     * Method Name: getPersonaje
     * Author/s Name: Laura, Marta y Teresa
     * Release/Creation date: Diciembre 2018
     * Method version: 1.0
     * Method description: Este método recibe el personaje introducido
     * por teclado en el apartado 3, y comprueba si existe en el grafo
     * recorriéndolo, si lo encuentra lo devuelve, si no, no devolverá nada
     * y el programa volverá a pedirlo.
     *
     **********************************************************************/

  
    public Vertex<EDVertex<String>> getPersonaje(String texto) {
        boolean encontrado=false;   
        Vertex<EDVertex<String>> v =null;		
        Iterator<Vertex<EDVertex<String>>> it;           
        it=g.getVertices();       
        while(it.hasNext() && !encontrado) {
            v=it.next();
            encontrado=(v.getID().toUpperCase().equals(texto.toUpperCase()));
        }
        return encontrado?v:null;
    }
}


