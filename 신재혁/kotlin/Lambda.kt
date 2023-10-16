fun main(args: Array<String>) {
    // 람다식 : 값 처럼 다룰 수 있는 익명 함수
    val sayHello = fun() {println("Hello")}

    sayHello()

    fun multiply(num: Int, num2 : Int) : Int{
        return num * num2
    }

    val squareNum : (Int) -> (Int) = {num -> num * num}

    val squareNum2 = {num : Int -> num * num}

    val squareNum3 : (Int) -> (Int) = {it * it}

    // 함수의 인자는 Int -> Boolean인 익명함수
    fun invokeLambda(lambda : (Int) -> Boolean) : Boolean {
        return lambda(5)
    }

    println("10 is equal to 5? : ${invokeLambda({num -> num == 10})}")
    println("10 is smaller than 5? : ${invokeLambda({num -> num < 10})}")

    invokeLambda({it < 10})
    invokeLambda(){it < 10}
    invokeLambda{it < 10}

    println(squareNum(7))
    println(squareNum2(10))
    println(squareNum3(3))


}