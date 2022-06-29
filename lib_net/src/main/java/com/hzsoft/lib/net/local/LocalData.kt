package com.hzsoft.lib.net.local

import com.hzsoft.lib.net.config.NetAppContext
import com.hzsoft.lib.net.dto.Resource
import com.hzsoft.lib.net.local.entity.UserTestRoom

/**
 * 本地数据
 * @author zhou
 * @time 2020/11/30
 */
class LocalData constructor() {

    private val appDatabase by lazy { AppDatabase.getDatabase(NetAppContext.getContext()) }

    fun getUserTestRoom(): Resource<List<UserTestRoom>> {
        return Resource.Success(appDatabase.userTestRoomDao().loadAllUserTestRooms())
    }

    fun insertUserTestRoom(userTestRoom: UserTestRoom): Resource<Long> {
        return Resource.Success(
            appDatabase.userTestRoomDao().insertUserTestRoom(userTestRoom = userTestRoom)
        )
    }

    fun doLogin(): Resource<String> {
        return Resource.Success("String")
    }

    fun removeUserTestRoom(userTestRoom: UserTestRoom): Resource<Int> {
        return Resource.Success(appDatabase.userTestRoomDao().deleteUserTestRoom(userTestRoom))
    }
}
