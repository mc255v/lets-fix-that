package moods

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

@JsModule("src/img/happy.jpg")
external val happy: dynamic
@JsModule("src/img/sad.jpg")
external val sad: dynamic
@JsModule("src/img/bored.jpg")
external val bored: dynamic
@JsModule("src/img/tired.jpg")
external val tired: dynamic

fun RBuilder.moodList(onClick: (String, String) -> Unit) {
    section("projects-section bg-light") {
        div("project-text w-100 my-auto text-center"){
            h1("title"){+"How are you feeling today?"}
        }
        div("container") {
//            <!-- Happy Row -->
            div("mood row justify-content-center no-gutters mb-5 mb-lg-0") {
                attrs {
                    onClickFunction = {
                        onClick("change", "happy")
                    }
                }
                div("col-lg-6") {
                    img(alt = "Man Smiling", src = "$happy", classes = "img-fluid") {}
                }
                div("col-lg-6") {
                    div("bg-black text-center h-100 project") {
                        div("d-flex h-100") {
                            div("project-text w-100 my-auto text-center text-lg-left") {
                                h4("text-white") { +"Happy" }
                                p("mb-0 text-white-50") {
                                    +"enjoying or characterized by well-being and contentment"
                                }
                                hr("d-none d-lg-block mb-0 ml-0") {}
                            }
                        }
                    }
                }
            }

//            <!-- Sad Row -->
            div("mood row justify-content-center no-gutters") {
                attrs {
                    onClickFunction = {
                        onClick("change", "sad")
                    }
                }
                div("col-lg-6") {
                    img(alt = "Sad Woman", src = "$sad", classes = "img-fluid") {}
                }
                div("col-lg-6 order-lg-first") {
                    div("bg-black text-center h-100 project") {
                        div("d-flex h-100") {
                            div("project-text w-100 my-auto text-center text-lg-right") {
                                h4("text-white") { +"Sad" }
                                p("mb-0 text-white-50") {
                                    +"affected with or expressive of grief or unhappiness"
                                }
                                hr("d-none d-lg-block mb-0 mr-0") {}
                            }
                        }
                    }
                }
            }

            //            <!-- Bored Row -->
            div("mood row justify-content-center no-gutters mb-5 mb-lg-0") {
                attrs {
                    onClickFunction = {
                        onClick("change", "bored")
                    }
                }
                div("col-lg-6") {
                    img(alt = "Bored Dog", src = "$bored", classes = "img-fluid") {}
                }
                div("col-lg-6") {
                    div("bg-black text-center h-100 project") {
                        div("d-flex h-100") {
                            div("project-text w-100 my-auto text-center text-lg-left") {
                                h4("text-white") { +"Bored" }
                                p("mb-0 text-white-50") {
                                    +"the state of being weary and restless through lack of interest"
                                }
                                hr("d-none d-lg-block mb-0 ml-0") {}
                            }
                        }
                    }
                }
            }

            //            <!-- Tired Row -->
            div("mood row justify-content-center no-gutters") {
                attrs {
                    onClickFunction = {
                        onClick("change", "tired")
                    }
                }
                div("col-lg-6") {
                    img(alt = "Tired Kid", src = "$tired", classes = "img-fluid") {}
                }
                div("col-lg-6 order-lg-first") {
                    div("bg-black text-center h-100 project") {
                        div("d-flex h-100") {
                            div("project-text w-100 my-auto text-center text-lg-right") {
                                h4("text-white") { +"Tired" }
                                p("mb-0 text-white-50") {
                                    +"drained of strength and energy : fatigued often to the point of exhaustion"
                                }
                                hr("d-none d-lg-block mb-0 mr-0") {}
                            }
                        }
                    }
                }
            }
        }
    }
}
