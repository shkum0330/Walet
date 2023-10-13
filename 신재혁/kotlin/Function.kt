fun main(args: Array<String>){
    printStudentInfo("Ranranru", 99)

    println(addNum(100, 16))
}

fun printStudentInfo(name : String, age : Int) {
    println("Student Name $name")
    println("Student Age $age")
}

fun addNum(n1 : Int, n2 : Int) : Int {
    return n1 + n2
}