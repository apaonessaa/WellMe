package com.example.wellme.utils

/**
 *  Mood constant data useful for MoodActivity
 */

object MoodData {

    val DEFAULT_STATE = 3  // Neutral state by default

    val states = listOf(
        "Very Unpleasant",
        "Unpleasant",
        "Slightly Unpleasant",
        "Neutral",
        "Slightly Pleasant",
        "Pleasant",
        "Very Pleasant"
    )

    val details = mapOf(
        "Very Unpleasant" to listOf(
            "Deep Sadness", "Despair", "Severe Anxiety", "Panic", "Hopelessness",
            "Severe Stress", "Overwhelm", "Guilt", "Shame"
        ),
        "Unpleasant" to listOf(
            "Frustration", "Disappointment", "Discouragement", "Loneliness",
            "Irritation", "Jealousy", "Bitterness", "Dissatisfaction"
        ),
        "Slightly Unpleasant" to listOf(
            "Boredom", "Apathy", "Skepticism", "Mild Anxiety", "Insecurity",
            "Lack of Motivation", "Doubt", "Mild Fatigue"
        ),
        "Neutral" to listOf(
            "Satisfaction", "Calmness", "Serenity", "Indifference",
            "Fatigue", "Equanimity", "Composure", "Contentment"
        ),
        "Slightly Pleasant" to listOf(
            "Curiosity", "Hope", "Mild Joy", "Excitement", "Optimism",
            "Enthusiasm", "Confidence", "Amusement", "Relief",
            "Satisfaction", "Tranquility", "Self-Appreciation"
        ),
        "Pleasant" to listOf(
            "Happiness", "Euphoria", "Fulfillment", "Passion",
            "Admiration", "Warmth", "Connectedness", "Pride"
        ),
        "Very Pleasant" to listOf(
            "Pure Joy", "Ecstasy", "Unconditional Love", "Gratitude",
            "Spiritual Awakening", "Profound Connection", "Inspiration"
        )
    )

    val causes = listOf(
        "💪 Physical Health", "🏃 Exercise", "🛁 Personal Care", "🎨 Hobbies", "🆔 Identity",
        "🕊️ Spirituality", "🎉 Social Life", "👨‍👩‍👧‍👦 Family", "🫂 Friendships", "💑 Romantic Relationship",
        "❤️ Love Life", "📋 Responsibilities", "💼 Work", "🎓 Education", "📈 Career Growth",
        "💰 Financial Stability", "⛅ Weather", "📰 News & Current Events", "😴 Sleep Quality",
        "🥗 Diet & Nutrition", "🌟 Self-Esteem", "🏆 Personal Achievements", "🎭 Creativity",
        "🔍 Self-Discovery", "🧘 Mental Clarity", "📚 Personal Development",
        "🏡 Surrounding Environment", "🌿 Nature Exposure", "📱 Technology & Social Media"
    )
}
