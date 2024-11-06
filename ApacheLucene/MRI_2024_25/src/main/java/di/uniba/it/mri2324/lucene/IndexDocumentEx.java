package di.uniba.it.mri2324.lucene;

import org.apache.lucene.analysis.Analyzer;
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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

// Index the famous novel Alice In Wonderland in a Lucene searchable index.
// You should index each Chapter as a separate Lucene Document.
// Each Document should contain four fields:
// 1. The Title of the Book
// 2. The Author of the Book
// 3. The title of the Chapter
// 4. The text of the Chapter

/**
 * @author marco
 */
public class IndexDocumentEx {

    /**
     * @param args the command line arguments
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {


        try {
            // 1. Create a new index (indicate the directory as a parameter)
            FSDirectory fsdir = FSDirectory.open(new File("./resources/alice").toPath());

            // 2. Create a new Analyzer and a new IndexWriter, with their configurations
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            IndexWriter writer = new IndexWriter(fsdir, iwc);

            // 3. Open the text file with the content
            File f = new File("ApacheLucene/MRI_2024_25/resources/Alice_Adv_Lewis_Carroll_utf8.txt");
            Scanner inputFile = new Scanner(f);

            // 4. Go through the content and identify a strategy to isolate the different pieces of text
            //  a) When do you understand that a new chapter has started?
            //  b) When do you understand that a new paragraph has started?
            //  etc.
            String title = "";
            String author = "";
            String chapter_title = "";
            StringBuilder text = new StringBuilder();

            if (inputFile.hasNext()) {
                title = inputFile.nextLine();
            }
            if (inputFile.hasNext()) {
                inputFile.nextLine();
            }
            if (inputFile.hasNext()) {
                author = inputFile.nextLine();
            }
            if (inputFile.hasNext()) {
                inputFile.nextLine();
            }
            if (inputFile.hasNext()) {
                chapter_title = inputFile.nextLine();
            }

            while (inputFile.hasNext()) {
                String currLine = inputFile.nextLine();
                if (currLine.startsWith("CHAPTER")) {
                    chapter_title = currLine;
                    if (inputFile.hasNext()) {
                        inputFile.nextLine();
                    }
                    continue;
                }

                text.append(currLine);

                // End of paragraph
                if (currLine.isEmpty()) {
                    // 5. Store each paragraph (together with the other fields) as a new document.
                    Document doc = new Document();
                    doc.add(new TextField("id", title, Field.Store.NO));
                    doc.add(new TextField("author", author, Field.Store.YES));
                    doc.add(new TextField("chapter", chapter_title, Field.Store.YES));
                    doc.add(new TextField("chapter_text", text.toString(), Field.Store.YES));

                    writer.addDocument(doc);

                    text = new StringBuilder();
                }
            }

            inputFile.close();


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
            QueryParser qp = new QueryParser("chapter_text", new StandardAnalyzer());

            //Parse the query
            Query q = qp.parse("Alice");

            //Search
            TopDocs topdocs = searcher.search(q, 10);
            System.out.println("Found " + topdocs.totalHits.value + " document(s).");

        } catch (IOException ex) {
            Logger.getLogger(IndexDocumentEx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
