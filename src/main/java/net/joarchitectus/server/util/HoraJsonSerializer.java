///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package net.joarchitectus.server.util;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.JsonSerializer;
//import org.codehaus.jackson.map.SerializerProvider;
//
///**
// *
// * @author jdbotero
// */
//public class HoraJsonSerializer extends JsonSerializer<Date>
//{
//    @Override
//    public void serialize(Date date, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException
//    {
//        SimpleDateFormat sdf = new SimpleDateFormat( "hh:mm a" );
//        jg.writeString( sdf.format( date ) );
//    }
//
//}
