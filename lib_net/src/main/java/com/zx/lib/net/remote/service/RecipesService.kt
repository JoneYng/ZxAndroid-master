package com.zx.lib.net.remote.service

import com.zx.lib.domain.base.BaseResponse
import com.zx.lib.domain.entity.Demo
import retrofit2.Response
import retrofit2.http.GET

/**
 * 服务端提供数据接口方法
 * @author zhou
 * @time 2020/11/30
 */
interface RecipesService {
    @GET("recipes.json")
    suspend fun fetchRecipes(): Response<BaseResponse<List<Demo>>>
}