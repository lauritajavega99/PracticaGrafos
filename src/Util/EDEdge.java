package Util;
import graphsDSESIUCLM.*;
import java.util.Objects;

/*********************************************************************
*
* Class Name: EDEgde
* Author/s name: Laura, Marta y Teresa.
* Release/Creation date: Diciembre 2018
* Class version: 1.0
* Class description: En esta clase creamos el elemento decorado de la arista 
* que vamos a utilizar en la practica con sus correspondientes atributos. 
* También consta del constructor y getters y setters.
*
***********************************************************************/

public class EDEdge<T> implements Element {    
    private String ID;
    private T elemento;     
    
    public EDEdge(String Key, T elemento) {
        this.ID = Key;
        this.elemento = elemento;    
    }   

    public String getID() {
        return ID;
    }

    public T getElemento() {
        return elemento;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setElemento(T elemento) {
        this.elemento = elemento;
    }
}
