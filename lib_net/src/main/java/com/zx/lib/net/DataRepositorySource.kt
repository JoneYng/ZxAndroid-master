package com.zx.lib.net

import com.zx.lib.domain.entity.Demo
import com.zx.lib.net.dto.Resource
import com.zx.lib.net.local.entity.UserTestRoom
import kotlinx.coroutines.flow.Flow


/**
 *
 * @author zhou
 * @time 2020/12/1 0:21
 */
interface DataRepositorySource {
    suspend fun requestRecipes(): Flow<Resource<List<Demo>>>
    suspend fun doLogin(): Flow<Resource<String>>
    suspend fun removeUserTestRoom(userTestRoom: UserTestRoom): Flow<Resource<Int>>
    suspend fun insertUserTestRoom(userTestRoom: UserTestRoom):Flow<Resource<Long>>
    suspend fun getAllUserTestRoom(): Flow<Resource<List<UserTestRoom>>>
}
