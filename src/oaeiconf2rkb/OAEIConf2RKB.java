/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oaeiconf2rkb;

import fr.irit.sparql.Proxy.SparqlProxy;
import fr.irit.sparql.query.DataQuery.Insert.SparqlInsertData;
import java.util.HashMap;

/**
 *
 * @author murloc
 */
public class OAEIConf2RKB {

    public static void saveSKB(SparqlProxy spIn, SparqlProxy spOut, HashMap<String, String> classMaps, HashMap<String, String> objPropMaps)
    {
        SKBGenerator skbgen = new SKBGenerator(spIn, spOut, classMaps, objPropMaps);
        SparqlInsertData sid = skbgen.generateSKB("http://muskca_evals/", "oaei_conf_module.owl");
        //System.out.println(sid.toString());
        skbgen.clearSpOut();
        skbgen.saveData(sid);
        
        SparqlInsertData sidModule = new SparqlInsertData();
        sidModule.addPrefix(" ", "<http://muskca_evals/>");
        sidModule.addData(Module.getModule());
        skbgen.saveData(sidModule);
        //System.out.println(sidModule);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    { 
        /*  EKAW source*/
        SparqlProxy spIn = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/EKAW-in/");
        SparqlProxy spOut = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/EKAW-out/");
        
        HashMap<String, String> classMaps = new HashMap<>();
         /*Manuel*/
        classMaps.put("http://ekaw#PC_Chair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://ekaw#Conference_Banquet", "http://muskca_evals/Banquet");
        classMaps.put("http://ekaw#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://ekaw#Conference_Trip", "http://muskca_evals/Trip");
        classMaps.put("http://ekaw#Location", "http://muskca_evals/Location");
        classMaps.put("http://ekaw#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://ekaw#Presenter", "http://muskca_evals/Speaker");
        classMaps.put("http://ekaw#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://ekaw#Review", "http://muskca_evals/Review");
        classMaps.put("http://ekaw#Conference_Participant", "http://muskca_evals/Attendee");
        classMaps.put("http://ekaw#Event", "http://muskca_evals/Event");
        classMaps.put("http://ekaw#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://ekaw#Paper_Author", "http://muskca_evals/Author");
        
        /* LogMap2 
        classMaps.put("http://ekaw#Session_Chair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://ekaw#Invited_Speaker", "http://muskca_evals/Speaker");
        classMaps.put("http://ekaw#Presenter", "http://muskca_evals/Speaker");
        classMaps.put("http://ekaw#Review", "http://muskca_evals/Review");
        classMaps.put("http://ekaw#Conference_Trip", "http://muskca_evals/Trip");
        classMaps.put("http://ekaw#PC_Chair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://ekaw#Location", "http://muskca_evals/Location");
        classMaps.put("http://ekaw#Paper_Author", "http://muskca_evals/Author");
        classMaps.put("http://ekaw#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://ekaw#Conference_Banquet", "http://muskca_evals/Banquet");
        classMaps.put("http://ekaw#Person", "http://muskca_evals/Person");
        classMaps.put("http://ekaw#Conference_Participant", "http://muskca_evals/Attendee");
        classMaps.put("http://ekaw#Event", "http://muskca_evals/Event");
        classMaps.put("http://ekaw#Conference", "http://muskca_evals/Conference");*/
        
        HashMap<String, String> objPropMaps = new HashMap<>();
        objPropMaps.put("http://ekaw#writtenBy", "http://muskca_evals/hasAuthor");
        objPropMaps.put("http://ekaw#reviewWrittenBy", "http://muskca_evals/reviewWrittenBy");
                
        OAEIConf2RKB.saveSKB(spIn, spOut, classMaps, objPropMaps);
        
        /* IASTED source*/
         spIn = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/iasted-in/");
         spOut = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/iasted-out/");
        
         classMaps = new HashMap<>(); 
        /* Manuel*/
        classMaps.put("http://iasted#Dinner_banquet", "http://muskca_evals/Banquet");
        classMaps.put("http://iasted#Coctail_reception", "http://muskca_evals/Reception");
        classMaps.put("http://iasted#Trip_city", "http://muskca_evals/Trip");
        classMaps.put("http://iasted#Place", "http://muskca_evals/Location");
        classMaps.put("http://iasted#Submission", "http://muskca_evals/Paper");
        classMaps.put("http://iasted#Speaker", "http://muskca_evals/Speaker");
        classMaps.put("http://iasted#Author", "http://muskca_evals/Author");
        classMaps.put("http://iasted#Review", "http://muskca_evals/Review");
        classMaps.put("http://iasted#City", "http://muskca_evals/City");
        classMaps.put("http://iasted#Place", "http://muskca_evals/Location");
        //classMaps.put("http://iasted#Session_chair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://iasted#Delegate", "http://muskca_evals/Attendee"); 
       
        /* LogMap2 
        classMaps.put("http://iasted#City", "http://muskca_evals/City");
        classMaps.put("http://iasted#Place", "http://muskca_evals/Location");
        classMaps.put("http://iasted#Person", "http://muskca_evals/Person");
        classMaps.put("http://iasted#Review", "http://muskca_evals/Review");
        classMaps.put("http://iasted#Speaker", "http://muskca_evals/Speaker");
        classMaps.put("http://iasted#Session_chair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://iasted#Author", "http://muskca_evals/Author");*/

        objPropMaps = new HashMap<>();
        objPropMaps.put("http://iasted#is_writen_by", "http://muskca_evals/hasAuthor");
        //objPropMaps.put("http://ekaw#reviewWrittenBy", "http://muskca_evals/reviewWrittenBy");
        
        OAEIConf2RKB.saveSKB(spIn, spOut, classMaps, objPropMaps);
        
        /* CMT source */
         spIn = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/cmt-in/");
         spOut = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/cmt-out/");
        
        classMaps = new HashMap<>();
        /* Manuel*/
        classMaps.put("http://cmt#ProgramCommitteeChair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://cmt#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://cmt#ConferenceMember", "http://muskca_evals/Attendee");
        classMaps.put("http://cmt#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://cmt#Author", "http://muskca_evals/Author");
        classMaps.put("http://cmt#Review", "http://muskca_evals/Review");
        classMaps.put("http://cmt#Person", "http://muskca_evals/Person");
        classMaps.put("http://cmt#ProgramCommittee", "http://muskca_evals/ProgramComitee");
        
        /*LogMap2 
        classMaps.put("http://cmt#Author", "http://muskca_evals/Author");
        classMaps.put("http://cmt#Person", "http://muskca_evals/Person");
        classMaps.put("http://cmt#Review", "http://muskca_evals/Review");
        classMaps.put("http://cmt#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://cmt#Conference", "http://muskca_evals/Conference");*/
        
        
        
        objPropMaps = new HashMap<>();
        objPropMaps.put("http://cmt#hasAuthor", "http://muskca_evals/hasAuthor");
        objPropMaps.put("http://cmt#writtenBy", "http://muskca_evals/reviewWrittenBy");
        
        OAEIConf2RKB.saveSKB(spIn, spOut, classMaps, objPropMaps);
        
        /* Conference source */
         spIn = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/conference-in/");
         spOut = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/conference-out/");
        
        
        classMaps = new HashMap<>();
        /*Manuel */
        classMaps.put("http://conference#Chair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://conference#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://conference#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://conference#Regular_author", "http://muskca_evals/Author");
        classMaps.put("http://conference#Review", "http://muskca_evals/Review");
        classMaps.put("http://conference#Active_conference_participant", "http://muskca_evals/Speaker");
        classMaps.put("http://conference#Conference_participant", "http://muskca_evals/Attendee");
        classMaps.put("http://conference#Person", "http://muskca_evals/Person");
        classMaps.put("http://conference#Program_committee", "http://muskca_evals/ProgramComitee");
       
        /* LogMap2 
        classMaps.put("http://conference#Review", "http://muskca_evals/Review");
        classMaps.put("http://conference#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://conference#Active_conference_participant", "http://muskca_evals/Speaker");
        classMaps.put("http://conference#Person", "http://muskca_evals/Person");
        classMaps.put("http://conference#Conference_participant", "http://muskca_evals/Attendee");
        classMaps.put("http://conference#Conference", "http://muskca_evals/Conference");*/
        
        
      
        objPropMaps = new HashMap<>();
        objPropMaps.put("http://conference#hasAuthors", "http://muskca_evals/hasAuthor");
        objPropMaps.put("http://conference#has_authors", "http://muskca_evals/reviewWrittenBy");
        
        OAEIConf2RKB.saveSKB(spIn, spOut, classMaps, objPropMaps);
            
        
        /* ConfOf Source */
         spIn = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/confof-in/");
         spOut = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/confof-out/");
        
        
        classMaps = new HashMap<>();
        /* Manuel*/
        classMaps.put("http://confOf#Chair_PC", "http://muskca_evals/Chair_PC");
        classMaps.put("http://confOf#Banquet", "http://muskca_evals/Banquet");
        classMaps.put("http://confOf#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://confOf#Reception", "http://muskca_evals/Reception");
        classMaps.put("http://confOf#Trip", "http://muskca_evals/Trip");
        classMaps.put("http://confOf#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://confOf#Author", "http://muskca_evals/Author");
        classMaps.put("http://confOf#City", "http://muskca_evals/City");
        classMaps.put("http://confOf#Participant", "http://muskca_evals/Attendee");
        classMaps.put("http://confOf#Event", "http://muskca_evals/Event");
        
        
        /*LogMap2
        classMaps.put("http://confOf#City", "http://muskca_evals/City");
        classMaps.put("http://confOf#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://confOf#Reviewing_event", "http://muskca_evals/Review");
        classMaps.put("http://confOf#Banquet", "http://muskca_evals/Banquet");
        classMaps.put("http://confOf#Trip", "http://muskca_evals/Trip");
        classMaps.put("http://confOf#Person", "http://muskca_evals/Person");
        classMaps.put("http://confOf#Author", "http://muskca_evals/Author");
        classMaps.put("http://confOf#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://confOf#Reception", "http://muskca_evals/Reception");
        classMaps.put("http://confOf#Chair_PC", "http://muskca_evals/Chair_PC");
        classMaps.put("http://confOf#Participant", "http://muskca_evals/Attendee");
        classMaps.put("http://confOf#Event", "http://muskca_evals/Event");*/

        objPropMaps = new HashMap<>();
        objPropMaps.put("http://confOf#writtenBy", "http://muskca_evals/hasAuthor");
        //objPropMaps.put("http://conference#has_authors", "http://muskca_evals/reviewWrittenBy");
        
        OAEIConf2RKB.saveSKB(spIn, spOut, classMaps, objPropMaps);
        
        
        /* edas Source */
         spIn = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/edas-in/");
         spOut = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/edas-out/");
        
        classMaps = new HashMap<>();
        /* Manuel*/
        //classMaps.put("http://edas#ConferenceChair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://edas#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://edas#Reception", "http://muskca_evals/Reception");
        classMaps.put("http://edas#Excursion", "http://muskca_evals/Trip");
        classMaps.put("http://edas#Place", "http://muskca_evals/Location");
        classMaps.put("http://edas#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://edas#Presenter", "http://muskca_evals/Speaker");
        classMaps.put("http://edas#Author", "http://muskca_evals/Author");
        classMaps.put("http://edas#Review", "http://muskca_evals/Review");
        classMaps.put("http://edas#Attendee", "http://muskca_evals/Attendee");
        classMaps.put("http://edas#Person", "http://muskca_evals/Person");
        
        
        /* LogMap2 
        classMaps.put("http://edas#Review", "http://muskca_evals/Review");
        classMaps.put("http://edas#Person", "http://muskca_evals/Person");
        classMaps.put("http://edas#Attendee", "http://muskca_evals/Attendee");
        classMaps.put("http://edas#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://edas#Reception", "http://muskca_evals/Reception");
        classMaps.put("http://edas#Place", "http://muskca_evals/Location");
        classMaps.put("http://edas#SessionChair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://edas#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://edas#Author", "http://muskca_evals/Author");
        classMaps.put("http://edas#Excursion", "http://muskca_evals/Trip");*/
       
        
        objPropMaps = new HashMap<>();
        objPropMaps.put("http://edas#isWrittenBy", "http://muskca_evals/hasAuthor");
        //objPropMaps.put("http://conference#has_authors", "http://muskca_evals/reviewWrittenBy");
        OAEIConf2RKB.saveSKB(spIn, spOut, classMaps, objPropMaps);
        
        
        /* sigkdd Source */
         spIn = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/sigkdd-in/");
         spOut = SparqlProxy.getSparqlProxy("http://amarger.murloc.fr:8080/sigkdd-out/");
        
         classMaps = new HashMap<>();
        /*Manuel*/
        classMaps.put("http://sigkdd#Program_Chair", "http://muskca_evals/Chair_PC");
        classMaps.put("http://sigkdd#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://sigkdd#Place", "http://muskca_evals/Location");
        classMaps.put("http://sigkdd#Paper", "http://muskca_evals/Paper");
        classMaps.put("http://sigkdd#Author", "http://muskca_evals/Author");
        classMaps.put("http://sigkdd#Review", "http://muskca_evals/Review");
        classMaps.put("http://sigkdd#Program_Committee", "http://muskca_evals/ProgramComitee");
        classMaps.put("http://sigkdd#Person", "http://muskca_evals/Person");
        classMaps.put("http://sigkdd#Listener", "http://muskca_evals/Attendee");
        classMaps.put("http://sigkdd#Speaker", "http://muskca_evals/Speaker");
        //classMaps.put("http://confOf#City", "http://muskca_evals/City");
        
        
        /* LogMap2
        classMaps.put("http://sigkdd#Speaker", "http://muskca_evals/Speaker");
        classMaps.put("http://sigkdd#Conference", "http://muskca_evals/Conference");
        classMaps.put("http://sigkdd#Program_Committee", "http://muskca_evals/ProgramComitee");
        classMaps.put("http://sigkdd#Person", "http://muskca_evals/Person");
        classMaps.put("http://sigkdd#Review", "http://muskca_evals/Review");
        classMaps.put("http://sigkdd#Author", "http://muskca_evals/Author");
        classMaps.put("http://sigkdd#Place", "http://muskca_evals/Location");
        classMaps.put("http://sigkdd#Paper", "http://muskca_evals/Paper");*/
        
         objPropMaps = new HashMap<>();
        objPropMaps.put("http://sigkdd#submit", "http://muskca_evals/hasAuthor");
        //objPropMaps.put("http://conference#has_authors", "http://muskca_evals/reviewWrittenBy");
        
        OAEIConf2RKB.saveSKB(spIn, spOut, classMaps, objPropMaps);
        
    }
    
}
