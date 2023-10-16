fun main(args: Array<String>){
    val memo = Memo("Go to grocery" ,"Eggs Milk Pork", false)

    println(memo.toString())
    println(memo.title)
    val memo2 = memo.copy("Go to Home")
    println(memo2.toString())
}
data class Memo(val title : String, val content : String, var isDone : Boolean)