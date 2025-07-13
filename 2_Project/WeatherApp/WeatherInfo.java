public class WeatherInfo {
    private String description;
    private double temperature;
    private double feelsLike;
    private String date; // Optional, for forecast
    private String iconPath;

    public WeatherInfo(String description, double temperature, double feelsLike, String iconPath) {
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.iconPath = iconPath;
    }

    public WeatherInfo(String date, String description, double temperature) {
        this.date = date;
        this.description = description;
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public String getDate() {
        return date;
    }

    public String getIconPath() {
        return iconPath;
    }
}
