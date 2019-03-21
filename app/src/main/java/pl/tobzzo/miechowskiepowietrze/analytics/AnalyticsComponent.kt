package pl.tobzzo.miechowskiepowietrze.analytics

interface AnalyticsComponent {
  fun initialize()
  fun logAction(category: String, action: String)
  fun logScreen(screenName: String)
}