package axios

import kotlinext.js.*
import kotlinx.html.*
import kotlinx.html.js.*
import org.w3c.dom.*
import react.*
import react.dom.*
import kotlin.js.*

@JsModule("axios")
external fun <T> axios(config: AxiosConfigSettings): Promise<AxiosResponse<T>>

external interface AxiosConfigSettings {
    var url: String
    var method: String
    var baseUrl: String
    var timeout: Number
    var data: dynamic
    var transferRequest: dynamic
    var transferResponse: dynamic
    var headers: dynamic
    var params: dynamic
    var withCredentials: Boolean
    var adapter: dynamic
    var auth: dynamic
    var responseType: String
    var xsrfCookieName: String
    var xsrfHeaderName: String
    var onUploadProgress: dynamic
    var onDownloadProgress: dynamic
    var maxContentLength: Number
    var validateStatus: (Number) -> Boolean
    var maxRedirects: Number
    var httpAgent: dynamic
    var httpsAgent: dynamic
    var proxy: dynamic
    var cancelToken: dynamic
}

external interface AxiosResponse<T> {
    val data: T
    val status: Number
    val statusText: String
    val headers: dynamic
    val config: AxiosConfigSettings
}

interface AxiosProps : RProps {
    var mood: String
    var onClick: (String, String) -> Unit
}

interface AxiosState : RState {
    var giphyResult: ArrayList<String>
    var errorMessage: String
    var count: Int
    var isLoading: Boolean
}

data class Giphy(val images: Images)
data class Images(val original: ImagesUrl)
data class ImagesUrl(val url: String)

external interface GiphyData {
    val data: Array<Giphy>
}

class AxiosSearch(props: AxiosProps) : RComponent<AxiosProps, AxiosState>(props) {
    override fun AxiosState.init(props: AxiosProps) {
        giphyResult = arrayListOf()
        errorMessage = ""
        count = 0
        isLoading = true
    }

    override fun componentDidMount() {
        var searchTerm: String = "oops"
        when(props.mood) {
            "happy" -> searchTerm = "middle%20finger"
            "sad" -> searchTerm = "you're%20awesome"
            "bored" -> searchTerm = "adventure"
            "tired" -> searchTerm = "energy"
        }
        searchGiphy(searchTerm)
    }

    private fun searchGiphy(phrase: String) {
        val config: AxiosConfigSettings = jsObject {
            //enter url
            timeout = 3000
        }

        axios<GiphyData>(config).then { response ->
            val result: ArrayList<String> = ArrayList()
            for (item in response.data.data) {
                result.add(item.images.original.url)
                console.log(item.images.original.url)
            }
            setState {
                giphyResult = result
                errorMessage = ""
                isLoading = false
            }
            console.log(result)
            console.log(response.data.data)
        }.catch { error ->
            setState {
                errorMessage = error.message ?: ""
            }
            console.log(error)
        }
    }

    private fun anotherGif() {
        if (state.count < 9) setState{
            count += 1
        } else {
            setState {
                count = 0
            }
        }
    }

    override fun RBuilder.render() {

//        <!-- Signup Section -->
        section("signup-section") {
            div("container") {
                div("row") {
                    div("col-md-10 col-lg-10 mx-auto text-center") {
                        div("change-title") {
                            h1("text-white mb-5") { +"So you are feeling ${props.mood}?" }
                        }
                        div {
                            if (!state.isLoading) img(alt = "animated gif", src = state.giphyResult[state.count], classes = "giphy"){}
                        }
                    }
                }
            }
        }

//        <!-- Contact Section -->
        section("contact-section bg-black") {
            div("container") {
                div("row justify-content-center") {
                    div("change-btn col-md-4 mb-3 mb-md-0") {
                        attrs {
                            onClickFunction = {
                                anotherGif()
                            }
                        }
                        div("card py-4 h-100") {
                            div("card-body text-center") {
                                i("fas fa-map-marked-alt text-primary mb-2") {}
                                h4("text-uppercase m-0") { +"Nope, still ${props.mood}" }
                                hr("my-4") {}
                                div("small text-black-50") {
                                    +"Maybe another"
                                }
                            }
                        }
                    }
                    div("change-btn col-md-4 mb-3 mb-md-0") {
                        attrs {
                            onClickFunction = {
                                props.onClick("home", "")
                            }
                        }
                        div("card py-4 h-100") {
                            div("card-body text-center") {
                                i("fas fa-envelope text-primary mb-2") {}
                                h4("text-uppercase m-0") { +"Thanks! You fixed it!" }
                                hr("my-4") {}
                                div("small text-black-50") {
                                    when(props.mood) {
                                        "happy" -> +"Now I feel like shit"
                                        "sad" -> +"Now I'm happy as can be!"
                                        "bored" -> +"Bored no more!"
                                        "tired" -> +"The energy flows through me!"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.axiosSearch(mood: String, onClick: (String, String) -> Unit) = child(AxiosSearch::class) {
    attrs.mood = mood
    attrs.onClick = onClick
}