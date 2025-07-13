import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.json.JSONObject;

public class WeatherAppUI {
    private JFrame frame;
    private JTextField cityField;
    private JTextArea resultArea;
    private JLabel iconLabel;
    private JCheckBox unitToggle;
    private final WeatherService weatherService = new WeatherService();
    private final PreferencesManager preferences = new PreferencesManager();

    public WeatherAppUI() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Weather App");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with padding and background
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(173, 216, 230)); // Light blue
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel cityLabel = new JLabel("Enter City:");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 18));

        cityField = new JTextField(15);
        cityField.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton getWeatherBtn = new JButton("Get Weather");
        getWeatherBtn.setFont(new Font("Arial", Font.BOLD, 16));

        unitToggle = new JCheckBox("Show in °F");
        unitToggle.setFont(new Font("Arial", Font.PLAIN, 16));
        unitToggle.setOpaque(false); // Transparent for background

        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        iconLabel = new JLabel();
        iconLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton saveBtn = new JButton("Save City");
        saveBtn.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton clearBtn = new JButton("Clear Saved");
        clearBtn.setFont(new Font("Arial", Font.PLAIN, 14));

        // Top panel for city input
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.add(cityLabel);
        topPanel.add(cityField);
        topPanel.add(getWeatherBtn);

        // Options panel at bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(unitToggle);
        bottomPanel.add(saveBtn);
        bottomPanel.add(clearBtn);

        // Center area for result + icon
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        centerPanel.add(iconLabel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);

        // Add listeners
        getWeatherBtn.addActionListener(e -> showWeather());
        saveBtn.addActionListener(e -> preferences.saveCity(cityField.getText()));
        clearBtn.addActionListener(e -> {
            preferences.clearCity();
            cityField.setText("");
        });

        String savedCity = preferences.getSavedCity();
        if (!savedCity.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Saved City: " + savedCity);
            cityField.setText(savedCity);
        }
    }

    private void showWeather() {
        String city = cityField.getText();
        boolean isCelsius = !unitToggle.isSelected();

        try {
            WeatherInfo info = weatherService.fetchWeather(city, isCelsius);
            String unit = isCelsius ? "°C" : "°F";

            resultArea.setText(String.format("City: %s\nWeather: %s\nTemp: %.1f%s\nFeels like: %.1f%s",
                capitalize(city),
                capitalize(info.getDescription()),
                info.getTemperature(), unit,
                info.getFeelsLike(), unit));

            ImageIcon icon = new ImageIcon(info.getIconPath());
            iconLabel.setIcon(icon);
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
            iconLabel.setIcon(null);
        }
    }

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherAppUI::new);
    }
}
