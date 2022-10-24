package animals;

import data.BinaryTreeDao;
import processor.GameProcessorFactory;
import processor.IGameProcessor;
import processor.impl.AnimalListProcessor;
import processor.impl.AnimalSearcher;
import processor.impl.BinaryAnimalLearner;
import processor.impl.KnowledgeTreeProcessor;
import processor.impl.StatisticsProcessor;
import utils.CliArgumentsProcessor;
import utils.LanguageRules;
import utils.ObjectMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileFormat = CliArgumentsProcessor.getFileExtension(args);
        String languageCode = CliArgumentsProcessor.getLanguageCode();
        BinaryTreeDao binaryTreeDao = new BinaryTreeDao(fileFormat, ObjectMapping.OBJECT_MAPPERS.get(fileFormat), languageCode);
        LanguageRules.setLanguage(languageCode);
        List<IGameProcessor> gameProcessors = new ArrayList<>() {{
            add(new AnimalListProcessor(binaryTreeDao));
            add(new BinaryAnimalLearner(binaryTreeDao));
            add(new AnimalSearcher(binaryTreeDao));
            add(new StatisticsProcessor(binaryTreeDao));
            add(new KnowledgeTreeProcessor(binaryTreeDao));
        }};
        gameProcessors.sort(Comparator.comparing(IGameProcessor::getNumber));
        GameProcessorFactory gameProcessorFactory = new GameProcessorFactory(gameProcessors);
        Application app = new Application(gameProcessorFactory, binaryTreeDao);
        app.run();
        binaryTreeDao.save();
    }
}
