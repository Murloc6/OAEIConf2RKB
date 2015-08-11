/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oaeiconf2rkb;

import com.fasterxml.jackson.databind.JsonNode;
import fr.irit.sparql.Proxy.SparqlProxy;
import fr.irit.sparql.query.Ask.SparqlAsk;
import fr.irit.sparql.query.DataQuery.Insert.SparqlInsertData;
import fr.irit.sparql.query.Exceptions.SparqlEndpointUnreachableException;
import fr.irit.sparql.query.Exceptions.SparqlQueryMalFormedException;
import fr.irit.sparql.query.Exceptions.SparqlQueryUnseparableException;
import fr.irit.sparql.query.Select.SparqlSelect;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author murloc
 */
public class SKBGenerator 
{
    private SparqlProxy spIn;
    private SparqlProxy spOut;
    private HashMap<String, String> classMaps;
    private HashMap<String, String> objPropMaps;
    
    private ArrayList<String> generatedInd;
    private ArrayList<String> relFounded;
    
    private int nbInd = 0;
    private int nbClass = 0;
    private int nbLabel = 0;
    private int nbRel = 0;

    public SKBGenerator(SparqlProxy spIn, SparqlProxy spOut, HashMap<String, String> classMaps, HashMap<String, String> objPropMaps)
    {
        this.spIn = spIn;
        this.spOut = spOut;
        this.classMaps = classMaps;
        this.objPropMaps = objPropMaps;
        
        this.generatedInd = new ArrayList<>();
        this.relFounded = new ArrayList<>();
    }
    
    
    static String splitCamelCase(String s) {
        return s.replaceAll(
           String.format("%s|%s|%s",
              "(?<=[A-Z])(?=[A-Z][a-z])",
              "(?<=[^A-Z])(?=[A-Z])",
              "(?<=[A-Za-z])(?=[^A-Za-z])"
           ),
           " "
        );
     }
    
    private void getLabels(String uri, SparqlInsertData sid)
    {
        
        String labelFragUri = splitCamelCase(uri.substring(uri.lastIndexOf("#")+1));
        labelFragUri = labelFragUri.replaceAll("_", " ").replaceAll("\\.", " ");
        //System.out.println(uri);
        //System.out.println(labelFragUri);
        sid.addData("<"+uri+"> rdfs:label \""+labelFragUri+"\".");
        this.nbLabel++;
        
        //SparqlSelect query = new SparqlSelect("?label ?lang", "<"+uri+"> rdfs:label ?label. BIND(lang(?label) AS ?lang)");
        SparqlSelect query = new SparqlSelect("?label", "<"+uri+"> rdfs:label ?label.");
        try
        {
            for(JsonNode jnode : spIn.getResponse(query))
            {
                String label = jnode.get("label").get("value").asText();
                String data = "<"+uri+"> rdfs:label \""+label+"\"";
                /*JsonNode langNode = jnode.get("lang");
                if(langNode != null )
                {
                    String lang = langNode.get("value").asText();
                    data += "@"+lang;
                }*/
                data += ".";
                this.nbLabel++;
                sid.addData(data);
            }
        }
        catch (SparqlQueryMalFormedException ex) 
        {
            System.err.println("Query mal formed ..."+query);
        } 
        catch (SparqlEndpointUnreachableException ex) 
        {
            System.err.println("Sparql endpoint unreachable ...");
        }
         
    }
    
    private void getNewClasses(String classUriSource, String classUriModule, SparqlInsertData sid)
    {
        SparqlSelect query = new SparqlSelect("DISTINCT ?class ?superClass", "?class rdfs:subClassOf ?superClass. ?superClass rdfs:subClassOf* <"+classUriSource+">.");
        try 
        {
            for(JsonNode jnode : spIn.getResponse(query))
            {
                String classUri = jnode.get("class").get("value").asText();
                String superClassUri = jnode.get("superClass").get("value").asText();
                if(superClassUri.equalsIgnoreCase(classUriSource))
                {
                    superClassUri = classUriModule;
                }
                if(this.classMaps.containsKey(classUri))
                {
                    classUri = classMaps.get(classUri);
                }
                sid.addData("<"+jnode.get("class").get("value").asText()+"> rdfs:subClassOf <"+classUriModule+">.");
                this.nbClass++;
                this.getLabels(classUri, sid);
                //this.newClasses.add(classUri);
            }
        } 
        catch (SparqlQueryMalFormedException ex) 
        {
            System.err.println("Query mal formed ..."+query);
        } 
        catch (SparqlEndpointUnreachableException ex) 
        {
            System.err.println("Sparql endpoint unreachable ...");
        }
    }
    
    private void getAllRel(String indUri, String relUri, String relModule)
    {
        SparqlSelect query = new SparqlSelect("?obj", "<"+indUri+"> <"+relUri+"> ?obj.");
        try
        {
            for(JsonNode jnode : this.spIn.getResponse(query))
            {
                this.relFounded.add(indUri+" "+relModule+" "+jnode.get("obj").get("value").asText());
            }
        } catch (SparqlQueryMalFormedException | SparqlEndpointUnreachableException ex) {
        }
    }
    
    private void getNewIndividuals(String classUriSource, String classUriModule, SparqlInsertData sid)
    {
        SparqlSelect query = new SparqlSelect("?ind ?class", "?ind rdf:type ?class. ?class rdfs:subClassOf* <"+classUriSource+">.");
        try 
        {
            for(JsonNode jnode : spIn.getResponse(query))
            {
                String indUri = jnode.get("ind").get("value").asText();
                String classUri = jnode.get("class").get("value").asText();
                if(classUri.equalsIgnoreCase(classUriSource))
                {
                    classUri = classUriModule;
                }
                else
                {
                    sid.addData("<"+indUri+"> rdf:type <"+classUriModule+">.");
                }
                if(this.classMaps.containsKey(classUri))
                {
                    classUri = this.classMaps.get(classUri);
                }
                sid.addData("<"+indUri+"> rdf:type <"+classUri+">.\n");
                this.nbInd++;
                this.getLabels(indUri, sid);
                this.generatedInd.add(indUri);
                for(Entry<String, String> e : this.objPropMaps.entrySet())
                {
                    this.getAllRel(indUri, e.getKey(), e.getValue());
                }
            }
        } 
        catch (SparqlQueryMalFormedException ex) 
        {
            System.err.println("Query mal formed ...");
        } 
        catch (SparqlEndpointUnreachableException ex) 
        {
            System.err.println("Sparql endpoint unreachable ...");
        }
    }
    
    
    private void getNewRelation(SparqlInsertData sid)
    {
        for(Entry<String, String> e: this.objPropMaps.entrySet())
        {
            sid.addData("<"+e.getValue()+"> owl:sameAs <"+e.getKey()+">. \n");
        }
        for(String triple : this.relFounded)
        {
            String[] elems = triple.split(" ");
            if(this.generatedInd.contains(elems[2]))
            {
                sid.addData("<"+elems[0]+"> <"+elems[1]+"> <"+elems[2]+">. \n");
                this.nbRel++;
            }
        }
    }
    
//    private void getNewRelation(SparqlInsertData sid)
//    {
//        for(Entry<String, String> e : this.objPropMaps.entrySet())
//        {
//            String sourceRel = e.getKey();
//            String moduleRel = e.getValue();
//            for(String indUriSubject : this.generatedInd)
//            {
//                for(String indUriObject : this.generatedInd)
//                {
//                    if(!indUriSubject.equals(indUriObject))
//                    {
//                        SparqlAsk sa = new SparqlAsk("<"+indUriSubject+"> <"+sourceRel+"> <"+indUriObject+">.");
//                        try 
//                        {
//                            if(this.spIn.sendAskQuery(sa))
//                            {
//                                sid.addData("<"+indUriSubject+"> <"+moduleRel+"> <"+indUriObject+">.");
//                                this.nbRel ++;
//                            }
//                        } 
//                        catch (SparqlQueryMalFormedException | SparqlEndpointUnreachableException ex){}
//                    }
//                }
//            }
//            
//        }
//    }
    
    
    public String getModule(String owlFile){
         String ret = "";
        try 
        {
            ret +=  IOUtils.toString( new FileInputStream(new File(owlFile)));
            //ret = ret.replaceAll("^@.+\\.$", "");   // remove Prefix (wrong syntax for SPARQL insert query)
        } 
        catch (IOException ex) 
        {
            System.err.println("Can't read provo file!");
            System.exit(0);
        }
        
        return ret;
    }
    
    
    public SparqlInsertData generateSKB(String baseUriModule, String moduleFile)
    {
        System.out.println("Start generate "+this.spIn.getUrlServer());
        SparqlInsertData sid = new SparqlInsertData();
        //sid.addPrefix(" ", "<"+baseUriModule+">");
        
        //sid.addData(this.getModule(moduleFile));
        
        for(Entry<String, String> e : this.classMaps.entrySet())
        {
            sid.addData("<"+e.getValue()+"> owl:sameAs <"+e.getKey()+">.\n");
            this.getNewClasses(e.getKey(), e.getValue(), sid);
            this.getNewIndividuals(e.getKey(), e.getValue(), sid);
        }
        this.getNewRelation(sid);
        
        System.out.println("STATS : ");
        System.out.println("\t Class : "+this.nbClass);
        System.out.println("\t Ind : "+this.nbInd);
        System.out.println("\t Labels : "+this.nbLabel);
        System.out.println("\t Rel : "+this.nbRel);
        
        return sid;
    }
    
    public void clearSpOut()
    {
        try 
        {
            this.spOut.clearSp();
        } catch (SparqlQueryMalFormedException ex) 
        {
            Logger.getLogger(SKBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SparqlEndpointUnreachableException ex) 
        {
            System.out.println("SpOut unreachable");
            System.exit(0);
        }
    }
    
    public void saveData(SparqlInsertData sid)
    {
        //System.out.println(sid);
        try
        {
            this.spOut.storeData(sid);
        }
        catch (SparqlQueryMalFormedException ex) 
        {
            System.err.println("Query mal formed ...");
        } 
        catch (SparqlEndpointUnreachableException ex) 
        {
            System.err.println("Sparql endpoint unreachable ...");
        } catch (SparqlQueryUnseparableException ex) {
            System.err.println("Sparql query unseparable ...");
        }
    }
    
}
