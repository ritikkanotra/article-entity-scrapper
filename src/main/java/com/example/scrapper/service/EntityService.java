package com.example.scrapper.service;

import com.example.scrapper.model.Entity;
import com.example.scrapper.model.EntityRelation;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.jsoup.Jsoup;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntityService {

    private final String standfordNerModelFileName = "english.all.3class.distsim.crf.ser.gz";

    public List<Entity> getEntities(String articleUrl) {

//        articleUrl = "https://indianexpress.com/article/technology/tech-news-technology/these-iphone-models-wont-get-apples-ios-17-8943436/";

        List<Entity> entitiyList = new ArrayList<>();
        String articleText = getArticleTextFromUrl(articleUrl);

        String modelPath = null;
        try {
            ClassPathResource resource = new ClassPathResource(standfordNerModelFileName);
            modelPath = resource.getFile().getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a CRFClassifier with the NER model
        CRFClassifier<CoreLabel> nerClassifier = CRFClassifier.getClassifierNoExceptions(modelPath);

        // Perform NER on the text
        List<List<CoreLabel>> entities = nerClassifier.classify(articleText);

        // Extract and print named entities
        for (List<CoreLabel> sentenceLabels : entities) {
            for (CoreLabel label : sentenceLabels) {
                String word = label.word();
                String nerTag = label.get(CoreAnnotations.AnswerAnnotation.class);
                if (!nerTag.equals("O")) {
                    entitiyList.add(new Entity(word, nerTag));
                }
            }
        }
        return entitiyList;
    }

    public List<EntityRelation> getEntitiesRelation(String articleUrl) {

        String articleText = getArticleTextFromUrl(articleUrl);
        List<EntityRelation> entityRelationList = new ArrayList<>();

        Document doc = new Document(articleText);

        // Extract relations using Stanford OpenIE
        for (Sentence sent : doc.sentences()) {
            for (RelationTriple triple : sent.openieTriples()) {
                String subject = triple.subjectLemmaGloss();
                String relation = triple.relationLemmaGloss();
                String object = triple.objectLemmaGloss();

                entityRelationList.add(new EntityRelation(subject, relation, object));
            }
        }
        return entityRelationList;
    }

    private String getArticleTextFromUrl(String articleUrl) {

//        articleUrl = "https://indianexpress.com/article/technology/tech-news-technology/these-iphone-models-wont-get-apples-ios-17-8943436/";
        String articleText = "";
        try {
            org.jsoup.nodes.Document document = Jsoup.connect(articleUrl).get();
            articleText = document.text();
            System.out.println(articleText);
            // Now you have the text content of the article.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articleText;
    }

}
