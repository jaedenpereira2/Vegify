# Vegify

Vegify is a Kotlin-based Android application that helps users generate recipes and get nutritional information based on ingredients, servings, and available time. This is my first project in Kotlin, created to showcase my learning and skills.

## Features
- Generate recipes using AI based on user-provided ingredients
- Get nutritional information for meals
- Modern Android UI with Jetpack Compose

## Getting Started

### Prerequisites
- Android Studio (latest version recommended)
- Kotlin 1.8+
- Internet connection (for API calls)

### Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/vegify.git
   cd vegify
   ```
2. **Set up the API Key:**
   - The app requires a HuggingFace API key for recipe and nutrition generation.
   - **Do NOT hardcode your API key in the source code.**
   - Set the API key as an environment variable named `VEGIFY_API_KEY` on your development machine:
     - On Windows (PowerShell):
       ```powershell
       $env:VEGIFY_API_KEY="your_huggingface_api_key"
       ```
     - On macOS/Linux:
       ```bash
       export VEGIFY_API_KEY="your_huggingface_api_key"
       ```
   - Alternatively, you can use a `local.properties` file (not committed to version control) for local development.

3. **Open the project in Android Studio** and run the app on an emulator or device.

## Hiding API Keys
- API keys are loaded from environment variables and are never committed to the repository.
- Make sure not to share your API keys publicly.
- Add any local configuration files (like `local.properties`) to `.gitignore`.

## Contributing
This project is for learning purposes, but feel free to fork and suggest improvements!

## License
[MIT](LICENSE) 