import java.io.InvalidObjectException

// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)

enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

class Usuario (val nome: String, val age: Int, var exp: Double = 0.0, val finishedCourses: MutableSet<String> = mutableSetOf()) {
    private var level: Int = 0

    private fun updateLevel() {
        level = (exp / 20).toInt();
    }

    fun level(): String {
        updateLevel();
        return "$nome's level: $level";
    }

    fun info(): String {
        updateLevel();
        var courses = "";
        for(course in finishedCourses) {
            courses += "\n$course";
        }
        return "Name: $nome\nAge: $age\nCurrent level: $level\nFinished courses:$courses\n"
    }

    fun increaseXP(gain: Double): Unit {
        exp += gain;
    }
}

data class ConteudoEducacional(var nome: String, val duracao: Int = 60, val nivel: Nivel)

data class Formacao(val nome: String, var conteudos: List<ConteudoEducacional>) {

    val inscritos = mutableListOf<Usuario>()

    fun matricular(usuario: Usuario) {
        if(inscritos.contains(usuario)) {
            throw InvalidObjectException("This user is already registered in this course!");
        }
        inscritos.add(usuario)
    }

    fun finishCourse(usuario: Usuario) {
        if(!inscritos.contains(usuario)) throw InvalidObjectException("User not registered!");
        for(conteudo in conteudos) {
            if(usuario.finishedCourses.contains(conteudo.nome)) continue
            when (conteudo.nivel) {
                Nivel.BASICO -> usuario.increaseXP(conteudo.duracao * 0.06)
                Nivel.INTERMEDIARIO -> usuario.increaseXP(conteudo.duracao * 0.12)
                Nivel.DIFICIL -> usuario.increaseXP(conteudo.duracao * 0.2)
            }
            usuario.finishedCourses.add(conteudo.nome);
        }
    }
}

fun main() {
    val conteudo1 = ConteudoEducacional("Java POO", 320, Nivel.BASICO);
    val conteudo2 = ConteudoEducacional("Unreal Engine 5, 2.5D Development", 1020, Nivel.DIFICIL);
    val conteudo3 = ConteudoEducacional("Kotlin", 400, Nivel.INTERMEDIARIO);

    val user1 = Usuario("Fulano de Tal", 27);
    val user2 = Usuario("Cicrano", 21);

    val courses1 = listOf(conteudo1, conteudo3);
    val courses2 = listOf(conteudo2);

    val formacao1 = Formacao("Mobile Development", courses1);
    val formacao2 = Formacao("Game Development", courses2);

    formacao1.matricular(user1);
    formacao2.matricular(user2);

    println(user1.info());
    println(user2.info());

    formacao1.finishCourse(user1);
    formacao2.finishCourse(user2);

    println(user1.info());
    println(user2.info());

    println(user1.level());
    println(user2.level());
}
