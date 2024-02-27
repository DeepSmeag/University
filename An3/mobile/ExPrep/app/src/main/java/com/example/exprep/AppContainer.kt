package com.example.exprep

import com.example.exprep.services.Api
import com.example.exprep.services.WSClient

class AppContainer  {
//    private val iService:
    private val wsClient: WSClient = WSClient(Api.okHttpClient)





}