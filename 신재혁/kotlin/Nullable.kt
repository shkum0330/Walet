fun main(args: Array<String>) {
    var myName : String = "Skywindragoon"
    var nullableName : String? = null
    println(myName.reversed())
//    println(nullableName.reversed())
    println(nullableName?.reversed())

    nullableName = "joyce"
//    nullableName = null
    nullableName?.let{nullableName = null}

    val joyce = nullableName?.reversed() ?: "Anonymous"
    println("joyce : $joyce")
    nullableName = "joyce"
    // 확정 연산자 !!
//    println(nullableName!!.reversed())
}