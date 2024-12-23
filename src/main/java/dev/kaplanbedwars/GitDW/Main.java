package dev.kaplanbedwars.GitDW;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static JTextArea terminalArea;
    private static JTextField inputField;
    private static List<String> commandHistory = new ArrayList<>();
    private static int historyIndex = 0;

    private static final String downloadDir = "downloads";
    private static final String configFile = "dw.xml";

    public static void main(String[] args) {
        // FlatLaf teması
        FlatDarkLaf.setup();

        // Ana pencere
        JFrame frame = new JFrame("Windows 11 CMD");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Ana panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));

        // Terminal ekranı
        terminalArea = new JTextArea();
        terminalArea.setEditable(false);
        terminalArea.setBackground(new Color(30, 30, 30));
        terminalArea.setForeground(new Color(220, 220, 220));
        terminalArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        terminalArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(terminalArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Komut giriş alanı
        inputField = new JTextField();
        inputField.setBackground(new Color(40, 40, 40));
        inputField.setForeground(new Color(220, 220, 220));
        inputField.setCaretColor(Color.WHITE);
        inputField.setFont(new Font("Consolas", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Komut geçmişi için dinleyici
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (historyIndex > 0) {
                        historyIndex--;
                        inputField.setText(commandHistory.get(historyIndex));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (historyIndex < commandHistory.size() - 1) {
                        historyIndex++;
                        inputField.setText(commandHistory.get(historyIndex));
                    } else {
                        inputField.setText("");
                    }
                }
            }
        });

        inputField.addActionListener(e -> {
            String command = inputField.getText().trim();
            if (!command.isEmpty()) {
                commandHistory.add(command);
                historyIndex = commandHistory.size();
                inputField.setText("");
                processCommand(command);
            }
        });

        // Alt panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(30, 30, 30));
        inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);

        // Panelleri birleştirme
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);

        log("Windows 11 CMD'ye hoş geldiniz. Komutlarınızı yazabilirsiniz:");
        log("Komutlar:");
        log("  open -xml   : dw.xml dosyasını not defterinde açar.");
        log("  re -DW      : İndirme işlemini yeniden başlatır.");
        log("  run         : XML dosyasını okuyup bağımlılıkları indirir.");
        log("  clear       : Terminal ekranını temizler.");
        log("  exit        : Uygulamadan çıkar.");
    }

    private static void processCommand(String command) {
        log("> " + command);
        switch (command.toLowerCase()) {
            case "open -xml":
                openXmlFile();
                break;
            case "re -dw":
                restartDownload();
                break;
            case "run":
                runMainTask();
                break;
            case "clear":
                terminalArea.setText("");
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                logError("Geçersiz komut: " + command);
        }
    }

    private static void openXmlFile() {
        try {
            File file = new File(configFile);
            if (!file.exists()) {
                logError("dw.xml bulunamadı.");
                return;
            }
            Desktop.getDesktop().edit(file);
            log("dw.xml açıldı.");
        } catch (IOException e) {
            logError("dw.xml açılamadı: " + e.getMessage());
        }
    }

    private static void restartDownload() {
        log("İndirme işlemi yeniden başlatılıyor...");
        runMainTask();
    }

    private static void runMainTask() {
        log("Bağımlılıkları indiriliyor...");
        if (!new File(configFile).exists()) {
            logError("dw.xml bulunamadı.");
            return;
        }

        ProjectConfig config = ConfigReader.readConfig(configFile);
        if (config != null) {
            log("Proje: " + config.getName() + ", Sürüm: " + config.getVersion());
            List<String> links = config.getGithubLinks();
            for (String link : links) {
                String downloadedFile = GitHubDownloader.downloadFile(link, downloadDir);
                if (downloadedFile != null) {
                    log("İndirildi: " + downloadedFile);
                } else {
                    logError("İndirme başarısız: " + link);
                }
            }
            log("Tüm dosyalar indirildi. Klasör: " + new File(downloadDir).getAbsolutePath());
        } else {
            logError("Proje yapılandırması yüklenemedi.");
        }
    }

    private static void log(String message) {
        terminalArea.append(message + "\n");
        terminalArea.setCaretPosition(terminalArea.getDocument().getLength());
    }

    private static void logError(String message) {
        terminalArea.append("[HATA] " + message + "\n");
        terminalArea.setCaretPosition(terminalArea.getDocument().getLength());
    }
}
