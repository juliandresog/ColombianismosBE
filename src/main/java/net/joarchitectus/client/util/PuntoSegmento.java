/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.joarchitectus.client.util;

/**
 * Distancia entre un punto y un segmento del plano cartesiano.
 * @author josorio
 */
public class PuntoSegmento {
    private NSPoint A;
    private NSPoint B;

    public  PuntoSegmento(){
        A = new NSPoint();
        B = new NSPoint();
    }

    public NSPoint getA() {
        return A;
    }

    public void setA(NSPoint A) {
        this.A = A;
    }

    public NSPoint getB() {
        return B;
    }

    public void setB(NSPoint B) {
        this.B = B;
    }


    // Cuadrado de la distancia
    double modulo2(NSPoint puntoA, NSPoint puntoB) {
        return (puntoB.getX() - puntoA.getX()) * (puntoB.getX() - puntoA.getX())
                + (puntoB.getY() - puntoA.getY()) * (puntoB.getY() - puntoA.getY());
    }

    // Distancia punto a punto en 2 dimensiones
    double distancia(NSPoint puntoA, NSPoint puntoB) {
        return Math.sqrt(modulo2(puntoA, puntoB));
    }

    // Longitud del vector
    double longitud() {
        return Math.sqrt(modulo2(A, B));
    }

    // Distancia del segmento al punto C
    double distanciaPunto(NSPoint C) {
        // Punto en el segmento al cual se calculará la distancia
        // iniciamos en uno de los extremos
        NSPoint P = A;

        // Para prevenir una división por cero se calcula primero el demoninador de
        // la división. (Se puede dar si A y B son el mismo punto).
        // Podría substituirse por [self modulo2:A a:B]
        double denominador = (B.getX() - A.getX()) * (B.getX() - A.getX()) + (B.getY() - A.getY()) * (B.getY() - A.getY());
        if (denominador != 0) {
            // Se calcula el parámetro, que indica la posición del punto P en la recta
            // del segmento
            double u = ((C.getX() - A.getX()) * (B.getX() - A.getX()) + (C.getY() - A.getY()) * (B.getY() - A.getY())) / denominador;
            // Si u esta en el intervalo [0,1], el punto P pertenece al segmento
            if (u > 0.0 && u < 1.0) {
                P.x = A.x + u * (B.x - A.x);
                P.y = A.y + u * (B.y - A.y);
            } // Si P no pertenece al segmento se toma uno de los extremos para calcular
            // la distancia. Si u < 0 el extremo es A. Si u >=1 el extremos es B.
            else {
                if (u >= 1.0) {
                    P = B;
                }
            }
        }
        // Se devuelve la distancia entre el punto C y el punto P calculado.
        return distancia(P, C);
    }

}
