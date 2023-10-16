fun main(args : Array<String>){
    val korea = Korea("Republic of Korea", "Seoul", "한국어")
    val usa = USA("United States of America", "Seoul", "한국어")
    korea.singNationalAnthem()
    usa.singNationalAnthem()
}

open class Country(val fullName : String, val capital : String, val language : String ){
    fun printFullName(){
        println("Full name : $fullName");
    }
    fun printCapital(){
        println("Capital : $capital");
    }
    fun printLanguage(){
        println("Language : $language");
    }
    open fun singNationalAnthem(){
        println("Starts singing");
    }
}
class Korea(fullName : String, capital : String, language : String) : Country(fullName, capital, language) {
    override fun singNationalAnthem(){
        super.singNationalAnthem()
        println("Sings Korean national anthem");
    }
}

class USA(fullName : String, capital : String, language : String) : Country(fullName, capital, language){

    override fun singNationalAnthem(){
        println("Sings American national anthem");
    }
}