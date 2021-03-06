package uz.creater.adiblar.utils

import java.util.*


object CyrillicLatinConverter {
    var cyrilic = charArrayOf(
        '\u0410', '\u0430',  //A
        '\u0411', '\u0431',  //B
        '\u0412', '\u0432',  //V
        '\u0413', '\u0433',  //G
        '\u0414', '\u0434',  //D
        '\u0402', '\u0452',  //?
        '\u0415', '\u0435',  //E
        '\u0416', '\u0436',  //?
        '\u0417', '\u0437',  //Z
        '\u0418', '\u0438',  //I
        '\u0408', '\u0458',  //J
        '\u041A', '\u043A',  //K
        '\u041B', '\u043B',  //L
        '\u0409', '\u0459',  //Lj
        '\u041C', '\u043C',  //M
        '\u041D', '\u043D',  //N
        '\u040A', '\u045A',  //Nj
        '\u041E', '\u043E',  //O
        '\u041F', '\u043F',  //P
        '\u0420', '\u0440',  //R
        '\u0421', '\u0441',  //S
        '\u0422', '\u0442',  //T
        '\u040B', '\u045B',  //?
        '\u0423', '\u0443',  //U
        '\u0424', '\u0444',  //F
        '\u0425', '\u0445',  //H
        '\u0426', '\u0446',  //C
        '\u0427', '\u0447',  //?
        '\u040F', '\u045F',  //D?
        '\u0428', '\u0448' //?
    )
    var latin = arrayOf(
        "A", "a",
        "B", "b",
        "V", "v",
        "G", "g",
        "D", "d",
        "\u0110", "\u0111",
        "E", "e",
        "\u017D", "\u017E",
        "Z", "z",
        "I", "i",
        "J", "j",
        "K", "k",
        "L", "l",
        "Lj", "lj",
        "M", "m",
        "N", "n",
        "Nj", "nj",
        "O", "o",
        "P", "p",
        "R", "r",
        "S", "s",
        "T", "t",
        "\u0106", "\u0107",
        "U", "u",
        "F", "f",
        "H", "h",
        "C", "c",
        "\u010C", "\u010D",
        "D\u017E", "d\u017E",
        "\u0160", "\u0161"
    )

    /**
     * Mapping of cyrillic characters to latin characters.
     */
    var cyrMapping: MutableMap<Char, String> = HashMap()

    /**
     * Mapping of latin characters to cyrillic characters.
     */
    var latMapping: MutableMap<String, Char> = HashMap()
    //*************************************************************************
    //*                            API methods                                *
    //*************************************************************************
    /**
     * Converts latin text to Serbian cyrillic
     *
     * @param latinText - Latin text to be converted to cyrillic.
     *
     * @return - Serbian cyrillic representation of given latin text.
     */
    fun latinToCyrillic(latinText: String?): String {
//        val latBuffer = StringBuffer(latinText)
//        val cyrBuffer = StringBuffer()
//        var i = 0
//        while (i < latBuffer.length) {
//            var s = latBuffer.substring(i, i + 1)
//            if (i < latBuffer.length - 1) {
//                val c = latBuffer[i + 1]
//                if ((s == "L" || s == "l" || s == "N" || s == "n") && (c == 'J' || c == 'j')) {
//                    s += 'j'
//                    i++
//                } else if ((s == "D" || s == "d")
//                    && (c == '\u017D' || c == '\u017E')
//                ) {
//                    s += '\u017E'
//                    i++
//                }
//            }
//            if (latMapping.containsKey(s)) {
//                cyrBuffer.append(latMapping[s]!!.toChar())
//            } else {
//                cyrBuffer.append(s)
//            }
//            i++
//        }
//        return cyrBuffer.toString()
        var text = ""
        if (latinText != null) {
            text = latinText
        }

        text = text.replace("Yu", "??")
        text = text.replace("yu", "??")
        text = text.replace("Ya", "??")
        text = text.replace("ya", "??")
        text = text.replace("Ch", "??")
        text = text.replace("ch", "??")
        text = text.replace("Sh", "??")
        text = text.replace("sh", "??")
        text = text.replace("Sh", "??")
        text = text.replace("sh", "??")

        text = text.replace("G'", "??")
        text = text.replace("g'", "??")
        text = text.replace("O'", "??")
        text = text.replace("o'", "??")
        text = text.replace("G??", "??")
        text = text.replace("g??", "??")
        text = text.replace("O??", "??")
        text = text.replace("o??", "??")
        text = text.replace("G???", "??")
        text = text.replace("g???", "??")
        text = text.replace("O???", "??")
        text = text.replace("o???", "??")
        text = text.replace("G`", "??")
        text = text.replace("g`", "??")
        text = text.replace("O`", "??")
        text = text.replace("o`", "??")
        text = text.replace("G???", "??")
        text = text.replace("g???", "??")
        text = text.replace("O???", "??")
        text = text.replace("o???", "??")

//        text = text.replace("??", "??")
//        text = text.replace("???", "??")
//        text = text.replace("`", "??")
//        text = text.replace("'", "??")
        text = text.replace("???", "??")

        text = text.replace("Yo", "??")
        text = text.replace("yo", "??")

        text = text.replace("A", "??")
        text = text.replace("a", "??")
        text = text.replace("B", "??")
        text = text.replace("b", "??")
        text = text.replace("V", "??")
        text = text.replace("v", "??")
        text = text.replace("G", "??")
        text = text.replace("g", "??")
        text = text.replace("D", "??")
        text = text.replace("d", "??")
        text = text.replace("E", "??")
        text = text.replace("e", "??")
        text = text.replace("J", "??")
        text = text.replace("j", "??")
        text = text.replace("Z", "??")
        text = text.replace("z", "??")
        text = text.replace("I", "??")
        text = text.replace("i", "??")
        text = text.replace("Y", "??")
        text = text.replace("y", "??")
        text = text.replace("K", "??")
        text = text.replace("k", "??")
        text = text.replace("L", "??")
        text = text.replace("l", "??")
        text = text.replace("M", "??")
        text = text.replace("m", "??")
        text = text.replace("N", "??")
        text = text.replace("n", "??")
        text = text.replace("O", "??")
        text = text.replace("o", "??")
        text = text.replace("P", "??")
        text = text.replace("p", "??")
        text = text.replace("R", "??")
        text = text.replace("r", "??")
        text = text.replace("S", "??")
        text = text.replace("s", "??")
        text = text.replace("T", "??")
        text = text.replace("t", "??")
        text = text.replace("U", "??")
        text = text.replace("u", "??")
        text = text.replace("F", "??")
        text = text.replace("f", "??")
        text = text.replace("X", "??")
        text = text.replace("x", "??")
        text = text.replace("C", "??")
        text = text.replace("c", "??")
        text = text.replace("E", "??")
        text = text.replace("e", "??")
        text = text.replace("H", "??")
        text = text.replace("h", "??")
        text = text.replace("Q", "??")
        text = text.replace("q", "??")
        return text
    }

    /**
     * Converts given Serbian cyrillic text to latin text.
     *
     * @param cyrillicText - Cyrillic text to be converted to latin text.
     *
     * @return latin representation of given cyrillic text.
     */
    fun cyrilicToLatin(cyrillicText: String?): String {
//       val cyrBuffer = StringBuffer(cyrillicText)
//        val latinBuffer = StringBuffer()
//        for (element in cyrBuffer) {
//            val c = element
//            if (cyrMapping.containsKey(c)) {
//                latinBuffer.append(cyrMapping[c])
//            } else {
//                latinBuffer.append(c)
//            }
//        }
//        return latinBuffer.toString()
        var text = ""
        if (cyrillicText != null) {
            text = cyrillicText
        }
        text = text.replace("??", "Yu")
        text = text.replace("??", "yu")
        text = text.replace("????", "yuye")
        text = text.replace("??", "Ya")
        text = text.replace("??", "ya")
        text = text.replace("??", "Ch")
        text = text.replace("??", "ch")
        text = text.replace("??", "Sh")
        text = text.replace("??", "sh")
        text = text.replace("??", "Sh")
        text = text.replace("??", "sh")
        text = text.replace("??", "Yo")

        text = text.replace("????", "yoye")
        text = text.replace("??", "yo")
        text = text.replace("??", "G'")
        text = text.replace("??", "g'")
        text = text.replace("??", "O'")
        text = text.replace("??", "o'")
        text = text.replace("??", "???")
        text = text.replace("??", "A")
        text = text.replace("??", "a")
        text = text.replace("????", "aye")
        text = text.replace("??", "B")
        text = text.replace("??", "b")
        text = text.replace("??", "V")
        text = text.replace("??", "v")
        text = text.replace("??", "G")
        text = text.replace("??", "g")
        text = text.replace("??", "D")
        text = text.replace("??", "d")
        text = text.replace("??", "E")
        text = text.replace("??", "e")
        text = text.replace("??", "J")
        text = text.replace("??", "j")
        text = text.replace("??", "Z")
        text = text.replace("??", "z")
        text = text.replace("??", "I")
        text = text.replace("??", "i")
        text = text.replace("????", "iye")
        text = text.replace("??", "Y")
        text = text.replace("??", "y")
        text = text.replace("??", "K")
        text = text.replace("??", "k")
        text = text.replace("??", "L")
        text = text.replace("??", "l")
        text = text.replace("??", "M")
        text = text.replace("??", "m")
        text = text.replace("??", "N")
        text = text.replace("??", "n")
        text = text.replace("??", "O")
        text = text.replace("??", "o")
        text = text.replace("????", "oye")
        text = text.replace("??", "P")
        text = text.replace("??", "p")
        text = text.replace("??", "R")
        text = text.replace("??", "r")
        text = text.replace("??", "S")
        text = text.replace("??", "s")
        text = text.replace("??", "T")
        text = text.replace("??", "t")
        text = text.replace("??", "U")
        text = text.replace("??", "u")
        text = text.replace("????", "uye")
        text = text.replace("??", "F")
        text = text.replace("??", "f")
        text = text.replace("??", "X")
        text = text.replace("??", "x")
        text = text.replace("??", "C")
        text = text.replace("??", "c")
        text = text.replace("??", "E")
        text = text.replace("??", "e")
        text = text.replace("??", "H")
        text = text.replace("??", "h")
        text = text.replace("??", "Q")
        text = text.replace("??", "q")

        return text
    }

//    // Static initialization of mappings between cyrillic and latin letters.
//    init {
//        for (i in cyrilic.indices) {
//            cyrMapping[cyrilic[i]] =
//                latin[i]
//            latMapping[latin[i]] =
//                cyrilic[i]
//        }
//    }
}