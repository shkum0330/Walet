fun main(args: Array<String>){
    val examResult = isPass(70)
    println("Exam Result : $examResult")

    val is3Contained = is3Contained(5 .. 10)
    println("Contains 3 : $is3Contained")

    val Grade = getGrade(59)
    println("MyGrade : $Grade")
}

fun isPass(examScore : Int) : Boolean {
//    if(examScore >= 60) return true;
//    return false;
    return examScore >= 60;
}

fun is3Contained(range : IntRange) : Boolean {
    return range.contains(3);
}
fun getGrade (score : Int) : Char {
    when(score){
        in 0 .. 49 -> return 'F'
        in 50 .. 59 -> return 'E'
        in 60 .. 69 -> return 'D'
        in 70 .. 79 -> return 'C'
        in 80 .. 89 -> return 'B'
        in 90 .. 100 -> return 'A'
        else -> return 'Z'
    }
}