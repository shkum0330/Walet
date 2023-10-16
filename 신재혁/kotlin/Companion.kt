fun main(args: Array<String>){
    // Object는 생성자 필요 없이 그냥 쓰면 됨
    println(MyFirstObject.number)
    MyFirstObject.sayHello()

    println(Dinner.MENU)
    Dinner.eatDinner()

    println(Dinner.MyCompanionObject.MENU)
}

object MyFirstObject {
    val number = 1
    fun sayHello(){
        println("Hello")
    }
}
class Dinner {
    // companion object는 클래스당 하나
    companion object MyCompanionObject{
        val MENU = "pasta"
        fun eatDinner(){
            println("Today's Menu is $MENU")
        }
    }
}