package gui.mainMenuTabPane;

import javafx.concurrent.Task;
import market.Market;
import xml.XmlSystemFactory;

import java.io.File;

public class MarketLoaderTask extends Task<Market> {
    private final XmlSystemFactory factory;
    private final File chosenFile;

    public MarketLoaderTask(XmlSystemFactory factory, File chosenFile) {
        this.factory = factory;
        this.chosenFile = chosenFile;
    }

    @Override
    protected Market call() {
        return factory.createMarket(chosenFile, (percentage, text) -> {
            updateMessage(text);
            updateProgress(percentage, 1.0);

            try {
                Thread.sleep(200); // Give the feeling of loading
            } catch (InterruptedException e) {
                if (isCancelled()) {
                    updateMessage("Cancelled");
                    updateProgress(1.0, 1.0);
                }
            }
        });
    }
}
