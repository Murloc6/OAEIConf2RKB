/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oaeiconf2rkb;

/**
 *
 * @author murloc
 */
public class Module {
    static public String getModule(){
        return 
":hasAuthor rdf:type owl:ObjectProperty ;\n" +
"           \n" +
"           rdfs:range :Author ;\n" +
"           \n" +
"           rdfs:domain :Paper .\n" +
":reviewWrittenBy rdf:type owl:ObjectProperty .\n" +
":Attendee rdf:type owl:Class ;\n" +
"          \n" +
"          rdfs:label \"Conference atendee\" ,\n" +
"                     \"Atendee\" ,\n" +
"                     \"Conference participant\" ,\n" +
"                     \"Participant\" ;\n" +
"          \n" +
"          rdfs:subClassOf :Person .\n" +
":Author rdf:type owl:Class ;\n" +
"        \n" +
"        rdfs:label \"Paper author\" ,\n" +
"                   \"Writer\" ;\n" +
"        \n" +
"        rdfs:subClassOf :Person .\n" +
":Banquet rdf:type owl:Class ;\n" +
"         \n" +
"         rdfs:label \"Banquet Event\" ,\n" +
"                    \"Banquet\" ;\n" +
"         \n" +
"         rdfs:subClassOf :Event .\n" +
":Chair_PC rdf:type owl:Class ;\n" +
"          \n" +
"          rdfs:label \"Chair PC\" ,\n" +
"                     \"Chair Program Comitee\" ,\n" +
"                     \"Session chair\" ,\n" +
"                     \"Program Comitee Chair\" ;\n" +
"          \n" +
"          rdfs:subClassOf :Person .\n" +
":City rdf:type owl:Class ;\n" +
"      \n" +
"      rdfs:label \"City\" ;\n" +
"      \n" +
"      rdfs:subClassOf :Location .\n" +
":Comitee rdf:type owl:Class ;\n" +
"         \n" +
"         rdfs:label \"Comitee\" .\n" +
":Conference rdf:type owl:Class ;\n" +
"            \n" +
"            rdfs:label \"Conference\" ;\n" +
"            \n" +
"            rdfs:subClassOf :Event .\n" +
":Event rdf:type owl:Class ;\n" +
"       \n" +
"       rdfs:label \"Event\" .\n" +
":Location rdf:type owl:Class ;\n" +
"          \n" +
"          rdfs:label \"Place\" ,\n" +
"                     \"Location\" .\n" +
":Paper rdf:type owl:Class ;\n" +
"       \n" +
"       rdfs:label \"Scientific article\" ,\n" +
"                  \"Paper\" ,\n" +
"                  \"Article\" .\n" +
":Person rdf:type owl:Class .\n" +
":ProgramComitee rdf:type owl:Class ;\n" +
"                \n" +
"                rdfs:label \"Program Comitee\" ;\n" +
"                \n" +
"                rdfs:subClassOf :Comitee .\n" +
":Reception rdf:type owl:Class ;\n" +
"           \n" +
"           rdfs:label \"Reception\" ;\n" +
"           \n" +
"           rdfs:subClassOf :Event .\n" +
":Review rdf:type owl:Class ;\n" +
"        \n" +
"        rdfs:label \"Review\" .\n" +
":Speaker rdf:type owl:Class ;\n" +
"         \n" +
"         rdfs:label \"Active participant\" ,\n" +
"                    \"Speaker\" ;\n" +
"         \n" +
"         rdfs:subClassOf :Attendee .\n" +
":Trip rdf:type owl:Class ;\n" +
"      \n" +
"      rdfs:label \"Trip\" ,\n" +
"                 \"Excursion\" ;\n" +
"      \n" +
"      rdfs:subClassOf :Event .\n";
    }
    
}
