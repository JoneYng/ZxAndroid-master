package com.zx.lib.net.utils


import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.internal.Util
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.Buffer
import okio.BufferedSource
import java.io.InputStream
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Describe:
 * <p>MoShi Json格式化工具</p>
 *
 * @author zhou
 * @Date 2020/12/1
 */
object MoshiUtils {
    // val moshiBuild = Moshi.Builder().build()
    //使用Kotlin-Reflect包时，这里改一下:
    // val moshiBuild: Moshi = Moshi.Builder().add(MyKotlinJsonAdapterFactory()).build()
    val moshiBuild: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    //普通序列化
    fun <T> fromJson(json: String, type: Type): T? = getAdapter<T>(type).fromJson(json)
    fun <T> fromJson(buffer: BufferedSource, type: Type): T? = getAdapter<T>(type).fromJson(buffer)
    fun <T> fromJson(`is`: InputStream, type: Type): T? =
        getAdapter<T>(type).fromJson(Buffer().readFrom(`is`))

    fun <T> fromJson(reader: JsonReader, type: Type): T? = getAdapter<T>(type).fromJson(reader)

    //自动获取type序列化,性能较差
    inline fun <reified T : Any> fromJson(json: String): T? = getAdapter<T>().fromJson(json)
    inline fun <reified T> fromJson(buffer: BufferedSource): T? = getAdapter<T>().fromJson(buffer)
    inline fun <reified T> fromJson(`is`: InputStream): T? =
        getAdapter<T>().fromJson(Buffer().readFrom(`is`))

    inline fun <reified T> fromJson(reader: JsonReader): T? = getAdapter<T>().fromJson(reader)

    //高效序列化为list
    inline fun <reified T> listFromJson(json: String): MutableList<T> =
        fromJson(json, Types.newParameterizedType(MutableList::class.java, T::class.java))
            ?: mutableListOf()

    inline fun <reified T> listFromJson(buffer: BufferedSource): MutableList<T> =
        fromJson(buffer, Types.newParameterizedType(MutableList::class.java, T::class.java))
            ?: mutableListOf()

    inline fun <reified T> listFromJson(`is`: InputStream): MutableList<T> =
        fromJson(`is`, Types.newParameterizedType(MutableList::class.java, T::class.java))
            ?: mutableListOf()

    inline fun <reified T> listFromJson(reader: JsonReader): MutableList<T> =
        fromJson(reader, Types.newParameterizedType(MutableList::class.java, T::class.java))
            ?: mutableListOf()


    //高效序列化为map
    inline fun <reified K, reified V> mapFromJson(json: String): MutableMap<K, V> = fromJson(
        json,
        Types.newParameterizedType(MutableMap::class.java, K::class.java, V::class.java)
    ) ?: mutableMapOf()

    inline fun <reified K, reified V> mapFromJson(buffer: BufferedSource): MutableMap<K, V> =
        fromJson(
            buffer,
            Types.newParameterizedType(MutableMap::class.java, K::class.java, V::class.java)
        ) ?: mutableMapOf()

    inline fun <reified K, reified V> mapFromJson(`is`: InputStream): MutableMap<K, V> = fromJson(
        `is`,
        Types.newParameterizedType(MutableMap::class.java, K::class.java, V::class.java)
    ) ?: mutableMapOf()

    inline fun <reified K, reified V> mapFromJson(reader: JsonReader): MutableMap<K, V> = fromJson(
        reader,
        Types.newParameterizedType(MutableMap::class.java, K::class.java, V::class.java)
    ) ?: mutableMapOf()

    //反序列化
    inline fun <reified T> toJson(t: T) = getAdapter<T>().toJson(t) ?: ""

    private fun <T> getAdapter(type: Type): JsonAdapter<T> = moshiBuild.adapter(type)

    inline fun <reified T> getAdapter(): JsonAdapter<T> =
        moshiBuild.adapter(object : TypeToken<T>() {}.type)

}

abstract class TypeToken<T> {
    val type: Type
        get() = run {
            val superclass = javaClass.genericSuperclass
            Util.canonicalize((superclass as ParameterizedType).actualTypeArguments[0])
        }
}


//快捷序列化
inline fun <reified T : Any> String.fromJson() = MoshiUtils.fromJson<T>(this)

//快捷反序列化
fun Any.toJson() = MoshiUtils.toJson(this)


/**
使用方法：
//实体类
@JsonClass(generateAdapter = true)
data class Person(val name: String = "Carlson", val age: Int? = 0)

//json
{"name":"Carlson","age":56}

//实体类转json
val person = Person("Carlson",56)
val json = person.toJson()

//json转实体类
val json = """{"name":"Carlson","age":56}"""
val person = json.fromJson<Person>()

val listJson = """[{"name":"Carlson","age":56},{"name":"Carlson","age":56},{"name":"Carlson","age":56}]"""

//三种方法都可以
val list1 = listJson.fromJson<List<Person>>()
val list2 = MoshiUtils.fromJson<List<Person>>(listJson,Types.newParameterizedType(List::class.java,Person::class.java))
val list3 = MoshiUtils.listFromJson<Person>(listJson)

//list转json
val json = list?.toJson()
 */