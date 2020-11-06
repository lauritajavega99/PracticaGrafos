package Util;
import graphsDSESIUCLM.*;

/*********************************************************************
*
* Class Name: EDVertex
* Author/s name: Laura, Marta y Teresa
* Release/Creation date: Diciembre 2018
* Class version: 1.0
* Class description: En esta clase creamos el elemento decorado del vértice
* con los atributos necesarios para la práctica. Con el constructor y
* getters y setters.
*
**********************************************************************/

public class EDVertex<T> implements Element {    
    private String ID;                  
    private boolean visitado;         
    private EDVertex<T> padre;  

    public EDVertex(String Key) {
        this.ID = Key;   
        this.visitado= false;
        this.padre = null;        
    }   

    public String getID() {
        return ID;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public EDVertex<T> getPadre() {
        return padre;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public void setPadre(EDVertex<T> padre) {
        this.padre = padre;
    }  
}



    

