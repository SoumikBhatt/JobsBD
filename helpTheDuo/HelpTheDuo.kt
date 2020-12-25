
import kotlin.math.ln
import kotlin.math.roundToInt


fun main() {

    // given num of Blankets Konka has is 53
    // num of Blankets Titli has is 36
    // So, total num of Blanket is: 53+36 = 89
    // So, now the num of ngo's they can help will be
    // with the help of FIBONACCI Series:
    // Total steps/addition needed to find the value of total blankets

    val konkaBlanket = 53
    val titliBlanket = 36

    val totalBlanket = konkaBlanket + titliBlanket

    println("NGOs they can Help is: ${findNumOfNGO(totalBlanket)}")
}

fun findNumOfNGO(n: Int) = (2.078087f * ln(n.toDouble()).toFloat() + 1.672276f).roundToInt()