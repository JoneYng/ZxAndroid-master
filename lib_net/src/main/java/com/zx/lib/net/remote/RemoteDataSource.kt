package com.zx.lib.net.remote

import com.zx.lib.domain.entity.Demo
import com.zx.lib.net.dto.Resource

/**
 * 服务端所有提供数据方法
 * @author zhou
 * @time 2020/12/1 0:06
 */
internal interface RemoteDataSource {
    suspend fun requestRecipes(): Resource<List<Demo>>
}
