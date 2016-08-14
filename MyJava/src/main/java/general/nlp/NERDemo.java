package general.nlp;

/**
 * Stanford Named-entity-recognition library demo
 * <p>
 * Created by yazhoucao on 1/21/16.
 */

import edu.stanford.nlp.hcoref.CorefCoreAnnotations;
import edu.stanford.nlp.hcoref.data.CorefChain;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This is a demo of calling CRFClassifier programmatically.
 * <p>
 * Usage: {@code java -mx400m -cp "*" NERDemo [serializedClassifier [fileName]] }
 * <p>
 * If arguments aren't specified, they default to
 * classifiers/english.all.3class.distsim.crf.ser.gz and some hardcoded sample text.
 * If run with arguments, it shows some of the ways to get k-best labelings and
 * probabilities out with CRFClassifier. If run without arguments, it shows some of
 * the alternative output formats that you can get.
 * <p>
 * To use CRFClassifier from the command line:
 * </p><blockquote>
 * {@code java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier [classifier] -textFile [file] }
 * </blockquote><p>
 * Or if the file is already tokenized and one word per line, perhaps in
 * a tab-separated value format with extra columns for part-of-speech tag,
 * etc., use the version below (note the 's' instead of the 'x'):
 * </p><blockquote>
 * {@code java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier [classifier] -testFile [file] }
 * </blockquote>
 *
 * @author Jenny Finkel
 * @author Christopher Manning
 */

public class NERDemo {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize, ner");
    CRFClassifier<CoreLabel> crfClassifier = new CRFClassifier(props);
    List<CoreLabel> labels = generateCoreLabel();
    List<List<CoreLabel>> docs = new ArrayList<>();
    docs.add(labels);
    crfClassifier.train(docs);
    List<List<CoreLabel>> classify = crfClassifier.classify("LAKSHMAN VENEPALL 200 Water St, New York, NY 10038 | 434-258-9649 | lv223@cornell.edu");
    classify.forEach(System.out::println);
  }

  private static List<CoreLabel> generateCoreLabel() {
    String resume = "LAKSHMAN VENEPALLY \n"
        + "200 Water St, New York, NY 10038 | 434-258-9649 | lv223@cornell.edu \n"
        + "EDUCATION \n"
        + "CORNELL UNIVERSITY \n"
        + "Master of Business Administration \n"
        + "Ithaca, NY \n"
        + "May 2013 \n"
        + "• GMAT 750 (98th Percentile) \n"
        + "• Big Red Ventures (Cornell-based VC fund), Associate: Sourced multiple investment opportunities by interacting with \n"
        + "startup CEOs. Executed due diligence on a $350K investment candidate by conducting industry and competitor analyses, \n"
        + "market sizing, and evaluating company strategy. \n"
        + "• Pick2Pay, Inc: Helped launch the startup by co-authoring its initial business plan.  \n"
        + "• Led an impactful advertising campaign, on behalf of the business school’s alumni affairs department, that involved user \n"
        + "acquisition and engagement through social media (Facebook ads), Bing Ads and E-mail (Constant Contact) channels. \n"
        + "• Relevant Courses: Data-driven Marketing, Operations, Cases in Business Strategy, Managerial Statistics, Consumer Behavior, \n"
        + "Disruptive Technologies, Applied Economic Analysis, Financial Modeling \n"
        + "VIRGINIA TECH \n"
        + "Master of Science, Computer Science  \n"
        + "• Awarded full-scholarship and stipend for 3 of 4 semesters \n"
        + "JAWAHARLAL NEHRU TECHNOLOGICAL UNIVERSITY \n"
        + "Bachelor of Technology, Computer Science & Engineering \n"
        + "Blacksburg, VA \n"
        + "Aug 2008 \n"
        + "Hyderabad, India \n"
        + "May 2006 \n"
        + "EXPERIENCE \n"
        + "UMT CONSULTING GROUP \n"
        + "Consultant \n"
        + "New York, NY \n"
        + "2013-Present \n"
        + "• Providing business operations advice to a major pharmaceutical client to help plan, prioritize and manage a portfolio of \n"
        + "over 200 cross-functional corporate projects ($150M+ budget) by conducting qualitative and quantitative analyses that take \n"
        + "into account total costs of ownership, budget constraints and strategic alignment with business drivers. \n"
        + "• Augmented client’s program and workflow governance capabilities by building risk assessment BI dashboard that \n"
        + "highlighted bottleneck projects across the company’s portfolio and calculated impact of schedule delays.  \n"
        + "• Provided data-driven decision making support to the client’s clinical operations group by building analytics dashboards, \n"
        + "using QlikView, Excel and SQL, to extract and present insights from clinical trial data from sites in over 50 countries.  \n"
        + "• Managing relationships and maintaining communication with stakeholders at Director and VP levels. \n"
        + "COGNEX CORP. (NASDAQ: CGNX, $1.6 BILLION COMPANY) \n"
        + "Corporate Strategy Consulting Intern \n"
        + "Natick, MA \n"
        + "2012 \n"
        + "• Developed growth strategy to augment sales through partner channels, and identified acquisition & partnership \n"
        + "opportunities. Defined and measured KPIs. \n"
        + "• Presented actionable recommendations to the CEO of Cognex. \n"
        + "HARRIS CORPORATION (NYSE: HRS, $6.5 BILLION COMPANY) \n"
        + "Software Engineer \n"
        + "Lynchburg, VA \n"
        + "2008-2012 \n"
        + "• Worked as the primary developer on a four person team that delivered $4 million worth of servers and software products to \n"
        + "enterprise customers in FY 2011.  \n"
        + "• Worked with engineering and product managers to define product requirements and roadmaps, and develop business cases. \n"
        + "• Cultivated experience with Stage-Gate and Agile development methodologies. \n"
        + "ADDITIONAL INFORMATION \n"
        + "• Tools: Excel (Regression, Correlation, Solver, Pivots), QlikView, MS Project, SurveyMonkey  \n"
        + "• Expert skills in SQL, multiple programming languages (Java, C#) and APIs. \n"
        + "• Mobile App: Conceived, designed, developed, and launched an Android application, LikesExplorer, that recommends \n"
        + "music, movies, games, books etc. to users based on the ‘likes’ of their Facebook friends. Achieved 20,000+ downloads.  \n"
        + "• Coursework in Wines (School of Hotel Administration, Cornell) \n";
    TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
    List<CoreLabel> tokenizedCoreLabels = tokenizerFactory.getTokenizer(new StringReader(resume)).tokenize();
    tokenizedCoreLabels.forEach(label -> label.set(CoreAnnotations.AnswerAnnotation.class, "O"));
    tokenizedCoreLabels.get(0).set(CoreAnnotations.AnswerAnnotation.class, "NAME");
    tokenizedCoreLabels.get(1).set(CoreAnnotations.AnswerAnnotation.class, "NAME");
    tokenizedCoreLabels.get(12).set(CoreAnnotations.AnswerAnnotation.class, "PHONE");
    tokenizedCoreLabels.get(14).set(CoreAnnotations.AnswerAnnotation.class, "EMAIL");
    tokenizedCoreLabels.stream().map(label -> label.toString(CoreLabel.OutputFormat.MAP)).forEach(System.out::println);
//    TokenizerFactory<Word> wordTokenizerFactory = PTBTokenizer.factory(new WordTokenFactory(), "");
//    List<Word> tokenizedWords = wordTokenizerFactory.getTokenizer(new StringReader(resume)).tokenize();
//    tokenizedWords.stream().map(Word::toString).forEach(System.out::println);
    return tokenizedCoreLabels;
  }

  @SneakyThrows({ IOException.class })
  public static void officialCoreNLPDemo(String[] args) {
    /** Usage: java -cp "*" StanfordCoreNlpDemo [inputFile [outputTextFile [outputXmlFile]]] */

    // set up optional output files
    PrintWriter out;
    if (args.length > 1) {
      out = new PrintWriter(args[1]);
    } else {
      out = new PrintWriter(System.out);
    }
    PrintWriter xmlOut = null;
    if (args.length > 2) {
      xmlOut = new PrintWriter(args[2]);
    }

    // Create a CoreNLP pipeline. To build the default pipeline, you can just use:
    //   StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    // Here's a more complex setup example:
    //   Properties props = new Properties();
    //   props.put("annotators", "tokenize, ssplit, pos, lemma, ner, depparse");
    //   props.put("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
    //   props.put("ner.applyNumericClassifiers", "false");
    //   StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    // Add in sentiment
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");

    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    // Initialize an Annotation with some text to be annotated. The text is the argument to the constructor.
    Annotation annotation;
    if (args.length > 0) {
      annotation = new Annotation(IOUtils.slurpFileNoExceptions(args[0]));
    } else {
      annotation = new Annotation("Kosgi Santosh sent an email to Stanford University. He didn't get a reply.");
    }

    // run all the selected Annotators on this text
    pipeline.annotate(annotation);

    // this prints out the results of sentence analysis to file(s) in good formats
    pipeline.prettyPrint(annotation, out);
    if (xmlOut != null) {
      pipeline.xmlPrint(annotation, xmlOut);
    }

    // Access the Annotation in code
    // The toString() method on an Annotation just prints the text of the Annotation
    // But you can see what is in it with other methods like toShorterString()
    out.println();
    out.println("The top level annotation");
    out.println(annotation.toShorterString());
    out.println();

    // An Annotation is a Map with Class keys for the linguistic analysis types.
    // You can get and use the various analyses individually.
    // For instance, this gets the parse tree of the first sentence in the text.
    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
    if (sentences != null && !sentences.isEmpty()) {
      CoreMap sentence = sentences.get(0);
      out.println("The keys of the first sentence's CoreMap are:");
      out.println(sentence.keySet());
      out.println();
      out.println("The first sentence is:");
      out.println(sentence.toShorterString());
      out.println();
      out.println("The first sentence tokens are:");
      for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
        out.println(token.toShorterString());
      }
      Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
      out.println();
      out.println("The first sentence parse tree is:");
      tree.pennPrint(out);
      out.println();
      out.println("The first sentence basic dependencies are:");
      out.println(sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class).toString(SemanticGraph.OutputFormat.LIST));
      out.println("The first sentence collapsed, CC-processed dependencies are:");
      SemanticGraph graph = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
      out.println(graph.toString(SemanticGraph.OutputFormat.LIST));

      // Access coreference. In the coreference link graph,
      // each chain stores a set of mentions that co-refer with each other,
      // along with a method for getting the most representative mention.
      // Both sentence and token offsets start at 1!
      out.println("Coreference information");
      Map<Integer, CorefChain> corefChains =
          annotation.get(CorefCoreAnnotations.CorefChainAnnotation.class);
      if (corefChains == null) {
        return;
      }
      for (Map.Entry<Integer, CorefChain> entry : corefChains.entrySet()) {
        out.println("Chain " + entry.getKey() + " ");
        for (CorefChain.CorefMention m : entry.getValue().getMentionsInTextualOrder()) {
          // We need to subtract one since the indices count from 1 but the Lists start from 0
          List<CoreLabel> tokens = sentences.get(m.sentNum - 1).get(CoreAnnotations.TokensAnnotation.class);
          // We subtract two for end: one for 0-based indexing, and one because we want last token of mention not one following.
          out.println("  " + m + ", i.e., 0-based character offsets [" + tokens.get(m.startIndex - 1).beginPosition() +
              ", " + tokens.get(m.endIndex - 2).endPosition() + ")");
        }
      }
      out.println();

      out.println("The first sentence overall sentiment rating is " + sentence.get(SentimentCoreAnnotations.SentimentClass.class));
    }
    IOUtils.closeIgnoringExceptions(out);
    IOUtils.closeIgnoringExceptions(xmlOut);
  }

  @SneakyThrows({ IOException.class, ClassNotFoundException.class })
  public static void officialDemo(String[] args) {
    String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";

    if (args.length > 0) {
      serializedClassifier = args[0];
    }

    AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);

    /**
     * For either a file to annotate or for the hardcoded text example, this demo file shows several ways to process the input, for teaching purposes.
     */

    if (args.length > 1) {
      /**
       * For the file, it shows
       * (1) how to run NER on a String,
       * (2) how to get the entities in the String with character offsets, and
       * (3) how to run NER on a whole file (without loading it into a String).
       */

      String fileContents = IOUtils.slurpFile(args[1]);
      List<List<CoreLabel>> out = classifier.classify(fileContents);
      for (List<CoreLabel> sentence : out) {
        for (CoreLabel word : sentence) {
          System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
        }
        System.out.println();
      }

      System.out.println("---");
      out = classifier.classifyFile(args[1]);
      for (List<CoreLabel> sentence : out) {
        for (CoreLabel word : sentence) {
          System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
        }
        System.out.println();
      }

      System.out.println("---");
      List<Triple<String, Integer, Integer>> list = classifier.classifyToCharacterOffsets(fileContents);
      for (Triple<String, Integer, Integer> item : list) {
        System.out.println(item.first() + ": " + fileContents.substring(item.second(), item.third()));
      }
      System.out.println("---");
      System.out.println("Ten best entity labelings");
      DocumentReaderAndWriter<CoreLabel> readerAndWriter = classifier.makePlainTextReaderAndWriter();
      classifier.classifyAndWriteAnswersKBest(args[1], 10, readerAndWriter);

      System.out.println("---");
      System.out.println("Per-token marginalized probabilities");
      classifier.printProbs(args[1], readerAndWriter);

      // -- This code prints out the first order (token pair) clique probabilities.
      // -- But that output is a bit overwhelming, so we leave it commented out by default.
      // System.out.println("---");
      // System.out.println("First Order Clique Probabilities");
      // ((CRFClassifier) classifier).printFirstOrderProbs(args[1], readerAndWriter);

    } else {

      /* For the hard-coded String, it shows how to run it on a single
         sentence, and how to do this and produce several formats, including
         slash tags and an inline XML output format. It also shows the full
         contents of the {@code CoreLabel}s that are constructed by the
         classifier. And it shows getting out the probabilities of different
         assignments and an n-best list of classifications with probabilities.
      */

      String[] example = { "Good afternoon Rajat Raina, how are you today?",
          "I go to school at Stanford University, which is located in California." };
      for (String str : example) {
        System.out.println(classifier.classifyToString(str));
      }
      System.out.println("---");

      for (String str : example) {
        // This one puts in spaces and newlines between tokens, so just print not println.
        System.out.print(classifier.classifyToString(str, "slashTags", false));
      }
      System.out.println("---");

      for (String str : example) {
        // This one is best for dealing with the output as a TSV (tab-separated column) file.
        // The first column gives entities, the second their classes, and the third the remaining text in a document
        System.out.print(classifier.classifyToString(str, "tabbedEntities", false));
      }
      System.out.println("---");

      for (String str : example) {
        System.out.println(classifier.classifyWithInlineXML(str));
      }
      System.out.println("---");

      for (String str : example) {
        System.out.println(classifier.classifyToString(str, "xml", true));
      }
      System.out.println("---");

      for (String str : example) {
        System.out.print(classifier.classifyToString(str, "tsv", false));
      }
      System.out.println("---");

      // This gets out entities with character offsets
      int j = 0;
      for (String str : example) {
        j++;
        List<Triple<String, Integer, Integer>> triples = classifier.classifyToCharacterOffsets(str);
        for (Triple<String, Integer, Integer> trip : triples) {
          System.out.printf("%s over character offsets [%d, %d) in sentence %d.%n",
              trip.first(), trip.second(), trip.third, j);
        }
      }
      System.out.println("---");

      // This prints out all the details of what is stored for each token
      int i = 0;
      for (String str : example) {
        for (List<CoreLabel> lcl : classifier.classify(str)) {
          for (CoreLabel cl : lcl) {
            System.out.print(i++ + ": ");
            System.out.println(cl.toShorterString());
          }
        }
      }

      System.out.println("---");

    }
  }

}
