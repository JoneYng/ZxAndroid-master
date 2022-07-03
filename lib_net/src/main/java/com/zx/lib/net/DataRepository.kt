package com.zx.lib.net

import com.zx.lib.domain.entity.Demo
import com.zx.lib.net.config.NetAppContext
import com.zx.lib.net.dto.Resource
import com.zx.lib.net.local.LocalData
import com.zx.lib.net.local.entity.UserTestRoom
import com.zx.lib.net.remote.RemoteData
import com.zx.lib.net.remote.RetrofitManager
import com.zx.lib.net.utils.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext


/**
 * 数据仓库进行分发
 * * 服务端
 * * 本地
 * @author zhou
 * @time 2020/12/1 0:21
 */
class DataRepository constructor(
    private val remoteRepository: RemoteData = RemoteData(
        RetrofitManager(), NetworkHelper(NetAppContext.getContext())
    ),
    private val localRepository: LocalData = LocalData(),
    private val ioDispatcher: CoroutineContext = Dispatchers.IO
) :
    DataRepositorySource {

    override suspend fun requestRecipes(): Flow<Resource<List<Demo>>> {
        return dealDataFlow { remoteRepository.requestRecipes() }
    }

    override suspend fun doLogin(): Flow<Resource<String>> {
        return dealDataFlow { localRepository.doLogin() }
    }

    override suspend fun removeUserTestRoom(userTestRoom: UserTestRoom): Flow<Resource<Int>> {
        return dealDataFlow { localRepository.removeUserTestRoom(userTestRoom) }
    }

    override suspend fun insertUserTestRoom(userTestRoom: UserTestRoom): Flow<Resource<Long>> {
        return dealDataFlow { localRepository.insertUserTestRoom(userTestRoom) }
    }

    override suspend fun getAllUserTestRoom(): Flow<Resource<List<UserTestRoom>>> {
        return dealDataFlow { localRepository.getUserTestRoom() }
    }

    private inline fun <reified T> dealDataFlow(crossinline block: suspend () -> T): Flow<T> {
        return flow {
            emit(block.invoke())
        }.flowOn(ioDispatcher)
    }
}
