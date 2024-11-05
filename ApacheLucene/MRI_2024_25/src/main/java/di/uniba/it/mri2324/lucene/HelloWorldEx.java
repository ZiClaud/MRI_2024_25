package di.uniba.it.mri2324.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/// Ex
// Modify HelloWorld.java
// Add five documents (define the fields and the content you prefer)
// Check the number of documents in the index
// Try some queries, and check if the number of documents matching the query is correct

/**
 * @author marco
 */
public class HelloWorldEx {

    /**
     * @param args the command line arguments
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
        try {
            //Open a directory from the file system (index directory)
            FSDirectory fsdir = FSDirectory.open(new File("./resources/documenti_news").toPath());

            //IndexWriter configuration
            IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());

            //Index directory is created if not exists or overwritten
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);


            //Create IndexWriter
            IndexWriter writer = new IndexWriter(fsdir, iwc);


            //Create document and add fields
            Document doc = new Document();
            doc.add(new TextField("titolo", "Articolo Web Numero 1", Field.Store.YES));
            doc.add(new TextField("introduzione", "questa è l'introduzione del mio documento", Field.Store.YES));
            doc.add(new TextField("contenuto", "questo è il contenuto del mio documento", Field.Store.NO));
            doc.add(new TextField("commenti", "questo è un commento di un utente di esempio", Field.Store.NO));
            writer.addDocument(doc);

            doc = new Document();
            doc.add(new TextField("titolo", "Articolo Web Numero 2", Field.Store.YES));
            doc.add(new TextField("introduzione", "questa è l'introduzione del tuo documento", Field.Store.YES));
            doc.add(new TextField("contenuto", "questo è il contenuto del tuo documento", Field.Store.NO));
            doc.add(new TextField("commenti", "questo è un altro commento di un utente di esempio", Field.Store.NO));
            writer.addDocument(doc);

            doc = new Document();
            doc.add(new TextField("titolo", "Articolo Web Numero 3", Field.Store.YES));
            doc.add(new TextField("introduzione", "questa è l'introduzione del suo documento", Field.Store.YES));
            doc.add(new TextField("contenuto", "questo è il contenuto del suo documento", Field.Store.NO));
            doc.add(new TextField("commenti", "questo è un altro ancora commento di un utente di esempio", Field.Store.NO));
            writer.addDocument(doc);

            doc = new Document();
            doc.add(new TextField("titolo", "Articolo Web Numero 4", Field.Store.YES));
            doc.add(new TextField("introduzione", "questa è l'introduzione del nostro documento", Field.Store.YES));
            doc.add(new TextField("contenuto", "questo è il contenuto del nostro documento", Field.Store.NO));
            doc.add(new TextField("commenti", "questo è un commento di due utenti di esempio", Field.Store.NO));
            writer.addDocument(doc);

            doc = new Document();
            doc.add(new TextField("titolo", "Articolo Web Numero 5", Field.Store.YES));
            doc.add(new TextField("introduzione", "questa è l'introduzione del vostro documento", Field.Store.YES));
            doc.add(new TextField("contenuto", "questo è il contenuto del vostro documento", Field.Store.NO));
            doc.add(new TextField("commenti", "questo è un commento di un utente di esempio, forse", Field.Store.NO));
            writer.addDocument(doc);

            //close IndexWriter
            writer.close();

            // Crea un IndexReader
            IndexReader reader = DirectoryReader.open(fsdir);

            // Conta il numero di documenti attivi (non cancellati)
            int numDocs = reader.numDocs();
            System.out.println("Numero di documenti nell'indice: " + numDocs);

            //Create the IndexSearcher
            IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(fsdir));

            //Create the query parser with the default field and analyzer
            QueryParser qp = new QueryParser("titolo", new StandardAnalyzer());

            //Parse the query
            Query q = qp.parse("3");

            //Search
            TopDocs topdocs = searcher.search(q, 10);
            System.out.println("Found " + topdocs.totalHits.value + " document(s).");

        } catch (IOException ex) {
            Logger.getLogger(HelloWorldEx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
