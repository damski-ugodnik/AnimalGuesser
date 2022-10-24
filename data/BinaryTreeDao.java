package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Node;
import utils.Constants;

import java.io.File;
import java.io.IOException;

public class BinaryTreeDao {
    private Node root;
    private final String fileName;
    private final ObjectMapper objectMapper;

    public BinaryTreeDao(String fileExtension, ObjectMapper objectMapper, String language) {
        if (!language.equals("")) {
            language = "_" + language;
        }
        language = language + ".";
        this.fileName = Constants.WORKING_DIRECTORY_PATH_WITHOUT_EXTENSION.replace(".", language) + fileExtension;
        this.objectMapper = objectMapper;
        init();
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void save() {
        try {
            File file = new File(fileName);
            file.createNewFile();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            File file = new File(fileName);
            root = null;
            if (file.exists()) {
                root = objectMapper.readValue(file, Node.class);
            }
        } catch (IOException e) {
            root = null;
        }
    }
}
