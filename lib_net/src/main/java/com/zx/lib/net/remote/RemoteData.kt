package com.zx.lib.net.remote

import android.text.TextUtils
import com.zx.lib.domain.base.BaseResponse
import com.zx.lib.domain.entity.Demo
import com.zx.lib.log.KLog
import com.zx.lib.net.BuildConfig
import com.zx.lib.net.config.NetAppContext
import com.zx.lib.net.dto.Resource
import com.zx.lib.net.error.*
import com.zx.lib.net.error.mapper.ErrorManager
import com.zx.lib.net.utils.ThreadUtils
import com.zx.lib.net.error.mapper.ErrorMapper
import com.zx.lib.net.remote.service.RecipesService
import com.zx.lib.net.utils.NetworkConnectivity
import com.zx.lib.net.utils.ext.view.showToast
import retrofit2.Response
import retrofit2.Response.error
import java.io.IOException


/**
 * 服务端数据提供者实现
 * @author zhou
 * @time 2020/12/1 0:08
 */
class RemoteData
constructor(
    private val retrofitManager: RetrofitManager,
    private val networkConnectivity: NetworkConnectivity
) : RemoteDataSource {
    private val errorManager by lazy { ErrorManager(ErrorMapper()) }

    override suspend fun requestRecipes(): Resource<List<Demo>> {
        //创建接口服务
        val recipesService = retrofitManager.create<RecipesService>()

        return dealDataWhen(processCall(recipesService::fetchRecipes))
    }


    /**
     * 数据结构体的返回处理
     */
    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            //若当前客户端未打开数据连接开关
            return showToast(NOT_NETWORD)
        }
        return try {
            val response = responseCall.invoke()
            if (response.code() in SUCCESS until UNAUTHORIZED) {
                response.body()
            } else {
                when (response.code()) {
                    UNAUTHORIZED -> showToast(UNAUTHORIZED)
                    FORBIDDEN -> showToast(FORBIDDEN)
                    NOT_FOUND -> showToast(NOT_FOUND)
                    REQUEST_TIMEOUT -> showToast(REQUEST_TIMEOUT)
                    INTERNAL_SERVER_ERROR -> showToast(INTERNAL_SERVER_ERROR)
                    SERVICE_UNAVAILABLE -> showToast(SERVICE_UNAVAILABLE)
                    else -> showToast(UNKNOWN)
                }
            }
        } catch (e: IOException) {
            if (com.zx.lib.net.BuildConfig.DEBUG) {
                com.zx.lib.net.utils.ThreadUtils.runOnUiThread {
                    e.message?.showToast()
                }
                KLog.e("RemoteData", e)
            }
            showToast(NETWORD_ERROR)
        }
    }

    /**
     * 处理相应结果
     */
    private inline fun <reified T> dealDataWhen(any: Any?): Resource<T> {
        return when (any) {
            is BaseResponse<*> -> {
                Resource.Success(data = toAs(if (any.data != null) any.data else any.msg))
            }
            else -> {
                Resource.DataError(errorCode = toAs(any))
            }
        }
    }

    /**
     * 类型转换
     */
    private inline fun <reified T> toAs(obj: Any?): T {
        return obj as T
    }

    /**
     * 错误吐司
     */
    private fun showToast(code: Int, msg: String? = ""): Int {
        com.zx.lib.net.utils.ThreadUtils.runOnUiThread {
            if (!TextUtils.isEmpty(msg)) msg?.showToast(NetAppContext.getContext())
            else errorManager.getError(code).description.showToast(NetAppContext.getContext())
        }
        return code
    }
}
