package debug

import com.zx.lib.base.module.ModuleApplication
import com.zx.lib.common.URL_BASE
import com.zx.lib.net.config.NetConfig

/**
 * Description: <><br>
 * Author:      mxdl<br>
 * Date:        2018/12/27<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
class MainApplication : ModuleApplication() {
    override fun onCreate() {
        super.onCreate()
        initNet()
    }

    private fun initNet() {
        val config = NetConfig.Builder()
            .setBaseUrl(URL_BASE)
            .build()
        config.initContext(this)
    }
}
