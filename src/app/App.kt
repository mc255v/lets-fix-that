package app

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import moods.*
import axios.*

interface AppState : RState {
    var currentView: String
    var currentMood: String
}

class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
        currentView = "home"
        currentMood = ""
    }

    fun onClick(view: String, mood: String) {
        setState {
            currentView = view
            currentMood = mood
        }
    }

    override fun RBuilder.render() {
        when (state.currentView) {
            "home" -> header("masthead") {
                    div("container d-flex h-100 align-items-center") {
                        div("mx-auto text-center") {
                            h1("mx-auto my-0 text-uppercase") { +"Let's Fix That" }
                            h2("text-white-50 mx-auto mt-2 mb-5") { +"Life got you up, down, all around? We can help." }
                            button(classes = "btn btn-primary js-scroll-trigger") {
                                +"Get Started"
                                attrs {
                                    onClickFunction = {
                                        onClick("moodList", "")
                                    }
                                }
                            }
                        }
                    }
                }
            "moodList" -> moodList(::onClick)
            "change" -> axiosSearch(state.currentMood, ::onClick)
        }
    }
}

fun RBuilder.app() = child(App::class) {}