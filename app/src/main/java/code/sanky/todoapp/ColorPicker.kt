package code.sanky.todoapp

object ColorPicker {
    val color = arrayOf(
        "#3EB9DF",
        "#3685BC",
        "#D36280",
        "#E44F55",
        "#FA8056",
        "#818BCA",
        "#7D659F",
        "#51BAB3",
        "#4FB66C",
        "#E3AD17",
        "#EF8EAD",
        "#B5BFC6"
    )
    var currentColorIndex = 0

    fun getColor(): String {
        currentColorIndex = (currentColorIndex + 1) % color.size
        return color[currentColorIndex]
    }
}