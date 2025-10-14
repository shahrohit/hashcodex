package utils

var LanguageTimeMultipliers = map[string]float64{
    "cpp":    1.0,
    "java":   2.0,
    "python": 5.0,
}

func GetTimeLimit(language string, baseTimeLimit float64) float64 {
    multiplier, exists := LanguageTimeMultipliers[language]
    if !exists { multiplier = 2.0 }

    effective := baseTimeLimit * multiplier
    return min(effective, 15.0)

}