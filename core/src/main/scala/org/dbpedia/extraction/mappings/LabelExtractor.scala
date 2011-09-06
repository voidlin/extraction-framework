package org.dbpedia.extraction.mappings

import org.dbpedia.extraction.destinations.{Graph, DBpediaDatasets, Quad}
import org.dbpedia.extraction.wikiparser.{PageNode, WikiTitle}
import org.dbpedia.extraction.ontology.Ontology
import org.dbpedia.extraction.util.Language

/**
 * Extracts labels to articles based on their title.
 */
class LabelExtractor( context : {
                          def ontology : Ontology
                          def language : Language } ) extends Extractor
{
    val labelProperty = context.ontology.getProperty("rdfs:label").get
    
    override def extract(node : PageNode, subjectUri : String, pageContext : PageContext) : Graph =
    {
        if(node.title.namespace != WikiTitle.Namespace.Main) return new Graph()

        val label = node.root.title.decoded
        if(label.isEmpty) return new Graph()

        new Graph(new Quad(DBpediaDatasets.Labels, new IriRef(subjectUri), new IriRef(labelProperty), new PlainLiteral(label),
                            new IriRef(node.sourceUri) ))
    }
}
