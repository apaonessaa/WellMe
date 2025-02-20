package com.example.wellme.utils

/**
 *  Mood constant data useful for MoodActivity
 */

object MoodData {

    val DEFAULT_STATE = 3

    val states = listOf(
        "Molto Spiacevole",
        "Spiacevole",
        "Leggermente Spiacevole",
        "Neutro",
        "Leggermente Piacevole",
        "Piacevole",
        "Molto Piacevole"
    )

    val details = mapOf(
        "Molto Spiacevole" to listOf("Tristezza", "Disperazione", "Ansia"),
        "Spiacevole" to listOf("Frustrazione", "Delusione", "Sconforto"),
        "Leggermente Spiacevole" to listOf("Noia", "Sottotono", "Scetticismo"),
        "Neutro" to listOf("Soddisfazione", "Calma", "Serenità", "Indifferenza", "Spossatezza"),
        "Leggermente Piacevole" to listOf(
            "Stupore", "Entusiasmo", "Sorpresa", "Passione", "Felicità", "Gioia",
            "Coraggio", "Orgoglio", "Fiducia in sé", "Speranza", "Divertimento", "Appagamento",
            "Sollievo", "Gratitudine", "Soddisfazione", "Calma", "Serenità"
        ),
        "Piacevole" to listOf("Gioia", "Entusiasmo", "Appagamento"),
        "Molto Piacevole" to listOf("Euforia", "Felicità", "Amore", "Gratitudine")
    )

    val causes = listOf(
        "Salute", "Attività Fisica", "Cura personale", "Hobby", "Identità", "Spiritualità",
        "Vita sociale", "Famiglia", "Amicizie", "Partner", "Vita sentimentale", "Impegni",
        "Lavoro", "Istruzione", "Viaggi", "Meteo", "Eventi di attualità", "Denaro"
    )
}