package ma.khairy.ragdocsreader.ingestion_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

//@Component
public class Tika implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final VectorStore vectorStore;

    @Value("classpath:/docs/big.pdf")
    private Resource marketPDF;

    public Tika(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) {
        TikaDocumentReader reader = new TikaDocumentReader(marketPDF);
        TextSplitter textSplitter = new TokenTextSplitter();
        log.info("Ingesting PDF file");
        vectorStore.accept(textSplitter.split(reader.read()));
        log.info("Completed Ingesting PDF file");
    }
}
