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
    val mood: String
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
//        var searchTerm: String = "happy"
//        when(props.mood) {
//            "happy" -> searchTerm = "middle%20finger"
//            "sad" -> searchTerm = "you're%20awesome"
//            "bored" -> searchTerm = "adventure"
//            "tired" -> searchTerm = "energy"
//        }
//        searchGiphy(searchTerm)
    }

    private fun searchGiphy(phrase: String) {
        val config: AxiosConfigSettings = jsObject {
            // enter url
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

    private fun handleChange() {
        searchGiphy("happy")
    }

    override fun RBuilder.render() {
        div {
            p { +"infoText" }
            button {
                attrs {
                    onClickFunction = {
                        handleChange()
                    }
                }
            }
            br {}
            if (!state.isLoading) img(alt = "animated gif", src = state.giphyResult[state.count]){}
        }
    }
}

fun RBuilder.axiosSearch(mood: String) = child(AxiosSearch::class) {
}