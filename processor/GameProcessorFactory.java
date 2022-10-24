package processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameProcessorFactory {
    private final Map<Integer, IGameProcessor> gameProcessors;
    private final List<String> descriptions = new ArrayList<>();

    public GameProcessorFactory(List<IGameProcessor> processors) {
        this.gameProcessors = processors.stream()
                .peek(p -> descriptions.add(p.getDescription()))
                .collect(Collectors.toMap(IGameProcessor::getNumber, Function.identity()));

    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public IGameProcessor getProcessor(int number) {
        IGameProcessor processor = gameProcessors.get(number);
        if (processor == null) {
            throw new IllegalArgumentException("No such processor");
        }
        return processor;
    }
}
