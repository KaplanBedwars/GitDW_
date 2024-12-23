package dev.kaplanbedwars.GitDW;

import dev.kaplanbedwars.erormessage.ErrorLog;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader {
    public static ProjectConfig readConfig(String fileName) {
        try {
            File xmlFile = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            String name = doc.getElementsByTagName("name").item(0).getTextContent();
            String version = doc.getElementsByTagName("version").item(0).getTextContent();

            NodeList links = doc.getElementsByTagName("link");
            List<String> githubLinks = new ArrayList<>();
            for (int i = 0; i < links.getLength(); i++) {
                githubLinks.add(links.item(i).getTextContent());
            }

            return new ProjectConfig(name, version, githubLinks);
        } catch (Exception e) {
            ErrorLog.logError(e, "XML dosyası okunurken bir hata oluştu.");
            return null;
        }
    }
}

class ProjectConfig {
    private final String name;
    private final String version;
    private final List<String> githubLinks;

    public ProjectConfig(String name, String version, List<String> githubLinks) {
        this.name = name;
        this.version = version;
        this.githubLinks = githubLinks;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public List<String> getGithubLinks() {
        return githubLinks;
    }
}
