package acme.me.cache.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;

public class LuceneView {
    public static void main(String[] args) throws IOException, ParseException {
        // 0. Specify the analyzer for tokenizing text.
        // The same analyzer should be used for indexing and searching
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // 1. create the index
        Directory directory = new SimpleFSDirectory(FileSystems.getDefault().getPath("D:\\lucene\\"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter iwriter = new IndexWriter(directory, config);
        iwriter.deleteAll();
        addDoc(iwriter, "Lucene in Action", "193398817");
        addDoc(iwriter, "Lucene for Dummies", "55320055Z");
        addDoc(iwriter, "Managing Gigabytes", "55063554A");
        addDoc(iwriter, "The Art of Computer Science", "9900333X");
        iwriter.close();

        // 2. query
        String querystr = args.length > 0 ? args[0] : "lucene";

        // the "title" arg specifies the default field to use
        // when no field is explicitly specified in the query.
        Query q = new QueryParser("title", analyzer).parse(querystr);

        // 3. search
        int hitsPerPage = 10;
        IndexReader ireader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(ireader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // 4. display results
        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
        }

        // reader can only be closed when there
        // is no need to access the documents any more.
        ireader.close();
    }

    /**
     * 注意，对于需要分词的内容我们使用TextField，对于像id这样不需要分词的内容我们使用StringField。
     *
     * @param w
     * @param title
     * @param isbn
     * @throws IOException
     */
    private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        w.addDocument(doc);
    }
}
