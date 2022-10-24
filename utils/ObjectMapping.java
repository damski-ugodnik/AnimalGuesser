package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public class ObjectMapping {
    public static final Map<String, ObjectMapper> OBJECT_MAPPERS = Map.of(
            "json", new JsonMapper(), "xml", new XmlMapper(), "yaml", new YAMLMapper());
}
