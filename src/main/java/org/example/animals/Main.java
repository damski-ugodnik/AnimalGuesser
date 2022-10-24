package org.example.animals;

import org.example.data.BinaryTreeDao;
import org.example.processor.GameProcessorFactory;
import org.example.processor.IGameProcessor;
import org.example.processor.impl.AnimalListProcessor;
import org.example.processor.impl.AnimalSearcher;
import org.example.processor.impl.BinaryAnimalLearner;
import org.example.processor.impl.KnowledgeTreeProcessor;
import org.example.processor.impl.StatisticsProcessor;
import org.example.utils.CliArgumentsProcessor;
import org.example.utils.LanguageRules;
import org.example.utils.ObjectMapping;

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
