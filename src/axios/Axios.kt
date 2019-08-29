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
}

interface AxiosState : RState {
    var mood: String
    var giphyResult: ArrayList<String>
    var errorMessage: String
}

data class Giphy(val url: String)

external interface GiphyData {
    val data: Array<Giphy>
}

class AxiosSearch(props: AxiosProps) : RComponent<AxiosProps, AxiosState>(props) {
    override fun AxiosState.init(props: AxiosProps) {
        mood = ""
        giphyResult = arrayListOf()
        errorMessage = ""
    }

    private fun searchGiphy(phrase: String) {
        val config: AxiosConfigSettings = jsObject {
            //add url
            timeout = 3000
        }

        axios<GiphyData>(config).then { response ->
            val result: ArrayList<String> = ArrayList()
            for (item in response.data.data) {
                result.add(item.url)
                console.log(item.url)
            }
            setState {
                giphyResult = result
                errorMessage = ""
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
        val infoText = "Enter a valid US ZIP code below"
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
//            h1 {
//                +"${state.zipCode} ZIP code belongs to: "
//                +"${state.zipResult.country} ${state.zipResult.state} ${state.zipResult.city} "
//                if (!state.errorMessage.isEmpty()) div {
//                    attrs.jsStyle = js {
//                        color = "red"
//                    }
//                    +"Error while searching for ZIP code: "
//                    +state.errorMessage
//                }
//            }
        }
    }
}

fun RBuilder.axiosSearch() = child(AxiosSearch::class) {
}