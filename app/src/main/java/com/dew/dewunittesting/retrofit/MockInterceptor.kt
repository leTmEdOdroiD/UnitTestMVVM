package com.dew.dewunittesting.retrofit

import com.dew.dewunittesting.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith("movielist") -> getMockResponse()
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }

    private fun getMockResponse():String{
        return """[{"name":"Adipurush","category":"drama","imageUrl":"https://akm-img-a-in.tosshub.com/indiatoday/images/story/202305/adipurush-1200-sixteen_nine.jpg?VersionId=CkZUfdCmzHdh8lxweTkm3lF3Gw_gU.pZ&size=690:388"}]"""
    }
}

