fun main(args: Array<String>) {
    val name = "Joyce"

    // lateinit -> var

    lateinit var lunch : String // Nullable엔 할 수 없고 val에만 가능하다 원시자료형이 아닌거에만 사용 가능

    lunch = "waffle"
    println(lunch)

    // lazy -> val
    val lazyBear : String by lazy {
        println("Bear is coming!")
        "bear"
    }
    println("1")
    // 처음으로 부를 때 초기화됨
    println(lazyBear)
    println("2")
    println(lazyBear)
}

