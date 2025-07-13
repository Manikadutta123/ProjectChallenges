import java.util.prefs.Preferences;

public class PreferencesManager {
    private static final String CITY_KEY = "preferredCity";
    private final Preferences prefs;

    public PreferencesManager() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
    }

    public void saveCity(String city) {
        prefs.put(CITY_KEY, city);
    }

    public String getSavedCity() {
        return prefs.get(CITY_KEY, "");
    }

    public void clearCity() {
        prefs.remove(CITY_KEY);
    }
}
