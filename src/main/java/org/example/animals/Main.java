package org.example.animals;

import org.example.data.BinaryTree;
import org.example.processor.GameProcessorFactory;
import org.example.processor.IGameProcessor;
import org.example.processor.impl.AnimalListProcessor;
import org.example.processor.impl.AnimalSearcher;
import org.example.processor.impl.BinaryAnimalLearner;
import org.example.processor.impl.KnowledgeTreeProcessor;
import org.example.processor.impl.StatisticsProcessor;
import org.example.utils.StartupArgumentsUtil;
import org.example.utils.LanguageRules;
import org.example.utils.ObjectMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileFormat = StartupArgumentsUtil.getFileExtension(args);
        String languageCode = StartupArgumentsUtil.getLanguageCode();
        BinaryTree binaryTree = new BinaryTree(fileFormat, ObjectMapping.OBJECT_MAPPERS.get(fileFormat), languageCode);
        LanguageRules.setLanguage(languageCode);
        List<IGameProcessor> gameProcessors = new ArrayList<>() {{
            add(new AnimalListProcessor(binaryTree));
            add(new BinaryAnimalLearner(binaryTree));
            add(new AnimalSearcher(binaryTree));
            add(new StatisticsProcessor(binaryTree));
            add(new KnowledgeTreeProcessor(binaryTree));
        }};
        gameProcessors.sort(Comparator.comparing(IGameProcessor::getNumber));
        GameProcessorFactory gameProcessorFactory = new GameProcessorFactory(gameProcessors);
        Application app = new Application(gameProcessorFactory, binaryTree);
        app.run();
        binaryTree.save();
    }
}
