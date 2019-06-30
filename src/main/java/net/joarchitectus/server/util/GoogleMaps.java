/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.joarchitectus.server.util;

import static org.w3c.dom.Node.ATTRIBUTE_NODE;
import static org.w3c.dom.Node.CDATA_SECTION_NODE;
import static org.w3c.dom.Node.COMMENT_NODE;
import static org.w3c.dom.Node.DOCUMENT_TYPE_NODE;
import static org.w3c.dom.Node.ELEMENT_NODE;
import static org.w3c.dom.Node.ENTITY_NODE;
import static org.w3c.dom.Node.ENTITY_REFERENCE_NODE;
import static org.w3c.dom.Node.NOTATION_NODE;
import static org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE;
import static org.w3c.dom.Node.TEXT_NODE;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import javax.xml.xpath.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/**
 * Busca las coordenadas GPS de una direccion
 *
 * @author josorio
 */
public class GoogleMaps {
    
    //private static final Logger log = Logger.getLogger(GoogleMaps.class);

    public static void main(String[] args) throws IOException {

        try {
            System.out.println("Buscando coordenadas GPS direccion...");
            Coordenadas coord = new GoogleMaps().procesarDireccion("calle 8 84b 65,medellin,colombia");
            if (coord != null) {
                System.out.println("Encontro: " + coord);
            } else {
                System.out.println("No encontro coordenadas.");
            }
            System.out.println("Buscando dirección de coordenadas...");
            Double latitud = 6.2088081;
            Double longitud = -75.5708415;
            String direccion = new GoogleMaps().procesarCoordenada(latitud, longitud);
            if(direccion!=null){
                System.out.println("Dirección: "+direccion);
            }else{
                System.out.println("No se pudo procesar las coordenadas.");
            }
            
        } catch (XPathExpressionException ex) {
            System.out.print("XPath Error");
        } catch (FileNotFoundException ex) {
            System.out.print("File Error");
        }
    }

    /**
     * Busca las coordenadas GPS de una direccion. Se espera una direccion de
     * tipo calle 8 84b 65,medellin,colombia
     *
     * @param direccion
     */
    public Coordenadas procesarDireccion(String direccion) throws XPathExpressionException, FileNotFoundException, IOException {
        //podria traducir la direccion, pendiente de definir.
        direccion = validarDireccionNomenclatura(direccion);
        //quito espacios
        direccion = cambiarEspacion(direccion);

        XPathFactory factory = XPathFactory.newInstance();

        XPath xpath = factory.newXPath();

        //System.out.println(">Buscando: "+direccion); 

        //System.out.print("Web Service Parser 1.0\n");

        // In practice, you'd retrieve your XML via an HTTP request.
        // Here we simply access an existing file.
        //File xmlFile = new File("XML_FILE");
        // The xpath evaluator requires the XML be in the format of an InputSource
        //InputSource inputXml = new InputSource(new FileInputStream(xmlFile));

        URL url1 = new URL("http://maps.googleapis.com/maps/api/geocode/xml?address=" + direccion + "&sensor=true");
        URLConnection urlConn = url1.openConnection();
        InputStream is1 = url1.openStream();

        //System.out.println("Content type: " + urlConn.getContentType());

        InputSource inputXml = new InputSource(is1);


        // Because the evaluator may return multiple entries, we specify that the expression
        // return a NODESET and place the result in a NodeList.
        NodeList nodes = (NodeList) xpath.evaluate("GeocodeResponse", inputXml, XPathConstants.NODESET);

        //System.out.println("Leyendo respuesta Google...");
        return procesarNodos(nodes, direccion);
    }
    /**
     * 
     * @param latitud
     * @param longitud
     * @return 
     */
    public String procesarCoordenada(Double latitud, Double longitud)  throws XPathExpressionException, FileNotFoundException, IOException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        
        URL url1 = new URL("http://maps.googleapis.com/maps/api/geocode/xml?latlng=" + latitud+","+longitud + "&sensor=true");
        URLConnection urlConn = url1.openConnection();
        InputStream is1 = url1.openStream();
        
        InputSource inputXml = new InputSource(is1);
        
        NodeList nodes = (NodeList) xpath.evaluate("GeocodeResponse", inputXml, XPathConstants.NODESET);

        //System.out.println("Leyendo respuesta Google...");
        return procesarNodos(nodes);
    }

    private String cambiarEspacion(String direccion) {
        return direccion.replaceAll(" ", "+");
    }
    
    /**
     * Lee la lista de nodos indicada. Doc
     * https://developers.google.com/maps/documentation/webservices/
     *
     * @param nodes
     */
    private String procesarNodos(NodeList nodes){
        for (int i = 0, n = nodes.getLength(); i < n; i++) {
            if (nodes.item(i).getNodeType() == ELEMENT_NODE && nodes.item(i).getLocalName().equalsIgnoreCase("formatted_address")) {
                //System.out.println("TXT: "+nodes.item(i).getTextContent());
                return nodes.item(i).getTextContent();
            }
            
            if (nodes.item(i).hasChildNodes() && nodes.item(i).getNodeType() == ELEMENT_NODE) {
                String var = procesarNodos(nodes.item(i).getChildNodes());
                if(!var.equalsIgnoreCase("Desconocido")){
                    return var;
                }
            }
        }
        return "Desconocido";
    }

    /**
     * Lee la lista de nodos indicada. Doc
     * https://developers.google.com/maps/documentation/webservices/
     *
     * @param nodes
     */
    private Coordenadas procesarNodos(NodeList nodes, String direccion) {
        //System.out.println("\n|||||||||||||||||||||||||||||||||||||||||\n");
        // We can then iterate over the NodeList and extract the content via getTextContent().
        // NOTE: this will only return text for element nodes at the returned context.
        Coordenadas coordenadasDireccion = null;
        for (int i = 0, n = nodes.getLength(); i < n; i++) {
//            String nodeString = "";//nodes.item(i).getTextContent();
//                System.out.println("1- "+ nodeString);
//                nodeString = nodes.item(i).getNodeName();
//                System.out.println("2- "+ nodeString);
//                nodeString = nodes.item(i).getNodeValue();
//                System.out.println("3- "+ nodeString);
//                nodeString = nodes.item(i).getLocalName();
//                System.out.println("4- "+ nodeString);
//                nodeString = nodes.item(i).getBaseURI();
//                System.out.println("5- "+ nodeString);
//                nodeString = nodes.item(i).getNamespaceURI();
//                System.out.println("6- "+ nodeString);
//                nodeString = nodes.item(i).getPrefix();
//                System.out.println("7- "+ nodeString);
//                nodeString = nodes.item(i).getNodeType()+"";
//                System.out.println("8- "+ nodeString);

//            System.out.println(" Node Type: " + nodeType(nodes.item(i).getNodeType()));
//            if (nodes.item(i).getNodeType() == TEXT_NODE) {
//                System.out.println("Content is: " + ((Text) nodes.item(i)).getWholeText());
//            }
//            System.out.println("\n---------------------------\n");

            if (nodes.item(i).getNodeType() == ELEMENT_NODE && nodes.item(i).getLocalName().equalsIgnoreCase("location")) {
                //System.out.println(":::::::::::::::::::::::: Estamos en locale ::::::::::::::::::::::::");
                //System.out.println("TXT: "+nodes.item(i).getTextContent());
                NodeList nodesCoordenadas = nodes.item(i).getChildNodes();
                coordenadasDireccion = new Coordenadas();
                coordenadasDireccion.setDireccion(direccion); 

                for (int j = 0; j < nodesCoordenadas.getLength(); j++) {
                    if (nodesCoordenadas.item(j).getNodeType() == ELEMENT_NODE && nodesCoordenadas.item(j).getLocalName().equalsIgnoreCase("lat")) {
                        //System.out.println("Latitud: "+nodesCoordenadas.item(j).getTextContent());
                        coordenadasDireccion.setLatitud(Double.parseDouble(nodesCoordenadas.item(j).getTextContent()));
                    } else if (nodesCoordenadas.item(j).getNodeType() == ELEMENT_NODE && nodesCoordenadas.item(j).getLocalName().equalsIgnoreCase("lng")) {
                        //System.out.println("Longitud: "+nodesCoordenadas.item(j).getTextContent());
                        coordenadasDireccion.setLongitud(Double.parseDouble(nodesCoordenadas.item(j).getTextContent()));
                    }
                }
                return coordenadasDireccion;
            }

            if (nodes.item(i).hasChildNodes() && nodes.item(i).getNodeType() == ELEMENT_NODE) {
                coordenadasDireccion = procesarNodos(nodes.item(i).getChildNodes(), direccion);
                if(coordenadasDireccion!=null){
                    return coordenadasDireccion;
                }
            }
        }
        return coordenadasDireccion;
    }

    static String nodeType(short type) {
        switch (type) {
            case ELEMENT_NODE:
                return "Element";
            case DOCUMENT_TYPE_NODE:
                return "Document type";
            case ENTITY_NODE:
                return "Entity";
            case ENTITY_REFERENCE_NODE:
                return "Entity reference";
            case NOTATION_NODE:
                return "Notation";
            case TEXT_NODE:
                return "Text";
            case COMMENT_NODE:
                return "Comment";
            case CDATA_SECTION_NODE:
                return "CDATA Section";
            case ATTRIBUTE_NODE:
                return "Attribute";
            case PROCESSING_INSTRUCTION_NODE:
                return "Attribute";
        }
        return "Unidentified";
    }

    /**
     * Verifica direccion suministrada y convierte abreviaciones y demas en
     * texto leible.
     *
     * @param direccionStr
     * @return
     */
    public static String validarDireccionNomenclatura(String direccionStr) {
        direccionStr = direccionStr.replaceAll("#", "");
        direccionStr = direccionStr.replaceAll("Â°", "");
        if (direccionStr != null) {
            direccionStr = direccionStr.toLowerCase();

            StringTokenizer st = new StringTokenizer(direccionStr);

            String aux = "";
            while (st.hasMoreElements()) {
                String parcial = st.nextToken();

                parcial = parcial.replaceAll("(?i)cra", "carrera");//reemplazo quitando el case sensitive.
                if (!parcial.contains("carrera")) {
                    parcial = parcial.replaceAll("(?i)cra.", "carrera");
                }
                if (!parcial.contains("carrera")) {
                    parcial = parcial.replaceAll("(?i)cr", "carrera");
                }
                if (!parcial.contains("carrera")) {
                    parcial = parcial.replaceAll("(?i)kra", "carrera");
                }
                if (!parcial.contains("carrera")) {
                    //parcial = parcial.replaceAll("(?i)k", "carrera");
                    if (parcial.equalsIgnoreCase("k")) {
                        parcial = "carrera";
                    }
                }
                if (!parcial.contains("calle")) {
                    parcial = parcial.replaceAll("(?i)cll", "calle");
                }
                if (!parcial.contains("calle")) {
                    parcial = parcial.replaceAll("(?i)cl", "calle");
                }
                if (!parcial.contains("calle")) {
                    //parcial = parcial.replaceAll("^[0-9]c", "calle");
                    if (parcial.equalsIgnoreCase("c")) {
                        parcial = "calle";
                    }
                }

                parcial = parcial.replaceAll("(?i)av", "avenida");
                //parcial = parcial.replaceAll("(?i)a", "avenida");
                parcial = parcial.replaceAll("(?i)ap", "apartamento");
                parcial = parcial.replaceAll("(?i)au", "autopista");
                parcial = parcial.replaceAll("(?i)br", "barrio");
                parcial = parcial.replaceAll("(?i)bq", "bloque");
                parcial = parcial.replaceAll("(?i)bl", "bulevar");
                parcial = parcial.replaceAll("(?i)cs", "casa");
                parcial = parcial.replaceAll("(?i)tv", "transversal");
                parcial = parcial.replaceAll("(?i)dg", "diagonal");
                parcial = parcial.replaceAll("(?i)cq", "circular");
                //parcial = parcial.replaceAll("(?i)d", "diagonal");

                parcial = parcial.replaceAll("#", "nÃºmero");
                parcial = parcial.replaceAll("(?i)no", "nÃºmero");

                aux = aux + parcial + " ";

            }

            return aux.trim();
        } else {
            return "";
        }
    }

    
}
