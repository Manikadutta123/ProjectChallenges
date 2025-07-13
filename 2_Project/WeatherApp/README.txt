🌦 Java Swing Weather App
==========================

📁 Requirements:
- Java 8 or higher
- Internet connection
- `json-20210307.jar` library (in project folder)
- PNG icon files from OpenWeatherMap in `icons/` folder

📦 Setup:
1. Place `json-20210307.jar` and all Java files in one folder.
2. Create an `icons/` folder.
3. Download icon PNGs from:
   https://openweathermap.org/weather-conditions
   (Save them like `01d.png`, `01n.png`, etc.)

⚙️ Compile:
> javac -cp .;json-20210307.jar *.java

▶️ Run:
> java -cp .;json-20210307.jar WeatherAppUI

🎯 Features:
- Current weather display
- Unit toggle (°C/°F)
- Weather icon
- Save preferred city
- Clean UI design
