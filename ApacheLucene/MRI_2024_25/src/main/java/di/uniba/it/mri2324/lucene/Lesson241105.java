package di.uniba.it.mri2324.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


// MultiFieldQueryParser:
// Like a QueryParser, but you can put more fields at the same time
// example: see code

// Tokenizers:
// example: string "full-text lucene.apache.org"
// StandardTokenizer: "full" "text" "lucene.apache.org"
// WhitespaceTokenizer: "full-text" "lucene.apache.org"
// LetterTokenizer: "full" "text" "lucene" "apache" "org"

// TokenFilters:
// LowerCaseFilter, StopFilter, LengthFilter, PorterStemFilter

// Analyzers:
// example: string "The quick brown fox jumped over the lazy dogs"
// WhitespaceAnalyzer: [The] [quick] [brown] [fox] [jumped] [over] [the] [lazy] [dogs]
// SimpleAnalyzer: [the] [quick] [brown] [fox] [jumped] [over] [the] [lazy] [dogs]
// StopAnalyzer: [quick] [brown] [fox] [jumped] [lazy] [dogs]
// StandardAnalyzer: [quick] [brown] [fox] [jumped] [over] [lazy] [dogs] - Most used

/**
 * @author marco
 */
public class Lesson241105 { // Edited HelloWorld code

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

            // MultiFieldQueryParser - Funziona come il QueryParser,
            String[] fields = {"titolo", "introduzione", "contenuto"};
            MultiFieldQueryParser qp = new MultiFieldQueryParser(fields, new StandardAnalyzer());

            //Parse the query
            Query q = qp.parse("4");

            //Search
            TopDocs topdocs = searcher.search(q, 10);
            System.out.println("Found " + topdocs.totalHits.value + " document(s).");

        } catch (IOException ex) {
            Logger.getLogger(Lesson241105.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
